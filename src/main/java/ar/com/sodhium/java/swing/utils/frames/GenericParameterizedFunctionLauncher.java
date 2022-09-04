package ar.com.sodhium.java.swing.utils.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

public class GenericParameterizedFunctionLauncher extends JInternalFrame {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GenericParameterizedFunctionLauncher frame = new GenericParameterizedFunctionLauncher();
                    frame.setVisible(true);
                    
                    JFrame example2 = new JFrame("Example of generic params frame");
                    example2.add(frame);
                    example2.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GenericParameterizedFunctionLauncher() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(2, 2, 0, 0));
        
        JPanel panel_1 = new JPanel();
        panel_1.setName("Group 1");
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.add(panel_1);
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        
        JPanel panel_3 = new JPanel();
        panel.add(panel_3);
        
        JPanel panel_4 = new JPanel();
        panel.add(panel_4);

    }

}
