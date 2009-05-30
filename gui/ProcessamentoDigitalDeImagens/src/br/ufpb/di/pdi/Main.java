/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi;

import br.ufpb.di.pdi.gui.FloatingMenu;
import br.ufpb.di.pdi.gui.TransparentFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Thiago
 */
public class Main implements MouseMotionListener, ActionListener, Runnable {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws Exception {
        new Main().main();
    }

    private FloatingMenu menu;
    private JButton botao;
    private ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    private volatile int counter = 0;

    public void main () throws Exception {
        JFrame frame = TransparentFrame.getInstance("Teste", 0.5f);
        menu = new FloatingMenu(frame);

        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(400, 300);
        frame.setLocation(0, 0);
        frame.setUndecorated(false);
        frame.setVisible(true);

//        menu.setSize(dim.width/2, dim.height/2);
        menu.setLocation(500, 300);
//        menu.setSize(300, 100);
        menu.setUndecorated(true);
        botao = new JButton("Clique para sair");
        botao.addActionListener(this);
        menu.add(botao);
        menu.pack();
        menu.setVisible(true);
        frame.addMouseMotionListener(this);

//        while (true) {
//            menu.setAlpha(0.0f, 1.0f, 100);
//            menu.joinAlphaSetter();
//            Thread.sleep(1000L);
//            menu.setAlpha(1.0f, 0.0f, 400);
//            menu.joinAlphaSetter();
//            Thread.sleep(1000L);
//        }

    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        synchronized (this) {
            ++counter;
            if (counter == 1)
                menu.setAlpha(menu.getAlpha(), 1.0f, 100);
        }
        worker.schedule(this, 400, TimeUnit.MILLISECONDS);
    }

    public void run () {
        synchronized (this) {
            --counter;
            if (counter == 0)
                menu.setAlpha(1.0f, 0.0f, 400);
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
