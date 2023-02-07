package com.brona.etendue.rev2.visualization.simulation;

import com.brona.etendue.rev2.data.scene.Scene;
import com.brona.etendue.rev2.visualization.Painter;
import com.brona.etendue.rev2.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@FunctionalInterface
public interface SceneVisualizer {

    @NotNull
    Painter visualize(@NotNull Scene scene, @NotNull Transformer transformer);

}
