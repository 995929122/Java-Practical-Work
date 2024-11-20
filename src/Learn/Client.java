package Learn;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args)throws Exception{
        Socket s = null;
        int port=9999;
        InetAddress inetAddress = InetAddress.getByName("172.17.10.144");

        while (true) {
            try {
            s = new Socket(inetAddress, port);
            break;
            } catch (Exception e) {
            System.out.println("连接失败，重试中...");
            Thread.sleep(100); // 等待1秒后重试
            }
        }
        System.out.println("连接成功");

    }
}
