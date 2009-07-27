/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.rotation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author thiago
 */
public class RotationCenter implements Icon {

    public Point myPosition;
    private boolean selected;
    private Color foreground;

    public static int SIZE_ON_MOUSE_HOVER = 17;
    public static int DEFAULT_SIZE = 9;

    public RotationCenter(int x, int y) {
        init(x, y);
    }

    public RotationCenter(Point pos) {
        init(pos.x, pos.y);
    }

    private void init (int x, int y) {
        myPosition = new Point(x, y);

//        setSize(SIZE_ON_MOUSE_HOVER, SIZE_ON_MOUSE_HOVER);
        setForeground(Color.WHITE);
    }

    private void dispatchPositionChange () {
//        setLocation(myPosition);
    }

    public void setPosition (int x, int y) {
        myPosition = new Point(x, y);
        dispatchPositionChange();
    }
    
    public void setPosition (Point newPos) {
        myPosition = new Point(newPos.x, newPos.y);
        dispatchPositionChange();
    }


    private boolean isMouseOver () {
        return selected;
    }

//    @Override
//    public boolean isOpaque () {
//        return true;
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        paintComponent(g);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
////        g.setColor(getForeground());
//        dispatchPositionChange();
//        g.setXORMode(getForeground());
//
//        Color f = getForeground();
//
//        if (isMouseOver()) {
//            g.fillOval(0, 0, SIZE_ON_MOUSE_HOVER, SIZE_ON_MOUSE_HOVER);
//        } else {
//
//            int initial = (SIZE_ON_MOUSE_HOVER-DEFAULT_SIZE) / 2;
//
//            g.fillOval(initial, initial, DEFAULT_SIZE, DEFAULT_SIZE);
//        }
//
//        return;
//    }
//
//
//    @Override
//    public Dimension getSize () {
//        return new Dimension(SIZE_ON_MOUSE_HOVER, SIZE_ON_MOUSE_HOVER);
//    }
//
//    @Override
//    public Dimension getPreferredSize () {
//        return getSize();
//    }
//
//    @Override
//    public Dimension getMaximumSize () {
//        return getSize();
//    }
//
//    @Override
//    public Dimension getMinimumSize () {
//        return getSize();
//    }
//
//    @Override
//    public void setLocation(int x, int y) {
//
//        x -= (SIZE_ON_MOUSE_HOVER-1) / 2;
//        y -= (SIZE_ON_MOUSE_HOVER-1) / 2;
//
//        super.setLocation(x, y);
//    }
//
//    @Override
//    public void setLocation(Point p) {
//        setLocation(p.x, p.y);
//    }


    public void notifySelected () {
        selected = true;
    }

    public void notifyDeselected () {
        selected = false;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        x = myPosition.x;
        y = myPosition.y;

        x -= (SIZE_ON_MOUSE_HOVER-1) / 2;
        y -= (SIZE_ON_MOUSE_HOVER-1) / 2;

        dispatchPositionChange();
        g.setXORMode(getForeground());


        if (isMouseOver()) {
            g.fillOval(x, y, SIZE_ON_MOUSE_HOVER, SIZE_ON_MOUSE_HOVER);
        } else {

            int initial = (SIZE_ON_MOUSE_HOVER-DEFAULT_SIZE) / 2;

            g.fillOval(x+initial, y+initial, DEFAULT_SIZE, DEFAULT_SIZE);
        }

    }

    public int getIconWidth() {
        return SIZE_ON_MOUSE_HOVER;
    }

    public int getIconHeight() {
        return SIZE_ON_MOUSE_HOVER;
    }

    private void setForeground(Color c) {
        foreground = c;
    }

    private Color getForeground () {
        return foreground;
    }

    public Point2D asPoint () {
        return new Point(myPosition.y, myPosition.x);
    }


}
