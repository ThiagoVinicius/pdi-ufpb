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
public class FilterApplyer {

    public static final String [] FILTERS = {
        "Brilho aditivo",
        "Brilho multiplicativo",
        "Média",
        "Mediana",
        "Desvio padrão",
        "Negativo"
    };

    public static void apply(String filterName, MainWindow where) {

        if (filterName.equals("Brilho aditivo"))
            brilhoa(where);
        else if (filterName.equals("Brilho multiplicativo"))
            brilhom(where);
        else if (filterName.equals("Média"))
            media(where);
        else if (filterName.equals("Mediana"))
            mediana(where);
        else if (filterName.equals("Desvio padrão"))
            stddev(where);
        else if (filterName.equals("Negativo"))
            negativo(where);

    }

    private static void brilhoa (MainWindow where) {

        SpinnerNumberModel model = new SpinnerNumberModel(0, -255, 255, 1);
        JSpinner input = new JSpinner(model);
        input.setBorder(new TitledBorder("Soma: "));

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
            filter = new AditiveBrightness((Integer) input.getValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);

    }

    private static void brilhom (MainWindow where) {

        SpinnerNumberModel model = new SpinnerNumberModel(1.0f, -10.0f, 10.0f, 0.05f);
        JSpinner input = new JSpinner(model);
        input.setBorder(new TitledBorder("Multiplicador: "));

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
            filter = new MultiplicativeBrightness(((Double) input.getValue()).floatValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);

    }

    private static void media(MainWindow where) {
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
            filter = new Average((Integer) input.getValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);
    }

    private static void mediana(MainWindow where) {
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
            filter = new Median((Integer) input.getValue());
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);
    }

    private static void stddev(MainWindow where) {
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

    private static void negativo(MainWindow where) {

        FilterDialog test = new FilterDialog(where);
//        test.setExtraComponents(input);

        int result;
        int mask;

        result = test.showDialog();

        AbstractFilter filter;

        filter = null;
        mask = 0;

        if (result == JOptionPane.OK_OPTION) {
            mask = test.getMask();
            filter = new Negative();
        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);
    }

}
