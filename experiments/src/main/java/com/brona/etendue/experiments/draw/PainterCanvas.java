package com.brona.etendue.experiments.draw;

import java.awt.*;
import java.util.ArrayList;

public class PainterCanvas extends Canvas {

    public final ArrayList<Painter> painters = new ArrayList<>();

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
        }

        painters.forEach(painter -> painter.paint(graphics));

        if (graphics instanceof Graphics2D) {
            ((Graphics2D) graphics).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF
            );
        }
    }
}