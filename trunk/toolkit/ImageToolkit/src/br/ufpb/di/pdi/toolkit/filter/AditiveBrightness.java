/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;

/**
 *
 * @author thiago
 */
public class AditiveBrightness extends PointyFilter {

    private float aditiveFactor;

    public AditiveBrightness (int add) {
        aditiveFactor = add / 255.0f;
    }

    @Override
    public void applyFilter(ColorComponent dest, ColorComponent source) {
        
        float destination[] = dest.getValueArray(true);
        float from[] = source.getValueArray();

        for (int i = 0; i < destination.length; ++i)
            destination[i] = from[i] + aditiveFactor;

    }

}
