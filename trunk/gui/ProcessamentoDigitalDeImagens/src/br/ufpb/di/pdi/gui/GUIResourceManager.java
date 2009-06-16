/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author thiago
 */
public class GUIResourceManager {

    private Set<MainWindow> allMainWindows = new HashSet<MainWindow>();
    private List<ShutdownListener> shutdown = new ArrayList<ShutdownListener>();

    private static GUIResourceManager singleInstance;

    private static void init () {
        singleInstance = new GUIResourceManager();
    }

    static {
        init();
    }

    private GUIResourceManager () {
    }

    public static GUIResourceManager getInstance () {
        return singleInstance;
    }

    public synchronized void registerWindow (MainWindow newWindow) {
        allMainWindows.add(newWindow);
    }

    public synchronized void unregisterWindow (MainWindow remove) {
        allMainWindows.remove(remove);
        if (allMainWindows.isEmpty())
            doShutdown();
    }

    public void addShutdownListener (ShutdownListener listener) {
        shutdown.add(listener);
    }

    public void removeShutdownListener (ShutdownListener listener) {
        while (shutdown.remove(listener));
    }

    private void doShutdown () {
        try {
            for (ShutdownListener i : shutdown)
                i.shutDownNow();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    public void shutDownNow () {
        doShutdown();
    }

    public Iterator<MainWindow> getWindowIterator () {
        return allMainWindows.iterator();
    }

    public void mayNowShutdown () {
        if (allMainWindows.isEmpty())
            doShutdown();
    }

}
