package config;

import java.io.Serializable;
import java.util.List;

public class P2PConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public long node_p2p_version;
  public int node_maxActiveNodes;
  public double node_activeConnectFactor;
  public int node_maxActiveNodesWithSameIp;
  public double connectFactor;
  public List<String> seed_node_ip_list;

  public P2PConfig(long node_p2p_version, int node_maxActiveNodes, double node_activeConnectFactor,
      int nodeMaxActiveNodesWithSameIp, double connectFactor, List<String> seed_node_ip_list) {
    this.node_p2p_version = node_p2p_version;
    this.node_maxActiveNodes = node_maxActiveNodes;
    this.node_activeConnectFactor = node_activeConnectFactor;
    this.node_maxActiveNodesWithSameIp = nodeMaxActiveNodesWithSameIp;
    this.connectFactor = connectFactor;
    this.seed_node_ip_list = seed_node_ip_list;
  }
}
