package com.brona.etendue.visualization.common;

import com.brona.etendue.visualization.Painter;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public interface ImageRegenerator {

    void regenerate(@NotNull Painter... painters);

    @NotNull
    BufferedImage toBufferedImage();

    @NotNull
    default Painter toPainter() {
        return graphics -> graphics.drawImage(toBufferedImage(), null, null);
    }

}
