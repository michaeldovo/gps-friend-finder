/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author bort
 */
public class myJSpielfeld extends JFrame{

    public myJSpielfeld() {
//        JFrame f = new JFrame( "myJPokerfeld" );
//
//        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//        f.setSize( 600, 400 );
//        f.setVisible( true );



        
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon("herz-10.png");
        JLabel label = new JLabel(icon);

        panel.setBackground(Color.magenta);
        panel.setForeground(Color.black);
      
        panel.add(label);
        setContentPane(panel);

        this.setSize(200,200);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
