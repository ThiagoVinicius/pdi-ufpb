/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import br.ufpb.di.imagemorph.filechooser.FileChooserManager;
import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.topologic.Morphing;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author thiago
 */
public class MainWindow extends JFrame implements ActionListener {

    public VectorPanel   image1;
    public VectorPanel   image2;
    public JButton       gogogo;
    public JToggleButton median;
    public JToggleButton nearest;

    public MainWindow(String title) throws HeadlessException {
        super(title);
        initComponents();
    }

    private void initComponents () {

        ButtonGroup bg = new ButtonGroup();

        JPanel upPanel = new JPanel();
        gogogo = new JButton("1, 2, 3, JÁ!");
        gogogo.addActionListener(this);
        median = new JToggleButton("Mediana");
        bg.add(median);
        nearest = new JToggleButton("Ponto mais próximo");
        bg.add(nearest);

        nearest.setSelected(true);

        upPanel.add(gogogo);
        upPanel.add(median);
        upPanel.add(nearest);

        image1 = new VectorPanel(null);
        image2 = new VectorPanel(image1);

        JSplitPane panelint = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        panelint.setOneTouchExpandable(true);

        panelint.add(image1);
        panelint.add(image2);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(upPanel, BorderLayout.NORTH);
        getContentPane().add(panelint, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gogogo) {
            try {
                letsRollBaby();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WrongImageSizeException ex) {
                
            }
        }
    }

    private void letsRollBaby() throws IOException, WrongImageSizeException {

        double a, b, p;
        int    nquadros, disIni, disFin;

        JOptionPane.showMessageDialog(this,
                "Agora, a parte chata. Responda corretamente as perguntas seguintes!",
                "Vamos começar =D.", JOptionPane.INFORMATION_MESSAGE);


        a = getDouble("Digite o parâmetro a: ");
        b = getDouble("Digite o parâmetro b: ");
        p = getDouble("Digite o parâmetro p: ");

        nquadros = getInteger("Digite o número de quadros: ");
        disIni   = getInteger("Digite em qual quadro começar o dissolve: ");
        disFin   = getInteger("Digite em qual quadro parar o dissolve: ");

        --disIni;
        --disFin;

        Morphing morpher = new Morphing(image2.image.getLines(),
                                        image1.image.getLines(),
                                        a, b, p,
                                        nquadros, disIni, disFin);

        JOptionPane.showMessageDialog(this,
                "Última parte. Selecione uma pasta vazia.",
                "Estamos quase lá!", JOptionPane.INFORMATION_MESSAGE);

        File folder = FileChooserManager.getFolder();

        File tmp;

        ImageWrapper im1 = ImageWrapper.createFromDisk(image1.file);
        ImageWrapper im2 = ImageWrapper.createFromDisk(image2.file);

        if (median.isSelected()) {
            im1.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_MEDIAN);
            im2.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_MEDIAN);
        } else {
            im1.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            im2.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
        }

        im1.recalculateBorderMedian();
        im2.recalculateBorderMedian();

        ImageWrapper tmpimg;

        System.out.println("Imagem 1: " + image1.file);
        System.out.println("Imagem 2: " + image2.file);


        im1.createRGBFromImage();
        im2.createRGBFromImage();

        for (int i = 0; i < nquadros; ++i) {
            tmpimg = morpher.getQuadro(im1, im2, i);

            tmp = new File(folder, String.format("seq%04d.png", i));
            tmpimg.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
            tmpimg.writeToDisk(tmp);
        }

        JOptionPane.showMessageDialog(this,
                "Acabou!.",
                "Ufa, terminamos", JOptionPane.INFORMATION_MESSAGE);

    }

    private double getDouble (String message) {

        boolean goodToGo = false;
        double result = 0.0;

        while (!goodToGo) {

            String text = JOptionPane.showInputDialog(null,
                    "Digite um número de ponto flutuante: (maior que zero, por favor) \n\n" + message,
                    1.0);

            text = text.replace(',', '.');

            try {
                result = Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                result = -1.0;
            }

            goodToGo = result > 0.0;

        }

        return result;

    }

    private int getInteger (String message) {

        boolean goodToGo = false;
        int result = 0;

        while (!goodToGo) {

            String text = JOptionPane.showInputDialog(null,
                    "Digite um número inteiro: (maior que zero, por favor) \n\n" + message,
                    1);

            text = text.replace(',', '.');

            try {
                result = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                result = -1;
            }

            goodToGo = result > 0;

        }

        return result;

    }

//    public void componentResized(ComponentEvent e) {
//        System.out.println("Opa, recebi um evento");
//        if (e.getSource() == image1 || e.getSource() == image2)
//            pack();
//    }
//
//    public void componentMoved(ComponentEvent e) {
//    }
//
//    public void componentShown(ComponentEvent e) {
//    }
//
//    public void componentHidden(ComponentEvent e) {
//    }

}
