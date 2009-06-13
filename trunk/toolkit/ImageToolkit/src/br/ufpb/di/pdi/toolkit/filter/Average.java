/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import java.util.Arrays;

/**
 *
 * @author thiago
 */
public class Average extends ConcurrentFilter {

    public final int radius;

    public Average(int radius) {
        this.radius = radius;
    }

    @Override
    public void applyFilter(ColorComponent dest, ColorComponent source) {
        super.applyFilter(dest, source);
    }



    @Override
    public void applyFilter(ColorComponent dest, 
                            ColorComponent source,
                            int xi,
                            int width,
                            int yi,
                            int height) {

        final int h, w;
        h = height+yi;
        w = width+xi;

        final float d;
        d = (radius*2 + 1)  *  (radius*2 + 1);

        float destination[] = dest.getValueArray(true);
        float from[] = source.getValueArray();

        int row, cur, rowAux;
        int maxK, maxL;

        /*Percorre imagem*/
        for (int i = yi; i < h; ++i) {
            row = i*dest.width;
            for (int j = xi; j < w; ++j) {
                cur = row+j;

                destination[cur] = 0.0f;
                maxK = dest.heigth-i-1 < radius ? dest.heigth-1 : i+radius;
                maxL = dest.width-j-1  < radius ? dest.width-1  : j+radius;
                /*Soma valores*/
                for (int k = i < radius ? 0: i-radius; k <= maxK; ++k) {
                    rowAux = k*dest.width;
                    for (int l = j < radius ? 0 : j-radius; l <= maxL; ++l) {
                        destination[cur] += from[rowAux+l];
                    }
                }

                /*divide, para obter a media*/
                destination[cur] /= d;

            }
        }

    }

}
