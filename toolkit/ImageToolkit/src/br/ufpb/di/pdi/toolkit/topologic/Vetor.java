package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Vetor extends Line2D{
    
    private double x1, y1, x2, y2;
    
    public double getX1() {
        return this.x1;
    }

    public double getY1() {
        return this.y1;
    }

    public Point2D getP1() {
        return new Ponto(x1, y1);
    }

    public double getX2() {
        return this.x2;
    }

    public double getY2() {
        return this.y2;
    }

    public Point2D getP2() {
        return new Ponto(x2, y2);
    }
    
    public void setX1(double a){
        this.x1 = a;
    }
    
    public void setX2(double a){
        this.x1 = a;
    }
    
    public void setY1(double a){
        this.y1 = a;
    }
    
    public void setY2(double a){
        this.y2 = a;
    }
    
    public void setP1(Ponto p){
        x1 = p.getX();
        y1 = p.getY();
    }
    
    public void setP2(Ponto p){
        x2 = p.getX();
        y2 = p.getY();
    }

    public void setLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
    }

}
