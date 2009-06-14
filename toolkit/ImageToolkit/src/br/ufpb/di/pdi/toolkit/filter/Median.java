package br.ufpb.di.pdi.toolkit.filter;

import java.util.Arrays;

import br.ufpb.di.pdi.toolkit.ColorComponent;

public class Median extends ConcurrentFilter {

	public final int radius;

    public Median(int radius) {
        this.radius = radius;
    }

    private final float median (float arr[], final int n) {

        //final int n = arr.length;
        float tmp;

        int low, high;
        int median;
        int middle, ll, hh;

        low = 0;
        high = n - 1;
        median = (low + high) / 2;
        for (;;) {
            if (high <= low) /* One element only */ {
                return arr[median];
            }

            if (high == low + 1) {  /* Two elements only */
                if (arr[low] > arr[high]) {
                    tmp = arr[high];
                    arr[high] = arr[low];
                    arr[low]  = tmp;
                }
                return arr[median];
            }

            /* Find median of low, middle and high items; swap into position low */
            middle = (low + high) / 2;
            if (arr[middle] > arr[high]) {
                tmp = arr[high];
                arr[high] = arr[middle];
                arr[middle]  = tmp;
            }
            if (arr[low] > arr[high]) {
                tmp = arr[high];
                arr[high] = arr[low];
                arr[low]  = tmp;
            }
            if (arr[middle] > arr[low]) {
                tmp = arr[middle];
                arr[middle] = arr[low];
                arr[low]  = tmp;
            }

            /* Swap low item (now in position middle) into position (low+1) */
            tmp = arr[middle];
            arr[middle] = arr[low+1];
            arr[low+1]  = tmp;

            /* Nibble from each end towards middle, swapping items when stuck */
            ll = low + 1;
            hh = high;
            for (;;) {
                do {
                    ll++;
                } while (arr[low] > arr[ll]);
                do {
                    hh--;
                } while (arr[hh] > arr[low]);

                if (hh < ll) {
                    break;
                }

                tmp = arr[ll];
                arr[ll] = arr[hh];
                arr[hh]  = tmp;
            }

            /* Swap middle item (in position low) back into correct position */
            tmp = arr[hh];
            arr[hh] = arr[low];
            arr[low]  = tmp;

            /* Re-set active partition */
            if (hh <= median) {
                low = ll;
            }
            if (hh >= median) {
                high = hh - 1;
            }
        }
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

               // Arrays.fill(data, 0.0f);
                dataIndex = 0;
                maxK = dest.heigth-i-1 < radius ? dest.heigth-1 : i+radius;
                maxL = dest.width-j-1  < radius ? dest.width-1  : j+radius;
                /*Soma valores*/
                for (int k = i < radius ? 0: i-radius; k <= maxK; ++k) {
                    rowAux = k*dest.width;
                    for (int l = j < radius ? 0 : j-radius; l <= maxL; ++l) {
                        data[dataIndex] = from[rowAux+l];
                        ++dataIndex;
                    }
                }

                if (dataIndex != data.length)
                    Arrays.fill(data, dataIndex, data.length, 0.0f);
                
                /*mediana.*/
                destination[cur] = median(data, data.length);

            }
        }

	}

}
