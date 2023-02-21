package com.brona.etendue.visualization.common.impl;

import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.common.ImagePainter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

@ToString
@EqualsAndHashCode
public class SimpleImagePainter implements ImagePainter {

    @Override
    public void paint(@NotNull BufferedImage image, @NotNull Painter... painters) {

        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0,0, image.getWidth(), image.getHeight());

        graphics.transform(AffineTransform.getTranslateInstance(0, image.getHeight() - 1));
        graphics.transform(AffineTransform.getScaleInstance(1, -1));

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        for (Painter painter : painters) {
            CancelableScheduler.check();
            painter.paint(graphics);
        }

        graphics.dispose();

    }

}
