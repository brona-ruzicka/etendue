package com.brona.etendue.visualization.detection.impl;

import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.detection.EtendueVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class DensityBasedEtendueVisualizer implements EtendueVisualizer {

    public static final int DEFAULT_STEP_COUNT = 200;
    @NotNull
    public static final Color DEFAULT_COLOR = Color.RED;

    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke();


    int stepCount = DEFAULT_STEP_COUNT;

    @NotNull Color color = DEFAULT_COLOR;
    @NotNull Stroke stroke = DEFAULT_STROKE;


    public DensityBasedEtendueVisualizer(int stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public @NotNull Painter visualize(@NotNull EtendueResult etendue, @NotNull Transformer transformer) {
        float stepX = (float) Math.ceil(transformer.getAuxGraphicsSize().getX() / stepCount);
        float stepY = (float) Math.ceil(transformer.getAuxGraphicsSize().getY() / stepCount);

        return graphics -> {
            AffineTransform transform = transformer.createEtendueTransform();

            graphics.setStroke(stroke);

            etendue.getPoints().forEach((point, count) -> {
                CancelableScheduler.check();

                float[] rgb = color.getRGBComponents(null);
                float intensity = 1 - count / etendue.getMax();
                Color lightened = new Color(
                        rgb[0] + (1 - rgb[0]) * intensity,
                        rgb[1] + (1 - rgb[1]) * intensity,
                        rgb[2] + (1 - rgb[2]) * intensity
                );

                Point2 graphicsPoint = Transformer.transformPoint(point, transform);

                graphics.setColor(lightened);
                graphics.fill(new Rectangle2D.Float(
                        graphicsPoint.getX() - stepX/2,
                        graphicsPoint.getY() - stepY/2,
                        stepX,
                        stepY
                ));
            });

        };
    }

}
