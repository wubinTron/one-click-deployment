package config;

import java.io.Serializable;

public class P2PVersion implements Serializable {
  private static final long serialVersionUID = 1L;

  public P2PVersion(long node_p2p_version) {
    this.node_p2p_version = node_p2p_version;
  }
  public long node_p2p_version;
}
