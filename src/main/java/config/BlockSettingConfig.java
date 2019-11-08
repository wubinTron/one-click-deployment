package config;

import java.io.Serializable;

public class BlockSettingConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public boolean block_needSyncCheck;

  public BlockSettingConfig(boolean block_needSyncCheck) {
    this.block_needSyncCheck = block_needSyncCheck;
  }
}
