package com.brona.etendue.visualization.common;

import com.brona.etendue.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImagePainter {

    void paint(@NotNull BufferedImage image, @NotNull Painter... painter);

}
