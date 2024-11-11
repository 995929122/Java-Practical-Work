package FrameWork;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WrongFrame extends JFrame implements ActionListener {
	private Image image;
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
	
    Label label = new Label("Welcome to Wrong Words ");
    Button backButton=new Button("Back");
    public WrongFrame(String imagePath) {
		super("记个词先");
		FrameBackgroundInit(imagePath);
		this.setLayout(new BorderLayout());
		
		this.add(label, BorderLayout.NORTH);
		
		//将按钮放在面板右边 将面板放在窗口下方 完美
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 40));  // FlowLayout.RIGHT 将按钮放置在右边
	    buttonPanel.setOpaque(false);  // 设置面板透明
        backButton.addActionListener(this);
        buttonPanel.add(backButton, BorderLayout.SOUTH);  
        this.add(buttonPanel, BorderLayout.SOUTH);
		
	}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            new MainFrame("D:\\Java_eclipse_workspace\\Practical_Work\\src\\FrameWork\\image.png");
        }
    }
}
