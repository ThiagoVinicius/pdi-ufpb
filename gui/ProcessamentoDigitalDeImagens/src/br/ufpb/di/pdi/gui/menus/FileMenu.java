/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.GUIResourceManager;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.gui.filechooser.FileChooserManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author thiago
 */
public class FileMenu extends JMenu {

    private MainWindow father;

    public FileMenu (MainWindow father) {
        super("Arquivo");
        this.father = father;
        initComponents();
    }

    private void initComponents() {

        JMenuItem item1;
        JMenuItem item2;
        JMenuItem item3;


        item1 = new JMenuItem("Abrir");
        add(item1);
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileChooserManager.open();
            }
        });


        item2 = new JMenuItem("Salvar");
        add(item2);
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.save();
            }
        });

        item3 = new JMenuItem("Encerrar programa");
        add(item3);
        item3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUIResourceManager.getInstance().shutDownNow();
            }
        });


    }

}
