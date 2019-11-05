package config;

import entity.AssetsEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.tron.core.config.args.Account;

public class GenesisAssetConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<AssetsEntity> genesis_block_assets;

  public GenesisAssetConfig(List<Account> genesis_block_assets) {
    this.genesis_block_assets = new ArrayList<>();
    genesis_block_assets.forEach(asset -> this.genesis_block_assets.add(new AssetsEntity(asset)));
  }

  public GenesisAssetConfig() {}
}
