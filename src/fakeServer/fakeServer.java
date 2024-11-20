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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class fakeServer {
    private static final String SENT_LINES_FILE = "src/fakeServer/sentLines.txt";
    public static final String WRONG_WORDS_FILE = "src/fakeServer/WrongWords.txt";
    public static final String TEXT_FILE = "D:\\Java_eclipse_workspace\\wordsTxT\\sorted.txt";

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

        // 启动发送数据的线程
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(WRONG_WORDS_FILE, true));
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

    public static Set<String> loadSentLines() throws IOException {
        Set<String> sentLines = new HashSet<>();
        File file = new File(SENT_LINES_FILE);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sentLines.add(line);
            }
            reader.close();
        }
        return sentLines;
    }

    public static void saveSentLine(String line) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SENT_LINES_FILE, true));
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    public static void clearSentLines() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SENT_LINES_FILE));
        writer.write("");
        writer.close();
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
            // 加载已发送的行
            Set<String> sentLines = fakeServer.loadSentLines();

            // 创建File类实例 向客户端发送数据
            File file = new File(fakeServer.TEXT_FILE);
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!sentLines.contains(line)) {
                    lines.add(line);
                }
            }
            br.close();

            if (!lines.isEmpty()) {
                Random rand = new Random();
                String randomLine = lines.get(rand.nextInt(lines.size()));
                os = socket.getOutputStream();
                os.write(randomLine.getBytes());
                os.flush();

                // 记录已发送的行
                fakeServer.saveSentLine(randomLine);
            } else {
                // 所有数据均已发送，通知客户端并清空记录
                os = socket.getOutputStream();
                String message = "单词已经记完，请开始新一轮";
                os.write(message.getBytes());
                os.flush();

                // 清空已发送的行的记录
                fakeServer.clearSentLines();

                // 向 WrongWords.txt 中写入空行
                BufferedWriter writer = new BufferedWriter(new FileWriter(fakeServer.WRONG_WORDS_FILE, true));
                writer.newLine();
                writer.close();
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
