/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import javax.swing.JMenuBar;

/**
 *
 * @author thiago
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar (MainWindow father) {
        add(new FileMenu(father));
        add(new ExibitionMenu(father));
        add(new FilterMenu(father));
        add(new NoiseMenu(father));
        add(new OperationMenu(father));
        add(new AboutMenu());
    }

}
