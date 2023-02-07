package com.brona.etendue.rev2.visualization;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

@FunctionalInterface
public interface Painter {

    void paint(@NotNull Graphics2D graphics);

}
