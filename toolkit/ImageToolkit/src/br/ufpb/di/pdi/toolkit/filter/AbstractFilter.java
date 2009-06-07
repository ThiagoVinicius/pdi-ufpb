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
public abstract class AbstractFilter implements Filter {

    public static final int RED   = 0x01;
    public static final int GREEN = 0x02;
    public static final int BLUE  = 0x04;
    public static final int Y     = 0x08;
    public static final int U     = 0x10;
    public static final int V     = 0x20;

    public static final int RGB   = RED | GREEN | BLUE;
    public static final int YUV   = Y   | U     | V;

    public void applyFilter (ImageWrapper target, int mask) {
        applyFilter(target, target.clone(), mask);
    }

    public void applyFilter (ImageWrapper dest, ImageWrapper source, int mask) {

        if ((mask & RED) != 0) {
            applyFilter(dest.red, source.red);
        }

        if ((mask & GREEN) != 0) {
            applyFilter(dest.green, source.green);
        }

        if ((mask & BLUE) != 0) {
            applyFilter(dest.blue, source.blue);
        }

        if ((mask & Y) != 0) {
            applyFilter(dest.yComponent, source.yComponent);
        }

        if ((mask & U) != 0) {
            applyFilter(dest.uComponent, source.uComponent);
        }

        if ((mask & V) != 0) {
            applyFilter(dest.vComponent, source.vComponent);
        }

    }

    public void applyFilter (ColorComponent target) {
        applyFilter(target, target.clone());
    }

    public abstract void applyFilter (ColorComponent dest, ColorComponent source);

}
