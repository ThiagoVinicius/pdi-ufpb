package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Point2D;

class Ponto extends Point2D{
    
    private double x, y;
    
    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void soma(double k){
        x = x + k;
        y = y + k;
    }
    
    public void soma(Ponto p){
        x = x + p.getX();
        y = y + p.getY();
    }
    
    public void subtrai(double k){
        x = x - k;
        y = y - k;
    }
    
    public void subtrai(Ponto p){
        x = x - p.getX();
        y = y - p.getY();
    }
    
    public void multiplica(double k){
        x = x * k;
        y = y * k;
    }
    
    public void multiplica(Ponto p){
        x = x * p.getX();
        y = y * p.getY();
    }
    
    public void divide(double k){
        x = x / k;
        y = y / k;
    }
    
    public void divide(Ponto p){
        x = x / p.getX();
        y = y / p.getY();
    }

}
