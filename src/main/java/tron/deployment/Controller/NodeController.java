package tron.deployment.Controller;

import static common.Util.readJsonFile;
import static common.Util.writeJsonFile;

import common.Common;
import common.ResultCode;
import common.Util;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.Response;


@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
public class NodeController {

  private JSONArray removeNodeInfo(JSONArray nodes, Long id) {
    JSONArray newNodes = new JSONArray();
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      Long nodeID = (Long) node.get(Common.idFile);
      if (nodeID != id) {
        newNodes.add(node);
      }
    }
    return newNodes;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/nodeInfo")
  public JSONObject addNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id,
      @RequestParam(value = "userName", required = false, defaultValue = "node1") String userName,
      @RequestParam(value = "ip", required = false, defaultValue = "127.0.0.1") String ip,
      @RequestParam(value = "port", required = false, defaultValue = "8090") int port,
      @RequestParam(value = "isSR", required = false, defaultValue = "false") boolean isSR,
      @RequestParam(value = "privateKey", required = false, defaultValue = "") String key
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray)json.get(Common.nodesFile);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node != null) {
      return new Response(ResultCode.FORBIDDEND.code, "node id already exist").toJSONObject();
    }

    JSONObject newNode = new JSONObject();
    newNode.put(Common.idFile, id);
    newNode.put(Common.userNameFile, userName);
    newNode.put(Common.portFile, port);
    newNode.put(Common.ipFile, ip);
    newNode.put(Common.isSRFile, isSR);
    newNode.put(Common.privateKeyFile, key);
    nodes.add(newNode);
    json.put(Common.nodesFile, nodes);

    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "write json file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/nodeInfo")
  public JSONObject updateNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id,
      @RequestParam(value = "userName", required = false, defaultValue = "") String userName,
      @RequestParam(value = "ip", required = false, defaultValue = "") String ip,
      @RequestParam(value = "port", required = false, defaultValue = "") int port,
      @RequestParam(value = "isSR", required = false, defaultValue = "") boolean isSR,
      @RequestParam(value = "privateKey", required = false, defaultValue = "") String key
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray)json.get(Common.nodesFile);
    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node == null) {
      return new Response(ResultCode.NOT_FOUND.code, "node id not exist").toJSONObject();
    }

    nodes = removeNodeInfo(nodes, id);
    node.put(Common.userNameFile, userName);
    node.put(Common.portFile, port);
    node.put(Common.ipFile, ip);
    node.put(Common.isSRFile, isSR);
    node.put(Common.privateKeyFile, key);
    nodes.add(node);
    json.put(Common.nodesFile, nodes);

    boolean result = writeJsonFile(json);
    if (!result) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "write json file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }


  @RequestMapping(method = RequestMethod.GET, value = "/nodeInfo")
  public JSONObject getNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFile);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONObject node = Util.getNodeInfo(nodes, id);
    if (node == null) {
      return new Response(ResultCode.NOT_FOUND.code, "node id not exist").toJSONObject();
    }
    return new Response(ResultCode.OK.code, node).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/allNodeInfo")
  public JSONObject getAllNode(
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFile);
    return new Response(ResultCode.OK.code, nodes).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/nodeInfo")
  public JSONObject deleteNode(
      @RequestParam(value = "id", required = true, defaultValue = "1") Long id
  ) {

    JSONObject json = readJsonFile();
    JSONArray nodes = (JSONArray) json.get(Common.nodesFile);
    if (Objects.isNull(nodes)) {
      nodes = new JSONArray();
    }

    JSONArray newNodes = removeNodeInfo(nodes, id);
    if (newNodes.size() == nodes.size()) {
      return new Response(ResultCode.NOT_FOUND.code, "node id not exist").toJSONObject();
    }

    json.put(Common.nodesFile, newNodes);
    if (!writeJsonFile(json)) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "write json file failed").toJSONObject();
    }

    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }
}
