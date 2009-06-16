/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit;

import br.ufpb.di.pdi.toolkit.exception.WrongImageSizeException;
import java.util.Arrays;

/**
 *
 * @author Thiago
 */
public class ColorComponent implements Cloneable {

    private float values[];
    public final int width, heigth;

    public ColorComponent (int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
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

    @Override
    public ColorComponent clone () {
        ColorComponent result;
        result = new ColorComponent(width, heigth);
        if (values != null)
            result.values = Arrays.copyOf(values, values.length);
        else
            result.values = null;
        return result;
    }


}
