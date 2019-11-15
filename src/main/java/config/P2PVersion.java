package config;

import java.io.Serializable;

public class P2PVersion implements Serializable {
  private static final long serialVersionUID = 1L;

  public P2PVersion(int node_p2p_version) {
    this.node_p2p_version = node_p2p_version;
  }
  public int node_p2p_version;
}
