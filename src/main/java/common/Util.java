package common;

import static wallet.Wallet.store2Keystore;
import static wallet.WalletFile.createWalletFile;

import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.tron.keystore.CipherException;
import wallet.WalletFile;

public class Util {
  public static com.typesafe.config.Config config;

  public static void parseConfig() {
    File confFile = new File(Common.configFiled);
    config = ConfigFactory.parseFile(confFile);
  }

  static public JSONObject readJsonFile() {
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;
    try {
      Object obj = parser.parse(new FileReader(Common.databaseFiled));
      jsonObject = (JSONObject) obj;
    } catch (IOException  | ParseException e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  static public boolean writeJsonFile(JSONObject json) {
    Writer write;
    try {
      write = new OutputStreamWriter(new FileOutputStream(Common.databaseFiled), "UTF-8");
      write.write(json.toString());
      write.flush();
      write.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  static public JSONObject getNodeInfo(JSONArray nodes, Long id) {
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      Long nodeID = (Long) node.get(Common.idFiled);
      if (nodeID == id) {
        return node;
      }
    }
    return null;
  }

  public static String importPrivateKey(byte[] priKey) throws CipherException, IOException {
    WalletFile walletFile = createWalletFile(new byte[] {}, priKey);
    String keystoreName = store2Keystore(walletFile);
    return keystoreName;
  }

}
