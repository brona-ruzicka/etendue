package com.brona.etendue.visualization.common.impl;

import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.visualization.common.ImageCreator;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class SimpleImageCreator implements ImageCreator {

    @NotNull
    @Override
    public BufferedImage create(Vector2 dimension) {

        return new BufferedImage(
                Math.round(dimension.getX()),
                Math.round(dimension.getY()),
                BufferedImage.TYPE_INT_RGB
        );

    }

}
