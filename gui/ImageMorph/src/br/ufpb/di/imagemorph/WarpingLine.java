/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.Icon;

/**
 *
 * @author Thiago
 */
public class WarpingLine implements Icon {

    public Line2D myPosition;
    private WarpingLine brother;
    private boolean selected;
    private boolean brotherComes;
    private boolean brotherSelected;

    public static final float STROKE_WHEN_SELECTED = 5.0f;
    public static final float STROKE_WHEN_DESELECTED = 2.0f;

    public WarpingLine (WarpingLine myBrother) {
        selected = false;
        brotherComes = false;
        brother = myBrother;
        myPosition = new Line2D.Float(0, 0 , 0, 0);
        if (brother != null)
            brother.brother = this;
    }

    public WarpingLine () {
        this(null);
    }

    public static void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x, int y, float stroke) {
        double aDir = Math.atan2(xCenter - x, yCenter - y);
        g2d.drawLine(x, y, xCenter, yCenter);
        g2d.setStroke(new BasicStroke(1f));					// make the arrow head solid even if dash pattern has been specified
        Polygon tmpPoly = new Polygon();
        int i1 = 12 + (int) (stroke * 2);
        int i2 = 6 + (int) stroke;							// make the arrow head the same size regardless of the length length
        tmpPoly.addPoint(x, y);							// arrow tip
        tmpPoly.addPoint(x + xCor(i1, aDir + .5), y + yCor(i1, aDir + .5));
        tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
        tmpPoly.addPoint(x + xCor(i1, aDir - .5), y + yCor(i1, aDir - .5));
        tmpPoly.addPoint(x, y);							// arrow tip
        g2d.drawPolygon(tmpPoly);
        g2d.fillPolygon(tmpPoly);						// remove this line to leave arrow head unpainted
    }

    private static int yCor(int len, double dir) {
        return (int) (len * Math.cos(dir));
    }

    private static int xCor(int len, double dir) {
        return (int) (len * Math.sin(dir));
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        float stroke;

        if (isSelected())
            stroke = STROKE_WHEN_SELECTED;
        else
            stroke = STROKE_WHEN_DESELECTED;

        if (isSelected())
            g2d.setColor(Color.RED);
        else if (brotherSelected)
            g2d.setColor(Color.GREEN);
        else
            g2d.setColor(Color.BLUE);

        g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawArrow(g2d,
                 (int)myPosition.getP2().getX(),
                 (int)myPosition.getP2().getY(),
                 (int)myPosition.getP1().getX(),
                 (int)myPosition.getP1().getY(),
                 stroke);
    }

    public int getIconWidth() {
        return myPosition.getBounds().width;
    }

    public int getIconHeight() {
        return myPosition.getBounds().height;
    }

    public void setLine (Line2D newLine) {
        if (newLine == null)
            return;

        myPosition = newLine;

        if (brotherComes && brother != null) {

            float x1 = (float) newLine.getP1().getX();
            float y1 = (float) newLine.getP1().getY();
            float x2 = (float) newLine.getP2().getX();
            float y2 = (float) newLine.getP2().getY();

            brother.setLine(new Line2D.Float(x1, y1, x2, y2));

        }
    }

    public void setLine (int xi1, int yi1, int xi2, int yi2) {
        setLine(new Line2D.Float(xi1, yi1, xi2, yi2));
    }

    public void setLineStart (int x, int y) {
        Point2D p1 = new Point2D.Float(x, y);
        Point2D p2 = myPosition.getP2();


        setLine(new Line2D.Float(p1, p2));
    }

    public void setLineEnd (int x, int y) {
        Point2D p1 = myPosition.getP1();
        Point2D p2 = new Point2D.Float(x, y);

        setLine(new Line2D.Float(p1, p2));
    }

    @Override
    public String toString() {
        return String.format("%.0f , %.0f ---> %.0f , %.0f",
                 myPosition.getP1().getX(),
                 myPosition.getP1().getY(),
                 myPosition.getP2().getX(),
                 myPosition.getP2().getY());
    }

    public boolean isSelected () {
        return selected;
    }

    public void setSelected (boolean newValue) {
        selected = newValue;
        if (brother != null)
            brother.brotherSelected = newValue;
    }

    public void bringYourBrotherWithYou () {
        brotherComes = true;
    }

    public void leaveYourBrotherAlone () {
        brotherComes = false;
    }

    public WarpingLine whoIsYourBrother () {
        return brother;
    }

}
