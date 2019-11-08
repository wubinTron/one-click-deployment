package tron.deployment.shellExecutor;

import static common.Common.logFormat;

import common.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Slf4j
public class BashExecutor {

    public void callScript(String ip, Long port, String userName, String jarPath, String privateKey, Long id){
        try {
            String absolutePath = System.getProperty("user.dir").concat("/deployNode.bash");
            String configPath = String.format("%s_%s", Common.configFiled, id.toString());
            String[] cmdArray = {absolutePath, ip, port.toString(), userName, jarPath, configPath};
            if (privateKey.length() != 0) {
                cmdArray = ArrayUtils.add(cmdArray, privateKey);
            }
            String logName = String.format("> ".concat(logFormat), id.toString());
            cmdArray = ArrayUtils.add(cmdArray, logName);
            String cmd = StringUtils.join(cmdArray, " ");
            Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
            log.info("deploy cmd: {}", cmd);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callBuildScript(String branch){
        try {
            Resource resource = new ClassPathResource("build.bash");
            File script =resource.getFile();
            String[] cmd = {"bash", script.getAbsolutePath(), branch};
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BashExecutor call = new BashExecutor();
        call.callBuildScript("");
    }
}