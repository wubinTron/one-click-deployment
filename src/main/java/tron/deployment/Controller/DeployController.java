package tron.deployment.Controller;

import static common.LogConfig.LOG;
import static common.Util.readJsonFile;

import common.Common;
import config.BlockSettingConfig;
import config.ConfigGenerator;
import config.DBConfig;
import config.P2PVersion;
import org.springframework.web.bind.annotation.RequestBody;
import response.ResultCode;
import common.Util;
import wallet.Wallet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
import tron.deployment.shellExecutor.BashExecutor;


@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
@Slf4j
public class DeployController {
	private String checkNodeStatus(String path) {
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), Common.encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt;

				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					if (lineTxt.contains(Common.deployFinishStatus)) {
						return Common.deployFinishStatus;
					}
				}
				bufferedReader.close();
				read.close();

			} catch (Exception  e) {
				LOG.error(e.toString());
			}
		} else {
			return Common.notFoundStatus;
		}
		return Common.deployFailedStatus;
	}
	private boolean isBlockNeedSync(JSONArray nodes, Long id) {
		for (int i = 0; i< nodes.size(); i++) {
			JSONObject node = (JSONObject) nodes.get(i);
			Long nodeID = (Long) node.get(Common.idFiled);
			if (id.compareTo(nodeID) > 0) {
				return true;
			}
		}
		return false;
	}

  @RequestMapping(method = RequestMethod.POST, value = "/oneClick")
  public JSONObject startDeployment() {
    int currentTime = (int) (System.currentTimeMillis() / 1000);
    ConfigGenerator configGenerator = new ConfigGenerator();
    boolean result = configGenerator.updateConfig(new P2PVersion(currentTime), Common.configFiled);
    if (result == false) {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.updateConfigFileFailed).toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

	@RequestMapping(method = RequestMethod.POST, value = "/checkNode")
	public JSONObject checkDeployStatus(@RequestBody ArrayList<Long> idList) {
		JSONObject result = new JSONObject();
		if (idList.isEmpty()) return result;
		for (Long id : idList) {
			String status = checkNodeStatus(String.format(Common.logFormat, id.toString()));
			result.put(id.toString(), status);
		}
		return new Response(ResultCode.OK.code, result).toJSONObject();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deployNode")
	public JSONObject deploy(
			@RequestParam(value = "id", required = false, defaultValue = "1") Long id,
			@RequestParam(value = "path", required = false, defaultValue = "") String path
	) {
		JSONObject json = readJsonFile();
		JSONArray nodes = (JSONArray)json.get(Common.nodesFiled);
		if (Objects.isNull(nodes)) {
			nodes = new JSONArray();
		}

		JSONObject node = Util.getNodeInfo(nodes, id);
		if (node == null) {
			return new Response(ResultCode.NOT_FOUND.code, Common.nodeIdNotExistFailed).toJSONObject();
		}

		boolean isSR = (Boolean) node.get(Common.isSRFiled);
    String privateKeypath = (String) node.get(Common.privateKeyFiled);
    String privateKey = null;
    boolean blockNeedSync = true;
		if (isSR) {
      try {
        privateKey = Wallet.getPrivateString(String.format("%s/%s", Common.walletFiled, privateKeypath));
				blockNeedSync = (Boolean) node.get(Common.needSyncCheck);
      } catch (CipherException | IOException e) {
        LOG.error(e.toString());
        return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "load privateKey info failed").toJSONObject();
      }
    }

		ConfigGenerator configGenerator = new ConfigGenerator();
		boolean result = configGenerator.updateConfig(
				new BlockSettingConfig(blockNeedSync), String.format("%s_%s", Common.configFiled, id.toString()));

		if (result == false) {
			return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, Common.updateConfigFileFailed).toJSONObject();
		}

		String ip = (String) node.get(Common.ipFiled);
		Long port = (Long)node.get(Common.portFiled);
		String userName = (String)node.get(Common.userNameFiled);
		BashExecutor bashExecutor = new BashExecutor();
    String plugin = "null";
    if (json.containsKey(Common.customTransactionFiled)
        && ((String) json.get(Common.customTransactionFiled)).length() != 0) {
      plugin = (String) json.get(Common.customTransactionFiled);
    }

		if (Objects.nonNull(privateKey)) {
			bashExecutor.callScript(ip, port, userName, path, privateKey, id, plugin);
		} else {
			bashExecutor.callScript(ip, port, userName, path, "", id, plugin);
		}

		return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getLogInfo")
	public JSONObject deploy(
			@RequestParam(value = "id", required = false, defaultValue = "1") Long id
	) {
		String logName = String.format(Common.logFormat, id.toString());
		File file = new File(logName);
		List<String> result= new ArrayList<>();
		if (file.isFile() && file.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), Common.encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt;

				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					result.add(lineTxt);
				}
				bufferedReader.close();
				read.close();

			} catch (Exception  e) {
				e.printStackTrace();
				return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "read log info failed").toJSONObject();
			}
		} else {
			return new Response(ResultCode.OK.code, "").toJSONObject();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Common.logInfoFiled, result);
		return new Response(ResultCode.OK.code, jsonObject).toJSONObject();
	}
}