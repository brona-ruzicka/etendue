package com.brona.etendue.visualization.detection.impl;

import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.detection.GraphVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleGraphVisualizer implements GraphVisualizer {

    @NotNull
    public static final Color[] DEFAULT_GRAPH_COLORS = new Color[]{ Color.RED, Color.MAGENTA };

    @NotNull
    public static final Color DEFAULT_SUPPORT_COLOR = Color.BLACK;

    @NotNull
    public static final Stroke DEFAULT_GRAPH_STROKE = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    @NotNull
    public static final Stroke DEFAULT_SUPPORT_STROKE = new BasicStroke();



    @NotNull Color[] graphColors = DEFAULT_GRAPH_COLORS;

    @NotNull Stroke graphStroke = DEFAULT_GRAPH_STROKE;

    @NotNull Color supportColor = DEFAULT_SUPPORT_COLOR;

    @NotNull Stroke supportStroke = DEFAULT_SUPPORT_STROKE;

    @NotNull
    @Override
    public Painter visualize(@NotNull Map<@NotNull Float, float[]> data, @NotNull Transformer transformer) {
        return graphics -> {
            Function<float[], Float> getMaximalFloatValue = floats -> {
                if (floats.length == 0)
                    throw new IndexOutOfBoundsException();

                float max = Float.MIN_VALUE;
                for (float f: floats)
                    if (f > max)
                        max = f;

                return max;
            };



            float maxValue = Math.max(
                    1f,
                    data.values().stream()
                            .map(getMaximalFloatValue)
                            .max(Float::compare)
                            .orElse(0f)
            );

            AffineTransform transform = transformer.createGraphTransform(maxValue);

            CancelableScheduler.check();

            boolean[] begin = new boolean[graphColors.length];
            Arrays.fill(begin, true);

            Path2D.Float[] paths = new Path2D.Float[graphColors.length];
            for (int i = 0; i < graphColors.length; i++)
                paths[i] = new Path2D.Float();

            data.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Float::compare))
                    .forEach(entry -> {
                        CancelableScheduler.check();

                        float x = entry.getKey();
                        float[] values = entry.getValue();

                        for (int i = 0; i < graphColors.length; i++) {

                            if (values.length <= i) {
                                begin[i] = true;
                                return;
                            }

                            if (begin[i]) {
                                paths[i].moveTo(x, values[i]);
                                begin[i] = false;
                            } else {
                                paths[i].lineTo(x, values[i]);
                            }
                        }
                    });

            for (int i = 0; i < graphColors.length; i++) {
                CancelableScheduler.check();

                graphics.setColor(graphColors[i]);
                graphics.setStroke(graphStroke);
                graphics.draw(Transformer.transformShape(paths[i], transform));
            }

            graphics.setColor(supportColor);
            graphics.setStroke(supportStroke);
            graphics.draw(Transformer.transformShape(new Line2D.Float(-4, 0, 4, 0), transform));
            graphics.draw(Transformer.transformShape(new Line2D.Float(0, -0.1f * maxValue, 0, 1.1f * maxValue), transform));

        };
    }
}
