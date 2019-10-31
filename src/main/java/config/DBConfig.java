package config;

import java.io.Serializable;

public class DBConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public String storage_index_directory;
  public boolean storage_db_sync;
  public String storage_transHistory_switch;
  public String  storage_db_engine;
  public boolean storage_needToUpdateAsset;

  public DBConfig(boolean storage_db_sync, String storage_transHistory_switch, String storage_db_enine, String storage_index_directory, boolean storage_needToUpdateAsset) {
    this.storage_db_sync = storage_db_sync;
    this.storage_transHistory_switch = storage_transHistory_switch;
    this.storage_db_engine = storage_db_enine;
    this.storage_index_directory = storage_index_directory;
    this.storage_needToUpdateAsset = storage_needToUpdateAsset;
  }
}
