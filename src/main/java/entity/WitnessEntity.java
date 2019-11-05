package entity;

import lombok.Getter;
import lombok.Setter;
import org.tron.core.Wallet;
import org.tron.core.config.args.Witness;

public class WitnessEntity {
  public String address;
  public String url;
  public String voteCount;

  public WitnessEntity(Witness witness) {
    this.address = Wallet.encode58Check(witness.getAddress());
    this.url = witness.getUrl();
    this.voteCount = String.format("%d", witness.getVoteCount());
  }

  public WitnessEntity(String address, String url, String voteCount) {
    this.address = address;
    this.url = url;
    this.voteCount = voteCount;
  }
}
