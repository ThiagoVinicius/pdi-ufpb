/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import br.ufpb.di.imagemorph.filechooser.FileChooserManager;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Thiago
 */
public class VectorPanel extends JPanel implements ActionListener {

    public ImageLabel image;
    public File file;
    JButton open, clear, borders;

    VectorPanel anotherOne;

    public VectorPanel(Icon myImage, VectorPanel another) {
        if (another != null) {
            this.anotherOne = another;
            another.anotherOne = this;
        }
        initImage(myImage);
        initComponents();
    }

    public VectorPanel (VectorPanel another) {
        this(null, another);
    }

    private void initComponents() {
        JPanel panel1 = new JPanel();
        open = new JButton("Carregar imagem");
        open.addActionListener(this);
        clear = new JButton("Limpar vetores");
        clear.addActionListener(this);
        borders = new JButton("Vetores nas bordas");
        borders.addActionListener(this);

        panel1.add(open);
        panel1.add(clear);
        panel1.add(borders);

        //JPanel panel2 = new JPanel();
        //panel2.add(new JScrollPane(image));

        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(new JScrollPane(image), BorderLayout.CENTER);

    }

    public void setImage (Icon newImage) {
        //System.out.println(getPreferredSize());
        image.setIcon(newImage);
        //System.out.println(getPreferredSize());
        //setSize(image.getWidth(), image.getHeight());
        revalidate();
        //setPreferredSize(getSize());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            FileChooserManager.open(this);
        } else if (e.getSource() == borders) {
            image.addLinesOnBorders();
        } else if (e.getSource() == clear) {
            image.clearLines();
        }

    }

    private void initImage(Icon newImage) {

        ImageLabel tmp = anotherOne == null ? null : anotherOne.image;

        if (image == null)
            image = new ImageLabel(newImage, tmp);
        else
            setImage(newImage);
    }





}
