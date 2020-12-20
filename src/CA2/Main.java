/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CA2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author Miteto
 */
public class Main extends JApplet {
    
    public static void main(String[] args)
    {
        // application title and dimensions
        final String title = "Dimitar Papazikov & Jake Keenan PhotoShop Application";
        Toolkit tk = Toolkit.getDefaultToolkit();  
        int xSize = ((int) tk.getScreenSize().getWidth());  
        int ySize = ((int) tk.getScreenSize().getHeight()) - 38; 
        final Dimension applicationFrameSize = new Dimension(xSize, ySize);

        // make an application frame to hold the applet
        final JFrame applicationFrame = new JFrame(title);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.setSize(applicationFrameSize);

        // place the applet inside the application's frame
        final JApplet applet = new Main();
        applicationFrame.setLayout(new BorderLayout());
        applicationFrame.getContentPane().add("Center", applet);
        applet.init();

        applicationFrame.setVisible(true);
    }

    @Override
    public void init()
    {
        this.setContentPane(new View());
    }
    
}
