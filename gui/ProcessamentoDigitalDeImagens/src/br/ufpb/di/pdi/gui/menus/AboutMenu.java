/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.about.AboutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author thiago
 */
public class AboutMenu extends JMenuItem {

    public AboutMenu () {
        super("Sobre");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutManager.getInstance().showAbout();
            }
        });
    }

}
