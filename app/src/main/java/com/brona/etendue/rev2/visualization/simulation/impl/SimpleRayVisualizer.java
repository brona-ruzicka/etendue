package com.brona.etendue.rev2.visualization.simulation.impl;

import com.brona.etendue.rev2.visualization.Painter;
import com.brona.etendue.rev2.visualization.simulation.RayVisualizer;
import com.brona.etendue.rev2.visualization.Transformer;
import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.visualization.simulation.util.RayShape;
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
