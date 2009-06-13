package br.ufpb.di.pdi.toolkit.filter;

import java.util.Arrays;

import br.ufpb.di.pdi.toolkit.ColorComponent;

public class Median extends ConcurrentFilter {

	public final int radius;

    public Median(int radius) {
        this.radius = radius;
    }
	
	@Override
	public void applyFilter(ColorComponent dest, ColorComponent source, int xi,
			int width, int yi, int height) {

		final int h, w;
        h = height+yi;
        w = width+xi;

        final int d;
        d = (radius*2 + 1)  *  (radius*2 + 1);
        final float data[] = new float[d];

        float destination[] = dest.getValueArray(true);
        float from[] = source.getValueArray();

        int row, cur, rowAux, dataIndex;
        int maxK, maxL;

        /*Percorre imagem*/
        for (int i = yi; i < h; ++i) {
            row = i*dest.width;
            for (int j = xi; j < w; ++j) {
                cur = row+j;

                Arrays.fill(data, 0.0f);
                dataIndex = d-1;
                maxK = dest.heigth-i-1 < radius ? dest.heigth-1 : i+radius;
                maxL = dest.width-j-1  < radius ? dest.width-1  : j+radius;
                /*Soma valores*/
                for (int k = i < radius ? 0: i-radius; k <= maxK; ++k) {
                    rowAux = k*dest.width;
                    for (int l = j < radius ? 0 : j-radius; l <= maxL; ++l) {
                        data[dataIndex] = from[rowAux+l];
                        dataIndex--;
                    }
                }
                
                Arrays.sort(data);
                
                /*divide, para obter a media*/
                destination[cur] = data[d/2];

            }
        }

	}

}
