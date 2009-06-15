/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author thiago
 */
public class NoiseMenu extends JMenu implements ActionListener {

    private MainWindow father;

    public NoiseMenu (MainWindow father) {
        super("Ruido");
        this.father = father;
        initComponents();
    }

    private void initComponents() {

        JMenuItem item1;

        item1 = new JMenuItem("Sal e Pimenta");
        item1.addActionListener(this);
        add(item1);


    }

    public void actionPerformed(ActionEvent e) {

        final String filterName = ((JMenuItem) (e.getSource())).getText();

        new Thread () {

            @Override
            public void run() {
                if (filterName != null)
                    NoiseApplyer.apply(filterName, father);
            }

        }.start();

    }



}
