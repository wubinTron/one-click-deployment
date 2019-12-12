package tron.deployment.Controller;

import static common.Util.readJsonFile;
import static common.Util.writeJsonFile;
import static org.tron.core.config.args.Storage.getDbEngineFromConfig;

import common.Args;
import common.Common;
import common.Util;
import config.ActuatorConfig;
import config.BlockSettingConfig;
import config.ConfigGenerator;
import config.CryptoConfig;
import config.NetworkConfig;
import org.spongycastle.util.Strings;
import response.ResultCode;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.Response;

@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
public class PluginConfig {
  @RequestMapping(method = RequestMethod.POST, value = "/consensus")
  public JSONObject consensus(
      @RequestParam(value = "consensus", required = false, defaultValue = "dpos") String consensus
  ){
    JSONObject json = readJsonFile();
    json.put(Common.consensusFiled, consensus);
    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.writeJsonFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/transaction")
  public JSONObject transaction(
      @RequestBody JSONObject jsonObject
  ){

    if (!jsonObject.containsKey(Common.transactionFiled) || !jsonObject.containsKey(Common.customTransactionFiled)) {
      return new Response(ResultCode.FAILED.code, "miss transaction or custom transaction").toJSONObject();
    }

    JSONObject json = readJsonFile();

    List<String> transactions = (ArrayList<String>)jsonObject.get(Common.transactionFiled);

    String customTransaction = (String) jsonObject.get(Common.customTransactionFiled);
    json.put(Common.customTransactionFiled, customTransaction);
    ConfigGenerator configGenerator = new ConfigGenerator();
    if (!configGenerator.updateConfig(new ActuatorConfig(transactions), Common.configFiled)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.updateConfigFileFailed).toJSONObject();
    }

    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.writeJsonFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/dbEngine")
  public JSONObject dbEngine(
      @RequestParam(value = "dbEngine", required = false, defaultValue = "leveldb") String dbEngine
  ){
    JSONObject json = readJsonFile();
    json.put(Common.dbEngineFiled, dbEngine);
    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.writeJsonFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/crypto")
  public JSONObject cryptoEngine(
      @RequestParam(value = "crypto", required = false, defaultValue = "eckey") String eckey
  ){
    ConfigGenerator configGenerator = new ConfigGenerator();
    boolean result = configGenerator.updateConfig(new CryptoConfig(eckey), Common.configFiled);

    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.writeJsonFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/pluginConfig")
  public JSONObject pluginConfig() {
    JSONObject json = readJsonFile();
    JSONObject result = new JSONObject();
    result.put(Common.consensusFiled, json.get(Common.consensusFiled));
    result.put(Common.dbEngineFiled, Strings.toLowerCase(getDbEngineFromConfig(Util.config)));
    result.put(Common.customTransactionFiled, json.get(Common.customTransactionFiled));
    Util.parseConfig();
    result.put(Common.transactionFiled, Args.getActuatorSet(Util.config));
    result.put(Common.cryptoEngine, Strings.toLowerCase(Args.getCrypto(Util.config)));

    return new Response(ResultCode.OK.code, result).toJSONObject();
  }
}
