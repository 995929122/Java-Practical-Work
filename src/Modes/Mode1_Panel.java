package Modes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mode1_Panel extends JPanel implements ActionListener {
    Socket socket = null;
    InputStream is = null;
    JPanel panel = new JPanel();
    JLabel information = null;
    JButton submitButton = new JButton("submit");
    StringBuffer part1 = new StringBuffer();
    StringBuffer part2 = new StringBuffer();
    public Mode1_Panel() throws Exception {
        this.setOpaque(false);
        
        fetchInformation();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 设置垂直布局
        information.setOpaque(false);
        information.setAlignmentX(CENTER_ALIGNMENT); // 设置 JLabel 在中轴线显示
        information.setFont(new Font("Serif", Font.PLAIN, 40)); // 设置 JLabel 字体大小为 40px
        submitButton.setAlignmentX(CENTER_ALIGNMENT); // 设置 JButton 在中轴线显示
        panel.add(information);
        panel.add(submitButton);
        submitButton.addActionListener(this);
        this.add(panel);
    }

    private void fetchInformation() throws Exception {
        socket = new Socket("127.0.0.1", 23333);
        is = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = is.read(buffer)) != -1) {
            stringBuffer.append(new String(buffer, 0, len));
        }
        //加工获取的数据
        String[] parts = stringBuffer.toString().split(" ", 3);
        if (parts.length >= 3) {
            part1 = new StringBuffer(parts[1]);
            part2 = new StringBuffer(parts[2]);
            // You can now use part1 and part2 as needed
        if (information == null) {
            information = new JLabel(part2.toString(), JLabel.CENTER);
        } else {
            information.setText(part2.toString());
        }
        socket.close();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                this.remove(panel);
                this.revalidate();
                this.repaint();

                fetchInformation();

                panel.removeAll();
                panel.add(information);
                panel.add(submitButton);
                this.add(panel);
                this.revalidate();
                this.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
