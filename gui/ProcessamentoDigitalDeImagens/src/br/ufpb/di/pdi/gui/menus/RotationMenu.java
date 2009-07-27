/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author thiago
 */
public class RotationMenu extends JMenuItem {

    private MainWindow father;

    public RotationMenu (MainWindow myFather) {
        super("Rotação");
        this.father = myFather;
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new Thread() {

                    @Override
                    public void run() {
                        TopologicApplyer.apply("Rotação", father);
                    }
                }.start();

            }
        });
    }

}
