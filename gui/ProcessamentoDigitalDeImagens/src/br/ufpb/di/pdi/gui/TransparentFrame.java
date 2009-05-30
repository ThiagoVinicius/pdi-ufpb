/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import javax.swing.JFrame;

/**
 *
 * @author Thiago
 */
public class TransparentFrame extends JFrame {

    private TransparentFrame () {
        super();
    }

    public static JFrame getInstance (String title, float alpha) {
        JFrame result;
        result = new JFrame(title);

        if (AWTUtilitiesWrapper.isTranslucencySupported(AWTUtilitiesWrapper.PERPIXEL_TRANSLUCENT))
            AWTUtilitiesWrapper.setWindowOpacity(result, alpha);

        return result;
    }

}
