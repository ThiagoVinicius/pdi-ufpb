/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.filter;

import br.ufpb.di.pdi.toolkit.ColorComponent;
import br.ufpb.di.pdi.toolkit.concurrent.ConcurrentManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiago
 */
public abstract class ConcurrentPointyFilter extends PointyFilter
        implements ConcurrentFilter {

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
            applyFilter(dest, source, xi, width, yi, height);
            semaphore.release();
        }

    }

    @Override
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
            es.submit(new Slave(
                    awaitTermination,
                    dest,
                    source,
                    0,
                    source.width,
                    step*i,
                    step));
        }
        es.submit(new Slave(
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
