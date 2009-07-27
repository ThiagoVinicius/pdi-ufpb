/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit;

import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import br.ufpb.di.pdi.toolkit.filter.Median;
import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 *
 * @author Thiago
 */
public class ColorComponent implements Cloneable {

    private float values[];
    public final int width, heigth;

    public float borderMedian = 1.0f;
    private int alienSelectionMethod;

    public static final int SELECT_ALIEN_BY_MEDIAN  = 0;
    public static final int SELECT_ALIEN_BY_NEAREST = 1;

    public ColorComponent (int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        alienSelectionMethod = SELECT_ALIEN_BY_NEAREST;
    }

    public static float byteToFloat (int value) {
        value &= 0x000000ff;
        return value / 255.0f;
    }

    public static int floatToByte (float value) {
        if (value > 1.0f)
            value = 1.0f;
        else if (value < 0.0f)
            value = 0.0f;
        return (int)(value * 255.0f);
    }

    public void setValueArray (float newArray[]) throws WrongImageSizeException {
        if (newArray == null || newArray.length == width*heigth)
            values = newArray;
        else
            throw new WrongImageSizeException();
    }

    public float [] getValueArray () {
        return getValueArray(false);
    }

    public float [] getValueArray (boolean allocateNewIfNecessary) {
        if (allocateNewIfNecessary && values == null)
            values = new float[width*heigth];
        return values;
    }

    public void setAlienSelectionMethod(int alienSelectionMethod) {
        if (alienSelectionMethod == SELECT_ALIEN_BY_MEDIAN ||
            alienSelectionMethod == SELECT_ALIEN_BY_NEAREST)
            this.alienSelectionMethod = alienSelectionMethod;
    }

    public int getAlienSelectionMethod() {
        return alienSelectionMethod;
    }

    public final float get (float x, float y) {

        if (x < 0 || x >= heigth-1 || y < 0 || y >= width-1)
            return getAlien(x, y);

        final int   xi         = (int) Math.floor(x);
        final int   xf         = xi + 1;

        final int   yi         = (int) Math.floor(y);
        final int   yf         = yi + 1;

        final float ydist      = y - yi;
        final float xdist      = x - xi;

        final float upperLeft  = values[xi*width + yi];
        final float upperRight = values[xi*width + yf];
        final float lowerLeft  = values[xf*width + yi];
        final float lowerRight = values[xf*width + yf];


        float uppertarget      = (1 - ydist)*upperLeft  +  ydist*upperRight;
        float lowertarget      = (1 - ydist)*lowerLeft  +  ydist*lowerRight;

        float finaltarget      = (1 - xdist)*uppertarget + xdist*lowertarget;

        return finaltarget;

    }


    private final float getAlien (float x, float y) {

        if (alienSelectionMethod == SELECT_ALIEN_BY_MEDIAN)
            return getAlienByMedian();
        else if (alienSelectionMethod == SELECT_ALIEN_BY_NEAREST)
            return getAlienByNearest(x, y);
        else
            return 0.0f;
    }

    public final float getAlienByNearest (float x, float y) {

        int ax = 0;
        int ay = 0;

        if (x < 0)
            ax = 0;
        else if (x > heigth-1)
            ax = heigth - 1;
        else
            ax = (int) x;

        if (y < 0)
            ay = 0;
        else if (y > width-1)
            ay = width - 1;
        else
            ay = (int) y;

        return values[ax*width + ay];

    }

    public final float getAlienByMedian () {
        return borderMedian;
    }

    public void calculateBorderMedian () {

        if (values == null)
            return;

        final float border[] = new float[2*width + 2*heigth];
        int bi = 0;

        for (int i = 0; i < width; ++i) {
            border[bi] = values[i];
            ++bi;
        }

        for (int i = 0; i < heigth; ++i) {
            border[bi] = values[i*width + width-1];
            ++bi;
        }

        for (int i = width-1; i >= 0; --i) {
            border[bi] = values[(heigth-1)*width + i];
            ++bi;
        }

        for (int i = heigth-1; i >= 0; --i) {
            border[bi] = values[i*width];
            ++bi;
        }

        borderMedian = Median.median(border, border.length);

    }

    @Override
    public ColorComponent clone () {
        ColorComponent result;
        result = new ColorComponent(width, heigth);
        if (values != null)
            result.values = Arrays.copyOf(values, values.length);
        else
            result.values = null;

        result.alienSelectionMethod = alienSelectionMethod;
        result.borderMedian = borderMedian;

        return result;
    }

    public void imitate (ColorComponent anotherGuy) throws WrongImageSizeException {

        if (width != anotherGuy.width || heigth != anotherGuy.heigth)
            throw new InvalidParameterException("Tamanhos nao batem");

        if (anotherGuy.values != null)
            setValueArray(anotherGuy.values.clone());
        else
            this.values = null;

        this.alienSelectionMethod = anotherGuy.alienSelectionMethod;
        this.borderMedian         = anotherGuy.borderMedian;

    }


}
