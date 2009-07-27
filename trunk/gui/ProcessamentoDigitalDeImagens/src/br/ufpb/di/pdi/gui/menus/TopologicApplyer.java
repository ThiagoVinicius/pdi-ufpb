/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.FilterDialog;
import br.ufpb.di.pdi.gui.MainWindow;
import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.filter.AbstractFilter;
import br.ufpb.di.pdi.toolkit.filter.AditiveBrightness;
import br.ufpb.di.pdi.toolkit.filter.Average;
import br.ufpb.di.pdi.toolkit.filter.Median;
import br.ufpb.di.pdi.toolkit.filter.MultiplicativeBrightness;
import br.ufpb.di.pdi.toolkit.filter.Negative;
import br.ufpb.di.pdi.toolkit.filter.StandardDeviation;
import br.ufpb.di.pdi.toolkit.topologic.Rotation;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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

        ButtonGroup group1 = new ButtonGroup();

        JRadioButton constant = new JRadioButton("Constante: Θ(d) = A * π");
        group1.add(constant);
        JRadioButton linear = new JRadioButton("Linear: Θ(d) = (d+A) * B * π");
        group1.add(linear);
        JRadioButton quad = new JRadioButton("Quadrática: Θ(d) = (d**A) * B * π");
        group1.add(quad);
        JRadioButton sin = new JRadioButton("Senoidal: Θ(d) = sin(d*A*π) * B * π");
        group1.add(sin);

        constant.setSelected(true);


        ButtonGroup group2 = new ButtonGroup();

        JRadioButton median = new JRadioButton("Mediana");
        group2.add(median);
        JRadioButton nearest = new JRadioButton("Mais próximo");
        group2.add(nearest);

        nearest.setSelected(true);

        SpinnerNumberModel model = new SpinnerNumberModel(1, -10000, 10000, 0.05f);
        JSpinner inputA = new JSpinner(model);
        inputA.setBorder(new TitledBorder("A = "));

        model = new SpinnerNumberModel(1, -10000, 10000, 0.05f);
        JSpinner inputB = new JSpinner(model);
        inputB.setBorder(new TitledBorder("B = "));

        FilterDialog test = new FilterDialog(where,
                                             constant,
                                             linear,
                                             quad,
                                             sin,
                                             inputA,
                                             inputB,
                                             median,
                                             nearest);
//        test.setExtraComponents(input);

        int result;
        int mask;

        result = test.showDialog();

        Rotation filter;

        filter = null;
        mask = 0;

        if (result == JOptionPane.OK_OPTION) {
            mask = test.getMask();
            filter = new Rotation(
                    where.imShow.getPoints(),
                    where.image.width,
                    where.image.height);
            
            if (constant.isSelected())
                filter.setThetaType(Rotation.CONSTANT_THETA);
            else if (linear.isSelected())
                filter.setThetaType(Rotation.LINEAR_THETA);
            else if (quad.isSelected())
                filter.setThetaType(Rotation.QUAD_THETA);
            else if (sin.isSelected())
                filter.setThetaType(Rotation.SEN_THETA);

            filter.setA((Double) (inputA.getValue()));
            filter.setB((Double) (inputB.getValue()));


            if (nearest.isSelected())
                where.image.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_NEAREST);
            else if (median.isSelected())
                where.image.setAlienSelectionMethod(ColorComponent.SELECT_ALIEN_BY_MEDIAN);

        } else {
            return;
        }

        test.dispose();

        where.applyFilter(filter, mask);
    }

}
