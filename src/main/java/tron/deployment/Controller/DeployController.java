package tron.deployment.Controller;

import static common.Util.readJsonFile;

import common.Common;
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

	@RequestMapping(method = RequestMethod.POST, value = "/deployNode")
	public Object deploy(
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
			return new Response(ResultCode.NOT_FOUND.code, "node id not exist").toJSONObject();
		}

		String privateKeypath = (String) node.get(Common.privateKeyFiled);
		String privateKey;
		try {
			privateKey = Wallet.getPrivateString(String.format("%s/%s", Common.walletFiled, privateKeypath));
		} catch (CipherException | IOException e) {
			log.error(e.toString());
			return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "load node info failed").toJSONObject();
		}

		String ip = (String) node.get(Common.ipFiled);
		Long port = (Long)node.get(Common.portFiled);
		String userName = (String)node.get(Common.userNameFiled);
		BashExecutor bashExecutor = new BashExecutor();
		if (privateKey.length() != 0) {
			bashExecutor.callScript(ip, port, userName, path, privateKey, id);
		} else {
			bashExecutor.callScript(ip, port, userName, path, "", id);
		}

		return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getLogInfo")
	public JSONObject deploy(
			@RequestParam(value = "id", required = false, defaultValue = "1") Long id
	) {
		String logName = String.format(Common.logFormat, id.toString());
		File file = new File(logName);
		String encoding = "GBK";
		List<String> result= new ArrayList<>();
		if (file.isFile() && file.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
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
			return new Response(ResultCode.NOT_FOUND.code, "log info file not found").toJSONObject();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Common.logInfoFiled, result);
		return new Response(ResultCode.OK.code, jsonObject).toJSONObject();
	}

	public void init() {
		BashExecutor bashExecutor = new BashExecutor();
		bashExecutor.callBuildScript("");
	}

}