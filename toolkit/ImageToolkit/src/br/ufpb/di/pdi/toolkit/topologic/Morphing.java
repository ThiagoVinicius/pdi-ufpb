/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.dissolve.Dissolve;
import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import java.awt.geom.Line2D;
import java.security.InvalidParameterException;

public class Morphing {

    private Line2D[] referenceVectors, targetVectors;
    private double a, b, p;
    private int nquadros, inicioDissolve, nquadrosDissolve;

    private double passoDissolve;

    private double[] passoPX, passoPY, passoQX, passoQY;

    int quadroAtual;

    public Morphing(Line2D targetVectors[], Line2D referenceVectors[],
            double a, double b, double p, int nquadros, int inicioD, int fimD) {

        if (targetVectors.length != referenceVectors.length) {
            throw new InvalidParameterException("Deve haver o mesmo numero de " +
                    "vetores referencia e alvo");
        }

        this.targetVectors = targetVectors;
        this.referenceVectors = referenceVectors;

        this.a = a;
        this.b = b;
        this.p = p;

        this.nquadros = nquadros;
        this.inicioDissolve = inicioD;
        this.nquadrosDissolve = fimD - inicioD;

        this.passoDissolve = 1.0 / (fimD - inicioD);

        passoPX = new double[referenceVectors.length];
        passoPY = new double[referenceVectors.length];
        passoQX = new double[referenceVectors.length];
        passoQY = new double[referenceVectors.length];
        
        for (int i = 0; i < referenceVectors.length; i++) {

            passoPX[i] = ( targetVectors[i].getX1() - referenceVectors[i].getX1() ) / nquadros;
            passoPY[i] = ( targetVectors[i].getY1() - referenceVectors[i].getY1() ) / nquadros;
            passoQX[i] = ( targetVectors[i].getX2() - referenceVectors[i].getX2() ) / nquadros;
            passoQY[i] = ( targetVectors[i].getY2() - referenceVectors[i].getY2() ) / nquadros;

        }

    }
    
    private Line2D[] getCurrentTargetVectors(int index){
        
        Line2D[] vectors = new Line2D[referenceVectors.length];
        
        for (int i = 0; i < vectors.length; i++) {
            
            vectors[i] = new Line2D.Double(
                    referenceVectors[i].getX1() + ( index * passoPX[i] ),
                    referenceVectors[i].getY1() + ( index * passoPY[i] ),
                    referenceVectors[i].getX2() + ( index * passoQX[i] ),
                    referenceVectors[i].getY2() + ( index * passoQY[i] ) );
            
        }
        
        return vectors;
        
    }

    public ImageWrapper getQuadro (ImageWrapper im1, ImageWrapper im2, int index) throws WrongImageSizeException {

        if (im1.width != im2.width || im1.height != im2.height)
            throw new WrongImageSizeException();

        ImageWrapper result = new ImageWrapper(im1.width, im1.height);

        result.red  .imitate(getQuadro(im1.red  , im2.red  , index));
        result.green.imitate(getQuadro(im1.green, im2.green, index));
        result.blue .imitate(getQuadro(im1.blue , im2.blue , index));

        return result;

    }

    public ColorComponent getQuadro(ColorComponent im1, ColorComponent im2, int index){

        ColorComponent imResult = new ColorComponent(im1.width, im1.heigth);
        ColorComponent imr1 = null;
        ColorComponent imr2 = null;
        
        Warping warp1 = null;
        Warping warp2 = null;
        
        Line2D[] target1 = getCurrentTargetVectors(index);
        //Line2D[] target2 = getCurrentTargetVectors(nquadros - index -1);
        
        if(index < inicioDissolve + nquadrosDissolve){
        
            warp1 = new Warping(target1, referenceVectors, a, b, p);
            imr1 = new ColorComponent(im1.width, im1.heigth);
            warp1.applyFilter(imr1, im1);
            
        }
        
        if(index > inicioDissolve){
        
            warp2 = new Warping(target1, targetVectors, a, b, p);
            imr2 = new ColorComponent(im2.width, im2.heigth);
            warp2.applyFilter(imr2, im2);
        
        }
        
        if( (imr1 != null)&&(imr2 != null) ){
            
            float fatorDissolve = (float)(index * passoDissolve);
            Dissolve dis = new Dissolve(fatorDissolve);

            dis.apply(imResult, imr1, imr2);
            return imResult;
            
        }
        
        if(imr2 == null)
            return imr1;

        return imr2;

    }

}
