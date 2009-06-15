/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.filechooser;

import br.ufpb.di.pdi.Main;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author thiago
 */
public class FileChooserManager {

    private static JFileChooser fc;
    private static JFrame frame;

    private static int show () {
        if (fc == null) {

            fc = new JFileChooser();

            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);

            fc.setMultiSelectionEnabled(true);

        }

        if (frame == null) {
            frame = new JFrame();
            frame.setSize(0, 0);
        }

        frame.setVisible(true);
        //Show it.
        int returnVal = fc.showDialog(frame,
                                      "Abrir!");
        frame.setVisible(false);

        return returnVal;
    }

    public static void open () {
        
        int returnVal = show();

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Main.run(new String [] { file.getAbsolutePath() });
        } 

        //Reset the file chooser for the next time it's shown.
        fc.setSelectedFile(null);
    }

    public static void save (BufferedImage image) throws IOException {

        while (true) {

            int returnVal = show();

            //Process the results.
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                boolean goodToGo = true;
                if (file.exists()) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "O arquivo selecionado ja existe!",
                            "Ooops!", JOptionPane.YES_NO_OPTION);
                    goodToGo = result == JOptionPane.YES_OPTION;
                }
                if (goodToGo) {
                    ImageIO.write(image, Utils.getExtension(file), file);
                    break;
                }
            } else {
                break;
            }


        }

        //Reset the file chooser for the next time it's shown.
        fc.setSelectedFile(null);
    }

}
