package Modes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import FrameWork.StartFrame;

public class Mode1_Panel extends JPanel implements ActionListener {
    Socket socket = null;
    InputStream is = null;
    JPanel panel = new JPanel();
    JLabel information = null;
    JButton submitButton = new JButton("submit");
    JTextField firstTextField;
    JTextField middleTextField;
    JTextField lastTextField;
    JPanel textFieldPanel;
    StringBuffer part1;
    StringBuffer part2;
    StartFrame parentFrame;

    public Mode1_Panel(StartFrame parentFrame) throws Exception {
        this.parentFrame = parentFrame;
        this.setOpaque(false);
        panel.setOpaque(false);
        fetchInformation();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 设置垂直布局
        information.setAlignmentX(CENTER_ALIGNMENT); // 设置 JLabel 在中轴线显示
        information.setFont(new Font("Serif", Font.PLAIN, 70)); // 设置 JLabel 字体大小为 70px
        submitButton.setAlignmentX(CENTER_ALIGNMENT); // 设置 JButton 在中轴线显示
        submitButton.setFont(new Font("Serif", Font.PLAIN, 40)); // 设置 JButton 字体大小为 40px
        panel.add(Box.createVerticalStrut(90)); // 添加垂直间距
        panel.add(information); // 显示单词解释
        panel.add(Box.createVerticalStrut(200)); // 添加垂直间距

        textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.X_AXIS));

        firstTextField = new JTextField(1);
        firstTextField.setFont(new Font("Serif", Font.PLAIN, 45)); // 设置字体大小为 30px
        firstTextField.setEditable(false);
        firstTextField.setOpaque(false);
        firstTextField.setMaximumSize(firstTextField.getPreferredSize());
        textFieldPanel.add(firstTextField);

        middleTextField = new JTextField();
        middleTextField.setFont(new Font("Serif", Font.PLAIN, 45)); // 设置字体大小为 30px
        middleTextField.addActionListener(this); // 添加 ActionListener
        textFieldPanel.add(middleTextField);

        lastTextField = new JTextField(1);
        lastTextField.setFont(new Font("Serif", Font.PLAIN, 45)); // 设置字体大小为 30px
        lastTextField.setEditable(false);
        lastTextField.setOpaque(false);
        lastTextField.setMaximumSize(lastTextField.getPreferredSize());
        textFieldPanel.add(lastTextField);

        panel.add(textFieldPanel);
        panel.add(Box.createVerticalStrut(200));
        panel.add(submitButton);
        submitButton.addActionListener(this);
        this.add(panel);

        // 设置初始值
        if (part1.length() > 0) {
            firstTextField.setText(String.valueOf(part1.charAt(0)));
            lastTextField.setText(String.valueOf(part1.charAt(part1.length() - 1)));
        }
    }

    public String getInformationText() {
        return part1.toString() + " " + part2.toString();
    }

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
            int response = JOptionPane.showOptionDialog(this, receivedMessage, "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (response == JOptionPane.OK_OPTION) {
                parentFrame.actionPerformed(new ActionEvent(parentFrame.getBackButton(), ActionEvent.ACTION_PERFORMED, null));
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
                information = new JLabel(part2.toString(), JLabel.CENTER);
            } else {
                information.setText(part2.toString());
            }
        }
        socket.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton || e.getSource() == middleTextField) {
            // 判断用户输入是否正确
            if (middleTextField.getText().equals(part1.substring(1, part1.length() - 1))) {
                JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // 向服务器端回传信息
                try {
                    Socket sendSocket = new Socket("127.0.0.1", 23334);
                    OutputStream os = sendSocket.getOutputStream();
                    String message = part1.toString() + " " + part2.toString() + "\n";
                    os.write(message.getBytes());
                    os.flush();
                    os.close();
                    sendSocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Wrong!", "Result", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                fetchInformation();
                firstTextField.setText(String.valueOf(part1.charAt(0)));
                middleTextField.setText(""); // 清空 middleTextField 等待用户输入
                lastTextField.setText(String.valueOf(part1.charAt(part1.length() - 1)));
                this.revalidate();
                this.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
