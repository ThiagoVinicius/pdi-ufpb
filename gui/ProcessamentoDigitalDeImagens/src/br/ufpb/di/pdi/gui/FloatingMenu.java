/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import java.awt.Dialog;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;

/**
 *
 * @author Thiago
 */
public class FloatingMenu extends JDialog implements Runnable, MouseListener {

    public static final boolean canBeTransparent;
    private static int refreshRate;

    private float alpha;
    private long interval;
    private volatile Thread worker;
    private float function[];
    private boolean mouseIn;

    static {
        canBeTransparent = AWTUtilitiesWrapper.isTranslucencySupported(
                AWTUtilitiesWrapper.PERPIXEL_TRANSLUCENT);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        refreshRate = gs.getDisplayMode().getRefreshRate();
        if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN)
            refreshRate = 1;
    }

    public FloatingMenu (Dialog owner, String title) {
        super(owner, title);
        init();
    }

    public FloatingMenu (Dialog owner) {
        super(owner);
        init();
    }

    public FloatingMenu (Frame owner, String title) {
        super(owner, title);
        init();
    }

    public FloatingMenu (Frame owner) {
        super(owner);
        init();
    }
    
    private void init () {
        worker = null;
        alpha = 1.0f;
        addMouseListener(this);
        mouseIn = false;
    }

    private boolean isMouseOver () {
        return getContentPane().getMousePosition() != null;
    }

    public void setAlpha (float newAlpha) {

        if (!isMouseOver ())
            alpha = newAlpha;
        else
            alpha = 1.0f;

        if (canBeTransparent)
            AWTUtilitiesWrapper.setWindowOpacity(this, alpha);

        if (alpha <= 0.005f)
            setVisible(false);
        else
            setVisible(true);

    }

    public float getAlpha () {
        return alpha;
    }

    public void setAlpha (float from, float to, long miliseconds) {

        float seconds = miliseconds / 1000.0f;

        function = new float[(int)Math.ceil(refreshRate * seconds * 2)];
        interval = (int)(1000.0f / refreshRate);

        float step = (to-from) / (function.length-1);

        for (int i = 0; i < function.length; ++i) {
            function[i] = from + step*i;
        }

        if (worker != null && worker.isAlive()) {
            worker.interrupt();
        }

        worker = new Thread(this);
        worker.start();

    }

    public void joinAlphaSetter () throws InterruptedException {
        if (worker != null && worker.isAlive())
            worker.join();
    }

    @Override
    public void run () {

        for (int index = 0; index < function.length; ++index) {
            if (Thread.currentThread().isInterrupted())
                break;
            setAlpha(function[index]);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                break;
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
        System.out.println("Mouse entrou");
    }

    public void mouseExited(MouseEvent e) {
        mouseIn = false;
        System.out.println("Mouse saiu");
    }

}
