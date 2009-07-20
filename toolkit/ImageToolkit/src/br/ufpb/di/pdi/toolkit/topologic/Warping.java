/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

/**
 *
 * @author thiago
 */
public class Warping {

    private final Line2D referenceVectors[];
    private final Line2D targetVectors[];

    /**Representam os respectivos Q-P*/
    private final Line2D referenceDiff[];

    /**Representam os respectivos Q'-P'*/
    private final Line2D targetDiff[];

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

    public Warping(Line2D referenceVectors[], Line2D targetVectors[]) {

        if (referenceVectors.length != targetVectors.length) {
            throw new InvalidParameterException("Deve haver o mesmo numero de " +
                    "vetores referencia e alvo");
        }

        this.referenceVectors = referenceVectors;
        this.targetVectors    = targetVectors;

        this.referenceDiff    = new Line2D[referenceVectors.length];
        for (int i = 0; i < referenceDiff.length; ++i) {
            referenceDiff[i] = subtract(
                    referenceVectors[i].getP2(),
                    referenceVectors[i].getP1());
        }

        this.targetDiff        = new Line2D[targetVectors.length];
        for (int i = 0; i < referenceDiff.length; ++i) {
            targetDiff[i] = subtract(
                    targetDiff[i].getP2(),
                    targetDiff[i].getP1());
        }

    }

    private static final Line2D subtract(Point2D l1, Point2D l2) {

        double xf, yf;

        xf = l1.getX() - l2.getX();
        yf = l1.getY() - l2.getY();

        return new Line2D.Double(0, 0, xf, yf);

    }

    private static final double cartezianProduct (Line2D l1, Line2D l2) {

        return l1.getX2()*l2.getX2() + l1.getY2()*l2.getY2();

    }

    //@FIXME So leva em conta o primeiro par de vetores do array
    //@FIXME nao faz nada, alem de calcular u e v =(
    public Point2D comesFrom (int x, int y) {
        Point2D.Double start = new Point2D.Double(x, y);
        Point2D.Float result = new Point2D.Float();

        //para o 'for' que vai ser necessario
        int i = 0;

        double u = cartezianProduct(
                        subtract(start, referenceVectors[i].getP1()),
                        referenceDiff[i]);
        u /= referenceVectors[i].getP2().distanceSq(referenceVectors[i].getP1());

        double v = referenceVectors[i].ptLineDist(start);
        v *= referenceVectors[i].relativeCCW(start);



        return result;
    }

}
