/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.rotation;

import br.ufpb.di.pdi.gui.ImageViewer;
import br.ufpb.di.pdi.toolkit.ImageWrapper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author thiago
 */
public class ImagePanel extends JLabel
        implements MouseListener, MouseMotionListener {

    //private JLabel imageShow;
    private ImageViewer icon;
    private List<RotationCenter> points;

    private Point lastKnownMousePosition;
    private volatile RotationCenter theChosenOne;


    public ImagePanel (ImageViewer icon) {
        init(icon);
    }

    public void clearCenters() {
        points.clear();
        repaint();
    }

    public Point2D[] getPoints() {
        Point2D result[] = new Point2D[points.size()];
        for (int i = 0; i < points.size(); ++i) {
            result[i] = points.get(i).asPoint();
        }
        return result;
    }

    private void init (ImageViewer image) {

        icon = image;
        points = new ArrayList<RotationCenter> ();

        setLayout(null);
        //add(imageShow);
        setSize(icon.getIconWidth(), icon.getIconHeight());
        //imageShow.setLocation(0, 0);
        //imageShow.setSize(icon.getIconWidth(), icon.getIconHeight());

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();

            RotationCenter newCenter = new RotationCenter(x, y);
            pushCenter(newCenter);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            apagar();
        }

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        lastKnownMousePosition.x = lastKnownMousePosition.y = Integer.MIN_VALUE;
    }

    private void pushCenter(RotationCenter newCenter) {
        if (pointBelongsToImage(newCenter.myPosition)) {
            points.add(newCenter);
//            add(newCenter);
//            newCenter.setPosition(newCenter.myPosition);
            //System.out.println("Pushing center: "+newCenter.myPosition);
            repaint();
        }
    }


    @Override
    public Dimension getSize () {
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }

    @Override
    public Dimension getPreferredSize () {
        return getSize();
    }

    @Override
    public Dimension getMaximumSize () {
        return getSize();
    }

    @Override
    public Dimension getMinimumSize () {
        return getSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        imageShow.paint(g);
        
        icon.paintIcon(this, g, 0, 0);

        verifySelectedPoint();

        for (Icon i : points) {
            i.paintIcon(this, g, 0, 0);
        }
    }


    private final boolean pointBelongsToImage (Point p) {
        //atencao!! O x, y do swing e diferente do x, y da ImageWrapper
        return p.x >=0 &&
               p.x < icon.getIconWidth() &&
               p.y >=0 &&
               p.y < icon.getIconHeight();
    }

    private void verifySelectedPoint () {
        RotationCenter theNewChosenOne = null;
        for (RotationCenter i : points) {

            i.notifyDeselected();

            if (theNewChosenOne == null) {
                if (i.myPosition.distance(lastKnownMousePosition) < RotationCenter.DEFAULT_SIZE) {
                    theNewChosenOne = i;
                }
            }



            else if (
                i.myPosition.distance(lastKnownMousePosition) < theNewChosenOne.myPosition.distance(lastKnownMousePosition)) {

                theNewChosenOne = i;
            }
        }

        if (theNewChosenOne != null)
            theNewChosenOne.notifySelected();

        this.theChosenOne = theNewChosenOne;

    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        lastKnownMousePosition = e.getPoint();
        repaint();
    }

    private void apagar () {
        if (theChosenOne != null) {
            points.remove(theChosenOne);
            repaint();
        }
    }




}
