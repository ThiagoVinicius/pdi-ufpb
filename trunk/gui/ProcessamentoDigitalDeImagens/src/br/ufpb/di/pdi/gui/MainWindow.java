/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import br.ufpb.di.pdi.gui.filechooser.FileChooserManager;
import br.ufpb.di.pdi.gui.menus.MainMenu;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author thiago
 */
public class MainWindow extends JFrame implements MouseListener {

    File originalPath;
    ImageWrapper image;


    public ImageViewer icon;
    JLabel label;
    JMenu  mainMenu;
    JPopupMenu popup;

    public MainWindow (String title, ImageWrapper image) {
        super(title);
        this.image = image;
        initComponents();
    }

    public MainWindow (String title, BufferedImage image) {
        super(title);
        this.image = ImageWrapper.createFromBuffer(image);
        initComponents();
    }

    public MainWindow (String title, File image) throws IOException {
        super(title + image.getAbsolutePath());
        this.image = ImageWrapper.createFromDisk(image);
        initComponents();
    }

    public void save() {

        BufferedImage temp = image.toBufferedImage();
        try {
            FileChooserManager.save(temp);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar",
                    "Ooops!", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void initComponents() {

        initData();

        label = new JLabel(icon);
        label.addMouseListener(this);
        getContentPane().add(new JScrollPane(label));

        mainMenu = new MainMenu(this);
        popup = new JPopupMenu();
        for (int i = 0; i < mainMenu.getItemCount(); ++i)
            popup.add(mainMenu.getItem(i));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void initData () {
        icon = new ImageViewer();
        icon.setImage(image);
        if (image.red.getValueArray() == null)
            image.createRGBFromImage();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        GUIResourceManager.getInstance().registerWindow(this);
    }



    @Override
    public void dispose() {
        super.dispose();
        GUIResourceManager.getInstance().unregisterWindow(this);
    }

    private void applyFilterImpl (ImageWrapper target, 
                                  AbstractFilter filter,
                                  int mask) {
        
        if (target.red.getValueArray() == null)
            target.createRGBFromImage();

        //filtrando algo em YUV.
        if ((mask & AbstractFilter.YUV) != 0x00) {
            target.rgbToYuv();
            filter.applyFilter(target, mask);
            target.yuvToRgb();
        } else {
            filter.applyFilter(target, mask);
        }
    }

    public void applyFilter (final AbstractFilter filter, final int mask) {


        final ImageWrapper newImage = image.clone();
        applyFilterImpl(newImage, filter, mask);

        SwingUtilities.invokeLater( new Runnable() {

            public void run() {
                MainWindow main =
                    new MainWindow(getTitle() +" : "+
                        filter.toString(), newImage);
                main.icon.update();
                main.setVisible(true);
            }
        });
        


    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        popup.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
