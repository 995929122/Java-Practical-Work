package fakeServer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TCP_client extends JFrame implements ActionListener ,Runnable {
    private JTextArea taMsg = new JTextArea("下为聊天记录\n");
    private JTextField tfMsg = new JTextField("");
    private Socket socket = null;
    private String nickName = null;

    public TCP_client() {
        this.setTitle("TCP客户端");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(taMsg, BorderLayout.CENTER);
        this.add(tfMsg, BorderLayout.SOUTH);
        tfMsg.addActionListener(this);
        this.setVisible(true);
        nickName = JOptionPane.showInputDialog("请输入昵称");
        try {
            socket = new Socket("101.34.239.84", 9999);//连接虚拟机
            JOptionPane.showMessageDialog(this, "连接成功");
            this.setTitle("客户端" + nickName);
            new Thread(this).start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void run() {
        try {
            while (true) {
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = br.readLine();
                taMsg.append(str + "\n");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void actionPerformed(ActionEvent e){
        try {
            OutputStream os=socket.getOutputStream();
            PrintStream ps=new PrintStream(os);
            ps.println(nickName+":"+tfMsg.getText());
            //ps.flush();
            tfMsg.setText("");
        } catch (Exception e2) {

        }
    }
    
    public static void main(String[] args) {
        TCP_client client = new TCP_client();
    }
}
