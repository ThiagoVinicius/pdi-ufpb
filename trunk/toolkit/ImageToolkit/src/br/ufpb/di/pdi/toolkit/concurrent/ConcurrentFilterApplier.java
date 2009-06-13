/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.concurrent;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import br.ufpb.di.pdi.toolkit.concurrent.ConcurrentManager;
import br.ufpb.di.pdi.toolkit.filter.AreaFilter;
import br.ufpb.di.pdi.toolkit.filter.ConcurrentFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 *
 * @author thiago
 */
public class ConcurrentFilterApplier {

    private final ConcurrentFilter filter;

    public ConcurrentFilterApplier (ConcurrentFilter filter) {
        this.filter = filter;
        if (filter == null)
            throw new NullPointerException();
    }

    private class Slave implements Runnable {

        public final int xi, width, yi, height;
        public final ColorComponent source, dest;
        public final Semaphore semaphore;

        public Slave(
                Semaphore semaphore,
                ColorComponent dest,
                ColorComponent source,
                int xi,
                int width,
                int yi,
                int height) {
            this.semaphore = semaphore;
            this.xi = xi;
            this.width = width;
            this.yi = yi;
            this.height = height;
            this.source = source;
            this.dest = dest;
        }

        public void run() {
            filter.applyFilter(dest, source, xi, width, yi, height);
            semaphore.release();
        }

    }

    public void applyFilter(ColorComponent dest, ColorComponent source) {
        ExecutorService es = ConcurrentManager.getDefaultExecutorService();
        Semaphore awaitTermination;
        int threads;
        int step, stepRemainder;

        threads = ConcurrentManager.suggestedNumberOfExectutionThreads();

        step = source.heigth/threads;
        stepRemainder = source.heigth%threads;
        awaitTermination = new Semaphore(0);

        for (int i = 0; i < threads-1; ++i) {
            es.submit(
                    new Slave(
                    awaitTermination,
                    dest,
                    source,
                    0,
                    source.width,
                    step*i,
                    step));
        }
        es.submit(
                new Slave(
                awaitTermination,
                dest,
                source,
                0,
                source.width,
                step*(threads-1),
                step + stepRemainder));

        awaitTermination.acquireUninterruptibly(threads);

    }

}
