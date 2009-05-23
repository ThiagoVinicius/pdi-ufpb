/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

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

        ImageWrapper input = new ImageWrapper(2, 2);
        input.loadFromDisk(new File(
                "D:\\Thiago\\ufpb\\p5\\pdi\\sketchpad\\simple-test.bmp"));

        ImageWrapper padded = input.addBorder(1, 1, 1, 1);
        
        for (int i = 0; i < input.image.length; ++i) {
            System.out.println(Integer.toBinaryString(input.image[i] & 0x00ffffff));
        }
        System.out.println(Arrays.toString(input.image));
        System.out.println(Arrays.toString(padded.image));

    }

}
