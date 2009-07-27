/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import br.ufpb.di.pdi.toolkit.arith.fixedpoint.FixedPointNumber;
import junit.framework.TestCase;

/**
 *
 * @author thiago
 */
@Deprecated
public class FixedPointTest extends TestCase {

    public static final int RANGE = 1000 ;
    public static final int FLOATING = 8;

    public void testSmaller () {

        int a, b;
        float x, y;

        for (int i = -RANGE; i < RANGE; ++i) {
            for (int j = -RANGE; j < RANGE; ++j ) {
                if (i != j) {

                    x = (float) i/.0005f;
                    y = (float) j/.0005f;

                    a = FixedPointNumber.toFixedPoint(FLOATING, x);
                    b = FixedPointNumber.toFixedPoint(FLOATING, y);

                    if (x - y < 0) {
                        assertTrue(FixedPointNumber.subtract(a, b) < 0);
                    } else if (x - y == 0) {
                        assertTrue(FixedPointNumber.subtract(a, b) == 0);
                    } else if (x - y > 0) {
                        assertTrue(FixedPointNumber.subtract(a, b) > 0);
                    }

                }
            }
        }


    }

}
