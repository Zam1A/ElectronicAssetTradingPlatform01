package net.wms.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

final class ResponsiveFrameSupport {
    private static final String BASE_BOUNDS = "wms.baseBounds";
    private static final String BASE_FONT = "wms.baseFont";

    private ResponsiveFrameSupport() {
    }

    static JLabel createBackground(final ImageIcon icon) {
        JLabel background = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Image image = icon == null ? null : icon.getImage();
                if (image == null) {
                    super.paintComponent(g);
                    return;
                }
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setOpaque(false);
        return background;
    }

    static void install(final JFrame frame, final int baseWidth, final int baseHeight, final JComponent background) {
        frame.setMinimumSize(new Dimension(baseWidth, baseHeight));

        final Container content = frame.getContentPane();
        content.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                capture(e.getChild());
                scale(frame, baseWidth, baseHeight, background);
            }
        });

        captureDirectChildren(content);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scale(frame, baseWidth, baseHeight, background);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                scale(frame, baseWidth, baseHeight, background);
            }
        });
    }

    private static void captureDirectChildren(Container container) {
        Component[] children = container.getComponents();
        for (int i = 0; i < children.length; i++) {
            capture(children[i]);
        }
    }

    private static void capture(Component component) {
        if (!(component instanceof JComponent)) {
            return;
        }
        JComponent jComponent = (JComponent) component;
        if (jComponent.getClientProperty(BASE_BOUNDS) == null) {
            jComponent.putClientProperty(BASE_BOUNDS, component.getBounds());
        }
        if (jComponent.getClientProperty(BASE_FONT) == null && component.getFont() != null) {
            jComponent.putClientProperty(BASE_FONT, component.getFont());
        }
    }

    private static void scale(JFrame frame, int baseWidth, int baseHeight, JComponent background) {
        Container content = frame.getContentPane();
        double scaleX = Math.max(1.0, content.getWidth() / (double) baseWidth);
        double scaleY = Math.max(1.0, content.getHeight() / (double) baseHeight);
        double fontScale = Math.min(scaleX, scaleY);

        Component[] children = content.getComponents();
        for (int i = 0; i < children.length; i++) {
            scaleComponent(children[i], scaleX, scaleY, fontScale);
        }

        if (background != null) {
            background.setBounds(0, 0, frame.getLayeredPane().getWidth(), frame.getLayeredPane().getHeight());
            background.repaint();
        }
        content.revalidate();
        content.repaint();
    }

    private static void scaleComponent(Component component, double scaleX, double scaleY, double fontScale) {
        if (!(component instanceof JComponent)) {
            return;
        }
        JComponent jComponent = (JComponent) component;
        Rectangle baseBounds = (Rectangle) jComponent.getClientProperty(BASE_BOUNDS);
        if (baseBounds == null) {
            capture(component);
            baseBounds = (Rectangle) jComponent.getClientProperty(BASE_BOUNDS);
        }
        if (baseBounds != null) {
            component.setBounds(
                    scale(baseBounds.x, scaleX),
                    scale(baseBounds.y, scaleY),
                    scale(baseBounds.width, scaleX),
                    scale(baseBounds.height, scaleY));
        }

        Font baseFont = (Font) jComponent.getClientProperty(BASE_FONT);
        if (baseFont != null) {
            float newSize = Math.max(baseFont.getSize2D(), (float) (baseFont.getSize2D() * fontScale));
            component.setFont(baseFont.deriveFont(newSize));
        }
    }

    private static int scale(int value, double scale) {
        return Math.max(1, (int) Math.round(value * scale));
    }
}
