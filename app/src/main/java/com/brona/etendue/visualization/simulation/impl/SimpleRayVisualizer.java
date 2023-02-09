package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.simulation.RayVisualizer;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.visualization.simulation.util.RayShape;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.Random;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleRayVisualizer implements RayVisualizer {

    public static final int DEFAULT_MAX_COUNT = 100;

    @NotNull
    public static final Color DEFAULT_COLOR = Color.RED;

    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);


    int maxCount = DEFAULT_MAX_COUNT;

    @NotNull Color color = DEFAULT_COLOR;
    @NotNull Stroke stroke = DEFAULT_STROKE;

    @NotNull
    Random random = new Random();


    public SimpleRayVisualizer(int maxCount) {
        this.maxCount = maxCount;
    }




    @NotNull
    @Override
    public Painter visualize(@NotNull Collection<@NotNull Ray> rays, @NotNull Transformer transformer) {
        return graphics -> {
            AffineTransform transform = transformer.createSimulationTransform();

            graphics.setColor(color);
            graphics.setStroke(stroke);

            int rayCount = rays.size();
            int displayingEveryNth = (int) Math.ceil((float) rayCount / maxCount);

            rays.stream()
                    .filter(ray -> random.nextInt(displayingEveryNth) == 0)
                    .forEach(ray -> {
                        CancelableScheduler.check();

                        Shape shape = Transformer.transformShape(new RayShape(ray), transform);
                        graphics.draw(shape);
                    });
        };
    }
}
