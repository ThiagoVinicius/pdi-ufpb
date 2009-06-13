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
public class AditiveBrightness extends ConcurrentPointyFilter {

    public final float aditiveFactor;

    public AditiveBrightness (int add) {
        aditiveFactor = add / 255.0f;
    }

    @Override
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
                destination[row+j] = from[row+j] + aditiveFactor;
            }
        }

    }

}
