package com.brona.etendue.visualization;

import java.awt.*;

@FunctionalInterface
public interface Painter {

    Painter NOOP = g -> {};

    void paint(Graphics g);

}
