/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import java.io.File;
import java.util.Arrays;
import junit.framework.TestCase;
import br.ufpb.di.pdi.toolkit.ImageWrapper;

/**
 *
 * @author Thiago
 */
public class WrapperTest extends TestCase {

    public void testWrapper () throws Exception {

        File path = new File("D:\\Thiago\\ufpb\\p5\\pdi\\sketchpad\\F-18.jpg");
        File out = new File("D:\\Thiago\\ufpb\\p5\\pdi\\sketchpad\\saida.png");

        long t0Total = System.currentTimeMillis();

        ImageWrapper input = ImageWrapper.createFromDisk(path);


        long t0Processamento = System.currentTimeMillis();
        ImageWrapper padded = input.addBorder(1, 1, 1, 1);
        ImageWrapper cropped = padded.crop(1, 1, 1, 1);

//        for (int i = 0; i < input.image.length; ++i) {
//            System.out.println(Integer.toBinaryString(input.image[i] & 0x00ffffff));
//        }
//        System.out.println(Arrays.toString(input.image));
//        System.out.println(Arrays.toString(padded.image));
//        System.out.println(Arrays.toString(cropped.image));

        assertEquals(input, cropped);
        cropped.createRGBFromImage();
        cropped.updateImageFromRGB(0x00ffffff);
        assertEquals(input, cropped);

        System.out.println("Tempo de processamento: " + (System.currentTimeMillis() - t0Processamento));

        cropped.writeToDisk(out);

        System.out.println("Tempo total: " + (System.currentTimeMillis() - t0Total));


    }

}