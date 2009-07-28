/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;

/**
 *
 * @author thiago
 */
public class Warping extends ConcurrentFilter {

    private final Line2D targetVectors[];
    private final Line2D referenceVectors[];

    /**Representam os respectivos Q-P*/
    private final Line2D referenceDiff[];

    /**Representam os respectivos Q'-P'*/
    private final Line2D targetDiff[];
    
    private final double a;
    private final double b;
    private final double p;
    
    private double weightsum = 0;
    private Point2D dsum;

    /**
     *
     * Representa um ponto a 'esquerda' da reta
     *
     * De acordo com o a convecao usada na classe Line2D.
     *
     * @see Line2D#relativeCCW(double, double, double, double, double, double)
     *
     */
    public static final int COUNTER_CLOCKWISE = 1;

    public Warping(Line2D targetVectors[], Line2D referenceVectors[], double a, double b, double p) {

        if (targetVectors.length != referenceVectors.length) {
            throw new InvalidParameterException("Deve haver o mesmo numero de " +
                    "vetores referencia e alvo");
        }

        this.targetVectors = targetVectors;
        this.referenceVectors    = referenceVectors;

        this.referenceDiff    = new Line2D[targetVectors.length];
        for (int i = 0; i < referenceDiff.length; ++i) {
            referenceDiff[i] = subtract(
                    targetVectors[i].getP2(),
                    targetVectors[i].getP1());
        }

        this.targetDiff        = new Line2D[referenceVectors.length];
        for (int i = 0; i < referenceDiff.length; ++i) {
            targetDiff[i] = subtract(
                    referenceVectors[i].getP2(),
                    referenceVectors[i].getP1());
        }
        
        this.a = a;
        this.b = b;
        this.p = p;

    }

    private static final Line2D subtract(Point2D l1, Point2D l2) {

        double xf, yf;

        xf = l1.getX() - l2.getX();
        yf = l1.getY() - l2.getY();

        return new Line2D.Double(0, 0, xf, yf);

    }

    private static final double internProduct(Line2D l1, Line2D l2) {

        return l1.getX2()*l2.getX2() + l1.getY2()*l2.getY2();

    }
    
    private static final Line2D unitarizacao(Line2D l, double norma) {
    	
    	return new Line2D.Double(l.getX1() / norma, l.getY1() / norma, l.getX2() / norma, l.getY2() / norma);
    	
    }
    
    private static final double norma(Line2D l) {
    	
//    	double a = l.getX2() - l.getX1();
//    	double b = l.getY2() - l.getY1();

       return l.getP2().distance(l.getP1());
    	
//    	return Math.sqrt( (a * a) + (b * b) );
    	
    }
    
    private static final Line2D multiplica(double f, Line2D l){
    	
    	return new Line2D.Double(l.getP1().getX() * f, l.getP1().getY() * f, l.getP2().getX() * f, l.getP2().getY() * f);
    	
    }
    
    private static final Point2D multiplica(double f, Point2D l){
    	
    	return new Point2D.Double(l.getX() * f, l.getY() * f);
    	
    }
    
    private static final Point2D divide(double f, Point2D l){
    	
    	return new Point2D.Double(l.getX() / f, l.getY() / f);
    	
    }
    
    private static final Point2D soma(Point2D l1, Point2D l2){
    	
    	return new Point2D.Double( l1.getX() + l2.getX(), l1.getY() + l2.getY() );
    	
    }
    
    //@FIXME So leva em conta o primeiro par de vetores do array
    //@FIXME nao faz nada, alem de calcular u e v =(
    public Point2D comesFrom (Point2D start, int i) {
        
        Point2D pd = referenceVectors[i].getP1();
        Line2D perpendicular = null;

        double u = internProduct(
                        subtract(start, targetVectors[i].getP1()),
                        referenceDiff[i]);
        
        //normalizando o u:
        u /= targetVectors[i].getP2().distanceSq(targetVectors[i].getP1());        
        
        double v = targetVectors[i].ptLineDist(start);
        v *= targetVectors[i].relativeCCW(start);
       
        perpendicular = new Line2D.Double
                    ( 0, 0, targetDiff[i].getY2(), -1 * targetDiff[i].getX2() );
                   
        double normap = norma( perpendicular );        
        Line2D unitario = unitarizacao(perpendicular, normap);

        Point2D p1 = multiplica(u, targetDiff[i]).getP2();
        Point2D p2 = multiplica(v, unitario).getP2();
        
        Point2D destinoProvisorio = new Point2D.Double
                ( pd.getX() + p1.getX() + p2.getX(), pd.getY() + p1.getY() + p2.getY() );

        return destinoProvisorio;
        
    }
    
    public void doWeight(Point2D start, int i){

    	Point2D destinoProvisorio = comesFrom(start, i);
    	
    	Point2D di = new Point2D.Double( subtract(destinoProvisorio, start).getX2(), subtract(destinoProvisorio, start).getY2() );
    	double dist = targetVectors[i].ptSegDist(start);
    	double lenght = norma(targetVectors[i]);
    	
    	double weight = Math.pow( Math.pow(lenght, p) / (a + dist) , b );   
    	
    	weightsum += weight;    	
    	dsum = soma( multiplica(weight, di), dsum );
        
        //System.out.println(weightsum + "/" + dsum);
    	
    }
    
    public void doWeight(int x, int y, int i){
    	
    	Point2D.Double start = new Point2D.Double(x, y);
    	doWeight(start, i);
    	
    }

	public void applyFilter(ColorComponent dest, ColorComponent source, int xi,
			int width, int yi, int height) {
		
		float destination[] = dest.getValueArray(true);
		float from[] = source.getValueArray();

        int row;
        final int iMax, jMax;

        iMax = yi+height;
        jMax = xi+width;

        for (int i = yi; i < iMax; ++i) {
            row = i*width;
            for (int j = xi; j < jMax; ++j) {

                dsum = new Point2D.Double(0.0, 0.0);
                weightsum = 0.0;

            	Point2D start = new Point2D.Double(i, j);
            	
            	for (int k = 0; k < targetVectors.length; k++) {
					//atualiza o dsum e o weightsum para cada vetor
            		doWeight(start, k);
            	
            	}
            	
            	Point2D destino = new Point2D.Double
            		(start.getX() + divide(weightsum, dsum).getX(), start.getY() + divide(weightsum, dsum).getY());

//                System.out.printf("(%d %d) - >(%.1f %.1f)\n", (int) start.getX(), (int) start.getY(), destino.getX(), destino.getY());
            	
            	destination[row+j] = source.get((float)destino.getX(), (float)destino.getY());
            }
        }	
		
	}

}
