/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import java.awt.geom.Point2D;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;

/**
 *
 * @author thiago, gabriela
 */
public class Rotation extends ConcurrentFilter{
	
	private final Point2D rotationCenters[];
	private final double diagonal;
	
	private double a = 0;
	private double b = 0;
	private int thetaType = 0;
	
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
	
	private double constantTheta(){
		return a*Math.PI;
	}
	
	private double linearTheta(double d) {
		return (d+a)*b*Math.PI;
	}
	
	private double quadTheta(double d) {
		return Math.pow(d, a)*b*Math.PI;
	}
	
	private double senTheta(double d)
	{
		return Math.sin(d*a*Math.PI)*b*Math.PI;
	}
	
	/**
	 * 
	 * @param x do ponto da imagem final 
	 * @param y do ponto da imagem final
	 * @return ponto da imagem inicial de onde veio (mapeamento reverso)
	 */
	private Point2D.Float comesFrom(int x, int y) {
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
				theta[i] = this.constantTheta();
			}
		} else if(thetaType == Rotation.LINEAR_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.linearTheta(d);
			}
		} else if(thetaType == Rotation.QUAD_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.quadTheta(d);
			}
		} else if(thetaType == Rotation.SEN_THETA) {
			for(int i=0; i<this.rotationCenters.length;i++) {
				double d = pixel.distance(this.rotationCenters[i])/diagonal;
				weight[i] = 1 - Math.pow(d, 2);
				totalWeight += weight[i];
				theta[i] = this.senTheta(d);
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

	public void applyRot(ColorComponent dest, ColorComponent source, int width, int height) {
		
		
		
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public int getThetaType() {
		return thetaType;
	}

	public void setThetaType(int thetaType) {
		this.thetaType = thetaType;
	}

	@Override
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
            	Point2D.Float veioDe = comesFrom(i, j);
            	destination[row+j] = source.get((float)veioDe.getX(), (float)veioDe.getY());
            }
        }
		
	}
	
	
}
