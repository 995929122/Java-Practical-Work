package Modes;

import java.io.InputStream;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import FrameWork.StartFrame;

public class Mode2_Panel extends JPanel implements ActionListener {

    Socket socket = null;
    InputStream is = null;
    JLabel information = null;
    StartFrame parentFrame;
    StringBuffer part1;
    StringBuffer part2;
    JPanel panel = new JPanel();
    // 该类用于实现 Mode2 的功能

    // Mode2_Panel() 构造方法用于初始化 Mode2_Panel
    public Mode2_Panel(StartFrame parentFrame) throws Exception {
        this.parentFrame = parentFrame;
        this.setOpaque(false);
        fetchInformation();

        information.setAlignmentX(CENTER_ALIGNMENT);
        information.setFont(new Font("Serif", Font.PLAIN, 70));
        panel.setOpaque(false);
        panel.add(Box.createVerticalStrut(90));
        panel.add(information);

        this.add(panel);

    }

    // fetchInformation() 方法用于获取服务器的数据
    private void fetchInformation() throws Exception {

        socket = new Socket("127.0.0.1", 23333);
        is = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = is.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
        String receivedMessage = stringBuffer.toString().trim();
        if (receivedMessage.equals("单词已经记完，请开始新一轮")) {
            int response = JOptionPane.showOptionDialog(this, receivedMessage, "提示", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (response == JOptionPane.OK_OPTION) {
                parentFrame.actionPerformed(
                        new ActionEvent(parentFrame.getBackButton(), ActionEvent.ACTION_PERFORMED, null));
            }
            part1 = new StringBuffer();
            part2 = new StringBuffer();
        } else {
            String[] parts = receivedMessage.split(" ");
            if (parts.length >= 3) {
                part1 = new StringBuffer(parts[1]);
                part2 = new StringBuffer(parts[2]);
            } else {
                part1 = new StringBuffer();
                part2 = new StringBuffer();
            }
            if (information == null) {
                information = new JLabel(part1.toString(), JLabel.CENTER);
            } else {
                information.setText(part1.toString());
            }
        }
        socket.close();
    }

    //一个成员函数 接新的服务器端口 随机接收三行数据并加工处理

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
