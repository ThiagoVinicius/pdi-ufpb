package br.ufpb.di.pdi.toolkit.filter;

import java.util.Random;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;

public class SaltAndPepperNoise extends AbstractFilter{

	public final float prob;
	public final long seed;
	
	public SaltAndPepperNoise(float prob, long seed){
		this.prob = prob;
		this.seed = seed;
	}
	
	public SaltAndPepperNoise(float prob){
		this(prob, System.currentTimeMillis());
	}
	

	public void applyFilter (ImageWrapper target, int mask) {
        applyFilter(target, target, mask);
    }
	
	public void applyFilter (ColorComponent target) {
        applyFilter(target, target);
    }
	
	@Override
	public void applyFilter(ColorComponent dest, ColorComponent source) {
		
		final float destination[] = dest.getValueArray(true);
		final Random rand = new Random(seed);
		
		for (int i = 0; i < destination.length; ++i) {
			
			if (rand.nextDouble() < prob) {
            	if (rand.nextDouble() < 0.5) {
            		destination[i] = 1.0f;
            	} else {
            		destination[i] = 0.0f;
            	}
            }
			
		}
		
	}

    public String toString () {
        return String.format("Sal e pimenta: %.2f", prob);
    }

}
