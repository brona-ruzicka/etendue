package com.brona.etendue.rev0.visualization;

import java.awt.*;

@FunctionalInterface
public interface Painter {

    void paint(Graphics2D g);


    Painter NOOP = g -> {};

}
