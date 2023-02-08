package com.brona.etendue.visualization;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

@FunctionalInterface
public interface Painter {

    void paint(@NotNull Graphics2D graphics);

}
