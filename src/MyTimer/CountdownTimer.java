package MyTimer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.EventObject;

public class CountdownTimer extends JFrame  {
	private JLabel label;
    private int counter = 10;
    //
    private javax.swing.Timer countdownTimer;

    //
    
    
    public CountdownTimer() {
        label = new JLabel("10", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(48.0f));
        label.setForeground(java.awt.Color.RED);
        add(label);

        countdownTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                label.setText(String.valueOf(counter));
                if (counter <= 0) {
                    countdownTimer.stop();

                }
            }
        });

        
        setSize(200, 200);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(true);

        countdownTimer.start();
    }
}
