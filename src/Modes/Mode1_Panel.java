package Modes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import FrameWork.MainFrame;
import FrameWork.StartFrame;
import ImagePath.imagePath;

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
    public javax.swing.Timer timer;
    int timeRemaining;
    JLabel timerLabel;
    int MARK = 0;
    JLabel markLabel=null;
    imagePath imagePath = new imagePath();

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
        panel.add(Box.createVerticalStrut(150)); // 添加垂直间距
        panel.add(information); // 显示单词解释
        panel.add(Box.createVerticalStrut(120)); // 添加垂直间距

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
        panel.add(Box.createVerticalStrut(100));

        // 创建一个新的 JPanel 作为计时器面板
        JPanel timerPanel = new JPanel();
        timerPanel.setOpaque(false); // 设置背景透明
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerLabel = new JLabel("10", JLabel.CENTER);
        timerLabel.setForeground(java.awt.Color.RED);
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        timerLabel.setAlignmentX(CENTER_ALIGNMENT); // 设置 JLabel 在中轴线显示
        timerPanel.add(timerLabel);
        panel.add(timerPanel);


        panel.add(submitButton);
        submitButton.addActionListener(this);
        this.add(panel);
        
        //分数显示
        markLabel = new JLabel("当前分数 " + MARK, JLabel.CENTER);
        markLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        markLabel.setAlignmentX(CENTER_ALIGNMENT); // 设置 JLabel 在中轴线显示
        panel.add(markLabel);
        panel.add(Box.createVerticalStrut(30));

        // 设置初始值
        if (part1.length() > 0) {
            firstTextField.setText(String.valueOf(part1.charAt(0)));
            lastTextField.setText(String.valueOf(part1.charAt(part1.length() - 1)));
        }

        // 创建一个计时器
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText(String.valueOf(timeRemaining));
                if (timeRemaining <= 0) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    // 向服务器端回传信息
                    try {
                        Socket sendSocket = new Socket("127.0.0.1", 23334);
                        OutputStream os = sendSocket.getOutputStream();
                        String message = part1.toString() + " " + part2.toString() + "  未回答" + "\n";
                        os.write(message.getBytes());
                        os.flush();
                        os.close();
                        sendSocket.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    MARK--;
                    // 显示时间耗尽的提示
                    int response = JOptionPane.showOptionDialog(Mode1_Panel.this, "您没有回答，答案是" + part1.toString(),
                            "Warning",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                    if (response == JOptionPane.OK_OPTION) {

                        // 重新获取信息并重绘面板
                        if (checkMARK()) {
                            try {
                                fetchInformation();
                                firstTextField.setText(String.valueOf(part1.charAt(0)));
                                middleTextField.setText(""); // 清空 middleTextField 等待用户输入
                                lastTextField.setText(String.valueOf(part1.charAt(part1.length() - 1)));
                                markLabel.setText("当前分数 " + MARK);
                                Mode1_Panel.this.revalidate();
                                Mode1_Panel.this.repaint();
                                resetTimer();// 重启计时器
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        resetTimer();

    }

    private void resetTimer() {
        timeRemaining = 10;
        timerLabel.setText(String.valueOf(timeRemaining));
        timer.start();
    }

    public boolean checkMARK() {
        if (MARK <= 0) {
            timer.stop();
            try {
                // 通知服务器删除 sentLines.txt 最后一行
                Socket sendSocket = new Socket("127.0.0.1", 23334);
                OutputStream os = sendSocket.getOutputStream();
                String message = "DELETE_LAST_LINE\n";
                os.write(message.getBytes());
                os.flush();
                os.close();
                sendSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(Mode1_Panel.this, "Game Over", "Warning", JOptionPane.WARNING_MESSAGE);
            parentFrame.dispose();
            new MainFrame(imagePath.getImagePath());
            return false;
        }
        return true;
    }

    // public String getInformationText() {
    // return part1.toString() + " " + part2.toString();
    // }

    private void fetchInformation() throws Exception {
        do {
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
                    information = new JLabel(part2.toString(), JLabel.CENTER);
                } else {
                    information.setText(part2.toString());
                }
            }
            socket.close();
        } while (part1.length() <= 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton || e.getSource() == middleTextField) {
            // 停止计时器
            timer.stop();
            // 判断用户输入是否正确
            if (middleTextField.getText().equals(part1.substring(1, part1.length() - 1))) {
                MARK++;
                JOptionPane.showMessageDialog(this, "恭喜回答正确", "Result", JOptionPane.INFORMATION_MESSAGE);
                try {
                    // 传输正确的单词
                    Socket sendSocket = new Socket("127.0.0.1", 23336);
                    OutputStream os = sendSocket.getOutputStream();
                    String message = part1.toString() + " " + part2.toString() + "\n";
                    os.write(message.getBytes());
                    os.flush();
                    os.close();
                    sendSocket.close();
                } catch (Exception ex) {

                }
            } else {
                MARK -= 2;
                // 向服务器端回传信息
                try {
                    Socket sendSocket = new Socket("127.0.0.1", 23334);
                    OutputStream os = sendSocket.getOutputStream();
                    String message = part1.toString() + " " + part2.toString() + "  回答错误" + "\n";
                    os.write(message.getBytes());
                    os.flush();
                    os.close();
                    sendSocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "回答错误，答案是" + part1.toString(), "Result",
                        JOptionPane.INFORMATION_MESSAGE);

            }
            if (checkMARK()) {
                try {
                    fetchInformation();
                    firstTextField.setText(String.valueOf(part1.charAt(0)));
                    middleTextField.setText(""); // 清空 middleTextField 等待用户输入
                    lastTextField.setText(String.valueOf(part1.charAt(part1.length() - 1)));
                    markLabel.setText("当前分数 " + MARK);
                    this.revalidate();
                    this.repaint();
                    resetTimer();// 重启计时器
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
