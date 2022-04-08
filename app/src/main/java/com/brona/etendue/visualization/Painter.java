package com.brona.etendue.visualization;

import java.awt.*;

@FunctionalInterface
public interface Painter {

    void paint(Graphics2D g);


    Painter NOOP = g -> {};

}
