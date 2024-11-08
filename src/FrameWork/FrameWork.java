package FrameWork;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Label
class Label extends JLabel{
	public Label(String text) {
		super(text);
		this.setFont(new Font("Serif", Font.ITALIC, 66)); // 设置字体为Serif，大小24，粗体
		this.setForeground(Color.RED); // 设置文字颜色为红色

	}
}

//Button
class Button extends JButton {
	public Button(String TextOnButton) {
		super(TextOnButton);
		setPreferredSize(new Dimension(300, 60)); // 设置按钮的固定大小
		setFont(new Font("Serif", Font.ITALIC, 44)); // 设置按钮的字体
		setBackground(new Color(0, 0, 0, 0)); // 设置背景颜色为透明
		setForeground(Color.BLACK); // 设置按钮文字颜色
		setFocusPainted(false); // 去掉按钮点击时的边框效果
		setBorder(null); // 设置按钮边框
		//setHorizontalAlignment(SwingConstants.LEFT); // 设置按钮文字左对齐
		//setVerticalAlignment(SwingConstants.CENTER); // 设置按钮文字垂直居中
		setOpaque(false); // 设置按钮为透明背景
		setContentAreaFilled(false); // 去掉按钮点击后的背景效果
	}
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

public class FrameWork extends JFrame{

	private JPanel jpl=new JPanel();//主面板
	
	private Label jlb=new Label("The name of whole project");	
	
	private Image image;
	
	public  FrameWork(String imagePath) {	//构造函数					////////////
		//主界面基础框架
		super("记个词先");	//窗口上的标题喵
		
		try {
            // 加载图像文件
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
		ImagePanel imagePanel = new ImagePanel(image);
		this.setContentPane(imagePanel); // 将背景面板作为窗口的内容面板
		
		this.setSize(1700, 1000); // 初始窗口大小	暂时没学会等比铺满显示的方法 以后再改吧
        this.setLocation(100, 100); // 设置窗口位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 窗口关闭时退出程序
		this.setResizable(false);//禁止调整窗口大小
		
		// 设置布局管理器
        this.setLayout(new BorderLayout());
		
        
        // 使用透明面板包裹label 将Label放置在左上角
        JPanel labelPanel = new JPanel(); 
        labelPanel.setOpaque(false); 
        int labeltopMargin = 80; 
        int labelleftMargin = 30; 
        labelPanel.setBorder(BorderFactory.createEmptyBorder(labeltopMargin, labelleftMargin, 0, 0)); // 设置边距
        labelPanel.add(jlb);
        this.add(labelPanel, BorderLayout.WEST); // 将Label面板放置在左侧
        //不知道为什么换成NORTH就变成了居中
		
		
		//创建按钮面板
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false); // 设置按钮面板透明
		
		//设置EmptyBorder 添加左边距 
        int buttonLeftMargin =75;  
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, buttonLeftMargin, 0, 0)); 
		
		Button button1=new Button("Start");
		Button button2=new Button("Wrong Words");
		Button button3=new Button("Settings");
		Button button4=new Button("Exit");
		
		
		buttonPanel.add(button1);/*按钮事件*/
		buttonPanel.add(Box.createVerticalStrut(40)); // 添加垂直间距
		buttonPanel.add(button2);/*按钮事件*/
		buttonPanel.add(Box.createVerticalStrut(40)); 
		buttonPanel.add(button3);/*按钮事件*/
		buttonPanel.add(Box.createVerticalStrut(40)); 
		buttonPanel.add(button4);/*按钮事件*/
		buttonPanel.add(Box.createVerticalStrut(40)); 
		
		
		button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击 Exit 按钮时退出程序
                System.exit(0); // 退出程序
            }
        });
		 
		
		//添加按钮面板并置于下方
		this.add(buttonPanel, BorderLayout.SOUTH); 
		
			
	}
   
}


// 将按钮容器添加到窗口的下方
//JPanel panelContainer = new JPanel();
//panelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // FlowLayout 左对齐
//panelContainer.add(jpl); // 将按钮面板添加到容器面板中
//this.add(panelContainer, BorderLayout.SOUTH); // 将按钮容器添加到窗口的下方
