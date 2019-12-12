package tron.deployment.shellExecutor;

import static common.Common.logFormat;
import static common.LogConfig.LOG;

import common.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class BashExecutor {

    public void callScript(String ip, Long port, String userName, String jarPath, String privateKey, Long id, String plugin){
        try {
            String absolutePath = System.getProperty("user.dir").concat("/deployNode.bash");
            String configPath = String.format("%s_%s", Common.configFiled, id.toString());
            String[] cmdArray = {absolutePath, ip, port.toString(), userName, jarPath, configPath, plugin};
            if (privateKey.length() != 0) {
                cmdArray = ArrayUtils.add(cmdArray, privateKey);
            }
            String logName = String.format("> ".concat(logFormat), id.toString());
            cmdArray = ArrayUtils.add(cmdArray, logName);
            String cmd = StringUtils.join(cmdArray, " ");
            Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
            LOG.info("deploy cmd: {}", cmd);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}