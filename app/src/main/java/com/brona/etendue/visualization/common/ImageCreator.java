package com.brona.etendue.visualization.common;

import com.brona.etendue.math.tuple.Vector2;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageCreator {

    @NotNull
    BufferedImage create(Vector2 dimension);

}
