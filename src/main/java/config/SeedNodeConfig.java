package config;

import java.io.Serializable;
import java.util.List;

public class SeedNodeConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<String> seed_node_ip_list;

  public SeedNodeConfig(List<String> seed_node_ip_list) {
    this.seed_node_ip_list = seed_node_ip_list;
  }
}
