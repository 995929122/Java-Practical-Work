package FrameWork;

import javax.swing.*;

import ImagePath.imagePath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//import java.awt.event.*;

// Label 类
class Label extends JLabel {
    public Label(String text) {
        super(text);
        this.setFont(new Font("Serif", Font.ITALIC, 66)); // 设置字体为Serif，大小66，粗体
        this.setForeground(Color.RED); // 设置文字颜色为红色
    }
}

// Button 类
class Button extends JButton {
    public Button(String TextOnButton) {
        super(TextOnButton);
        setPreferredSize(new Dimension(200, 65)); // 设置按钮的固定大小
        setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 44)); // 设置按钮的字体
        setBackground(new Color(0, 0, 0, 0)); // 设置背景颜色为透明
        setForeground(Color.BLACK); // 设置按钮文字颜色
        setFocusPainted(false); // 去掉按钮点击时的边框效果
        setBorder(null); // 设置按钮边框
        setOpaque(false); // 设置按钮为透明背景
        setContentAreaFilled(false); // 去掉按钮点击后的背景效果
    }
}

// ImagePanel 类
class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // 调整图片尺寸以填满窗口
    }		//没有这个真画不出来图
}
// 问AI paintComponent 方法是由 Swing 框架自动调用的




public class MainFrame extends JFrame implements ActionListener {

    private Image image;
    imagePath imagePath=new imagePath();
    Button button1 = new Button("Start");
    Button button2 = new Button("Words");
    Button button3 = new Button("Settings");
    Button button4 = new Button("Exit");

    Label jlb = new Label("   The name of whole project");
    
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
    
    public MainFrame(String imagePath) {		//界面初始化
        super("记个词先");
        FrameBackgroundInit(imagePath);
        this.setLayout(new BorderLayout());		//设置布局管理器
       
        // 创建一个面板来放置标签，并使用 BoxLayout 来控制间距
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));		//不进行这个设置就会跑到左边
        labelPanel.setOpaque(false); // 设置为透明 避免把我的图图盖住了
        labelPanel.add(Box.createVerticalStrut(30)); // 设置顶部间距为30像素
        labelPanel.add(jlb);
        // 将标签面板添加到窗口顶部
        this.add(labelPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // 设置为透明，以便背景图不被遮挡
        // 将按钮添加到面板并绑定按钮事件
        buttonPanel.add(button1);button1.addActionListener(this);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,45 ))); 
        buttonPanel.add(button2);button2.addActionListener(this);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,45 )));
        buttonPanel.add(button3);button3.addActionListener(this);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,45 )));
        buttonPanel.add(button4);button4.addActionListener(this);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,45 )));
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent e) {
    	if (e.getSource()==button1) {
    		new StartFrame(imagePath.getImagePath());
    		this.dispose();
    	}
    	if (e.getSource()==button2) {
    		new Words(imagePath.getImagePath());
    		this.dispose();
    	}
    	if (e.getSource()==button3) {
    		new SettingsFrame(imagePath.getImagePath());
    		this.dispose();
    	}
    	if (e.getSource()==button4) {
    		int result =JOptionPane.showConfirmDialog(null,"确定退出程序？","Exit",JOptionPane.YES_NO_CANCEL_OPTION);
    		if(result == JOptionPane.YES_OPTION) {
    			System.exit(0);
    		} else if (result == JOptionPane.NO_OPTION) {
    		    // 用户点击了"No"按钮
    		} else if (result == JOptionPane.CANCEL_OPTION) {
    		    // 用户点击了"取消"按钮
    		} else {
    		    // 用户关闭了确认对话框
    		}

    	
    	}
    }




}



