package com.brona.etendue.visualization;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainterCanvas extends Canvas {

    protected final List<Painter> painters = new ArrayList<>();;

    public boolean addPainter(Painter painter) {
        return painters.add(painter);
    }

    public List<Painter> getPainters() {
        return painters;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        painters.forEach(painter -> painter.paint(g));
    }

}
