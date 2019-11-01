package config;

import java.io.Serializable;
import java.util.List;

public class NetworkConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public int node_maxHttpConnectNumber;
  public int node_rpc_port;
  public int node_rpc_solidityPort;
  public int node_http_fullNodePort;
  public int node_http_solidityPort;
  public int node_listen_port;

  public NetworkConfig(int node_maxHttpConnectNumber, int node_rpc_solidityPort,
      int node_rpc_port, int node_http_fullNodePort, int node_http_solidityPort, int node_listen_port) {
    this.node_maxHttpConnectNumber = node_maxHttpConnectNumber;
    this.node_rpc_solidityPort = node_rpc_solidityPort;
    this.node_rpc_port = node_rpc_port;
    this.node_http_fullNodePort = node_http_fullNodePort;
    this.node_http_solidityPort = node_http_solidityPort;
    this.node_listen_port = node_listen_port;
  }
}
