/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.Negative;
import br.ufpb.di.pdi.toolkit.filter.PointyFilter;
import br.ufpb.di.pdi.toolkit.test.util.Timer;
import java.io.File;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author thiago
 */
public class FilterTest extends TestCase {

    public static final String FILENAMES[] = {
        "atlantis.jpg",
        "vialactea.jpg",
        "thiago.jpg"
    };
    
    private static void applyRGBFilter (AbstractFilter filter, ImageWrapper target) {

        Timer aplicacao = new Timer();

        aplicacao.start();
            target.createRGBFromImage();
            filter.applyFilter(target, AbstractFilter.RGB);
            target.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
       aplicacao.stop();
       System.out.println("Tempo para aplicar filtro: "+aplicacao);

    }

    private static ImageWrapper readImage (File arquivo) throws Exception {
        ImageWrapper result;
        Timer leitura = new Timer();

        leitura.start();
            result = ImageWrapper.createFromDisk(new File("input", arquivo.getName()));
        leitura.stop();
        System.out.println("Tempo para ler imagem: "+leitura);

        return result;
    }

    private static void writeImage (ImageWrapper image, File saida) throws Exception {
        Timer gravacao = new Timer();

        gravacao.start();
            image.writeToDisk(new File("output", saida.getName()));
        gravacao.stop();
        System.out.println("Tempo para gravar imagem: "+gravacao);
    }

    public void testAditiveBrightness () throws Exception {

        for (String i : FILENAMES) {

            System.out.println("Processando brilho aditivo em: "+i);

            ImageWrapper original = readImage(new File(i));

            AditiveBrightness filter = new AditiveBrightness(50);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i+"brilho 50.png"));

            System.out.println("----- ----- ----- ----- -----");

        }

    }

    public void testNegative () throws Exception {

        for (String i : FILENAMES) {

            System.out.println("Processando negativo em: "+i);

            ImageWrapper original = readImage(new File(i));

            Negative filter = new Negative();
            applyRGBFilter(filter, original);

            writeImage(original, new File (i+"negativo.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }

    public void testConcurrentNegative () throws Exception {

        for (String i : FILENAMES) {

            System.out.println("Processando negativo concorrente em: "+i);

            ImageWrapper original = readImage(new File(i));

            Negative filter = new Negative(true);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i+"negativo concorrente.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }

}
