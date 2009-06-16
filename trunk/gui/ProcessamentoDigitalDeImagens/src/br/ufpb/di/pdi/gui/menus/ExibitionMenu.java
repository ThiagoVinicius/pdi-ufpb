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
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author thiago
 */
public class ExibitionMenu extends JMenu {

    private MainWindow father;
    private JCheckBoxMenuItem red, green, blue;

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


        red = new JCheckBoxMenuItem("Vermelho");
        mask.add(red);
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.RED : mask & ~ImageWrapper.RED ;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        green = new JCheckBoxMenuItem("Verde");
        mask.add(green);
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.GREEN : mask & ~ImageWrapper.GREEN;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        blue = new JCheckBoxMenuItem("Azul");
        mask.add(blue);
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int mask = father.icon.getExibitionMask();
                mask = ((AbstractButton) e.getSource()).isSelected() ?
                    mask | ImageWrapper.BLUE : mask & ~ImageWrapper.BLUE ;
                father.icon.setExibitionMask(mask);
                update();
            }
        });

        mask.addSeparator();

        JMenuItem item10 = new JMenuItem ("RGB");
        mask.add(item10);
        item10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.  setSelected(true);
                green.setSelected(true);
                blue. setSelected(true);
                father.icon.setExibitionMask(
                     ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
                update();
            }
        });


        JMenuItem item11 = new JMenuItem ("Vermelho apenas");
        mask.add(item11);
        item11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.  setSelected(true);
                green.setSelected(false);
                blue. setSelected(false);
                father.icon.setExibitionMask(
                     ImageWrapper.RED);
                update();
            }
        });

        JMenuItem item12 = new JMenuItem ("Verde apenas");
        mask.add(item12);
        item12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.  setSelected(false);
                green.setSelected(true);
                blue. setSelected(false);
                father.icon.setExibitionMask(
                     ImageWrapper.GREEN);
                update();
            }
        });

        JMenuItem item13 = new JMenuItem ("Azul apenas");
        mask.add(item13);
        item13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.  setSelected(false);
                green.setSelected(false);
                blue. setSelected(true);
                father.icon.setExibitionMask(
                     ImageWrapper.BLUE);
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
        red.setSelected(true);
        green.setSelected(true);
        blue.setSelected(true);


    }

    private void update () {
        father.repaint();
    }


}
