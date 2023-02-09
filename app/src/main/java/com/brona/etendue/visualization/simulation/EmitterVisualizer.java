package com.brona.etendue.visualization.simulation;

import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

public interface EmitterVisualizer {

    @NotNull
    Painter visualize(@NotNull Scene scene, @NotNull Transformer transformer);

}
