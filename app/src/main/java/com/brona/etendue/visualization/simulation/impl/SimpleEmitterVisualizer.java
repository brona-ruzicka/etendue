package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.simulation.EmitterVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleEmitterVisualizer implements EmitterVisualizer {


    @NotNull
    public static final Color DEFAULT_EMITTER_COLOR = Color.RED;

    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke();


    @NotNull Color color = DEFAULT_EMITTER_COLOR;
    @NotNull Stroke stroke = DEFAULT_STROKE;


    @Override
    public @NotNull Painter visualize(@NotNull Scene scene, @NotNull Transformer transformer) {
        return graphics -> {
            AffineTransform transform = transformer.createSimulationTransform();

            graphics.setColor(color);
            graphics.setStroke(stroke);

            scene.getEmitters().forEach(emitter -> {
                BoundingBox box = emitter.getBoundingBox();

                float width = box.getWidth();
                float height = box.getHeight();

                if (width < 0f || height < 0f)
                    return;

                Point2 point = Transformer.transformPoint(box.getMinPoint(), transform);

                if (width == 0f && height == 0f) {
                    graphics.fillOval(Math.round(point.getX() - 1.5f), Math.round(point.getY() - 1.5f), 3, 3);
                    return;
                }

                if (width == 0f) {
                    graphics.fillRect(
                            Math.round(point.getX() - 1.5f),
                            Math.round(point.getY()),
                            Math.round(3f),
                            Math.round(height)
                    );
                    return;
                }

                if (height == 0f) {
                    graphics.fillRect(
                            Math.round(point.getX()),
                            Math.round(point.getY() - 1.5f),
                            Math.round(width),
                            Math.round(3f)
                    );
                    return;
                }

                graphics.fillRect(
                        Math.round(point.getX()),
                        Math.round(point.getY()),
                        Math.round(width),
                        Math.round(height)
                );

            });
        };
    }
}
