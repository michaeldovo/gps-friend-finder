/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jSpielfeld.java
 *
 * Created on 10.12.2008, 23:48:05
 */

package Graphics;

/**
 *
 * @author bort
 */
public class jSpielfeld extends javax.swing.JFrame {

    /** Creates new form jSpielfeld */
    public jSpielfeld() {

        initComponents();
        System.out.println("SpielfeldUI erstellt");


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 788, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */


   public void init() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jSpielfeld().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}