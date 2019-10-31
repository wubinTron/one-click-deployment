package tron.deployment.Controller;

import static common.Util.readJsonFile;
import static common.Util.writeJsonFile;

import common.Common;
import common.ResultCode;
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
public class BranchController {
  @RequestMapping(method = RequestMethod.GET, value = "/projectBranch")
  public JSONObject getBranch() {
    JSONObject json = readJsonFile();
    JSONObject result = new JSONObject();
    result.put(Common.branchFileName, json.get(Common.branchFileName));
    result.put(Common.branchNoteFileName, json.get(Common.branchNoteFileName));
    return new Response(ResultCode.OK.code, result).toJSONObject();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/projectBranch")
  public JSONObject saveBranch(
      @RequestParam(value = "branchName", required = false, defaultValue = "develop") String branchName,
      @RequestParam(value = "branchNote", required = false, defaultValue = "") String brachNote
  ) {

    JSONObject json = readJsonFile();
    json.put(Common.branchFileName, branchName);
    json.put(Common.branchNoteFileName, brachNote);
    if (!writeJsonFile(json))  {
      return new Response(ResultCode.INTERNAL_SERVER_ERROR.code, "write json file failed").toJSONObject();
    }
    return new Response(ResultCode.OK_NO_CONTENT.code, "").toJSONObject();
  }

}
