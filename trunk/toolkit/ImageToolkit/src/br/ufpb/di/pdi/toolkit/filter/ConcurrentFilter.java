/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;

/**
 *
 * @author thiago
 */
public interface ConcurrentFilter extends Filter {
    public void applyFilter (
            ColorComponent dest,
            ColorComponent source,
            int xi,
            int width,
            int yi,
            int height);
}
