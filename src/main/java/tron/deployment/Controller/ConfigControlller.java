package tron.deployment.Controller;
import static common.Util.config;
import static common.Util.parseConfig;
import static common.Util.parseOriginConfig;
import static common.Util.readJsonFile;
import static common.Util.writeJsonFile;
import static org.tron.core.config.args.Storage.getDbEngineFromConfig;
import static org.tron.core.config.args.Storage.getDbVersionSyncFromConfig;
import static org.tron.core.config.args.Storage.getIndexDirectoryFromConfig;
import static org.tron.core.config.args.Storage.getTransactionHistoreSwitchFromConfig;

import common.AssetsEntity;
import common.Args;
import common.Common;
import common.ResultCode;
import common.WitnessEntity;
import config.BaseSettingConfig;
import config.CrossChainConfig;
import config.DBConfig;
import config.GenesisSettingConfig;
import config.NetworkConfig;
import config.P2PConfig;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.Response;
import tron.deployment.pojo.ConfigGenerator;


@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
public class ConfigControlller {
  String chainId;
  String chainName;
  // db
  DBConfig dbConfig;

  // p2p
  P2PConfig p2pConfig;

  // network
  NetworkConfig networkConfig;

  // crossChain
  CrossChainConfig crossChainConfig;

  // baseSetting
  BaseSettingConfig baseSettingConfig;
  // genesisSetting
  GenesisSettingConfig genesisSettingConfig;

  private void initCrossSetting() {
    JSONObject json = readJsonFile();
    crossChainConfig = new CrossChainConfig((boolean)json.get(Common.enableCrossChainFile),
        (Long)json.get(Common.maxValidatorNumberFile), (Long)json.get(Common.minValidatorNumberFile), (Double)json.get(Common.crossChainFeeFile));
  }

  private void initBaseSettingConfig() {
    JSONObject json = readJsonFile();
    chainId = (String)json.get(Common.chainIdFile);
    chainName = (String)json.get(Common.chainNameFile);
    baseSettingConfig = new BaseSettingConfig(Args.getBlockProducedTimeOut(config), Args.getMaintenanceTimeInterval(config),
        Args.getProposalExpireTime(config), Args.getMinParticipationRate(config));
  }

  private List<String> getSeedNode() {
    JSONObject json = readJsonFile();
    if (json.containsKey(Common.nodesFile) == false) {
      return null;
    }
    JSONArray nodes = (JSONArray)json.get(Common.nodesFile);
    List<String> result = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i ++) {
      JSONObject node = (JSONObject)nodes.get(i);
      if (node.containsKey(Common.ipFile) || node.containsKey(Common.portFile)) {
        result.add(String.format("%s", node.get(Common.ipFile)));
      }
    }
    return result;
  }


  private JSONObject generateJSONObject(Field[] fields, Serializable configuration) {
    JSONObject json = new JSONObject();
    for(Field field : fields ){
      String name = field.getName();
      try {
        Object value = field.get(configuration); // get the value
        json.put(name, value);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return json;
  }

  private void loadConfig() {
    // dbConfig
    dbConfig = new DBConfig(getDbVersionSyncFromConfig(config), getTransactionHistoreSwitchFromConfig(config),
        getDbEngineFromConfig(config), getIndexDirectoryFromConfig(config), Args.needToUpdateAsset(config));

    // p2pConfig
    p2pConfig = new P2PConfig(Args.getP2pVersionFromConfig(config), Args.getNodeMaxActiveNodes(config),
        Args.getActiveConnectFactor(config), Args.getNodeMaxActiveNodesWithSameIp(config), Args.getConnectFactor(config), Args.getSeedNode(config));

    // network
    networkConfig = new NetworkConfig(Args.getMaxHttpConnectNumber(config), Args.getRPCFullNodePort(config),
        Args.getHTTPFullNodePort(config), Args.getRPCSolidityNodePort(config), Args.getHTTPSolidityNodePort(config));

    // crossChain
    initCrossSetting();

    // baseSetting
    initBaseSettingConfig();

    // GenesisSetting
    genesisSettingConfig = new GenesisSettingConfig(Args.getAccountsFromConfig(config), Args.getWitnessesFromConfig(config));
  }

  private JSONObject getConfigJsonObject() {
    loadConfig();
    JSONObject configObject = new JSONObject();
    JSONObject dbObject = generateJSONObject(dbConfig.getClass().getFields(), dbConfig);
    configObject.put("dbConfig", dbObject);

    JSONObject p2pObject = generateJSONObject(p2pConfig.getClass().getFields(), p2pConfig);
    configObject.put("p2pConfig", p2pObject);

    JSONObject networkObject = generateJSONObject(networkConfig.getClass().getFields(), networkConfig);
    configObject.put("networkConfig", networkObject);

    JSONObject crossChainObject = generateJSONObject(crossChainConfig.getClass().getFields(), crossChainConfig);
    configObject.put("crossChainConfig", crossChainObject);

    JSONObject baseSettingObject = generateJSONObject(baseSettingConfig.getClass().getFields(), baseSettingConfig);
    baseSettingObject.put(Common.chainIdFile, chainId);
    baseSettingObject.put(Common.chainNameFile, chainName);
    configObject.put("baseSettingConfig", baseSettingObject);

    JSONObject genesisSettingObject = generateJSONObject(genesisSettingConfig.getClass().getFields(), genesisSettingConfig);;
    configObject.put("genesisSetting", genesisSettingObject);
    return configObject;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/dbconfig")
  public JSONObject dbConfig(
      @RequestParam(value = "isDBSync", required = false, defaultValue = "false") boolean isDBSync,
      @RequestParam(value = "isOpenTransaction", required = false, defaultValue = "on") String isOpenTransaction,
      @RequestParam(value = "dbEnine", required = false, defaultValue = "LEVELDB") String dbEnine,
      @RequestParam(value = "indexDirectory", required = false, defaultValue = "index") String indexDirectory,
      @RequestParam(value = "needToUpdateAsset", required = false, defaultValue = "true") boolean needToUpdateAsset
  ) {
    ConfigGenerator configGenerator = new ConfigGenerator();
    boolean result = configGenerator.updateConfig(new DBConfig(isDBSync, isOpenTransaction,
        dbEnine, indexDirectory, needToUpdateAsset));
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update config.conf file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }


  @RequestMapping(method = RequestMethod.POST, value = "/networkconfig")
  public JSONObject networkConfig(
      @RequestParam(value = "maxHttpConnectNumber", required = false, defaultValue = "50") int maxHttpConnectNumber,
      @RequestParam(value = "rpcPort", required = false, defaultValue = "50051") int rpcPort,
      @RequestParam(value = "rpcSolidityPort", required = false, defaultValue = "50061") int solidityRPCPort,
      @RequestParam(value = "httpFullNodePort", required = false, defaultValue = "8090") int httpFullNodePort,
      @RequestParam(value = "httpSolidityPort", required = false, defaultValue = "8091") int httpSolidityPort
  ) {
    ConfigGenerator configGenerator = new ConfigGenerator();
    boolean result = configGenerator.updateConfig(new NetworkConfig(maxHttpConnectNumber, rpcPort, solidityRPCPort, httpFullNodePort, httpSolidityPort));
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update config.conf file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }


  @RequestMapping(method = RequestMethod.POST, value = "/p2pconfig")
  public JSONObject p2pConfig(
      @RequestParam(value = "p2pVersion", required = false, defaultValue = "0") int p2pVersion,
      @RequestParam(value = "maxActiveNodes", required = false, defaultValue = "30") int node_max_active_nodes,
      @RequestParam(value = "nodeActiveConnectFactor", required = false, defaultValue = "0.1") double activeConnectFactor,
      @RequestParam(value = "nodeMaxActiveNodesWithSameIp", required = false, defaultValue = "2") int nodeMaxActiveNodesWithSameIp,
      @RequestParam(value = "connectFactor", required = false, defaultValue = "0.3") double connectFactor,
      @RequestBody ArrayList<String> ipList //seedNode
  ) {
    ConfigGenerator configGenerator = new ConfigGenerator();
    boolean result = configGenerator.updateConfig(new P2PConfig(p2pVersion, node_max_active_nodes,
        activeConnectFactor, nodeMaxActiveNodesWithSameIp, connectFactor, ipList));

    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update config.conf file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/crossChainConfig")
  public JSONObject dbConfig(
      @RequestParam(value = "enableCrossChain", required = false, defaultValue = "false") boolean enableCrossChain,
      @RequestParam(value = "maxValidatorNumber", required = false, defaultValue = "4") int maxValidatorNumber,
      @RequestParam(value = "minValidatorNumber", required = false, defaultValue = "1") int minValidatorNumber,
      @RequestParam(value = "crossChainFee", required = false, defaultValue = "0.00") double crossChainFee
  ) {
    JSONObject jsonObject = readJsonFile();
    jsonObject.put(Common.enableCrossChainFile, enableCrossChain);
    jsonObject.put(Common.maxValidatorNumberFile, maxValidatorNumber);
    jsonObject.put(Common.minValidatorNumberFile, minValidatorNumber);
    jsonObject.put(Common.crossChainFeeFile, crossChainFee);
    boolean result = writeJsonFile(jsonObject);
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update json file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/baseSettingConfig")
  public JSONObject baseSettingConfig(
      @RequestParam(value = "chainId", required = false, defaultValue = "1") String chainId,
      @RequestParam(value = "chainName", required = false, defaultValue = "Parachain") String chainName,
      @RequestParam(value = "blockProducedTimeOut", required = false, defaultValue = "75") int blockProducedTimeOut,
      @RequestParam(value = "maintenanceTimeInterval", required = false, defaultValue = "21600000") int maintenanceTimeInterval,
      @RequestParam(value = "proposalExpireTime", required = false, defaultValue = "259200000") int proposalExpireTime,
      @RequestParam(value = "minParticipationRate", required = false, defaultValue = "15") int minParticipationRate) {
    JSONObject jsonObject = readJsonFile();
    jsonObject.put(Common.chainIdFile, chainId);
    jsonObject.put(Common.chainNameFile, chainName);
    boolean result = writeJsonFile(jsonObject);
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update json file failed").toJSONObject();
    }
    ConfigGenerator configGenerator = new ConfigGenerator();
    result = configGenerator.updateConfig(new BaseSettingConfig(blockProducedTimeOut, maintenanceTimeInterval,
        proposalExpireTime, minParticipationRate));
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update config file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/genesisSetting")
  public JSONObject genesisSettingConfig(@RequestBody JSONObject jsonObject) {
    if (jsonObject.containsKey("witness") == false || jsonObject.containsKey("assets") == false) {
      return new Response(ResultCode.FAILED.code, "miss assets or witness").toJSONObject();
    }
    List<AssetsEntity> assets = (ArrayList<AssetsEntity>)jsonObject.get("assets");
    List<WitnessEntity> witness = (ArrayList<WitnessEntity>)jsonObject.get("witness");
    ConfigGenerator configGenerator = new ConfigGenerator();
    GenesisSettingConfig genesisSettingConfig = new GenesisSettingConfig();
    genesisSettingConfig.genesis_block_assets = assets;
    genesisSettingConfig.genesis_block_witnesses = witness;
    boolean result = configGenerator.updateConfig(genesisSettingConfig);

    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update config file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/config")
  public JSONObject getConfig() {
    parseConfig();

    JSONObject configObject = getConfigJsonObject();
    return new Response(ResultCode.OK.code, configObject).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/originConfig")
  public JSONObject getOriginConfig() {
    parseOriginConfig();
    loadConfig();
    JSONObject configObject = getConfigJsonObject();
    JSONObject p2pObject = generateJSONObject(p2pConfig.getClass().getFields(), p2pConfig);
    p2pObject.put("seed_node_ip_list", getSeedNode());
    configObject.put("p2pConfig", p2pObject);
    return new Response(ResultCode.OK.code, configObject).toJSONObject();
  }
}
