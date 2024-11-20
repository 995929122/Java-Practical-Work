package FrameWork;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.Socket;
import javax.swing.*;
import Modes.*;

public class StartFrame extends JFrame implements ActionListener {
    private Image image;
    private Mode1_Panel mode1Panel;

    public void FrameBackgroundInit(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePanel imagePanel = new ImagePanel(image);
        this.setContentPane(imagePanel);
        this.setSize(1700, 1000);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    Button backButton = new Button("Back");

    Button Mode1 = new Button("Mode1");
    Button Mode2 = new Button("Mode2");

    Box ModeBox = Box.createVerticalBox();

    // 实例化面板
    public StartFrame(String imagePath) {
        super("记个词先");
        FrameBackgroundInit(imagePath);
        this.setLayout(new BorderLayout());

        ModeBox.add(Box.createHorizontalStrut(250));
        // 添加按钮并设置按钮字体
        ModeBox.add(Box.createVerticalStrut(350));
        Mode1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 66));
        ModeBox.add(Mode1);
        ModeBox.add(Box.createVerticalStrut(50));
        Mode2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 66));
        ModeBox.add(Mode2);

        this.add(ModeBox, BorderLayout.NORTH);

        Mode1.addActionListener(this);
        Mode2.addActionListener(this);

        // 将按钮放在面板右边 将面板放在窗口下方 完美
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 40)); // FlowLayout.RIGHT 将按钮放置在右边
        buttonPanel.setOpaque(false); // 设置面板透明
        backButton.addActionListener(this);
        buttonPanel.add(backButton, BorderLayout.SOUTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            if (mode1Panel != null) {
                try {
                    // 回传信息到服务器并保存到 WrongWords.txt 中
                    Socket sendSocket = new Socket("127.0.0.1", 23334);
                    OutputStream os = sendSocket.getOutputStream();
                    String message = mode1Panel.getInformationText() + "\n";
                    os.write(message.getBytes());
                    os.flush();
                    os.close();
                    sendSocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.dispose();
            new MainFrame("D:\\Java_eclipse_workspace\\Practical_Work\\src\\FrameWork\\image.png");
        }
        if (e.getSource() == Mode1) {
            this.remove(ModeBox); // 移除 ModeBox 面板
            this.revalidate(); // 通知布局管理器重新布局
            this.repaint(); // 重新绘制窗口
            // 在这里添加 Mode1 按钮的逻辑
            try {
                mode1Panel = new Mode1_Panel(this);
                this.add(mode1Panel);
                this.revalidate(); // 通知布局管理器重新布局
                this.repaint(); // 重新绘制窗口
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == Mode2) {
            this.remove(ModeBox);
            this.revalidate(); // 通知布局管理器重新布局
            this.repaint(); // 重新绘制窗口
            // 在这里添加 Mode1 按钮的逻辑
        }
    }
}
