package com.brona.etendue.rev2.visualization.simulation.impl;

import com.brona.etendue.rev2.visualization.Painter;
import com.brona.etendue.rev2.visualization.simulation.SceneVisualizer;
import com.brona.etendue.rev2.visualization.Transformer;
import com.brona.etendue.rev2.data.scene.Scene;
import com.brona.etendue.rev2.data.scene.interactor.ReflectingInteractor;
import com.brona.etendue.rev2.math.geometry.Geometry;
import com.brona.etendue.rev2.visualization.simulation.util.GeometryShape;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ColoredSceneVisualizer implements SceneVisualizer {

    @NotNull
    public static final Color REFLECTOR_COLOR = Color.BLUE;
    @NotNull
    public static final Color DEFAULT_COLOR = Color.BLACK;


    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    @NotNull
    @Override
    public Painter visualize(@NotNull Scene scene, @NotNull Transformer transformer) {
        return graphics -> {

            graphics.setStroke(DEFAULT_STROKE);

            scene.getInteractors().forEach(interactor -> {

                if (interactor instanceof ReflectingInteractor) {
                    graphics.setColor(REFLECTOR_COLOR);
                } else {
                    graphics.setColor(DEFAULT_COLOR);
                }

                Geometry geometry = interactor.getGeometry();
                Shape shape = transformer.transformShape(new GeometryShape(geometry));
                graphics.draw(shape);
            });
        };
    }

}
