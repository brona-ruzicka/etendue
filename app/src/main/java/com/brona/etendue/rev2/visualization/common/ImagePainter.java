package com.brona.etendue.rev2.visualization.common;

import com.brona.etendue.rev2.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImagePainter {

    void paint(@NotNull BufferedImage image, @NotNull Painter... painter);

}
