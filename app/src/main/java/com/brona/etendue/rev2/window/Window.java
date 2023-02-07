package com.brona.etendue.rev2.window;


import com.brona.etendue.rev2.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Window extends JFrame {

    @NotNull
    protected final java.util.List<@NotNull Painter> painters = new ArrayList<>();

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


    public void addPainter(Painter painter) {
        if (painter == null)
            return;

        painters.add(painter);
    }

    public void removePainter(Painter painter) {
        if (painter == null)
            return;

        painters.remove(painter);
    }

    public Painter[] getPainters() {
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

            graphics2D.setBackground(Color.WHITE);
            graphics2D.clearRect(0, 0, this.getWidth(), this.getHeight());

            graphics2D.transform(AffineTransform.getTranslateInstance(0, this.getHeight() - 1));
            graphics2D.transform(AffineTransform.getScaleInstance(1, -1));

            painters.forEach(painter -> painter.paint(graphics2D));

        }

    }


}
