package config;

import java.io.Serializable;

public class BaseSettingConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public int node_blockProducedTimeOut;
  public long block_maintenanceTimeInterval;
  public long block_proposalExpireTime;
  public int node_minParticipationRate;

  public BaseSettingConfig(int node_blockProducedTimeOut,
      long block_maintenanceTimeInterval, long block_proposalExpireTime,
      int node_minParticipationRate) {
    this.node_blockProducedTimeOut = node_blockProducedTimeOut;
    this.block_maintenanceTimeInterval = block_maintenanceTimeInterval;
    this.block_proposalExpireTime = block_proposalExpireTime;
    this.node_minParticipationRate = node_minParticipationRate;
  }
}
