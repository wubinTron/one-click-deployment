package config;

import static org.tron.core.Wallet.encode58Check;

import common.Common;
import entity.WitnessEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.tron.core.config.args.Witness;

public class GenesisWitnessConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<LinkedHashMap> genesis_block_witnesses;

  public GenesisWitnessConfig(List<Witness> genesis_block_witnesses) {
    this.genesis_block_witnesses = new ArrayList<>();
    genesis_block_witnesses.forEach(witness ->
        {
          LinkedHashMap<String, String> seqMap = new LinkedHashMap<>();
          seqMap.put(Common.addressFiled, encode58Check(witness.getAddress()));
          seqMap.put(Common.urlFiled, witness.getUrl());
          seqMap.put(Common.voteCountFiled, String.format("%d", witness.getVoteCount()));
          this.genesis_block_witnesses.add(seqMap);
        }
    );
  }

  public GenesisWitnessConfig() {}

  public void setGenesisBlockWitnesses(ArrayList<WitnessEntity> genesisWitness) {
    this.genesis_block_witnesses = new ArrayList<>();
    genesisWitness.forEach(witness ->
        {
          LinkedHashMap<String, String> seqMap = new LinkedHashMap<>();
          seqMap.put(Common.addressFiled, witness.address);
          seqMap.put(Common.urlFiled,  witness.url);
          seqMap.put(Common.voteCountFiled, witness.voteCount);
          this.genesis_block_witnesses.add(seqMap);
        }
    );
  }
}
