package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.tron.core.Wallet;
import org.tron.core.config.args.Account;
import org.tron.core.config.args.SeedNode;
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
        config.hasPath("node.rpc.solidityPort") ? config.getInt("node.rpc.solidityPort") : 50061;
  }

  public static int getHTTPFullNodePort(final Config config) {
    return
        config.hasPath("node.http.fullNodePort") ? config.getInt("node.http.fullNodePort") : 8090;
  }

  public static int getHTTPSolidityNodePort(final Config config) {
    return
        config.hasPath("node.http.solidityPort") ? config.getInt("node.http.solidityPort") : 8091;
  }

  public static long getMaintenanceTimeInterval(final Config config) {
    return
        config.hasPath("block.maintenanceTimeInterval") ? config.getInt("block.maintenanceTimeInterval") : 21600000L;
  }

  public static int getBlockProducedTimeOut(final Config config) {
    return
        config.hasPath("node.blockProducedTimeOut") ?
            config.getInt("node.blockProducedTimeOut") : 50;

  }

  public static List<Account> getAccountsFromConfig(final Config config) {
    return config.getObjectList("genesis.block.assets").stream()
        .map(Args::createAccount)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Account createAccount(final ConfigObject asset) {
    final Account account = new Account();
    account.setAccountName(asset.get("accountName").unwrapped().toString());
    account.setAccountType(asset.get("accountType").unwrapped().toString());
    account.setAddress(Wallet.decodeFromBase58Check(asset.get("address").unwrapped().toString()));
    account.setBalance(asset.get("balance").unwrapped().toString());
    return account;
  }

  public static List<Witness> getWitnessesFromConfig(final Config config) {
    return config.getObjectList("genesis.block.witnesses").stream()
        .map(Args::createWitness)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Witness createWitness(final ConfigObject witnessAccount) {
    final Witness witness = new Witness();
    witness.setAddress(
        Wallet.decodeFromBase58Check(witnessAccount.get("address").unwrapped().toString()));
    witness.setUrl(witnessAccount.get("url").unwrapped().toString());
    witness.setVoteCount(witnessAccount.toConfig().getLong("voteCount"));
    return witness;
  }

  public static long getProposalExpireTime(final Config config) {
    return config.hasPath("block.proposalExpireTime") ? config
        .getInt("block.proposalExpireTime") : 259200000L;
  }

  public static int getMinParticipationRate(final Config config) {
    return config.hasPath("node.minParticipationRate") ? config.getInt("node.minParticipationRate")
        : 0;
  }

  public static List<String> getSeedNode(final Config config) {
    if (config.hasPath("seed.node.ip.list")) {
      return config.getStringList("seed.node.ip.list").size() == 0
          ? null : config.getStringList("seed.node.ip.list");
    }
    return null;
  }
}
