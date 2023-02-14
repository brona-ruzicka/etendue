package com.brona.etendue.visualization.detection.impl;

import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.detection.GraphVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Map;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleGraphVisualizer implements GraphVisualizer {

    @NotNull
    public static final Color[] DEFAULT_COLORS = new Color[]{ Color.RED, Color.MAGENTA };

    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    @NotNull Color[] colors = DEFAULT_COLORS;

    @NotNull Stroke stroke = DEFAULT_STROKE;

    @NotNull
    @Override
    public Painter visualize(@NotNull GraphResult result, @NotNull Transformer transformer) {
        return graphics -> {
            AffineTransform transform = transformer.createGraphTransform(result.getMaxValue());

            CancelableScheduler.check();

            boolean[] begin = new boolean[colors.length];
            Arrays.fill(begin, true);

            Path2D.Float[] paths = new Path2D.Float[colors.length];
            for (int i = 0; i < colors.length; i++)
                paths[i] = new Path2D.Float();

            result.getData().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Float::compare))
                    .forEach(entry -> {
                        CancelableScheduler.check();

                        float x = entry.getKey();
                        float[] values = entry.getValue();

                        for (int i = 0; i < colors.length; i++) {

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

            for (int i = 0; i < colors.length; i++) {
                CancelableScheduler.check();

                graphics.setColor(colors[i]);
                graphics.setStroke(stroke);
                graphics.draw(Transformer.transformShape(paths[i], transform));
            }

        };
    }
}
