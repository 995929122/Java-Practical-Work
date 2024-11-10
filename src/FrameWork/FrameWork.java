package FrameWork;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setPreferredSize(new Dimension(300, 60)); // 设置按钮的固定大小
        setFont(new Font("Serif", Font.ITALIC, 44)); // 设置按钮的字体
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
    }
}

public class FrameWork extends JFrame {

    private Image image;
    private CardLayout cardLayout; // 定义CardLayout
    private JPanel mainPanel; // 主内容面板

    Button button1 = new Button("Start");
    Button button2 = new Button("Wrong Words");
    Button button3 = new Button("Settings");
    Button button4 = new Button("Exit");

    public FrameWork(String imagePath) {
        super("记个词先");

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

        // 创建CardLayout和主内容面板
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);//CardLayout 是一个LayoutManager 规定一次显示一个组件
        mainPanel.setOpaque(false);

        // 创建初始主界面面板
        JPanel homePanel = createHomePanel();
        
        
        // 把主界面面板添加到CardLayout中
        mainPanel.add(homePanel, "home");

        // 添加按钮事件监听
        button1.addActionListener(e -> cardLayout.show(mainPanel, "panel1"));
        button2.addActionListener(e -> cardLayout.show(mainPanel, "panel2"));
        button3.addActionListener(e -> cardLayout.show(mainPanel, "panel3"));
        button4.addActionListener(e -> System.exit(0));

        // 创建三个新的面板
        JPanel panel1 = createPanel("Start Panel");
        JPanel panel2 = createPanel("Wrong Words Panel");
        JPanel panel3 = createPanel("Settings Panel");

        mainPanel.add(panel1, "panel1");
        mainPanel.add(panel2, "panel2");
        mainPanel.add(panel3, "panel3");

        // 将主内容面板添加到Frame
        this.add(mainPanel);
    }

    // 工具方法：创建主界面面板
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setOpaque(false); // 使主面板透明
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS)); // 使用垂直布局

        Label jlb = new Label("The name of whole project");
        jlb.setAlignmentX(Component.RIGHT_ALIGNMENT); // 标签显示

        // 添加组件到主面板
        homePanel.add(Box.createVerticalStrut(20)); // 添加顶部间距
        homePanel.add(jlb); // 添加标签
        homePanel.add(Box.createVerticalStrut(700)); // 标签和按钮组之间的垂直间距

        // 添加按钮组
        JPanel buttonGroup = new JPanel();
        buttonGroup.setOpaque(false);
        buttonGroup.setLayout(new BoxLayout(buttonGroup, BoxLayout.X_AXIS)); // 使用水平布局

        // 定义按钮之间的自定义水平间距
        int buttonSpacing = 300; // 自定义水平间距（可根据需要调整）

        buttonGroup.add(button1);
        buttonGroup.add(Box.createRigidArea(new Dimension(buttonSpacing, 0))); // 添加水平和竖直间距
        buttonGroup.add(button2);
        buttonGroup.add(Box.createRigidArea(new Dimension(buttonSpacing, 0))); 
        buttonGroup.add(button3);
        buttonGroup.add(Box.createRigidArea(new Dimension(buttonSpacing, 0))); 
        buttonGroup.add(button4);

        buttonGroup.setAlignmentX(Component.CENTER_ALIGNMENT); // 按钮组显示位置
       
        homePanel.add(buttonGroup);

        return homePanel;
    }


    // 工具方法：创建带标题和Back按钮的面板
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);


        // Back 按钮
        JButton backButton = new Button("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false);
        //backButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        backButtonPanel.add(backButton);
        panel.add(backButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

}



