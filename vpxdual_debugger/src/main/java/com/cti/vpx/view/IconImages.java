package com.cti.vpx.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class IconImages {

    protected static BufferedImage drawFromShape(Shape s, int width, int height, Paint paint, boolean border) {
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        gr.setPaint(paint);
        gr.fill(s);
        if(border) {
            gr.setColor(Color.BLACK);
            gr.draw(s);
        }
        gr.dispose();
        return bi;
    }


    /**
     * Create an image of a filled circle
     */
    public static BufferedImage round(int size, Paint paint, boolean border) {

        Shape circle = new Ellipse2D.Float(1,1,size-2,size-2);
        return drawFromShape(circle, size, size, paint, border);
    }
    
     public static BufferedImage empty(int width, int height) {
       return new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    }
}
