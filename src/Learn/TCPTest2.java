package Learn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;




//！！！！！！！！！！！！！！！
//会报错找不到指定路径
//因为源文件在VSC中执行
//此处仅作学习用
public class TCPTest2 {
    public void client() {
        Socket s = null;
        FileInputStream fis = null;
        OutputStream os = null;
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 1创建socket对象
            // 指明服务器的ip和端口
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            int port = 18989;
            s = new Socket(ip, port);

            // 2创建file的实例 FileInputSteam的实例
            File file = new File("work_space\\InternetProgram\\default.png");
            fis = new FileInputStream(file);

            // 3通过Socket获取输出流
            os = s.getOutputStream();

            // 读写数据
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            System.out.println("数据发送完毕");
            //表明不在发送数据
            s.shutdownOutput();

            // 4接收服务器端的反馈
            is = s.getInputStream();
            
            byte[] buffer2 = new byte[20];
            int len2;
            while ((len2 = is.read(buffer2)) != -1) {
                baos.write(buffer2, 0, len2);
            }

            System.out.println(baos.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5关闭Socket和相关流
            try {
                if (baos!=null)
                    baos.close();
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
                if (fis != null)
                    fis.close();
                if (s != null)
                    s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void server()  {
        ServerSocket ss = null;
        Socket s = null;
        InputStream is = null;
        FileOutputStream fos = null;
        OutputStream os = null;
        try {
            // 1创建ServerSocket对象
            int port = 18989;
            ss = new ServerSocket(port);

            // 2调用accept()方法，接收客户端的Socket
            s = ss.accept();

            // 3通过Socket获取输入流
            is = s.getInputStream();

            // 4创建File类的实例 FileOutputSteam的实例
            File file = new File("work_space\\InternetProgram\\copy_default.png");
            fos = new FileOutputStream(file);

            // 5读写过程
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            System.out.println("数据接收完毕");

            // 6通知客户端接收完毕
            os = s.getOutputStream();
            os.write("好图 韩了".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7关闭资源
            try {
                if (fos != null)
                    fos.close();
                if (is != null)
                    is.close();
                if (s != null)
                    s.close();
                if (ss != null)
                    ss.close();
                if (os != null)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TCPTest2 tcp = new TCPTest2();
        new Thread(() -> {
            try {
                tcp.server();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> tcp.client()).start();
    }
}
