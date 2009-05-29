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

/**
 *
 * @author Thiago
 */
public class ImageWrapper {


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

    private void loadFromDiskImpl (File path, BufferedImage bi) {
        type = bi.getType();

        bi.getRGB(0, 0, width, height, image, 0, width);
    }

    public void loadFromDisk (File path) throws IOException, WrongImageSizeException {

        BufferedImage temp = ImageIO.read(path);

        if (temp.getHeight() != height || temp.getWidth() != width)
            throw new WrongImageSizeException();

        loadFromDiskImpl(path, temp);
        
    }

    public static ImageWrapper createFromDisk (File path) throws IOException {

        BufferedImage temp = ImageIO.read(path);
        ImageWrapper result;

        result = new ImageWrapper(temp.getWidth(), temp.getHeight());

        result.loadFromDiskImpl(path, temp);

        return result;

    }

    public void writeToDisk (File path) throws IOException {
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        OutputStream os = new FileOutputStream(path);
        temp.setRGB(0, 0, width, height, image, 0, width);
        ImageIO.write(temp, "png", os);
    }

    public void updateImageFromRGB (int mask) {

        float rArray[] = red.values;
        float gArray[] = green.values;
        float bArray[] = blue.values;

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
        float rArray[] = red.values;
        float gArray[] = green.values;
        float bArray[] = blue.values;

        if (rArray == null)
            rArray = new float[height*width];
        if (gArray == null)
            gArray = new float[height*width];
        if (bArray == null)
            bArray = new float[height*width];

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

        red.values = rArray;
        green.values = gArray;
        blue.values = bArray;

    }

    public void rgbToYuv () {
        float rArray[] = red.values;
        float gArray[] = green.values;
        float bArray[] = blue.values;

        float yArray[] = yComponent.values;
        float uArray[] = uComponent.values;
        float vArray[] = vComponent.values;

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

                uArray[row + j] =
                    rArray[cur]*(-0.147f) +
                    gArray[cur]*(-0.289f) +
                    bArray[cur]*0.436f;
                vArray[row + j] =
                    rArray[cur]*0.615f +
                    gArray[cur]*(-0.515f) +
                    bArray[cur]*(-0.100f);
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

}
