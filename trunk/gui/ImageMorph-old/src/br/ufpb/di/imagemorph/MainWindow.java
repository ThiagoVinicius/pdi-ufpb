/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import br.ufpb.di.pdi.gui.filechooser.FileChooserManager;
import br.ufpb.di.pdi.gui.menus.MainMenu;
import br.ufpb.di.pdi.gui.menus.MainMenuBar;
import br.ufpb.di.pdi.gui.menus.MainMenuPopup;
import br.ufpb.di.pdi.gui.rotation.ImagePanel;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author thiago
 */
public class MainWindow extends JFrame implements MouseListener, ShutdownListener {

    File originalPath;
    public ImageWrapper image;


    public ImageViewer icon;
    //JLabel label;
    public ImagePanel imShow;
    MainMenu  mainMenu;
    MainMenuPopup popup;
    MainMenuBar bar;

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
        super(title);
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

//        label = new JLabel(icon);
//        label.addMouseListener(this);
//        getContentPane().add(new JScrollPane(label));

        imShow = new ImagePanel(icon);
        imShow.addMouseListener(this);
        getContentPane().add(new JScrollPane(imShow));

        //mainMenu = new MainMenu(this);
        popup = new MainMenuPopup(this);

        bar = new MainMenuBar(this);
        this.setJMenuBar(bar);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void initData () {
        icon = new ImageViewer();
        icon.setImage(image);
        if (image.red.getValueArray() == null)
            image.createRGBFromImage();
        GUIResourceManager.getInstance().addShutdownListener(this);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        GUIResourceManager.getInstance().registerWindow(this);
    }



    @Override
    public void dispose() {
        super.dispose();
        GUIResourceManager.getInstance().removeShutdownListener(this);
        GUIResourceManager.getInstance().unregisterWindow(this);
    }

    private static void applyFilterImpl (ImageWrapper target,
                                  ImageWrapper source,
                                  AbstractFilter filter,
                                  int mask) {
        
        if (target.red.getValueArray() == null)
            target.createRGBFromImage();

        source.recalculateBorderMedian();

        //filtrando algo em YUV.
        if ((mask & AbstractFilter.YUV) != 0x00) {
            filter.applyFilter(target, source, mask);
            target.yuvToRgb();
        } else {
            filter.applyFilter(target, source, mask);
        }
    }

    public void applyFilter (final AbstractFilter filter, final int mask) {

        if ((mask & AbstractFilter.YUV) != 0x00) {
            image.rgbToYuv();
        }
        
        final ImageWrapper newImage = image.clone();
        applyFilterImpl(newImage, image, filter, mask);

        SwingUtilities.invokeLater( new Runnable() {

            public void run() {
                MainWindow main =
                    new MainWindow(getTitle() +" ---> "+
                        filter.toString(), newImage);
                main.icon.update();
                main.setVisible(true);
            }
        });
        


    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            popup.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void shutDownNow() {
        dispose();
    }

}
