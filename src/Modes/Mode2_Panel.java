package Modes;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.Collections;

import FrameWork.MainFrame;
import FrameWork.StartFrame;
import ImagePath.imagePath;

public class Mode2_Panel extends JPanel implements ActionListener {
    InputStream is = null;
    private StartFrame parentFrame;
    private Socket socket;
    private JPanel panel = new JPanel();
    private JLabel information = new JLabel();
    StringBuffer part1;
    StringBuffer part2;
    private String answer = null; // 用于存储正确答案
    String[] wrongAnswer = new String[3]; // 用于存储错误答案
    JButton confirmButton = new JButton("confirm");
    JPanel buttonPanel = new JPanel();
    ButtonGroup buttonGroup = new ButtonGroup();
    JLabel timerLabel;
    int timeRemaining;
    public javax.swing.Timer timer;
    int MARK = 0;
    imagePath imagePath = new imagePath();

    // Mode2_Panel() 构造方法用于初始化 Mode2_Panel
    public Mode2_Panel(StartFrame parentFrame) throws Exception {
        this.parentFrame = parentFrame;
        this.setOpaque(false);

        fetchInformation();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        information.setAlignmentX(CENTER_ALIGNMENT);
        information.setFont(new Font("Serif", Font.BOLD, 70));
        panel.setOpaque(false);
        panel.add(Box.createVerticalStrut(160));
        panel.add(information);
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
                    int response = JOptionPane.showOptionDialog(Mode2_Panel.this, "您没有回答，正确答案是" + part2.toString(),
                            "Warning",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                    if (response == JOptionPane.OK_OPTION) {
                        // 重新获取信息并重绘面板
                        if (checkMARK()) {
                            try {
                                fetchInformation();
                                buttonPanel.removeAll();
                                for (int i = 0; i < 3; i++) {
                                    wrongAnswer[i] = fetchWrongInformation();
                                }
                                buttonGroup = new ButtonGroup();
                                String[] options = new String[4];
                                options[0] = answer;
                                System.arraycopy(wrongAnswer, 0, options, 1, wrongAnswer.length);
                                // Shuffle the options array to randomize the order
                                java.util.Collections.shuffle(java.util.Arrays.asList(options));
                                for (String option : options) {
                                    JRadioButton radioButton = new JRadioButton(option);
                                    radioButton.setOpaque(false);
                                    radioButton.setAlignmentX(CENTER_ALIGNMENT);
                                    radioButton.setFont(new Font("Serif", Font.PLAIN, 40));
                                    buttonGroup.add(radioButton);
                                    buttonPanel.add(radioButton);
                                }
                                buttonPanel.revalidate();
                                buttonPanel.repaint();
                                // 重启计时器
                                resetTimer();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        resetTimer();

        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i++) {
            wrongAnswer[i] = fetchWrongInformation();
        }
        String[] options = new String[4];
        options[0] = answer;
        System.arraycopy(wrongAnswer, 0, options, 1, wrongAnswer.length);
        // Shuffle the options array to randomize the order
        java.util.Collections.shuffle(java.util.Arrays.asList(options));
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setAlignmentX(CENTER_ALIGNMENT);
            radioButton.setOpaque(false);
            radioButton.setFont(new Font("Serif", Font.PLAIN, 40));
            buttonGroup.add(radioButton);
            buttonPanel.add(radioButton);
        }
        panel.add(buttonPanel);

        confirmButton.setAlignmentX(CENTER_ALIGNMENT);
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 40));
        panel.add(Box.createVerticalStrut(50));
        panel.add(confirmButton);
        confirmButton.addActionListener(this);

        this.add(panel);
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
            JOptionPane.showMessageDialog(Mode2_Panel.this, "Game Over", "Warning", JOptionPane.WARNING_MESSAGE);
            parentFrame.dispose();
            new MainFrame(imagePath.getImagePath());
            return false;
        }
        return true;
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
                answer = part2.toString();
            } else {
                information.setText(part1.toString());
                answer = part2.toString();
            }
        }
        socket.close();

    }

    public String fetchWrongInformation() throws Exception {
        socket = new Socket("127.0.0.1", 23335);
        is = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = is.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
        String receivedMessage = stringBuffer.toString().trim();
        socket.close();
        return receivedMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractButton button = null;
        for (AbstractButton b : Collections.list(buttonGroup.getElements())) {
            if (b.isSelected()) {
                button = b;
                break;
            }
        }
        if (button == null) {
            JOptionPane.showMessageDialog(this, "Please select an option.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String selected = button.getText();
        if (selected.equals(answer)) {
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
            // 回传错误单词
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
            JOptionPane.showMessageDialog(this, "回答错误，答案是" + part2.toString(), "Result",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (checkMARK()) {
            resetTimer();
            try {
                fetchInformation();
                buttonPanel.removeAll();
                for (int i = 0; i < 3; i++) {
                    wrongAnswer[i] = fetchWrongInformation();
                }
                buttonGroup = new ButtonGroup();
                String[] options = new String[4];
                options[0] = answer;
                System.arraycopy(wrongAnswer, 0, options, 1, wrongAnswer.length);
                // Shuffle the options array to randomize the order
                java.util.Collections.shuffle(java.util.Arrays.asList(options));
                for (String option : options) {
                    JRadioButton radioButton = new JRadioButton(option);
                    radioButton.setOpaque(false);
                    radioButton.setAlignmentX(CENTER_ALIGNMENT);
                    radioButton.setFont(new Font("Serif", Font.PLAIN, 40));
                    buttonGroup.add(radioButton);
                    buttonPanel.add(radioButton);
                }
                buttonPanel.revalidate();
                buttonPanel.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
