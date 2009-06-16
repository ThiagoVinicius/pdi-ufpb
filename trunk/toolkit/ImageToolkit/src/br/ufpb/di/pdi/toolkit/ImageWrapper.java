/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Thiago
 */
public class ImageWrapper implements Cloneable {


    /**largura da imagem*/
    public final int width;

    /**altura da imagem*/
    public final int height;

    /**Tipo da imagem*/
    public int type;

    /**
     * Esta e' uma representacao da imagem.
     * <p/>
     * Para acessar uma coordenada c = (i, j),
     * use: index = i*width + j.
     * <p/>
     * Esta classe possui alguns metodos para escita e leitura de imagens
     * em disco.
     */
    public final int image[];

    public final ColorComponent red;
    public final ColorComponent green;
    public final ColorComponent blue;

    public final ColorComponent yComponent;
    public final ColorComponent uComponent;
    public final ColorComponent vComponent;

    public static final int RED   = 0x00ff0000;
    public static final int GREEN = 0x0000ff00;
    public static final int BLUE  = 0x000000ff;

    public ImageWrapper (int width, int height, int image[]) {
        this.width = width;
        this.height = height;
        this.image = image;

        red = new ColorComponent(width, height);
        green = new ColorComponent(width, height);
        blue = new ColorComponent(width, height);

        yComponent = new ColorComponent(width, height);
        uComponent = new ColorComponent(width, height);
        vComponent = new ColorComponent(width, height);

    }

    public ImageWrapper (int width, int height) {
        this(width, height, new int[width*height]);
    }

    private void loadFromDiskImpl (BufferedImage bi) {
        type = bi.getType();

        bi.getRGB(0, 0, width, height, image, 0, width);
    }

    public void loadFromDisk (File path) throws IOException, WrongImageSizeException {

        BufferedImage temp = ImageIO.read(path);

        if (temp.getHeight() != height || temp.getWidth() != width)
            throw new WrongImageSizeException();

        loadFromDiskImpl(temp);
        
    }

    public static ImageWrapper createFromDisk (File path) throws IOException {

        BufferedImage temp = ImageIO.read(path);
        ImageWrapper result;

        result = new ImageWrapper(temp.getWidth(), temp.getHeight());

        result.loadFromDiskImpl(temp);

        return result;

    }

    public static ImageWrapper createFromBuffer (BufferedImage bi) {
        ImageWrapper result;
        result = new ImageWrapper(bi.getWidth(), bi.getHeight());
        result.loadFromDiskImpl(bi);
        return result;
    }

    public void writeToDisk (File path) throws IOException {
        OutputStream os = new FileOutputStream(path);
        ImageIO.write(toBufferedImage(), "png", os);
    }

    public BufferedImage toBufferedImage () {
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        temp.setRGB(0, 0, width, height, image, 0, width);
        return temp;
    }

    public void writeToBufferedImage (BufferedImage where) 
            throws WrongImageSizeException {
        if (where.getHeight() != height || where.getWidth() != width)
            throw new WrongImageSizeException();
        where.setRGB(0, 0, width, height, image, 0, width);
    }



    public void updateImageFromRGB (int mask) throws IllegalStateException {

        float rArray[] = red.getValueArray();
        float gArray[] = green.getValueArray();
        float bArray[] = blue.getValueArray();
        
        if (rArray == null || gArray == null || bArray == null)
            throw new IllegalStateException ();

        int row;

        for (int i = 0; i < height; ++i) {
            row = i*width;
            for (int j = 0; j < width; ++j) {
                image[row + j] =
                        (ColorComponent.floatToByte(rArray[row + j]) << 16) |
                        (ColorComponent.floatToByte(gArray[row + j]) <<  8) |
                        (ColorComponent.floatToByte(bArray[row + j]) <<  0);
                image[row + j] &= mask;

            }
        }

    }

    public void createRGBFromImage () {
        float rArray[] = red.getValueArray(true);
        float gArray[] = green.getValueArray(true);
        float bArray[] = blue.getValueArray(true);

        int row;

        for (int i = 0; i < height; ++i) {
            row = i*width;
            for (int j = 0; j < width; ++j) {
                rArray[row + j] =
                    ColorComponent.byteToFloat((image[row + j] & RED) >> 16 );
                gArray[row + j] =
                    ColorComponent.byteToFloat((image[row + j] & GREEN) >> 8 );
                bArray[row + j] =
                    ColorComponent.byteToFloat((image[row + j] & BLUE) >> 0 );
            }
        }

    }

    public void rgbToYuv () {
        float rArray[] = red.getValueArray();
        float gArray[] = green.getValueArray();
        float bArray[] = blue.getValueArray();

        if (rArray == null || gArray == null || bArray == null)
            throw new IllegalStateException ();

        float yArray[] = yComponent.getValueArray(true);
        float uArray[] = uComponent.getValueArray(true);
        float vArray[] = vComponent.getValueArray(true);

        int row;
        int cur;

        for (int i = 0; i < height; ++i) {
            row = i*width;
            for (int j = 0; j < width; ++j) {
                cur = row + j;
                yArray[cur] =
                        rArray[cur]*0.299f +
                        gArray[cur]*0.587f +
                        bArray[cur]*0.114f;

                uArray[cur] =
                        rArray[cur]*(-0.14713f) +
                        gArray[cur]*(-0.28886f) +
                        bArray[cur]*0.436f;
                vArray[cur] =
                        rArray[cur]*0.615f +
                        gArray[cur]*(-0.51499f) +
                        bArray[cur]*(-0.10001f);
            }
        }

    }


    public void yuvToRgb() {
        float yArray[] = yComponent.getValueArray();
        float uArray[] = uComponent.getValueArray();
        float vArray[] = vComponent.getValueArray();

        if (yArray == null || uArray == null || vArray == null)
            throw new IllegalStateException ();

        float rArray[] = red.getValueArray(true);
        float gArray[] = green.getValueArray(true);
        float bArray[] = blue.getValueArray(true);


        int row;
        int cur;

        for (int i = 0; i < height; ++i) {
            row = i*width;
            for (int j = 0; j < width; ++j) {
                cur = row + j;
                rArray[cur] =
                        yArray[cur]*1.0f +
                        uArray[cur]*0.0f +
                        vArray[cur]*1.13983f;

                gArray[cur] =
                        yArray[cur]*1.0f +
                        uArray[cur]*(-0.39465f) +
                        vArray[cur]*(-0.58060f);
                bArray[cur] =
                        yArray[cur]*1.0f +
                        uArray[cur]*2.03211f +
                        vArray[cur]*0.0f;
            }
        }
    }

    public ImageWrapper addBorder (int up, int down, int left, int right) {

        ImageWrapper result;
        int newWidth = width+left+right;
        int newHeight = height+up+down;
        int newArray[];
        int newRow;
        int row;
        result = new ImageWrapper(newWidth, newHeight);
        newArray = result.image;

        for (int i = 0; i < height; ++i) {
            newRow = (i+up) * newWidth;
            row = i*width;
            for (int j = 0; j < width; ++j) {
                newArray[newRow + j+left] = image[row + j];
            }
        }

        return result;

    }

    public ImageWrapper crop (int up, int down, int left, int right) {
        ImageWrapper result;
        int newWidth = width-left-right;
        int newHeight = height-up-down;
        int newArray[];
        int newRow;
        int row;
        result = new ImageWrapper(newWidth, newHeight);
        newArray = result.image;

        for (int i = 0; i < newHeight; ++i) {
            newRow = (i+up) * width;
            row = i*newWidth;
            for (int j = 0; j < newWidth; ++j) {
                newArray[row + j] = image[newRow + j+left];
            }
        }

        return result;
    }

    public boolean equals (Object o) {
        if (!(o instanceof ImageWrapper))
            return false;

        int ignoreAlpha = RED | GREEN | BLUE;
        ImageWrapper asBrother = (ImageWrapper)o;
        int check[] = asBrother.image;
        boolean sameSize =
                asBrother.height == height &&
                asBrother.width == width;


        if (!sameSize)
            return false;


        for (int i = 0; i < image.length; ++i)
            if ((image[i] & ignoreAlpha) != (check[i] & ignoreAlpha))
                return false;

        return true;

    }

    @Override
    public ImageWrapper clone() {
        ImageWrapper result;
        result = new ImageWrapper(width, height,
                Arrays.copyOf(image, image.length));

        try {

            if (red != null)
                result.red.setValueArray(red.clone().getValueArray());
            if (green != null)
                result.green.setValueArray(green.clone().getValueArray());
            if (blue != null)
                result.blue.setValueArray(blue.clone().getValueArray());
            if (yComponent.getValueArray() != null)
                result.yComponent.setValueArray(yComponent.clone().getValueArray());
            if (uComponent.getValueArray() != null)
                result.uComponent.setValueArray(uComponent.clone().getValueArray());
            if (vComponent.getValueArray() != null)
                result.vComponent.setValueArray(vComponent.clone().getValueArray());

        } catch (WrongImageSizeException e) {
            System.err.println("Excecao inesperada!");
        }

        return result;
    }

}
