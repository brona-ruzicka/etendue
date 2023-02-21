package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.simulation.EmitterVisualizer;
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
                    graphics.fill(new Ellipse2D.Float(
                            point.getX() - 1.5f,
                            point.getY() - 1.5f,
                            3f,
                            3f
                    ));
                    return;
                }

                Point2 otherPoint = Transformer.transformPoint(box.getMaxPoint(), transform);
                Vector2 size = otherPoint.minus(point);

                if (width == 0f) {
                    graphics.fill(new Rectangle2D.Float(
                            point.getX() - 1.5f,
                            point.getY(),
                            3f,
                            size.getY()
                    ));
                    return;
                }

                if (height == 0f) {
                    graphics.fill(new Rectangle2D.Float(
                            point.getX(),
                            point.getY() - 1.5f,
                            size.getX(),
                            3f
                    ));
                    return;
                }

                graphics.fill(new Rectangle2D.Float(
                        point.getX(),
                        point.getY(),
                        size.getX(),
                        size.getY()
                ));

            });
        };
    }
}
