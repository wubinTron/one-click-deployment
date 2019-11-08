package entity;

import lombok.Getter;
import lombok.Setter;
import org.tron.core.Wallet;
import org.tron.core.config.args.Witness;

public class WitnessEntity {
  public String address;
  public String url;
  public String voteCount;
  public WitnessEntity(String address, String url, String voteCount) {
    this.address = address;
    this.url = url;
    this.voteCount = voteCount;
  }
}
