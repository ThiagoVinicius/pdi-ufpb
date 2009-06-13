package br.ufpb.di.pdi.toolkit.dissolve;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;

public class Dissolve {
	
	public final float t;
	
	public Dissolve(float t){
		
		this.t = t;
	}
	
	public void apply (ImageWrapper dest, ImageWrapper img1, ImageWrapper img2,  int mask) {

        if ((mask & AbstractFilter.RED) != 0) {
            apply(dest.red, img1.red, img2.red);
        }

        if ((mask & AbstractFilter.GREEN) != 0) {
            apply(dest.green, img1.green, img2.green);
        }

        if ((mask & AbstractFilter.BLUE) != 0) {
            apply(dest.blue, img1.blue, img2.blue);
        }

        if ((mask & AbstractFilter.Y) != 0) {
            apply(dest.yComponent, img1.yComponent, img2.yComponent);
        }

        if ((mask & AbstractFilter.U) != 0) {
            apply(dest.uComponent, img1.uComponent, img2.uComponent);
        }

        if ((mask & AbstractFilter.V) != 0) {
            apply(dest.vComponent, img1.vComponent, img2.vComponent);
        }

    }
	
	public void apply(ColorComponent dest, ColorComponent img1, ColorComponent img2){
		
		final float destination[] = dest.getValueArray(true);
		final float image1[] = img1.getValueArray();
		final float image2[] = img2.getValueArray();
		
		for(int i=0; i<destination.length; i++){
			destination[i] = (1.0f - this.t)*image1[i] + this.t*image2[i];
		}
		
	}

}
