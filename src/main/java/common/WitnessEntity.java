package common;

import lombok.Getter;
import lombok.Setter;
import org.tron.core.Wallet;
import org.tron.core.config.args.Witness;

public class WitnessEntity {
  public String address;
  public String url;
  public long voteCount;

  public WitnessEntity(Witness witness) {
    this.address = Wallet.encode58Check(witness.getAddress());
    this.url = witness.getUrl();
    this.voteCount = witness.getVoteCount();
  }
}
