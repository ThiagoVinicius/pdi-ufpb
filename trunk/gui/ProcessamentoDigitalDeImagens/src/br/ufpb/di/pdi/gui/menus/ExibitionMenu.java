/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.ImageViewer;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author thiago
 */
public class ExibitionMenu extends JMenu {

    private MainWindow father;

    public ExibitionMenu (MainWindow father) {
        super("Exibição");
        this.father = father;
        initComponents();
    }

    private void initComponents() {

        JMenu mode = new JMenu("Modo de exibicao");
        JMenu mask = new JMenu("Mascara");
        ButtonGroup modeBG = new ButtonGroup();

        JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("Mono -> banda vermelha");
        modeBG.add(item1);
        mode.add(item1);
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.icon.setMono(ImageViewer.VIEW_MONO_OF_RED);
                update();
            }
        });

        JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("Mono -> banda verde");
        modeBG.add(item2);
        mode.add(item2);
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.icon.setMono(ImageViewer.VIEW_MONO_OF_GREEN);
                update();
            }
        });

        JRadioButtonMenuItem item3 = new JRadioButtonMenuItem("Mono -> banda azul");
        modeBG.add(item3);
        mode.add(item3);
        item3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.icon.setMono(ImageViewer.VIEW_MONO_OF_BLUE);
                update();
            }
        });

        JRadioButtonMenuItem item4 = new JRadioButtonMenuItem("Mono -> banda Y");
        modeBG.add(item4);
        mode.add(item4);
        item4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.icon.setMono(ImageViewer.VIEW_MONO_OF_Y);
                update();
            }
        });

        JRadioButtonMenuItem item5 = new JRadioButtonMenuItem("Colorido -> RGB");
        modeBG.add(item5);
        mode.add(item5);
        item5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                father.icon.setMono(ImageViewer.VIEW_COLOR);
                update();
            }
        });


        JCheckBoxMenuItem item6 = new JCheckBoxMenuItem("Vermelho");
        mask.add(item6);
        item6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.RED : mask & ~ImageWrapper.RED ;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        JCheckBoxMenuItem item7 = new JCheckBoxMenuItem("Verde");
        mask.add(item7);
        item7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.GREEN : mask & ~ImageWrapper.GREEN;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        JCheckBoxMenuItem item8 = new JCheckBoxMenuItem("Azul");
        mask.add(item8);
        item8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.BLUE : mask & ~ImageWrapper.BLUE ;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        add(mode);
        add(mask);

        item1.setSelected(false);
        item2.setSelected(false);
        item3.setSelected(false);
        item4.setSelected(false);
        item5.setSelected(true);
        item6.setSelected(true);
        item7.setSelected(true);
        item8.setSelected(true);


    }

    private void update () {
        father.repaint();
    }


}
