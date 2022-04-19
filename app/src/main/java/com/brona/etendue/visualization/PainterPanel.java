package com.brona.etendue.visualization;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainterPanel extends JPanel {

    protected final List<Painter> painters = new ArrayList<>();

    public boolean addPainter(Painter painter) {
        return painters.add(painter);
    }

    public List<Painter> getPainters() {
        return painters;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            painters.forEach(painter -> painter.paint(g2d));

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF
            );
        }
    }

}
