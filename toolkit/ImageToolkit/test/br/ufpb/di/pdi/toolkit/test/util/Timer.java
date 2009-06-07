/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.test.util;

/**
 *
 * @author thiago
 */
public class Timer {

    private long ti, tf;

    public void start () {
        ti = System.currentTimeMillis();
    }

    public void stop () {
        tf = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("%.4f", (tf-ti)/1000.0f) + "s";
    }



}
