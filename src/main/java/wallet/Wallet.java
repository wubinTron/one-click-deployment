package wallet;

import static java.util.Arrays.copyOfRange;
import static org.tron.common.utils.ByteArray.toHexString;
import static org.tron.core.Wallet.getAddressPreFixByte;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.ArrayUtils;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.generators.SCrypt;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.math.ec.ECPoint;
import org.springframework.util.StringUtils;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Hash;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Sha256Hash;
import org.tron.common.utils.Utils;
import org.tron.keystore.CipherException;

/**
 * <p>Ethereum wallet file management. For reference, refer to <a href="https://github.com/ethereum/wiki/wiki/Web3-Secret-Storage-Definition">
 * Web3 Secret Storage Definition</a> or the <a href="https://github.com/ethereum/go-ethereum/blob/master/accounts/key_store_passphrase.go">
 * Go Ethereum client implementation</a>.</p>
 *
 * <p><strong>Note:</strong> the Bouncy Castle Scrypt implementation {@link SCrypt}, fails to comply
 * with the following Ethereum reference <a href="https://github.com/ethereum/wiki/wiki/Web3-Secret-Storage-Definition#scrypt">
 * Scrypt test vector</a>:</p>
 *
 * <pre>
 * {@code
 * // Only value of r that cost (as an int) could be exceeded for is 1
 * if (r == 1 && N_STANDARD > 65536)
 * {
 *     throw new IllegalArgumentException("Cost parameter N_STANDARD must be > 1 and < 65536.");
 * }
 * }
 * </pre>
 */
public class Wallet {

  private static final int N_LIGHT = 1 << 12;
  private static final int P_LIGHT = 6;

  private static final int N_STANDARD = 1 << 18;
  private static final int P_STANDARD = 1;

  private static final int R = 8;
  private static final int DKLEN = 32;

  private static final int CURRENT_VERSION = 3;

  private static final String CIPHER = "aes-128-ctr";
  static final String AES_128_CTR = "pbkdf2";
  static final String SCRYPT = "scrypt";
  private static final String FilePath = "Wallet";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static WalletFile create(byte[] password, ECKey ecKeyPair, int n, int p)
      throws CipherException {

    byte[] salt = generateRandomBytes(32);

    byte[] derivedKey = generateDerivedScryptKey(password, salt, n, R, p, DKLEN);

    byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
    byte[] iv = generateRandomBytes(16);

    byte[] privateKeyBytes = ecKeyPair.getPrivKeyBytes();

    byte[] cipherText = performCipherOperation(Cipher.ENCRYPT_MODE, iv, encryptKey,
        privateKeyBytes);

    byte[] mac = generateMac(derivedKey, cipherText);

    return createWalletFile(ecKeyPair, cipherText, iv, salt, mac, n, p);
  }

  private static byte[] generateDerivedScryptKey(
      byte[] password, byte[] salt, int n, int r, int p, int dkLen) throws CipherException {
    return SCrypt.generate(password, salt, n, r, p, dkLen);
  }

  public static WalletFile createStandard(byte[] password, ECKey ecKeyPair)
      throws CipherException {
    return create(password, ecKeyPair, N_STANDARD, P_STANDARD);
  }

  public static WalletFile createLight(byte[] password, ECKey ecKeyPair)
      throws CipherException {
    return create(password, ecKeyPair, N_LIGHT, P_LIGHT);
  }

  public static String store2Keystore(WalletFile walletFile) throws IOException {
    if (walletFile == null) {
      return null;
    }
    File file = new File(FilePath);
    if (!file.exists()) {
      if (!file.mkdir()) {
        throw new IOException("Make directory failed!");
      }
    } else {
      if (!file.isDirectory()) {
        if (file.delete()) {
          if (!file.mkdir()) {
            throw new IOException("Make directory failed!");
          }
        } else {
          throw new IOException("File exists and can not be deleted!");
        }
      }
    }
    return generateWalletFile(walletFile, file);
  }

  private static String generateWalletFile(WalletFile walletFile, File destinationDirectory)
      throws IOException {
    String fileName = getWalletFileName(walletFile);
    File destination = new File(destinationDirectory, fileName);

    objectMapper.writeValue(destination, walletFile);
    return fileName;
  }

  private static String getWalletFileName(WalletFile walletFile) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern(
        "'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    return now.format(format) + walletFile.getAddress() + ".json";
  }

  private static String encode58Check(byte[] input) {
    byte[] hash0 = Sha256Hash.hash(input);
    byte[] hash1 = Sha256Hash.hash(hash0);
    byte[] inputCheck = new byte[input.length + 4];
    System.arraycopy(input, 0, inputCheck, 0, input.length);
    System.arraycopy(hash1, 0, inputCheck, input.length, 4);
    return Base58.encode(inputCheck);
  }

  private static WalletFile createWalletFile(
      ECKey ecKeyPair, byte[] cipherText, byte[] iv, byte[] salt, byte[] mac,
      int n, int p) {

    WalletFile walletFile = new WalletFile();
    walletFile.setAddress(encode58Check(ecKeyPair.getAddress()));

    WalletFile.Crypto crypto = new WalletFile.Crypto();
    crypto.setCipher(CIPHER);
    crypto.setCiphertext(toHexString(cipherText));
    walletFile.setCrypto(crypto);

    WalletFile.CipherParams cipherParams = new WalletFile.CipherParams();
    cipherParams.setIv(toHexString(iv));
    crypto.setCipherparams(cipherParams);

    crypto.setKdf(SCRYPT);
    WalletFile.ScryptKdfParams kdfParams = new WalletFile.ScryptKdfParams();
    kdfParams.setDklen(DKLEN);
    kdfParams.setN(n);
    kdfParams.setP(p);
    kdfParams.setR(R);
    kdfParams.setSalt(toHexString(salt));
    crypto.setKdfparams(kdfParams);

    crypto.setMac(toHexString(mac));
    walletFile.setCrypto(crypto);
    walletFile.setId(UUID.randomUUID().toString());
    walletFile.setVersion(CURRENT_VERSION);

    return walletFile;
  }

  private static byte[] performCipherOperation(
      int mode, byte[] iv, byte[] encryptKey, byte[] text) throws CipherException {

    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

      SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
      cipher.init(mode, secretKeySpec, ivParameterSpec);
      return cipher.doFinal(text);
    } catch (NoSuchPaddingException | NoSuchAlgorithmException
        | InvalidAlgorithmParameterException | InvalidKeyException
        | BadPaddingException | IllegalBlockSizeException e) {
      throw new CipherException("Error performing cipher operation", e);
    }
  }

  private static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
    byte[] result = new byte[16 + cipherText.length];

    System.arraycopy(derivedKey, 16, result, 0, 16);
    System.arraycopy(cipherText, 0, result, 16, cipherText.length);

    return Hash.sha3(result);
  }

  static void validate(WalletFile walletFile) throws CipherException {
    WalletFile.Crypto crypto = walletFile.getCrypto();

    if (walletFile.getVersion() != CURRENT_VERSION) {
      throw new CipherException("Wallet version is not supported");
    }

    if (!crypto.getCipher().equals(CIPHER)) {
      throw new CipherException("Wallet cipher is not supported");
    }

    if (!crypto.getKdf().equals(AES_128_CTR) && !crypto.getKdf().equals(SCRYPT)) {
      throw new CipherException("KDF type is not supported");
    }
  }

  static byte[] generateRandomBytes(int size) {
    byte[] bytes = new byte[size];
    new SecureRandom().nextBytes(bytes);
    return bytes;
  }

  static public byte[] getPrivateBytes(String filePath) throws CipherException, IOException {
    WalletFile walletFile = loadWalletFile(filePath);
    return decrypt2PrivateBytes(new byte[]{}, walletFile);
  }

  static public String getPrivateString(String filePath) throws CipherException, IOException {
    return toHexString(getPrivateBytes(filePath));
  }

  private static WalletFile loadWalletFile(String filePath) throws IOException {
    File wallet = new File(filePath);
    if (wallet == null) {
      throw new IOException(
          "No keystore file found, please use registerwallet or importwallet first!");
    }
    return readWalletFile(wallet);
  }

  public static WalletFile readWalletFile(File source) throws IOException {
    return objectMapper.readValue(source, WalletFile.class);
  }

  public static byte[] decrypt2PrivateBytes(byte[] password, WalletFile walletFile)
      throws CipherException {

    validate(walletFile);

    WalletFile.Crypto crypto = walletFile.getCrypto();

    byte[] mac = ByteArray.fromHexString(crypto.getMac());
    byte[] iv = ByteArray.fromHexString(crypto.getCipherparams().getIv());
    byte[] cipherText = ByteArray.fromHexString(crypto.getCiphertext());

    byte[] derivedKey;

    WalletFile.KdfParams kdfParams = crypto.getKdfparams();
    if (kdfParams instanceof WalletFile.ScryptKdfParams) {
      WalletFile.ScryptKdfParams scryptKdfParams =
          (WalletFile.ScryptKdfParams) crypto.getKdfparams();
      int dklen = scryptKdfParams.getDklen();
      int n = scryptKdfParams.getN();
      int p = scryptKdfParams.getP();
      int r = scryptKdfParams.getR();
      byte[] salt = ByteArray.fromHexString(scryptKdfParams.getSalt());
      derivedKey = generateDerivedScryptKey(password, salt, n, r, p, dklen);
    } else if (kdfParams instanceof WalletFile.Aes128CtrKdfParams) {
      WalletFile.Aes128CtrKdfParams aes128CtrKdfParams =
          (WalletFile.Aes128CtrKdfParams) crypto.getKdfparams();
      int c = aes128CtrKdfParams.getC();
      String prf = aes128CtrKdfParams.getPrf();
      byte[] salt = ByteArray.fromHexString(aes128CtrKdfParams.getSalt());

      derivedKey = generateAes128CtrDerivedKey(password, salt, c, prf);
    } else {
      throw new CipherException("Unable to deserialize params: " + crypto.getKdf());
    }

    byte[] derivedMac = generateMac(derivedKey, cipherText);

    if (!Arrays.equals(derivedMac, mac)) {
      throw new CipherException("Invalid password provided");
    }

    byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
    byte[] privateKey = performCipherOperation(Cipher.DECRYPT_MODE, iv, encryptKey, cipherText);

    return privateKey;
  }

  private static byte[] generateAes128CtrDerivedKey(
      byte[] password, byte[] salt, int c, String prf) throws CipherException {

    if (!prf.equals("hmac-sha256")) {
      throw new CipherException("Unsupported prf:" + prf);
    }

    // Java 8 supports this, but you have to convert the password to a character array, see
    // http://stackoverflow.com/a/27928435/3211687

    PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
    gen.init(password, salt, c);
    return ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
  }

  public static byte[] hexs2Bytes(byte[] a) {
    if (ArrayUtils.isEmpty(a)) {
      return null;
    }
    if ((a.length & 0x01) != 0) {
      return null;
    }
    byte[] result = new byte[a.length / 2];
    for (int i = 0; i < result.length; i++) {
      byte h = a[i * 2];
      byte l = a[i * 2 + 1];
      if (h >= '0' && h <= '9') {
        result[i] = (byte) ((h - '0') << 4);
      } else if (h >= 'a' && h <= 'f') {
        result[i] = (byte) ((h - 'a' + 10) << 4);
      } else if (h >= 'A' && h <= 'F') {
        result[i] = (byte) ((h - 'A' + 10) << 4);
      } else {
        return null;
      }

      if (l >= '0' && l <= '9') {
        result[i] += (l - '0');
      } else if (l >= 'a' && l <= 'f') {
        result[i] += (l - 'a' + 10);
      } else if (l >= 'A' && l <= 'F') {
        result[i] += (l - 'A' + 10);
      } else {
        return null;
      }
      h = l = 0;
    }
    return result;
  }

  public static String private2Address(byte[] privateKey) throws CipherException {
    ECKey eCkey;
    if (StringUtils.isEmpty(privateKey)) {
      eCkey = new ECKey(Utils.getRandom());  //Gen new Keypair
    } else {
      eCkey = ECKey.fromPrivate(privateKey);
    }
    System.out.println("Private Key: " + ByteArray.toHexString(eCkey.getPrivKeyBytes()));

    byte[] publicKey0 = eCkey.getPubKey();
    byte[] publicKey1 = private2PublicDemo(eCkey.getPrivKeyBytes());
    if (!Arrays.equals(publicKey0, publicKey1)){
      throw new CipherException("publickey error");
    }
    System.out.println("Public Key: " + ByteArray.toHexString(publicKey0));

    byte[] address0 = eCkey.getAddress();
    byte[] address1 = public2AddressDemo(publicKey0);
    if (!Arrays.equals(address0, address1)){
      throw new CipherException("address error");
    }
    System.out.println("Address: " + ByteArray.toHexString(address0));

    String base58checkAddress0 = encode58Check(address0);
    String base58checkAddress1 = address2Encode58CheckDemo(address0);
    if (!base58checkAddress0.equals(base58checkAddress1)){
      throw new CipherException("base58checkAddress error");
    }

    return base58checkAddress1;
  }

  private static byte[] private2PublicDemo(byte[] privateKey) {
    BigInteger privKey = new BigInteger(1, privateKey);
    ECPoint point = ECKey.CURVE.getG().multiply(privKey);
    return point.getEncoded(false);
  }

  private static byte[] public2AddressDemo(byte[] publicKey) {
    byte[] hash = Hash.sha3(copyOfRange(publicKey, 1, publicKey.length));
    System.out.println("sha3 = " + ByteArray.toHexString(hash));
    byte[] address = copyOfRange(hash, 11, hash.length);
    address[0] = getAddressPreFixByte();
    return address;
  }

  public static String address2Encode58CheckDemo(byte[] input) {
    byte[] hash0 = Sha256Hash.hash(input);
    System.out.println("sha256_0: " + ByteArray.toHexString(hash0));

    byte[] hash1 = Sha256Hash.hash(hash0);
    System.out.println("sha256_1: " + ByteArray.toHexString(hash1));

    byte[] inputCheck = new byte[input.length + 4];
    System.out.println("checkSum: " + ByteArray.toHexString(copyOfRange(hash1, 0, 4)));

    System.arraycopy(input, 0, inputCheck, 0, input.length);
    System.arraycopy(hash1, 0, inputCheck, input.length, 4);
    System.out.println("addchecksum: " + ByteArray.toHexString(inputCheck));

    return Base58.encode(inputCheck);
  }
}
