/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.FilterDialog;
import br.ufpb.di.pdi.gui.GUIResourceManager;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.dissolve.Dissolve;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import com.sun.org.apache.xerces.internal.jaxp.JAXPConstants;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author thiago
 */
public class DissolveMenu extends JMenu implements MenuListener {

    private static class MyEars implements ActionListener {


        private MainWindow father;
        private MainWindow target;

        public MyEars(MainWindow father, MainWindow target) {
            this.father = father;
            this.target = target;
        }

        public void actionPerformed(ActionEvent e) {
            doDissolve(father, target);
        }

    }

    private List<MainWindow> compatible = new ArrayList<MainWindow> ();
    private MainWindow father;

    public DissolveMenu (MainWindow father) {
        super("Dissolve cruzado");
        this.father = father;
        addMenuListener(this);
    }

    public static void doDissolve (final MainWindow father, final MainWindow target) {

        new Thread() {

            public void run() {

                SpinnerNumberModel model = new SpinnerNumberModel(0.5f, 0.0f, 1.0f, 0.05f);
                JSpinner input = new JSpinner(model);
                input.setBorder(new TitledBorder("Intensidade"));

                FilterDialog test = new FilterDialog(father, "Dissolve Cruzado", input);

                int result;
                final float value;
                int mask;

                result = test.showDialog();

                Dissolve filter;

                filter = null;
                mask = 0;

                if (result == JOptionPane.OK_OPTION) {
                    mask = test.getMask();
                    value = ((Double) input.getValue()).floatValue();
                    filter = new Dissolve(value);
                } else {
                    return;
                }

                test.dispose();

                if (filter != null) {

                    final ImageWrapper newImage = new ImageWrapper(
                            father.icon.getIconWidth(), father.icon.getIconHeight());

                    filter.apply(newImage, target.image, father.image, AbstractFilter.RGB);

                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            MainWindow main =
                                    new MainWindow("Dissolve cruzado: " +
                                    "("+father.getTitle()+") "+value+" + "
                                    +
                                    "("+target.getTitle()+") "+(1.0f-value)+" + "
                                    , newImage);
                            main.icon.update();
                            main.setVisible(true);
                        }
                    });

                }
            }
        }.start();
    }

    private void recreate () {
        Iterator<MainWindow> it =
                GUIResourceManager.getInstance().getWindowIterator();
        MainWindow current;
        JMenuItem item;

        compatible.clear();
        removeAll();

        while (it.hasNext()) {
            current = it.next();
            if (current != father &&
                current.icon.getIconWidth() == father.icon.getIconWidth() &&
                current.icon.getIconHeight() == father.icon.getIconHeight()
                )
            {
                compatible.add(current);
            }
        }


        it = compatible.iterator();
        while (it.hasNext()) {
            current = it.next();
            item = new JMenuItem(current.getTitle());
            add(item);
            item.addActionListener(new MyEars(father, current));
            if (it.hasNext())
                addSeparator();
        }

    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag)
            recreate();
        super.setVisible(aFlag);
    }

    public void menuSelected(MenuEvent e) {
        recreate();
    }

    public void menuDeselected(MenuEvent e) {
    }

    public void menuCanceled(MenuEvent e) {
    }

}
