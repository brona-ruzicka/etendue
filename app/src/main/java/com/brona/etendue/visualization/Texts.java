package com.brona.etendue.visualization;

import com.brona.etendue.math.tuple.Point2;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;

public final class Texts {

    public static void drawText(@NotNull Graphics2D graphics,@NotNull Point2 position, @NotNull String text) {
        graphics.transform(AffineTransform.getScaleInstance(1, -1));
        graphics.drawString(text, position.getX(), -position.getY());
        graphics.transform(AffineTransform.getScaleInstance(1, -1));
    }

    public static void drawText(@NotNull Graphics2D graphics, float x, float y, @NotNull String text) {
        graphics.transform(AffineTransform.getScaleInstance(1, -1));
        graphics.drawString(text, x, -y);
        graphics.transform(AffineTransform.getScaleInstance(1, -1));
    }

}
