package com.brona.etendue.rev1.window;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class AppCanvas extends JPanel {

    protected final List<Painter> painters = new ArrayList<>();

    protected AffineTransform transform = null;

    public boolean addPainter(Painter painter) {
        return painters.add(painter);
    }

    public List<Painter> getPainters() {
        return painters;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public AppCanvas setTransform(AffineTransform transform) {
        this.transform = transform;
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            AffineTransform transform = g2d.getTransform();

            g2d.clearRect(0, 0, getWidth(), getHeight());
            if (this.transform != null)
                g2d.transform(this.transform);

            painters.forEach(painter -> painter.paint(g2d));

            g2d.setTransform(transform);
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF
            );
        }
    }

    @FunctionalInterface
    public interface Painter {

        void paint(@NotNull Graphics2D g);


        @NotNull
        AppCanvas.Painter NOOP = g -> {};

    }
}
