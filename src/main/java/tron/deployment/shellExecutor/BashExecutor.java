package tron.deployment.shellExecutor;

import static common.Common.logFormat;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class BashExecutor {

    public void callScript(String ip, Long port, String userName, String path, String privateKey, Long id){
        try {

            Resource resource = new ClassPathResource("deploy.bash");
            File script =resource.getFile();
            String[] cmdArray = {script.getAbsolutePath(), ip, port.toString(), userName, path};
            if (privateKey.length() != 0) {
                cmdArray = ArrayUtils.add(cmdArray, privateKey);
            }
            String logName = String.format(">> " + logFormat, id.toString());
            cmdArray = ArrayUtils.add(cmdArray, logName);
            String cmd = StringUtils.join(cmdArray, " ");
            Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
//            Process process = Runtime.getRuntime().exec(cmd, evnp, dir);
//            process = Runtime.getRuntime().exec(cmd);
//            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = "";
//            while ((line = input.readLine()) != null) {
//                System.out.println(line);
//            }
//            input.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callDeployScript(String ip, Long port, String userName, String path){
        try {
            Resource resource = new ClassPathResource("deploy.bash");
            File script =resource.getFile();
            String[] cmd = {"bash", script.getAbsolutePath(), ip, port.toString(), userName, path};
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
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callScript2(String ip, Long port, String userName, String path, String privateKey){
        try {
            Resource resource = new ClassPathResource("deploy.bash");
            File script =resource.getFile();
            String[] cmd = {"bash", script.getAbsolutePath(), ip, port.toString(), userName, path};
            if (privateKey.length() != 0) {
                ArrayUtils.add(cmd, privateKey);
            }
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