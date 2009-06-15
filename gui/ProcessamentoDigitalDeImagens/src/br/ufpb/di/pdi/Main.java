/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi;

import br.ufpb.di.pdi.gui.FloatingMenu;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.gui.GUIResourceManager;
import br.ufpb.di.pdi.gui.TransparentFrame;
import br.ufpb.di.pdi.gui.filechooser.FileChooserManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Thiago
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    private static class Openner implements Runnable {
        public final String filename;

        public Openner(String filename) {
            this.filename = filename;
        }

        public void run() {
            try {
                new MainWindow(filename, new File(filename)).setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    public static void main(final String[] args) {


        String defaulLookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(defaulLookAndFeel);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        run(args);
        
        if (args.length == 0) {
            FileChooserManager.open();
        }
        
        //dorme por 20 segundos
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //e entao informa ao gerente de recursos que pode encerrar a aplicacao
        //caso nao haja nenhuma janela aberta
        GUIResourceManager.getInstance().mayNowShutdown();
        
    }

    public static void run (String args[]) {
        for (String i : args) {
            SwingUtilities.invokeLater(new Openner(i));
        }
    }

}
