package entity;

import org.tron.core.Wallet;
import org.tron.core.config.args.Account;

public class AssetsEntity {
  public String accountName;
  public String accountType;
  public String address;
  public String  balance;

  public AssetsEntity(Account account) {
    this.accountName = account.getAccountName().toStringUtf8();
    this.accountType = account.getAccountType().toString();
    this.address = Wallet.encode58Check(account.getAddress());
    this.balance = String.valueOf(account.getBalance());
  }
}
