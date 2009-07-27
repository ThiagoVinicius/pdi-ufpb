/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author thiago
 */
public class MainWindow extends JFrame {

    public VectorPanel image1;
    public VectorPanel image2;

    public MainWindow(String title) throws HeadlessException {
        super(title);
        initComponents();
    }

    private void initComponents () {

        image1 = new VectorPanel(null);
        image2 = new VectorPanel(image1);

        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        panel.setOneTouchExpandable(true);

        panel.add(image1);
        panel.add(image2);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
    }

//    public void componentResized(ComponentEvent e) {
//        System.out.println("Opa, recebi um evento");
//        if (e.getSource() == image1 || e.getSource() == image2)
//            pack();
//    }
//
//    public void componentMoved(ComponentEvent e) {
//    }
//
//    public void componentShown(ComponentEvent e) {
//    }
//
//    public void componentHidden(ComponentEvent e) {
//    }

}
