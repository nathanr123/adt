package com.cti.vpx.view;

/*
 * StatusWindow.java
 *
 * Created on March 14, 2006, 2:35 PM
 *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.AccessControlException;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author cary
 */
public class StatusWindow extends JWindow implements MouseListener {

    JLabel messageLabel;

    /**
     * Creates a new instance of StatusWindow
     */
    public StatusWindow(Component parent, String message) {
        this.setSize(325, 75);
        try {
            this.setAlwaysOnTop(true);
        } catch (AccessControlException ex) {
            System.out.println("cant set always on top");
        }
        this.setLocationRelativeTo(parent);

        messageLabel = new JLabel();
        messageLabel.setBorder(new LineBorder(Color.black, 2));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setText(message);
        messageLabel.setPreferredSize(new Dimension(325, 75));
        this.add(messageLabel);
        addMouseListener(this);
        this.pack();
        this.setVisible(true);
        Point p = new Point();
    }

    public void close() {
        dispose();

    }

    public void mouseClicked(MouseEvent e) {
        // dispose();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JTextArea t = new JTextArea();
        t.setPreferredSize(new Dimension(250, 250));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(t);
        f.pack();
        f.setVisible(true);
        new StatusWindow(f, "Fred is working...");
    }

    public void setMessage(String status) {
        messageLabel.setText(status);
    }
}
