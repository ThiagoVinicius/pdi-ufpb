/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.toolkit.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author thiago
 */
public class ConcurrentManager {

    private static ExecutorService defaultExecutorService;

    private static void init () {
        defaultExecutorService = Executors.newCachedThreadPool();
    }

    static {
        init();
    }

    public static ExecutorService getDefaultExecutorService () {
        return defaultExecutorService;
    }

    public static void setDefaultExecutorService (ExecutorService newOne) {
        defaultExecutorService = newOne;
    }

    public static int suggestedNumberOfExectutionThreads () {
        return 4*Runtime.getRuntime().availableProcessors();
    }

}
