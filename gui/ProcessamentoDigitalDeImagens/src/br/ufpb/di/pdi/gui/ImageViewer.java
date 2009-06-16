/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author thiago
 */
public class ImageViewer implements Icon {

    public static final int VIEW_MONO_OF_RED   = 0x01;
    public static final int VIEW_MONO_OF_GREEN = 0x02;
    public static final int VIEW_MONO_OF_BLUE  = 0x04;
    public static final int VIEW_MONO_OF_Y     = 0x08;
    public static final int VIEW_COLOR         = 0x10;

    private ImageIcon worker;
    private BufferedImage buffer;
    private ImageWrapper currentImage;

    private int monoType;
    private int exibitionMask;

    public ImageViewer() {
        worker = new ImageIcon();
        monoType = VIEW_COLOR;
        exibitionMask = ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE;
    }

    private void realocateBufferIfNecessary (int width, int height) {
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height)
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }


    public void setImage (ImageWrapper newImage) {
        realocateBufferIfNecessary(newImage.width, newImage.height);

        try {
            newImage.writeToBufferedImage(buffer);
        } catch (WrongImageSizeException ex) {
            Logger.getLogger(ImageViewer.class.getName()).log(Level.SEVERE, null, ex);
        }

        worker.setImage(buffer);
        currentImage = newImage;
    }


    public void paintIcon(Component c, Graphics g, int x, int y) {
        worker.paintIcon(c, g, x, y);
    }

    public int getIconWidth() {
        return worker.getIconWidth();
    }

    public int getIconHeight() {
        return worker.getIconHeight();
    }


    public void update () {

        applyMono();

        applyExibitionMask();

        setImage(currentImage);

    }

    public void setExibitionMask(int exibitionMask) {
        this.exibitionMask = exibitionMask;
        update();
    }

    public int getExibitionMask() {
        return exibitionMask;
    }

    private void applyMono () {
        if (monoType == VIEW_MONO_OF_RED)
            makeMonoOfComponent(currentImage.red);
        else if (monoType == VIEW_MONO_OF_GREEN)
            makeMonoOfComponent(currentImage.green);
        else if (monoType == VIEW_MONO_OF_BLUE)
            makeMonoOfComponent(currentImage.blue);
        else if (monoType == VIEW_MONO_OF_Y) {
            currentImage.rgbToYuv();
            makeMonoOfComponent(currentImage.yComponent);
        } else if (monoType == VIEW_COLOR) {
            currentImage.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
        } 
    }

    public void setMono (int type) {

        boolean valid =
                type == VIEW_MONO_OF_RED   ||
                type == VIEW_MONO_OF_GREEN ||
                type == VIEW_MONO_OF_BLUE  ||
                type == VIEW_MONO_OF_Y     ||
                type == VIEW_COLOR
                ;

        if (valid)
            monoType = type;
        else {
            throw new InvalidParameterException();
        }

        update();

    }

    public int getMonoType () {
        return monoType;
    }

    private void makeMonoOfComponent (ColorComponent comp) {

        final int image[] = currentImage.image;
        final float src[] = comp.getValueArray();

        int row;

        for (int i = 0; i < currentImage.height; ++i) {
            row = i*currentImage.width;
            for (int j = 0; j < currentImage.width; ++j) {
                int value = ColorComponent.floatToByte(src[row + j]);
                image[row + j] =
                        (value << 16) |
                        (value <<  8) |
                        (value <<  0);
            }
        }

    }

    private void applyExibitionMask () {
        final int image[] = currentImage.image;
        for (int i = 0; i < image.length; ++i)
            image[i] &= exibitionMask;
    }



}
