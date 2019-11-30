package entity;

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
