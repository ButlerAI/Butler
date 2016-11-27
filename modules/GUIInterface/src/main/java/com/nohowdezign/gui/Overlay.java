package com.nohowdezign.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Overlay {

    public static void main(String[] args) {
        JFrame f = new JFrame("Top-Right Frame");
        f.setUndecorated(true);
        f.setAlwaysOnTop(true);

        FrameDragListener frameDragListener = new FrameDragListener(f);
        f.addMouseListener(frameDragListener);
        f.addMouseMotionListener(frameDragListener);
        f.add(new JPanel() {

            @Override // placeholder for actual content
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }

        });
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        int y = 0;
        f.setLocation(x, y);
        f.setVisible(true);
    }

    public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
            System.out.println(rect.getMaxY() - 100);
            System.out.println(frame.getLocation().y);
            if(!(frame.getLocation().y > (int) rect.getMaxY() - 100) || !(frame.getLocation().x > rect.getMaxX() - 100)) {
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        }
    }
}