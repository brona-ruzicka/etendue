package com.brona.etendue.rev2.visualization.common;

import com.brona.etendue.rev2.math.tuple.Vector2;
import com.brona.etendue.rev2.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageCreator {

    @NotNull
    BufferedImage create(Vector2 dimension);

}
