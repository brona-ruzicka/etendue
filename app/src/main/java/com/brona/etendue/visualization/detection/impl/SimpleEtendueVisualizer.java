package com.brona.etendue.visualization.detection.impl;

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
import java.awt.geom.Line2D;
import java.util.Collection;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleEtendueVisualizer implements EtendueVisualizer {

    @NotNull
    public static final Color DEFAULT_GRAPH_COLOR = Color.RED;
    @NotNull
    public static final Color DEFAULT_SUPPORT_COLOR = Color.BLACK;

    @NotNull
    public static final Stroke DEFAULT_GRAPH_STROKE = new BasicStroke();
    @NotNull
    public static final Stroke DEFAULT_SUPPORT_STROKE = new BasicStroke();


    @NotNull Color graphColor = DEFAULT_GRAPH_COLOR;
    @NotNull Stroke graphStroke = DEFAULT_GRAPH_STROKE;

    @NotNull Color supportColor = DEFAULT_SUPPORT_COLOR;
    @NotNull Stroke supportStroke = DEFAULT_SUPPORT_STROKE;

    @Override
    public @NotNull Painter visualize(@NotNull Collection<Point2> points, @NotNull Transformer transformer) {
        return graphics -> {
            AffineTransform transform = transformer.createEtendueTransform();

            graphics.setColor(graphColor);
            graphics.setStroke(graphStroke);

            points.forEach(point -> {
                CancelableScheduler.check();

                point = Transformer.transformPoint(point, transform);
                graphics.fillRect(Math.round(point.getX()), Math.round(point.getY()), 1, 1);
            });

            graphics.setColor(supportColor);
            graphics.setStroke(supportStroke);

            graphics.draw(Transformer.transformShape(new Line2D.Float(-1.2f, 0, 1.2f, 0), transform));
            graphics.draw(Transformer.transformShape(new Line2D.Float(0, transformer.getMinPoint().getY(), 0, transformer.getMaxPoint().getY()), transform));
        };
    }

}
