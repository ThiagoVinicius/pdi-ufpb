/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.concurrent.ConcurrentFilterApplier;
import br.ufpb.di.pdi.toolkit.concurrent.ConcurrentManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 *
 * @author thiago
 */
public abstract class ConcurrentFilter extends AbstractFilter
    implements AreaFilter {

    private class Slave implements Runnable {

        private final Semaphore notifier;
        private final ColorComponent dest;
        private final ColorComponent src;

        public Slave(Semaphore notifier,
                     ColorComponent dest,
                     ColorComponent src) {
            this.notifier = notifier;
            this.dest = dest;
            this.src = src;
        }



        public void run() {
            applyFilter(dest, src);
            notifier.release();
        }

    }

    @Override
    public void applyFilter(ColorComponent dest, ColorComponent source) {

        (new ConcurrentFilterApplier(this)).applyFilter(dest, source);

    }

    @Override
    public void applyFilter (ImageWrapper dest, ImageWrapper source, int mask) {

        int accquire;
        accquire = 0;
        Semaphore awaitTermination = new Semaphore(0);
        ExecutorService es = ConcurrentManager.getDefaultExecutorService();

        if ((mask & RED) != 0) {
            es.submit(new Slave(awaitTermination, dest.red, source.red));
            ++accquire;
        }

        if ((mask & GREEN) != 0) {
            es.submit(new Slave(awaitTermination, dest.green, source.green));
            ++accquire;
        }

        if ((mask & BLUE) != 0) {
            es.submit(new Slave(awaitTermination, dest.blue, source.blue));
            ++accquire;
        }

        if ((mask & Y) != 0) {
            es.submit(new Slave(awaitTermination, dest.yComponent, source.yComponent));
            ++accquire;
        }

        if ((mask & U) != 0) {
            es.submit(new Slave(awaitTermination, dest.uComponent, source.uComponent));
            ++accquire;
        }

        if ((mask & V) != 0) {
            es.submit(new Slave(awaitTermination, dest.vComponent, source.vComponent));
            ++accquire;
        }

        awaitTermination.acquireUninterruptibly(accquire);

    }

}
