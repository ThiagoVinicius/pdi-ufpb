/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;

/**
 *
 * @author thiago
 */
public class Negative extends ConcurrentPointyFilter {

    public final boolean useConcurrency;

    public Negative (boolean useConcurrency) {
        this.useConcurrency = useConcurrency;
    }

    public Negative () {
        this(false);
    }

    @Override
    public void applyFilter(ColorComponent dest, ColorComponent source) {

        if (useConcurrency) {
            super.applyFilter(dest, source);
        }

        else {
            float destination[] = dest.getValueArray(true);
            float from[] = source.getValueArray();

            for (int i = 0; i < destination.length; ++i)
                destination[i] = 1.0f - from[i];
        }

    }

    public void applyFilter(
            ColorComponent dest,
            ColorComponent source,
            int xi,
            int width,
            int yi,
            int height) {

        float destination[] = dest.getValueArray(true);
        float from[] = source.getValueArray();

        int row;
        final int iMax, jMax;

        iMax = yi+height;
        jMax = xi+width;

        for (int i = yi; i < iMax; ++i) {
            row = i*width;
            for (int j = xi; j < jMax; ++j) {
                destination[row+j] = 1.0f - from[row+j];
            }
        }

    }

    
}
