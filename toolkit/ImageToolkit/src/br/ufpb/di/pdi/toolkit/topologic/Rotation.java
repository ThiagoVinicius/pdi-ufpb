/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Point2D;

/**
 *
 * @author thiago, gabriela
 */
public class Rotation {
	
	private final Point2D rotationCenters[];
	private final double diagonal;
	
	public static final int CONSTANT_THETA = 0;
	public static final int LINEAR_THETA = 1;
	public static final int QUAD_THETA = 2;
	public static final int SEN_THETA = 3;

	/**
	 * 
	 * @param rotationCenters centros de rotacao escolhidos pelo usuario
	 * @param width largura da imagem
	 * @param height altura da imagem
	 */
	public Rotation(Point2D rotationCenters[], int width, int height) {
		this.rotationCenters = rotationCenters;
		this.diagonal = Math.hypot(width, height);
	}
	
	private double constantTheta(double a){
		return a*Math.PI;
	}
	
	private double linearTheta(double a, double b, double d) {
		return (d+a)*b*Math.PI;
	}
	
	private double quadTheta(double a, double b, double d) {
		return Math.pow(d, a)*b*Math.PI;
	}
	
	private double senTheta(double a, double b, double d)
	{
		return Math.sin(d*a*Math.PI)*b*Math.PI;
	}
	
	/**
	 * 
	 * @param x do ponto da imagem final 
	 * @param y do ponto da imagem final
	 * @param thetaType tipo da fun��o do angulo escolhido
	 * @param a
	 * @param b
	 * @return ponto da imagem inicial de onde veio (mapeamento reverso)
	 */
	public Point2D comesFrom(int x, int y, int thetaType, double a, double b) {
		final Point2D.Float pixel = new Point2D.Float(x,y);
		Point2D.Float result = new Point2D.Float();
		final double theta[] = new double[this.rotationCenters.length];
		final double weight[] = new double[this.rotationCenters.length];
		double is = 0;
		double js = 0;
		double totalWeight = 0;
		
		//Calculando o angulo de rotacao de acordo com a funcao escolhida
		if(thetaType == Rotation.CONSTANT_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.constantTheta(a);
			}
		} else if(thetaType == Rotation.LINEAR_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.linearTheta(a, b, d);
			}
		} else if(thetaType == Rotation.QUAD_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.quadTheta(a, b, d);
			}
		} else if(thetaType == Rotation.SEN_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.senTheta(a, b, d);
			}
		}
		
		//Calculando i' e j' para cada centro e multiplicando por seu peso
		for(int i = 0; i<this.rotationCenters.length; i++) {
			double ic = this.rotationCenters[i].getX();
			double jc = this.rotationCenters[i].getY();
			double iLine = (x - ic)*Math.cos(theta[i]) - (y - jc)*Math.sin(theta[i]) + ic;
			double jLine = (x - ic)*Math.sin(theta[i]) + (y - jc)*Math.cos(theta[i]) + jc;
			is += iLine*weight[i];
			js += jLine*weight[i];
		}
		
		result.x = (float) (is/totalWeight);
		result.y = (float) (js/totalWeight);
		
		return result;
	}
    

}
