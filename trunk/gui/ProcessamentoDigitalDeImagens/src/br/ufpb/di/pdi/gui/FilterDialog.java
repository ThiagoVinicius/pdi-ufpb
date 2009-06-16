/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author thiago
 */
public class FilterDialog extends JDialog implements ActionListener {

    private FilterPanel panel;

    public int showDialog () {
        setVisible(true);
        panel.waitSelection();
        return panel.canceled ? JOptionPane.CANCEL_OPTION : JOptionPane.YES_OPTION;
    }

    public int getMask () {
        return panel.getMask();
    }

    private void init (Component toAdd[]) {
        panel = new FilterPanel(toAdd);
        this.getContentPane().add(panel);
        panel.jButton1.addActionListener(this);
        panel.jButton2.addActionListener(this);
        this.setResizable(false);
        this.pack();
    }

    public FilterDialog(Dialog owner, String title, Component ... extra) {
        super(owner, title);
        init(extra);
    }

    public FilterDialog(Dialog owner, Component ... extra) {
        super(owner);
        init(extra);
    }

    public FilterDialog(Frame owner, String title, Component ... extra) {
        super(owner, title);
        init(extra);
    }

    public FilterDialog(Frame owner, Component ... extra) {
        super(owner);
        init(extra);
    }

//    public void setExtraComponents (Component ... comp) {
//        getContentPane().remove(panel);
//        panel = new FilterPanel(comp);
//        getContentPane().add(panel);
//        validate();
//        pack();
//    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panel.jButton1) {
            setVisible(false);
        }else if (e.getSource() == panel.jButton2) {
            setVisible(false);
        }
    }

}
