package fakeServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import java.io.IOException;

public class fakeServer extends JFrame {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream os = null;

        // 创建ServerSocket对象
        int port = 23333;
        serverSocket = new ServerSocket(port);

        while (true) {
            try {
                socket = serverSocket.accept();

                // 创建File类实例 向客户端发送数据
                File file = new File("D:\\Java_eclipse_workspace\\wordsTxT\\sorted.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                br.close();
                if (!lines.isEmpty()) {
                    Random rand = new Random();
                    String randomLine = lines.get(rand.nextInt(lines.size()));
                    os = socket.getOutputStream();
                    os.write(randomLine.getBytes());
                    os.flush();
                }

                // 确保所有流都已关闭
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
