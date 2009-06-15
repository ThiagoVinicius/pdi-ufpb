/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import java.awt.PopupMenu;
import java.util.Iterator;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Popup;

/**
 *
 * @author thiago
 */
public class MainMenuPopup extends JPopupMenu {

    public MainMenuPopup (MainWindow father) {
        add(new FileMenu(father));
        addSeparator();
        add(new ExibitionMenu(father));
        addSeparator();
        add(new FilterMenu(father));
        addSeparator();
        add(new NoiseMenu(father));
    }

}
