/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import javax.swing.JMenu;

/**
 *
 * @author thiago
 */
public class OperationMenu extends JMenu {

    private MainWindow father;

    public OperationMenu (MainWindow father) {
        super("Operações");
        this.father = father;
        add(new DissolveMenu(father));
    }

}
