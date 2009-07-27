/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test;

import java.awt.geom.Line2D;
import junit.framework.TestCase;

/**
 *
 * @author Thiago
 */
public class Line2DTest extends TestCase {

    public void testDistance () {

        Line2D test = new Line2D.Float(0, 0, 0, 1);

        System.out.println(test.ptLineDist(2, 50));
        System.out.println(test.ptLineDistSq(2, 50));

    }

}
