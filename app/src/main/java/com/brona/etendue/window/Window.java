package com.brona.etendue.window;


import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Window extends JFrame {

    @NotNull
    protected final List<@NotNull Painter> painters = new ArrayList<>();

    public Window(@NotNull String name, @NotNull Vector2 dimension) {
        this(
                name,
                Math.round(dimension.getX()),
                Math.round(dimension.getY())
        );
    }

    public Window(@NotNull String name, int width, int height) {
        super(name);

        this.getContentPane().add(new PainterPanel(width, height), BorderLayout.CENTER);
        this.pack();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void open() {
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
    }


    public void addPainter(com.brona.etendue.visualization.Painter painter) {
        if (painter == null)
            return;

        painters.add(painter);
    }

    public void removePainter(com.brona.etendue.visualization.Painter painter) {
        if (painter == null)
            return;

        painters.remove(painter);
    }

    public com.brona.etendue.visualization.Painter[] getPainters() {
        return painters.toArray(new Painter[0]);
    }


    protected class PainterPanel extends JPanel {

        protected PainterPanel(int width, int height) {
            this.setPreferredSize(new Dimension(width, height));
        }

        @Override
        public void paintComponent(Graphics graphics) {
            if (!(graphics instanceof Graphics2D))
                return;

            Graphics2D graphics2D = (Graphics2D) graphics;

            painters.forEach(painter -> painter.paint(graphics2D));

        }

    }


}
