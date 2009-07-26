/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.AreaFilter;
import br.ufpb.di.pdi.toolkit.filter.Average;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;
import br.ufpb.di.pdi.toolkit.filter.Median;
import br.ufpb.di.pdi.toolkit.filter.MultiplicativeBrightness;
import br.ufpb.di.pdi.toolkit.filter.Negative;
import br.ufpb.di.pdi.toolkit.filter.StandardDeviation;
import br.ufpb.di.pdi.toolkit.test.util.Timer;
import br.ufpb.di.pdi.toolkit.topologic.Rotation;
import br.ufpb.di.pdi.toolkit.topologic.Scale;
import java.awt.geom.Point2D;
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

    private static void applyDebugRGBFilter (ConcurrentFilter filter, ImageWrapper target) {

        Timer aplicacao = new Timer();

        aplicacao.start();
            target.createRGBFromImage();
            ImageWrapper input = target.clone();
            //filter.applyFilter(target, AbstractFilter.RGB);

            input.red  .calculateBorderMedian();
            input.green.calculateBorderMedian();
            input.blue .calculateBorderMedian();

            input.red  .setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            input.green.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            input.blue .setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);

            filter.applyFilter(target.blue, input.blue, 0, target.width, 0, target.height);
            filter.applyFilter(target.green, input.green, 0, target.width, 0, target.height);
            filter.applyFilter(target.red, input.red, 0, target.width, 0, target.height);
            target.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
       aplicacao.stop();
       System.out.println("Tempo para aplicar filtro: "+aplicacao);

    }
    
    private static void applyRGBFilter (AbstractFilter filter, ImageWrapper target) {

        Timer aplicacao = new Timer();

        aplicacao.start();
            target.createRGBFromImage();
            target.red  .calculateBorderMedian();
            target.green.calculateBorderMedian();
            target.blue .calculateBorderMedian();

            target.red  .setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            target.green.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            target.blue .setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);

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

//    public void testAditiveBrightness () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando brilho aditivo em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            AditiveBrightness filter = new AditiveBrightness(50);
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+"brilho 50.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//
//    }
//
//    public void testConcurrentNegative () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando negativo concorrente em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            Negative filter = new Negative();
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+"negativo concorrente.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//    }
//
//    public void testAverage () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando media em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            Average filter = new Average(15);
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+" media concorrente.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//    }
//
//    public void testMultiplicative () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando brilho multiplicativo em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            MultiplicativeBrightness filter = new MultiplicativeBrightness(0.6f);
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+" brilho multiplicativo concorrente.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//    }
    
//    public void testMedian () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando mediana em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            Median filter = new Median(1);
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+" mediana.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//    }
    
//    public void testStdDev () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando desvio padrao em: "+i);
//
//            ImageWrapper original = readImage(i);
//
//            StandardDeviation filter = new StandardDeviation(1);
//            applyRGBFilter(filter, original);
//
//            writeImage(original, new File (i.getName()+" desvio padrao.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//    }

    public void testRotation () throws Exception {

        for (File i : FILENAMES) {

            System.out.println("Processando rotacao em: "+i);

            ImageWrapper original = readImage(i);

            Rotation filter = new Rotation(
                    new Point2D[] {
                        new Point2D.Double(original.height-200, original.width-200),
                        new Point2D.Double(200, 200)


                    },
                    original.width, original.height);

            filter.setThetaType(Rotation.LINEAR_THETA);
            filter.setA(0.5);
            filter.setB(-1.0);


            applyRGBFilter(filter, original);
//            applyDebugRGBFilter(filter, original);

            writeImage(original, new File (i.getName()+" rotacao.png"));

            System.out.println("----- ----- ----- ----- -----");

        }
    }

//    public void testZoom2x () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando rotacao em: "+i);
//
//            ImageWrapper original = readImage(i);
//            ImageWrapper result   = new ImageWrapper(original.width*2, original.height*2);
//
//            original.createRGBFromImage();
//
//            final float[] r = result.red  .getValueArray(true);
//            final float[] g = result.green.getValueArray(true);
//            final float[] b = result.blue .getValueArray(true);
//
//            int row;
//
//            for (int j = 0; j < result.height; ++j) {
//                row = j*result.width;
//                for (int k = 0; k < result.width; ++k) {
//                    r[row+k] = original.red  .get(j/2.0f, k/2.0f);
//                    g[row+k] = original.green.get(j/2.0f, k/2.0f);
//                    b[row+k] = original.blue .get(j/2.0f, k/2.0f);
//                }
//            }
//
//            result.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
//
//
//
//            //applyRGBFilter(filter, original);
//            //applyDebugRGBFilter(filter, original);
//
//            writeImage(result, new File (i.getName()+" zoomin.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//
//    }

//    public void testZoom2x () throws Exception {
//
//        for (File i : FILENAMES) {
//
//            System.out.println("Processando escala em: "+i);
//
//            ImageWrapper original = readImage(i);
//            ImageWrapper result   = new ImageWrapper(160, 120);
//
//            original.createRGBFromImage();
//
//            Scale filter = new Scale(160, 120);
//
//            filter.applyFilter(result, original, AbstractFilter.RGB);
//
//            result.updateImageFromRGB(ImageWrapper.RED | ImageWrapper.GREEN | ImageWrapper.BLUE);
//
//
//
//            //applyRGBFilter(filter, original);
//            //applyDebugRGBFilter(filter, original);
//
//            writeImage(result, new File (i.getName()+" escala 160 120.png"));
//
//            System.out.println("----- ----- ----- ----- -----");
//
//        }
//
//    }

}
