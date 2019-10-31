package config;

import common.AssetsEntity;
import common.WitnessEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.tron.core.config.args.Account;
import org.tron.core.config.args.Witness;

public class GenesisSettingConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<AssetsEntity> genesis_block_assets;
  public List<WitnessEntity> genesis_block_witnesses;

  public GenesisSettingConfig(List<Account> genesis_block_assets,
      List<Witness> genesis_block_witnesses) {
    this.genesis_block_assets = new ArrayList<>();
    this.genesis_block_witnesses = new ArrayList<>();
    genesis_block_assets.forEach(asset -> this.genesis_block_assets.add(new AssetsEntity(asset)));
    genesis_block_witnesses.forEach(witness -> this.genesis_block_witnesses.add(new WitnessEntity(witness)));
  }

  public GenesisSettingConfig() {}
}
