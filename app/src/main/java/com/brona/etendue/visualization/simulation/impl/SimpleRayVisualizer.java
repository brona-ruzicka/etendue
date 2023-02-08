package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.simulation.RayVisualizer;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.visualization.simulation.util.RayShape;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;

public class SimpleRayVisualizer implements RayVisualizer {

    @NotNull
    public static final Color RAY_COLOR = Color.RED;
    public static final Stroke RAY_STROKE = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);

    @NotNull
    @Override
    public Painter visualize(@NotNull Collection<@NotNull Ray> rays, @NotNull Transformer transformer) {
        return graphics -> {
            graphics.setColor(RAY_COLOR);
            graphics.setStroke(RAY_STROKE);

            rays.stream()
                    .map(RayShape::new)
                    .map(transformer::transformShape)
                    .forEach(graphics::draw);
        };
    }
}
