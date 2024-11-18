package FrameWork;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;



public class StartFrame extends JFrame implements ActionListener {
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
	
    
    Button backButton=new Button("Back");

    Button Mode1=new Button("Mode1");
    Button Mode2=new Button("Mode2");
    


    Box ModeBox=Box.createVerticalBox();
    
    //实例化面板
    public StartFrame(String imagePath) {
		super("记个词先");
		FrameBackgroundInit(imagePath);
		this.setLayout(new BorderLayout());
		
        ModeBox.add(Box.createHorizontalStrut(250));
        //添加按钮并设置按钮字体
        ModeBox.add(Box.createVerticalStrut(350));
		Mode1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 66));ModeBox.add(Mode1);
		ModeBox.add(Box.createVerticalStrut(50));
        Mode2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 66));ModeBox.add(Mode2);
		
		this.add(ModeBox,BorderLayout.NORTH);
		
 
		Mode1.addActionListener(this);
		Mode2.addActionListener(this);
		
        
		
		//将按钮放在面板右边 将面板放在窗口下方 完美
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 40)); // FlowLayout.RIGHT 将按钮放置在右边
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
        if(e.getSource()==Mode1) {
        	System.out.println("Mode1 按钮被点击！");
        	this.remove(ModeBox); // 移除 ModeBox 面板
            this.revalidate();    // 通知布局管理器重新布局
            this.repaint();       // 重新绘制窗口
            // 在这里添加 Mode1 按钮的逻辑
        }
        if(e.getSource()==Mode2) {
        	System.out.println("Mode2 按钮被点击！");
        	this.remove(ModeBox); // 移除 ModeBox 面板
            this.revalidate();    // 通知布局管理器重新布局
            this.repaint();       // 重新绘制窗口
            // 在这里添加 Mode1 按钮的逻辑
        }
        
    }
}


class Mode1_Panel extends JPanel{
        

}

