package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.data.scene.interactor.ReflectingInteractor;
import com.brona.etendue.math.geometry.Geometry;
import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.simulation.InteractorVisualizer;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.visualization.simulation.util.GeometryShape;
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
public class ColoredInteractorVisualizer implements InteractorVisualizer {

    @NotNull
    public static final Color DEFAULT_REFLECTING_INTERACTOR_COLOR = Color.BLUE;
    @NotNull
    public static final Color DEFAULT_GENERIC_INTERACT_COLOR = Color.DARK_GRAY;


    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    @NotNull Color reflectingInteractorColor = DEFAULT_REFLECTING_INTERACTOR_COLOR;
    @NotNull Color genericInteractorColor = DEFAULT_GENERIC_INTERACT_COLOR;
    @NotNull Stroke stroke = DEFAULT_STROKE;


    @NotNull
    @Override
    public Painter visualize(@NotNull Scene scene, @NotNull Transformer transformer) {
        AffineTransform transform = transformer.createSimulationTransform();

        return graphics -> {
            graphics.setStroke(stroke);

            scene.getInteractors().forEach(interactor -> {
                CancelableScheduler.check();

                if (interactor instanceof ReflectingInteractor) {
                    graphics.setColor(reflectingInteractorColor);
                } else {
                    graphics.setColor(genericInteractorColor);
                }

                Geometry geometry = interactor.getGeometry();
                Shape shape = Transformer.transformShape(new GeometryShape(geometry), transform);
                graphics.draw(shape);
            });
        };
    }

}
