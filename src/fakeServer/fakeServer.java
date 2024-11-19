package fakeServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class fakeServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(23333);

        // 启动接收数据的线程
        new Thread(() -> {
            try {
                receiveWrongWords();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new DataSender(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 新增接收返回数据的部分
    public static void receiveWrongWords() throws Exception {
        ServerSocket receiveServerSocket = new ServerSocket(23334);
        while (true) {
            Socket receiveSocket = receiveServerSocket.accept();
            InputStream is = receiveSocket.getInputStream();
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\fakeServer\\WrongWords.txt", true));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                writer.write(new String(buffer, 0, len));
            }
            writer.close();
            is.close();
            receiveSocket.close();
        }
    }
}

class DataSender implements Runnable {
    private Socket socket;

    public DataSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        try {
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
