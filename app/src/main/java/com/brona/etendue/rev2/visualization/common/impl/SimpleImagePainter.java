package com.brona.etendue.rev2.visualization.common.impl;

import com.brona.etendue.rev2.visualization.Painter;
import com.brona.etendue.rev2.visualization.common.ImagePainter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SimpleImagePainter implements ImagePainter {


    @Override
    public void paint(@NotNull BufferedImage image, @NotNull Painter... painters) {

        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0,0, image.getWidth(), image.getHeight());

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Arrays.stream(painters).forEach(painter -> painter.paint(graphics));

        graphics.dispose();

    }

}
