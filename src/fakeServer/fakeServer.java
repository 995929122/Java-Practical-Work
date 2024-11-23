package fakeServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.io.InputStreamReader;

public class fakeServer {
    private static final String SENT_LINES_FILE = "Practical_Work\\src\\fakeServer\\sentLines.txt";
    public static final String WRONG_WORDS_FILE = "Practical_Work\\src\\fakeServer\\WrongWords.txt";
    public static final String MASTER_WORDS = "Practical_Work\\src\\fakeServer\\mastered.txt";
    public static final String TEXT_FILE = "Practical_Work\\src\\fakeServer\\sorted.txt";
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        serverSocket = new ServerSocket(23333);

        // 启动接收错误单词数据的线程
        new Thread(() -> {
            try {
                receiveWrongWords();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 启动发送正确单词数据的线程
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new DataSender(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 启动发送错误单词的线程
        new Thread(() -> {
            try {
                sendWrongWords();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 启动接收正确单词的线程
        new Thread(() -> {
            try {
                reveiveMasterWords();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 启动发送已掌握或未掌握单词的线程
        new Thread(() -> {
            try {
                sendMastered();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        
        new Thread(() -> {
            try {
                sendunMastered();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendMastered() throws Exception {
        ServerSocket serverSocket = new ServerSocket(23337);
        while (true) {
            Socket socket = serverSocket.accept();
            File file = new File(MASTER_WORDS);
            try (FileInputStream fis = new FileInputStream(file);
                    OutputStream os = socket.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendunMastered() throws Exception {
        ServerSocket serverSocket = new ServerSocket(23338);
        while (true) {
            Socket socket = serverSocket.accept();
            File file = new File(WRONG_WORDS_FILE);
            try (FileInputStream fis = new FileInputStream(file);
                    OutputStream os = socket.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendWrongWords() throws Exception {
        ServerSocket serverSocket = new ServerSocket(23335);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 3);
                if (parts.length == 3) {
                    lines.add(parts[2]);
                }
            }
            reader.close();

            if (!lines.isEmpty()) {
                Random rand = new Random();
                String randomLine = lines.get(rand.nextInt(lines.size()));
                OutputStream os = socket.getOutputStream();
                os.write(randomLine.getBytes());
                os.flush();
                os.close();
            }

            socket.close();
        }

    }

    // 新增接收返回数据的部分
    public static void receiveWrongWords() throws Exception {
        ServerSocket receiveServerSocket = new ServerSocket(23334);
        while (true) {
            Socket receiveSocket = receiveServerSocket.accept();
            InputStream is = receiveSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder messageBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line);
            }
            String message = messageBuilder.toString().trim();
            if (message.equals("DELETE_LAST_LINE")) {
                deleteLastLine();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(WRONG_WORDS_FILE, true));
                writer.write(message);
                writer.newLine();
                writer.close();
            }
            is.close();
            receiveSocket.close();
        }
    }

    public static void reveiveMasterWords() throws Exception {
        ServerSocket receiveServerSocket = new ServerSocket(23336);
        while (true) {
            Socket receiveSocket = receiveServerSocket.accept();
            InputStream is = receiveSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder messageBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line);
            }
            String message = messageBuilder.toString().trim();
            BufferedWriter writer = new BufferedWriter(new FileWriter(MASTER_WORDS, true));
            writer.write(message);
            writer.newLine();
            writer.close();
            is.close();
            receiveSocket.close();
        }
    }

    public static void deleteLastLine() throws IOException {
        File file = new File(SENT_LINES_FILE);
        if (!file.exists()) {
            return;
        }

        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        if (!lines.isEmpty()) {
            lines.remove(lines.size() - 1);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(SENT_LINES_FILE));
        for (String l : lines) {
            writer.write(l);
            writer.newLine();
        }
        writer.close();
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
