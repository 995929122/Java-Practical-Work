package FrameWork;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.io.FileOutputStream;
import java.awt.Desktop;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ImagePath.imagePath;

public class Words extends JFrame implements ActionListener {
    private Image image;
    imagePath imagePath = new imagePath();
    Button Mastered = new Button("已掌握单词");
    Button unMastered = new Button("未掌握单词");

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

    public Words(String imagePath) {
        super("记个词先");
        FrameBackgroundInit(imagePath);
        this.setLayout(new BorderLayout());

        JPanel SelectPanel = new JPanel();
        SelectPanel.setLayout(new BoxLayout(SelectPanel, BoxLayout.Y_AXIS));
        SelectPanel.setOpaque(false);
        Mastered.setAlignmentX(CENTER_ALIGNMENT); // 设置按钮居中（how？读文档去）
        Mastered.setOpaque(false);
        Mastered.setFont(new Font("Serif", Font.PLAIN | Font.BOLD, 55));
        Mastered.addActionListener(this);
        unMastered.setAlignmentX(CENTER_ALIGNMENT);
        unMastered.setOpaque(false);
        unMastered.setFont(new Font("Serif", Font.PLAIN | Font.BOLD, 55));
        unMastered.addActionListener(this);
        SelectPanel.add(Box.createVerticalStrut(300));
        SelectPanel.add(Mastered);
        SelectPanel.add(Box.createVerticalStrut(80));
        SelectPanel.add(unMastered);
        this.add(SelectPanel, BorderLayout.CENTER);

        // 将按钮放在面板右边 将面板放在窗口下方 完美
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 40)); // FlowLayout.RIGHT 将按钮放置在右边
        buttonPanel.setOpaque(false); // 设置面板透明
        backButton.addActionListener(this);
        buttonPanel.add(backButton, BorderLayout.SOUTH);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            new MainFrame(imagePath.getImagePath());
        }
        if (e.getSource() == Mastered) {
            try {
                Socket socket = new Socket("127.0.0.1", 23337);
                File file = new File("src/main/java/words/已掌握单词.txt");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                socket.close();
                try {
                    Desktop.getDesktop().open(new File("src/main/java/words/已掌握单词.txt"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        if (e.getSource() == unMastered) {
            try {
                System.out.println("unMastered");
                Socket socket = new Socket("127.0.0.1", 23338);
                File file = new File("src/main/java/words/未掌握单词.txt");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                socket.close();
                try {
                    Desktop.getDesktop().open(new File("src/main/java/words/未掌握单词.txt"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}