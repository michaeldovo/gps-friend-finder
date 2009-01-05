/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author bort
 */
public class awtSpielfeld extends Canvas{

    public awtSpielfeld(){
        System.out.println("AWTSpielfeld erstellt");
        
    }

    public void paint(Graphics g) {

      g.setColor(Color.BLUE);
      g.drawLine(10, 10, 20, 20);

    }
}
