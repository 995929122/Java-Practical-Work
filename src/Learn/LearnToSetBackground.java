package Learn;

import javax.swing.*;

import FrameWork.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LearnToSetBackground extends JFrame {
	
	private Image image;
	
	public LearnToSetBackground(String imagePath) {
		super("图像显示窗口");
		// 尝试加载图像
        try {
            // 加载图像文件
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // 设置窗口大小
        this.setSize(1600, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

        // 创建一个自定义的面板来绘制图像
        ImagePanel imagePanel = new ImagePanel(image);
        this.setContentPane(imagePanel);// 将背景面板作为窗口的内容面板
        imagePanel.setOpaque(false);
        
        // 设置布局管理器		
        this.setLayout(new BorderLayout());
        
        Label jlb = new Label("TEST");
        jlb.setOpaque(false); // 设置透明背景
        
        // 将 Label 添加到窗口的顶部（北部）
        this.add(jlb, BorderLayout.NORTH);
        
        // 将图像面板添加到窗口的中央
        //this.add(imagePanel, BorderLayout.CENTER);//将背景面板作为窗口内容面板后不需要再添加图像面板
	
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
	//Label
		class Label extends JLabel{
			public Label(String text) {
				super(text);setBackground(new Color(0, 0, 0, 0));
				this.setFont(new Font("Serif", Font.ITALIC, 36)); // 设置字体为Serif，大小24，粗体
		        this.setForeground(Color.RED); // 设置文字颜色为红色
		        this.setOpaque(false);
			}
		}
		
	
	public static void main(String[] args) {
        // 设置图像路径
        String imagePath = "D:\\Java_eclipse_workspace\\Practical_Work\\src\\FrameWork\\image.png"; // 请替换为你自己的图像文件路径

        // 创建窗口并显示图像
        LearnToSetBackground frame = new LearnToSetBackground(imagePath);
        frame.setVisible(true);
    }
}
