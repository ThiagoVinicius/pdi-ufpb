/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;

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

    public ImageWrapper (int width, int height, int image[]) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public ImageWrapper (int width, int height) {
        this(width, height, new int[width*height]);
    }

    public void loadFromDisk (File path) throws IOException, WrongImageSizeException {

        BufferedImage temp = ImageIO.read(path);

        if (temp.getHeight() != height || temp.getWidth() != width)
            throw new WrongImageSizeException();

        type = temp.getType();

        temp.getRGB(0, 0, width, height, image, 0, width);
        
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

        for (int i = 0; i < width; ++i) {
            newRow = (i+up) * newWidth;
            row = i*width;
            for (int j = 0; j < height; ++j) {
                newArray[newRow + j+left] = image[row + j];
            }
        }

        return result;

    }

}
