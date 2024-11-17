package Learn;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;


//！！！！！！！！！！！！！！！
//会报错找不到指定路径
//因为源文件在VSC中执行
//此处仅作学习用
public class TCPTest {

    public void Server() {
        ServerSocket ss = null;
        Socket s = null;
        InputStream is = null;
        try {
            // 创建一个ServerSocket对象
            int port = 18989;
            ss = new ServerSocket(port);
            // 调用accept()方法，接收客户端的Socket
            s = ss.accept(); // 阻塞式方法  死等
            System.out.println("服务器端已开启");
            System.out.println("客户端的IP地址是：" + s.getInetAddress().getHostAddress()); 

            // 接收数据
            is = s.getInputStream();
            byte[] buffer = new byte[5];
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//内部维护了一个byte[]
            while ((len = is.read(buffer)) != -1) {
                //可能会出现乱码 buffer数组设置较小时，可能切断中文编码导致出现乱码
                //System.out.println(new String(buffer, 0, len));
                //正确方法
                baos.write(buffer, 0, len);
            }
            System.out.println("接收到的数据是：" + baos.toString());
            System.out.println("\n数据接收完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (is != null) {
                    is.close();
                }
                if (s != null) {
                    s.close();
                }
                if (ss != null) {
                    ss.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Client() {
        Socket s = null;
        OutputStream os = null;
        try {
            // 创建一个Socket对象
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            int port = 18989;
            s = new Socket(inetAddress, port);

            // 发送数据
            os = s.getOutputStream();
            os.write("hello，我是客户端".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (os != null) {
                    os.close();
                }
                if (s != null) {
                    s.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TCPTest tcpTest = new TCPTest();

        // 启动服务器线程
        new Thread(() -> tcpTest.Server()).start();

        // 启动客户端线程
        new Thread(() -> tcpTest.Client()).start();
    }
}


//Server的accept()方法是一个阻塞式方法，当没有客户端连接时，会一直等待，直到有客户端连接为止。
//导致下面的代码无法执行