/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.FilterDialog;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.SaltAndPepperNoise;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author thiago
 */
public class NoiseApplyer {

    private NoiseApplyer () {
    }

    public static void apply(String filterName, MainWindow father) {

        if (filterName.equals("Sal e Pimenta"))
            applySaltPepper(father);

    }

    private static void applySaltPepper(MainWindow where) {

        SpinnerNumberModel model = new SpinnerNumberModel(0.05f, 0.0f, 1.0f, 0.025f);
        JSpinner input = new JSpinner(model);
        input.setBorder(new TitledBorder("Probabilidade: "));

        FilterDialog test = new FilterDialog(where, input);
//        test.setExtraComponents(input);

        int result;
        int mask;

        result = test.showDialog();

        AbstractFilter filter;

        filter = null;
        mask = 0;

        if (result == JOptionPane.OK_OPTION) {
            mask = test.getMask();
            filter = new SaltAndPepperNoise(((Double) input.getValue()).floatValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);

    }

}
