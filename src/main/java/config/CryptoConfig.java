package config;

import java.io.Serializable;

public class CryptoConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public String crypto_engine;

  public CryptoConfig(String crypto_engine) {
    this.crypto_engine = crypto_engine;
  }
}
