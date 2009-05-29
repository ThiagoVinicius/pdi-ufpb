/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit;

/**
 *
 * @author Thiago
 */
public class ColorComponent {

    public float values[];
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


}
