package br.ufpb.di.pdi.toolkit.test;

import java.io.File;

import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.dissolve.Dissolve;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.test.util.Timer;
import junit.framework.TestCase;

public class DissolveTest extends TestCase {
	
public static final File FILENAMES[];
    
    static {
    	 FILENAMES = new File("dissolve").listFiles();
    }
    
    private static void applyRGBFilter (Dissolve filter, ImageWrapper dest, ImageWrapper src1, ImageWrapper src2) {

        Timer aplicacao = new Timer();

        aplicacao.start();
            src1.createRGBFromImage();
            src2.createRGBFromImage();
            filter.apply(dest, src1, src2, AbstractFilter.RGB);
            dest.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
       aplicacao.stop();
       System.out.println("Tempo para aplicar filtro: "+aplicacao);

    }

    private static ImageWrapper readImage (File arquivo) throws Exception {
        ImageWrapper result;
        Timer leitura = new Timer();

        leitura.start();
            result = ImageWrapper.createFromDisk(new File("dissolve", arquivo.getName()));
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

        for (int k = 0; k < FILENAMES.length - 1; ++k) {
        	File i = FILENAMES[k];
        	File j = FILENAMES[k+1];

            System.out.println("Processando dissolve em: "+i+" e " +j);

            ImageWrapper img1 = readImage(i);
            ImageWrapper img2 = readImage(j);
            ImageWrapper img3 = new ImageWrapper(img1.width, img2.height);

            Dissolve filter = new Dissolve(0.5f);
            applyRGBFilter(filter, img3, img1, img2);

            writeImage(img3, new File (i.getName()+"brilho 50.png"));

            System.out.println("----- ----- ----- ----- -----");

        }

    }

}
