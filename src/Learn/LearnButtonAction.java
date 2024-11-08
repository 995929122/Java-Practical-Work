package Learn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//学习按钮事件和面板控制 使得按钮按下后进入对应功能面板
//比如点setting进入设置面板之类的 
//暂时毫无头绪


class ButtonClicked implements ActionListener{
	private CardLayout cardLayout;
    private JPanel panelContainer;

    public ButtonClicked(CardLayout cardLayout, JPanel panelContainer) {
        this.cardLayout = cardLayout;
        this.panelContainer = panelContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 点击按钮后切换到新的面板
        cardLayout.show(panelContainer, "NewPanel");
    }
}


class BackButtonClicked implements ActionListener {
    private CardLayout cardLayout;
    private JPanel panelContainer;

    public BackButtonClicked(CardLayout cardLayout, JPanel panelContainer) {
        this.cardLayout = cardLayout;
        this.panelContainer = panelContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 点击回退按钮返回到原来的面板
        cardLayout.show(panelContainer, "OriginalPanel");
    }
}

public class LearnButtonAction {
	public static void main(String[] args) {
		
		JFrame frmFrame=new JFrame("主界面窗口");
		frmFrame.setSize(600,600);
		
		CardLayout cardLayout = new CardLayout();//同一时间只显示一个card
        JPanel panelContainer = new JPanel(cardLayout);
        
     // 创建原始面板
        JPanel originalPanel = new JPanel();
        JButton button = new JButton("显示新面板");
        JLabel jlb=new JLabel("JLABEL");
        originalPanel.add(jlb);
        originalPanel.add(button);
        
     // 创建新的面板和回退按钮
        JPanel newPanel = new JPanel();
        JButton backButton = new JButton("回退到原面板");
        newPanel.add(backButton);
		
     // 将面板添加到 CardLayout 容器中
        panelContainer.add(originalPanel, "OriginalPanel");
        panelContainer.add(newPanel, "NewPanel");

        // 按钮事件监听：显示新面板
        button.addActionListener(new ButtonClicked(cardLayout, panelContainer));
		
     // 回退按钮事件监听：返回到原面板
        backButton.addActionListener(new BackButtonClicked(cardLayout, panelContainer));
        

		
        
        // 将 CardLayout 容器添加到 JFrame
        frmFrame.add(panelContainer);
        
        
		frmFrame.setVisible(true);
		frmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
	}
}
