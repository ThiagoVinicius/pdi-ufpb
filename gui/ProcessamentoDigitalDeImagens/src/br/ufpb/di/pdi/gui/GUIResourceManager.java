/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 *
 * @author thiago
 */
public class GUIResourceManager {

    private Set<Window> allMainWindows = new HashSet<Window>();
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

    public synchronized void registerWindow (Window newWindow) {
        allMainWindows.add(newWindow);
    }

    public synchronized void unregisterWindow (Window remove) {
        allMainWindows.remove(remove);
        if (allMainWindows.isEmpty())
            doShutdown();
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

    public void mayNowShutdown () {
        if (allMainWindows.isEmpty())
            doShutdown();
    }

}
