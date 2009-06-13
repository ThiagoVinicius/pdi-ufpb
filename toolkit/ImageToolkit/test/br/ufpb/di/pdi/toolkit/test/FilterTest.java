/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.AreaFilter;
import br.ufpb.di.pdi.toolkit.filter.Average;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;
import br.ufpb.di.pdi.toolkit.filter.Median;
import br.ufpb.di.pdi.toolkit.filter.MultiplicativeBrightness;
import br.ufpb.di.pdi.toolkit.filter.Negative;
import br.ufpb.di.pdi.toolkit.test.util.Timer;
import java.io.File;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author thiago
 */
public class FilterTest extends TestCase {

    public static final File FILENAMES[];
    
    static {
    	 FILENAMES = new File("input").listFiles();
    }
    
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

        for (File i : FILENAMES) {

            System.out.println("Processando brilho aditivo em: "+i);

            ImageWrapper original = readImage(i);

            AditiveBrightness filter = new AditiveBrightness(50);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+"brilho 50.png"));

            System.out.println("----- ----- ----- ----- -----");

        }

    }

    public void testConcurrentNegative () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando negativo concorrente em: "+i);

            ImageWrapper original = readImage(i);

            Negative filter = new Negative();
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+"negativo concorrente.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }

    public void testAverage () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando media em: "+i);

            ImageWrapper original = readImage(i);

            Average filter = new Average(3);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+" media concorrente.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }
    
    public void testMultiplicative () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando brilho multiplicativo em: "+i);

            ImageWrapper original = readImage(i);

            MultiplicativeBrightness filter = new MultiplicativeBrightness(0.6f);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+" brilho multiplicativo concorrente.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }
    
    public void testMedian () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando mediana em: "+i);

            ImageWrapper original = readImage(i);

            Median filter = new Median(3);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+" mediana.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }

}
