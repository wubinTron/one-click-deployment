package common;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;

public class SSHConnect {
  private String userName;
  private String hostName;
  private int port;
  private String password;

  public SSHConnect(String userName, String hostName, int port, String password) {
    this.userName = userName;
    this.hostName = hostName;
    this.port = port;
    this.password = password;
  }

  public void cmdhandler() throws Exception {
    JSch jsch = new JSch();

    // connect session
    Session session = jsch.getSession(userName, hostName, port);
  //  session.setPassword(password);
    session.setConfig("StrictHostKeyChecking", "no");
    session.connect();

    // exec command remotely
    String command = "ls -l";
    ChannelExec channel = (ChannelExec) session.openChannel("exec");
    channel.setCommand(command);
    channel.connect();

    // get stdout
    InputStream in = channel.getInputStream();
    byte[] tmp = new byte[1024];
    while (true) {
      while (in.available() > 0) {
        int i = in.read(tmp, 0, 1024);
        if (i < 0)
          break;
        System.out.print(new String(tmp, 0, i));
      }
      if (channel.isClosed()) {
        System.out.println("exit-status: " + channel.getExitStatus());
        break;
      }
      try {
        Thread.sleep(1000);
      } catch (Exception ee) {
      }
    }
    channel.disconnect();
    session.disconnect();
  }

  public static void main(String[] args) {
    SSHConnect sshConnect = new SSHConnect("wubin1", "47.252.2.112", 22008, "");
    try {
      sshConnect.cmdhandler();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
