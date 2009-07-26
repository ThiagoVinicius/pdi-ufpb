/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.topologic;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.security.InvalidParameterException;

/**
 *
 * @author thiago
 */
public class Scale extends ConcurrentFilter {

    public final int finalWidth;
    public final int finalHeight;

    public Scale(int finalWidth, int finalHeight) {

        this.finalWidth = finalWidth;
        this.finalHeight = finalHeight;

    }

    public void applyFilter(ColorComponent dest,
                            ColorComponent source,
                            int xi,
                            int width,
                            int yi,
                            int height) {

        if (dest.width != finalWidth || dest.heigth != finalHeight)
            throw new InvalidParameterException("Mismatching sizes o.O");

        float destination[] = dest.getValueArray(true);

        int row;
        final int iMax, jMax;

        iMax = yi+height;
        jMax = xi+width;

        float horStep = source.width /(finalWidth -1.0f);
        float verStep = source.heigth/(finalHeight-1.0f);

        for (int i = yi; i < iMax; ++i) {
            row = i*width;
            for (int j = xi; j < jMax; ++j) {
            	destination[row+j] = source.get(i*verStep, j*horStep);
            }
        }


    }

//    @Override
//    public void applyFilter(ColorComponent dest, ColorComponent source) {
//        applyFilter(dest, source, 0, dest.width, 0, dest.heigth);
//    }

}
