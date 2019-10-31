package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigRenderOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;
import tron.deployment.pojo.Configuration;

public class Util {
  public static com.typesafe.config.Config config;
  public static  com.typesafe.config.Config originConfig;

  public static void parseConfig() {
    File confFile = new File(Common.configFile);
    config = ConfigFactory.parseFile(confFile);
  }

  public static void parseOriginConfig() {
    File confFile = new File(Common.originConfigFile);
    config = ConfigFactory.parseFile(confFile);
  }

  static public void generateConfig(Configuration configuration){
    // Load the original config file
    File defaultConfigFile = new File(Common.configFile);
    Config defaultConfig = ConfigFactory.parseFile(defaultConfigFile);
    //use string builder to generate Config String
    StringBuilder configSB = new StringBuilder();
    Field[] fields = configuration.getClass().getFields();
    for(Field field : fields ){
      String fieldName = field.getName();
      fieldName = fieldName.replaceAll("_",".");
      Object value = null;
      try {
        value = field.get(configuration); // get the value
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      if (value!=null) {
        configSB.append(fieldName).append("=")
            .append(value.toString()).append(",");
      }
    }
    Config modifiedConfig = ConfigFactory.parseString(configSB.toString());
    Config newConfig = modifiedConfig.withFallback(defaultConfig);
    String configStr = newConfig.root().render(
        ConfigRenderOptions.defaults().setOriginComments(false).setComments(false).setJson(false));
    File tempConfigFile = new File(Common.configFile);
    try{
      tempConfigFile.delete();
      tempConfigFile.createNewFile();
      FileWriter fileWriter = new FileWriter(tempConfigFile);
      fileWriter.write(configStr);
      fileWriter.flush();
      fileWriter.close();
      System.out.println("finish generation");
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }

  static public JSONObject readJsonFile() {
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;
    try {
      Object obj = parser.parse(new FileReader(Common.databaseFile));
      jsonObject = (JSONObject) obj;
    } catch (IOException  | ParseException e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  static public boolean writeJsonFile(JSONObject json) {
    Writer write;
    try {
      write = new OutputStreamWriter(new FileOutputStream(Common.databaseFile), "UTF-8");
      write.write(json.toString());
      write.flush();
      write.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  static public JSONObject getNodeInfo(JSONArray nodes, Long id) {
    for (int i = 0; i< nodes.size(); i++) {
      JSONObject node = (JSONObject) nodes.get(i);
      Long nodeID = (Long) node.get(Common.idFile);
      if (nodeID == id) {
        return node;
      }
    }
    return null;
  }
}
