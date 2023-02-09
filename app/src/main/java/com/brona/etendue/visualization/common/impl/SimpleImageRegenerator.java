package com.brona.etendue.visualization.common.impl;

import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.common.ImageCreator;
import com.brona.etendue.visualization.common.ImagePainter;
import com.brona.etendue.visualization.common.ImageRegenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@ToString
@EqualsAndHashCode
public class SimpleImageRegenerator implements ImageRegenerator {

    @NotNull
    protected final ImagePainter imagePainter;

    @NotNull
    protected final Object lock = new Object();

    @NotNull
    protected BufferedImage finished, drawing;

    public SimpleImageRegenerator(@NotNull ImageCreator creator, @NotNull ImagePainter imagePainter, @NotNull Vector2 dimension) {
        finished = creator.create(dimension);
        drawing = creator.create(dimension);
        this.imagePainter = imagePainter;
    }

    @Override
    public void regenerate(@NotNull Painter... painters) {
        synchronized (lock) {
            CancelableScheduler.check();
            imagePainter.paint(drawing, painters);

            CancelableScheduler.check();
            BufferedImage storage = finished;
            finished = drawing;
            drawing = storage;
        }
    }

    @NotNull
    @Override
    public BufferedImage toBufferedImage() {
        return finished;
    }
}
