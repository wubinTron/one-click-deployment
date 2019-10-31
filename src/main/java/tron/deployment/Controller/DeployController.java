package tron.deployment.Controller;

import static common.Util.readJsonFile;

import common.Common;
import common.ResultCode;
import common.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.Response;
import tron.deployment.pojo.ConfigGenerator;
import tron.deployment.pojo.Configuration;
import tron.deployment.shellExecutor.BashExecutor;


@CrossOrigin
@RestController
@Component
@RequestMapping(value = "/")
public class DeployController {

	@RequestMapping(method = RequestMethod.POST, value = "/deployNode")
	public Object deploy(
			@RequestParam(value = "id", required = false, defaultValue = "1") Long id,
			@RequestParam(value = "path", required = false, defaultValue = "") String path
	) {
		JSONObject json = readJsonFile();
		JSONArray nodes = (JSONArray)json.get(Common.nodesFile);
		if (Objects.isNull(nodes)) {
			nodes = new JSONArray();
		}

		JSONObject node = Util.getNodeInfo(nodes, id);
		if (node == null) {
			return new Response(ResultCode.NOT_FOUND.code, "node id not exist").toJSONObject();
		}

		String privateKey = (String) node.get(Common.privateKeyFile);
		String ip = (String) node.get(Common.ipFile);
		Long port = (Long)node.get(Common.portFile);
		String userName = (String)node.get(Common.userNameFile);
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
		jsonObject.put(Common.logInfoFile, result);
		return new Response(ResultCode.OK.code, jsonObject).toJSONObject();
	}

	public void init() {
		BashExecutor bashExecutor = new BashExecutor();
		bashExecutor.callBuildScript("");
	}

}