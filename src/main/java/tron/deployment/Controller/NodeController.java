package tron.deployment.Controller;

import static common.Util.readJsonFile;
import static common.Util.writeJsonFile;
import static wallet.Wallet.hexs2Bytes;
import static wallet.Wallet.private2Address;

import common.Common;
import config.SeedNodeConfig;
import java.util.List;
import response.ResultCode;
import common.Util;
import entity.WitnessEntity;
import config.GenesisWitnessConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tron.keystore.CipherException;
import response.Response;
import config.ConfigGenerator;


@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
@Slf4j
public class NodeController {
  private JSONObject updateNodesInfo(JSONArray nodes, JSONObject json) {
    ConfigGenerator configGenerator = new ConfigGenerator();

    ArrayList<WitnessEntity> witnessnodes = new ArrayList<>();
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      boolean isSR = (Boolean) node.get(Common.isSRFiled);

      if (isSR == true) {
        witnessnodes.add(new WitnessEntity((String) node.get(Common.publicKeyFiled),
            (String)node.get(Common.urlFiled), (String)node.get(Common.voteCountFiled)));
      }
    }
    GenesisWitnessConfig witnessConfig =  new GenesisWitnessConfig();
    witnessConfig.setGenesisBlockWitnesses(witnessnodes);
    if (configGenerator.updateConfig(witnessConfig, Common.configFiled) == false) {
      log.error("update witness config file failed");
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "update witness config file failed").toJSONObject();
    }

    json.put(Common.nodesFiled, nodes);
    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.writeJsonFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  private JSONArray  removeNodeInfo(JSONArray nodes, Long id, boolean flag) {
    JSONArray newNodes = new JSONArray();
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      Long nodeID = (Long) node.get(Common.idFiled);
      if (nodeID == id) {
        String privateKeyFile = (String) node.get(Common.privateKeyFiled);
        File file = new File(String.format("%s/%s", Common.walletFiled, privateKeyFile));
        if(file.exists() && flag){
          file.delete();
        }
      } else {
        newNodes.add(node);
      }
    }
    return newNodes;
  }

  private boolean isIpExist(JSONArray nodes, String ip) {
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      String nodeIp = (String) node.get(Common.ipFiled);
      if (nodeIp.equals(ip)) {
        return true;
      }
    }
    return false;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/nodeInfo")
  public JSONObject addNode(
      @RequestParam(value = "id", required = false, defaultValue = "1") Long id,
      @RequestParam(value = "userName", required = false, defaultValue = "node1") String userName,
      @RequestParam(value = "ip", required = false, defaultValue = "127.0.0.1") String ip,
      @RequestParam(value = "port", required = false, defaultValue = "8090") int port,
      @RequestParam(value = "isSR", required = false, defaultValue = "false") boolean isSR,
      @RequestParam(value = "url", required = false, defaultValue = "") String url,
      @RequestParam(value = "privateKey", required = false, defaultValue = "") String privateKey,
      @RequestParam(value = "voteCount", required = false, defaultValue = "104") String voteCount
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray)json.get(Common.nodesFiled);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node != null) {
      return new Response(ResultCode.FORBIDDEND.code, "node id already exist").toJSONObject();
    }


    if (isIpExist(nodes, ip)) {
      return new Response(ResultCode.FORBIDDEND.code, "ip should be different").toJSONObject();
    }

    JSONObject newNode = new JSONObject();
    if (isSR) {
      String path;
      String publicKey;
      try {
        path = Util.importPrivateKey(hexs2Bytes(privateKey.getBytes()));
        publicKey = private2Address(hexs2Bytes(privateKey.getBytes()));
        newNode.put(Common.privateKeyFiled, path);
        newNode.put(Common.publicKeyFiled, publicKey);
      } catch (CipherException | IOException e) {
        log.error(e.toString()) ;
        return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.savePrivateKeyFailed).toJSONObject();
      }
    }

    newNode.put(Common.idFiled, id);
    newNode.put(Common.userNameFiled, userName);
    newNode.put(Common.portFiled, port);
    newNode.put(Common.ipFiled, ip);
    newNode.put(Common.isSRFiled, isSR);
    newNode.put(Common.urlFiled, url);
    newNode.put(Common.voteCountFiled, voteCount);
    nodes.add(newNode);

    return updateNodesInfo(nodes, json);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/nodeInfo")
  public JSONObject updateNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id,
      @RequestParam(value = "userName", required = false, defaultValue = "") String userName,
      @RequestParam(value = "ip", required = false, defaultValue = "") String ip,
      @RequestParam(value = "port", required = false, defaultValue = "") int port,
      @RequestParam(value = "isSR", required = false, defaultValue = "") boolean isSR,
      @RequestParam(value = "privateKey", required = false, defaultValue = "") String key,
      @RequestParam(value = "url", required = false, defaultValue = "") String url,
      @RequestParam(value = "voteCount", required = false, defaultValue = "104") String voteCount
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray)json.get(Common.nodesFiled);
    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node == null) {
      return new Response(ResultCode.NOT_FOUND.code, Common.nodeIdNotExistFailed).toJSONObject();
    }

    if (key.length() != 0) {
      String path;
      String publicKey;
      try {
        path = Util.importPrivateKey(hexs2Bytes(key.getBytes()));
        publicKey = private2Address(hexs2Bytes(key.getBytes()));
        node.put(Common.privateKeyFiled, path);
        node.put(Common.publicKeyFiled, publicKey);
      } catch (CipherException | IOException e) {
        log.error(e.toString()) ;
        return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.savePrivateKeyFailed).toJSONObject();
      }
    }

    boolean flag = key.length()!= 0;
    nodes = removeNodeInfo(nodes, id, flag);
    node.put(Common.userNameFiled, userName);
    node.put(Common.portFiled, port);
    node.put(Common.ipFiled, ip);
    node.put(Common.isSRFiled, isSR);
    node.put(Common.urlFiled, url);
    node.put(Common.voteCountFiled, voteCount);
    nodes.add(node);
    json.put(Common.nodesFiled, nodes);

    return updateNodesInfo(nodes, json);
  }


  @RequestMapping(method = RequestMethod.GET, value = "/nodeInfo")
  public JSONObject getNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFiled);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node == null) {
      return new Response(ResultCode.NOT_FOUND.code, Common.nodeIdNotExistFailed).toJSONObject();
    }
    return new Response(ResultCode.OK.code, node).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/allNodeInfo")
  public JSONObject getAllNode(
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFiled);
    return new Response(ResultCode.OK.code, nodes).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/initConfig")
  public JSONObject initConfig() {
    ConfigGenerator configGenerator = new ConfigGenerator();
    if (configGenerator.updateConfig(new SeedNodeConfig(new ArrayList<>()), Common.configFiled) == false) {
      log.error("update seed node config file failed");
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/nodeInfo")
  public JSONObject deleteNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFiled);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONArray newNodes = removeNodeInfo(nodes, id, true);
    if (newNodes.size() == nodes.size()) {
      return new Response(ResultCode.NOT_FOUND.code, Common.nodeIdNotExistFailed).toJSONObject();
    }

    return updateNodesInfo(newNodes, json);
  }

}
