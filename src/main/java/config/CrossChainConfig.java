package config;

import java.io.Serializable;

public class CrossChainConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public boolean enableCrossChain;
  public Long maxValidatorNumber;
  public Long minValidatorNumber;
  public double crossChainFee;

  public CrossChainConfig(boolean enableCrossChain, Long maxValidatorNumber, Long minValidatorNumber, double crossChainFee) {
    this.enableCrossChain = enableCrossChain;
    this.maxValidatorNumber = maxValidatorNumber;
    this.minValidatorNumber = minValidatorNumber;
    this.crossChainFee = crossChainFee;
  }
}
