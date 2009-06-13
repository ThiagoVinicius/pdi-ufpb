package br.ufpb.di.pdi.toolkit.test;

import java.io.File;

import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.Average;
import br.ufpb.di.pdi.toolkit.filter.SaltAndPepperNoise;
import br.ufpb.di.pdi.toolkit.test.util.Timer;
import junit.framework.TestCase;

public class NoiseTest extends TestCase {
	
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

    public void testSaltPepper () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando ruido salt and pepper em: "+i);

            ImageWrapper original = readImage(i);

            SaltAndPepperNoise noise = new SaltAndPepperNoise(0.02f);
            applyRGBFilter(noise, original);

            writeImage(original, new File (i.getName()+"salt and pepper.png"));
            
            Average filter = new Average(3);
            applyRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+"salt and pepper + media.png"));

            System.out.println("----- ----- ----- ----- -----");

        }

    }

	
}
