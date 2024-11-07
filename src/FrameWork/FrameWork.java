package FrameWork;

import javax.swing.*;

import java.awt.*;

public class FrameWork extends JFrame{

	private JPanel jpl=new JPanel();//主面板
	
	//Label
	class Label extends JLabel{
		public Label(String text) {
			super(text);
			this.setFont(new Font("Serif", Font.BOLD, 24)); // 设置字体为Serif，大小24，粗体
	        this.setForeground(Color.RED); // 设置文字颜色为红色
	        this.setHorizontalAlignment(SwingConstants.CENTER); // 设置文字水平居中
	        this.setVerticalAlignment(SwingConstants.TOP); // 设置文字垂直居顶
		}
	}
	private Label jlb=new Label("The name of whole project");	
	
	//Button
	class Button extends JButton {
		public Button(String TextOnButton) {
			super(TextOnButton);
			setPreferredSize(new Dimension(200, 50)); // 设置按钮的固定大小
			setFont(new Font("Song", Font.PLAIN, 18)); // 设置按钮的字体
			setBackground(new Color(0, 0, 0, 0)); // 设置背景颜色为透明
			setForeground(Color.BLACK); // 设置按钮文字颜色
			setFocusPainted(false); // 去掉按钮点击时的边框效果
			setBorder(null); // 设置按钮边框
			setHorizontalAlignment(SwingConstants.CENTER); // 设置按钮文字水平居中
			setVerticalAlignment(SwingConstants.CENTER); // 设置按钮文字垂直居中
			setOpaque(false); // 设置按钮为透明背景
		}
	}
	////////////////////////////////
	//需要设置按钮整体位于面板中部
	////////////////////////////////
	public  FrameWork() {
		//主界面基础框架
		super("主界面标题");
		this.setSize(1000,700);this.setLocation(300,250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 窗口关闭时退出程序
		
		
		jpl.setLayout(new BoxLayout(jpl, BoxLayout.Y_AXIS)); // 使用垂直方向布局
		
		jpl.add(jlb);jpl.add(Box.createVerticalStrut(30)); // 添加垂直间距
		
		Button button1=new Button("Start");
		Button button2=new Button("错词");
		Button button3=new Button("Settings");
		Button button4=new Button("Exit");
		
		/*按钮事件*/jpl.add(button1);
		jpl.add(Box.createVerticalStrut(10)); // 添加垂直间距
		/*按钮事件*/jpl.add(button2);
		jpl.add(Box.createVerticalStrut(10)); 
		/*按钮事件*/jpl.add(button3);
		jpl.add(Box.createVerticalStrut(10)); 
		/*按钮事件*/jpl.add(button4);
		jpl.add(Box.createVerticalStrut(10)); 
		
		// 将面板jpl放入一个容器（FlowLayout）中来居中对齐
		JPanel panelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100)); // FlowLayout居中	两个变量分别是x y坐标 因为设置了垂直居中 x不再生效
        panelContainer.add(jpl); // 将按钮面板添加到容器面板中

        // 将容器面板添加到窗口
        
        this.add(panelContainer);
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		FrameWork frameWork=new FrameWork();
		frameWork.setVisible(true);
		
	}

}
