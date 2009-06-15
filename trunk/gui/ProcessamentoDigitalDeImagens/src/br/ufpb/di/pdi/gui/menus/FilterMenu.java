/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.xml.stream.events.StartDocument;

/**
 *
 * @author thiago
 */
public class FilterMenu extends JMenu implements ActionListener {

    private MainWindow father;

    public FilterMenu (MainWindow father) {
        this.father = father;
        setText("Filtros");
        initComponents();
    }

    private void initMenuItems (List<JMenuItem> where) {

        for (String i : FilterApplyer.FILTERS) {
            where.add(new JMenuItem(i));
        }
        
    }

    private void initComponents() {
        List<JMenuItem> items = new ArrayList<JMenuItem> ();
        initMenuItems(items);
        for (JMenuItem i : items) {
            add(i);
            i.addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        final String filterName = ((JMenuItem) (e.getSource())).getText();

        new Thread () {

            @Override
            public void run() {
                if (filterName != null)
                    FilterApplyer.apply(filterName, father);
            }

        }.start();

    }

}
