/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.FilterDialog;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.Average;
import br.ufpb.di.pdi.toolkit.filter.Median;
import br.ufpb.di.pdi.toolkit.filter.MultiplicativeBrightness;
import br.ufpb.di.pdi.toolkit.filter.Negative;
import br.ufpb.di.pdi.toolkit.filter.StandardDeviation;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author thiago
 */
public class TopologicApplyer {

    public static final String [] FILTERS = {
        "Rotação",
    };

    public static void apply(String filterName, MainWindow where) {

        if (filterName.equals("Rotação"))
            rotation(where);

    }

    private static void rotation (MainWindow where) {
        SpinnerNumberModel model = new SpinnerNumberModel(3, 1, 100, 1);
        JSpinner input = new JSpinner(model);
        input.setBorder(new TitledBorder("Raio: "));

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
            filter = new StandardDeviation((Integer) input.getValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);
    }

}
