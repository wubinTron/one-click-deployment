package response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Response {
  public Response(int code, String msg) {
    jsonObject = new JSONObject();
    jsonObject.put("code", code);
    jsonObject.put("msg", msg);
  }

  public Response(int code, JSONArray array) {
    jsonObject = new JSONObject();
    jsonObject.put("code", code);
    jsonObject.put("data", array);
  }

  public Response(int code, JSONObject json) {
    jsonObject = new JSONObject();
    jsonObject.put("code", code);
    jsonObject.put("data", json);
  }

  public JSONObject toJSONObject() {
    return jsonObject;
  }

  private JSONObject jsonObject;

}