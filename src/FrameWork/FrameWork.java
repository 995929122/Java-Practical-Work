package FrameWork;

import javax.swing.*;

import FrameWork.LearnToSetBackground.ImagePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.*;


public class FrameWork extends JFrame{

	private JPanel jpl=new JPanel();//主面板
	
	//Label
	class Label extends JLabel{
		public Label(String text) {
			super(text);
			this.setFont(new Font("Serif", Font.ITALIC, 36)); // 设置字体为Serif，大小24，粗体
	        this.setForeground(Color.RED); // 设置文字颜色为红色
	        //this.setHorizontalAlignment(SwingConstants.LEFT); // 设置文字水平左对齐
	        //this.setVerticalAlignment(SwingConstants.TOP); // 设置文字垂直居顶
	        //this.setOpaque(false);
	        //this.setBorder(null);
		}
	}
	private Label jlb=new Label("The name of whole project");	
	
	
	//Button
	class Button extends JButton {
		public Button(String TextOnButton) {
			super(TextOnButton);
			setPreferredSize(new Dimension(300, 60)); // 设置按钮的固定大小
			setFont(new Font("Serif", Font.ITALIC, 28)); // 设置按钮的字体
			setBackground(new Color(0, 0, 0, 0)); // 设置背景颜色为透明
			setForeground(Color.BLACK); // 设置按钮文字颜色
			setFocusPainted(false); // 去掉按钮点击时的边框效果
			setBorder(null); // 设置按钮边框
			//setHorizontalAlignment(SwingConstants.LEFT); // 设置按钮文字左对齐
			//setVerticalAlignment(SwingConstants.CENTER); // 设置按钮文字垂直居中
			setOpaque(false); // 设置按钮为透明背景
		}
	}
	

	private Image image;
	public  FrameWork(String imagePath) {	//构造函数					////////////
		//主界面基础框架
		super("记个词先");
		
		try {
            // 加载图像文件
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
		ImagePanel imagePanel = new ImagePanel(image);
		this.setContentPane(imagePanel); // 将背景面板作为窗口的内容面板
		
		this.setSize(1700, 1000); // 16:10比例初始窗口大小	暂时没学会等比铺满显示的方法 以后再改吧
        this.setLocation(100, 100); // 设置窗口位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 窗口关闭时退出程序
		this.setResizable(false);//禁止调整窗口大小
		
		jpl.setLayout(new BoxLayout(jpl, BoxLayout.Y_AXIS)); // 使用垂直方向布局
		
		Button button1=new Button("    Start   ");
		Button button2=new Button("    Wrong Words   ");
		Button button3=new Button("    Settings   ");
		Button button4=new Button("    Exit   ");
		
		jpl.add(button1);/*按钮事件*/
		jpl.add(Box.createVerticalStrut(20)); // 添加垂直间距
		jpl.add(button2);/*按钮事件*/
		jpl.add(Box.createVerticalStrut(20)); 
		jpl.add(button3);/*按钮事件*/
		jpl.add(Box.createVerticalStrut(20)); 
		jpl.add(button4);/*按钮事件*/
		jpl.add(Box.createVerticalStrut(20)); 
		jpl.setOpaque(false);
		this.add(jlb,BorderLayout.NORTH);
		//this.add(jpl);
		
		// 添加透明面板作为间距控制（设置顶部间距）
        //JPanel topMarginPanel = new JPanel();
        //topMarginPanel.setPreferredSize(new Dimension(0, 40)); // 设置顶部间距
        //topMarginPanel.setOpaque(false); // 使该面板透明

        
        // 将透明面板添加到顶部（给Label留出间距）
        //this.add(topMarginPanel, BorderLayout.NORTH); // 将透明面板添加到窗口的顶部
		
        // 将Label添加到窗口的顶部
        //this.add(jlb, BorderLayout.NORTH);

        // 将按钮容器添加到窗口的下方
        //JPanel panelContainer = new JPanel();
        //panelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // FlowLayout 左对齐
        //panelContainer.add(jpl); // 将按钮面板添加到容器面板中
        //this.add(panelContainer, BorderLayout.SOUTH); // 将按钮容器添加到窗口的下方
        
	}
   

	class ImagePanel extends JPanel {
		private Image image;
		
		public ImagePanel(Image image) {
			this.image = image;
		}
		// 重写paintComponent方法，在面板上绘制图像
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 将图像绘制到面板上，图像位于(0, 0)位置
			g.drawImage(image, 0, 0, this);
		}
	}
	
	

}
