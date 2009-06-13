/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.concurrent.ConcurrentFilterApplier;

/**
 *
 * @author thiago
 */
public abstract class ConcurrentPointyFilter extends ConcurrentFilter
    implements AreaFilter {

    @Override
    public void applyFilter(ImageWrapper target, int mask) {
        applyFilter(target, target, mask);
    }

    @Override
    public void applyFilter(ColorComponent target) {
        applyFilter(target, target);
    }

    @Override
    public void applyFilter(ColorComponent dest, ColorComponent source) {

        (new ConcurrentFilterApplier(this)).applyFilter(dest, source);

    }

}