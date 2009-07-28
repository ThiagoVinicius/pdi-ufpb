package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;

public class MultiplicativeBrightness extends ConcurrentPointyFilter{

	public final float multFactor;
	
	public MultiplicativeBrightness(float multFactor){
		this.multFactor = multFactor;
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
                destination[row+j] = from[row+j] * multFactor;
            }
        }
		
	}

    @Override
    public String toString () {
        return "Brilho multiplicativo: " + multFactor;
    }

}
