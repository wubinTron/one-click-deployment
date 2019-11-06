package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.tron.core.Wallet;
import org.tron.core.config.args.Account;
import org.tron.core.config.args.Witness;

public class Args {
  // Config
  private static final String P2P_VERSION_KEY = "node.p2p.version";
  private static final String NEED_TO_UPDATE_ASSET_KEY = "storage.needToUpdateAsset";
  private static final String MAX_HTTP_CONNECT_KEY = "node.maxHttpConnectNumber";
  private static final String RPC_FULLNODE_PORT_KEY = "node.rpc.port";
  private static final String MAX_ACTIVE_NODES_KEY = "node.maxActiveNodes";
  private static final String MAX_ACTIVE_CONNECT_FACTOR_KEY = "node.activeConnectFactor";
  private static final String MAX_ACTIVE_NODE_WITH_SAME_IP_KEY = "node.maxActiveNodesWithSameIp";
  private static final String CONNECT_FACTOR_KEY = "node.connectFactor";
  private static final String NODE_LISTEN_PORT = "node.listen.port";
  private static final String RPC_SOLIDITY_PORT = "node.rpc.solidityPort";
  private static final String NODE_HTTP_FULLNODE_PORT = "node.http.fullNodePort";
  private static final String NODE_HTTP_SOLIDITY_PORT = "node.http.solidityPort";
  private static final String BLOCK_MAINTENANCE_TIME_INTERVAR = "block.maintenanceTimeInterval";
  private static final String NODE_BLOCK_PRODUCED_TIMEOUT = "node.blockProducedTimeOut";
  private static final String GENESIS_BLOCK_ASSETS = "genesis.block.assets";
  private static final String BLOCK_PROPOSAL_EXPIRETIME = "block.proposalExpireTime";
  private static final String NODE_MIN_PARTICIPATIONRATE = "node.minParticipationRate";
  private static final String SEED_NODE_IP_LIST = "seed.node.ip.list";
  private static final String GENESIS_BLOCK_WITNESSES = "genesis.block.witnesses";
  private static final String ACTUATOR_WHITELIST = "actuator.whitelist";

  public static boolean needToUpdateAsset(final Config config) {
    return config.hasPath(NEED_TO_UPDATE_ASSET_KEY) ? config
        .getBoolean(NEED_TO_UPDATE_ASSET_KEY)
        : true;
  }

  public static int getP2pVersionFromConfig(final Config config) {
    return config.hasPath(P2P_VERSION_KEY) ? config.getInt(P2P_VERSION_KEY) : 0;
  }

  public static int getNodeMaxActiveNodes(final Config config) {
    return config.hasPath(MAX_ACTIVE_NODES_KEY) ? config.getInt(MAX_ACTIVE_NODES_KEY) : 30;
  }

  public static double getActiveConnectFactor(final Config config) {
    return config.hasPath(MAX_ACTIVE_CONNECT_FACTOR_KEY) ?
        config.getDouble(MAX_ACTIVE_CONNECT_FACTOR_KEY) : 0.1;
  }

  public static int getNodeMaxActiveNodesWithSameIp(final Config config) {
     return
        config.hasPath(MAX_ACTIVE_NODE_WITH_SAME_IP_KEY) ? config
            .getInt(MAX_ACTIVE_NODE_WITH_SAME_IP_KEY) : 2;
  }

  public static double getConnectFactor(final Config config) {
    return
        config.hasPath(CONNECT_FACTOR_KEY) ? config.getDouble(CONNECT_FACTOR_KEY) : 0.3;
  }

  public static int getMaxHttpConnectNumber(final Config config) {
    return  config.hasPath(MAX_HTTP_CONNECT_KEY) ?
        config.getInt(MAX_HTTP_CONNECT_KEY) : 50;
  }

  public static int getRPCFullNodePort(final Config config) {
    return
        config.hasPath(RPC_FULLNODE_PORT_KEY) ? config.getInt(RPC_FULLNODE_PORT_KEY) : 50051;
  }

  public static int getRPCSolidityNodePort(final Config config) {
    return
        config.hasPath(RPC_SOLIDITY_PORT) ? config.getInt(RPC_SOLIDITY_PORT) : 50061;
  }

  public static int getHTTPFullNodePort(final Config config) {
    return
        config.hasPath(NODE_HTTP_FULLNODE_PORT) ? config.getInt(NODE_HTTP_FULLNODE_PORT) : 8090;
  }

  public static int getHTTPSolidityNodePort(final Config config) {
    return
        config.hasPath(NODE_HTTP_SOLIDITY_PORT) ? config.getInt(NODE_HTTP_SOLIDITY_PORT) : 8091;
  }

  public static long getMaintenanceTimeInterval(final Config config) {
    return
        config.hasPath(BLOCK_MAINTENANCE_TIME_INTERVAR) ? config.getInt(BLOCK_MAINTENANCE_TIME_INTERVAR) : 21600000L;
  }

  public static int getBlockProducedTimeOut(final Config config) {
    return
        config.hasPath(NODE_BLOCK_PRODUCED_TIMEOUT) ?
            config.getInt(NODE_BLOCK_PRODUCED_TIMEOUT) : 50;

  }

  public static List<Account> getAccountsFromConfig(final Config config) {
    return config.getObjectList(GENESIS_BLOCK_ASSETS).stream()
        .map(Args::createAccount)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Account createAccount(final ConfigObject asset) {
    final Account account = new Account();
    account.setAccountName(asset.get(Common.accountNameField).unwrapped().toString());
    account.setAccountType(asset.get(Common.accountTypeField).unwrapped().toString());
    account.setAddress(Wallet.decodeFromBase58Check(asset.get(Common.addressFiled).unwrapped().toString()));
    account.setBalance(asset.get(Common.balanceField).unwrapped().toString());
    return account;
  }

  public static List<Witness> getWitnessesFromConfig(final Config config) {
    return config.getObjectList(GENESIS_BLOCK_WITNESSES).stream()
        .map(Args::createWitness)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Witness createWitness(final ConfigObject witnessAccount) {
    final Witness witness = new Witness();
    witness.setAddress(
        Wallet.decodeFromBase58Check(witnessAccount.get(Common.addressFiled).unwrapped().toString()));
    witness.setUrl(witnessAccount.get(Common.urlFiled).unwrapped().toString());
    witness.setVoteCount(witnessAccount.toConfig().getLong(Common.voteCountFiled));
    return witness;
  }

  public static long getProposalExpireTime(final Config config) {
    return config.hasPath(BLOCK_PROPOSAL_EXPIRETIME) ? config
        .getInt(BLOCK_PROPOSAL_EXPIRETIME) : 259200000L;
  }

  public static int getMinParticipationRate(final Config config) {
    return config.hasPath(NODE_MIN_PARTICIPATIONRATE) ? config.getInt(NODE_MIN_PARTICIPATIONRATE)
        : 0;
  }

  public static List<String> getSeedNode(final Config config) {
    if (config.hasPath(SEED_NODE_IP_LIST)) {
      return config.getStringList(SEED_NODE_IP_LIST).size() == 0
          ? null : config.getStringList(SEED_NODE_IP_LIST);
    }
    return null;
  }

  public static int getListenPort(final Config config) {
    return config.hasPath(NODE_LISTEN_PORT) ? config.getInt(NODE_LISTEN_PORT) : 0;
  }

  public static Set<String> getActuatorSet(final Config config) {
    return config.hasPath(ACTUATOR_WHITELIST) ?
        new HashSet<>(config.getStringList(ACTUATOR_WHITELIST))
        : Collections.emptySet();
  }
}
