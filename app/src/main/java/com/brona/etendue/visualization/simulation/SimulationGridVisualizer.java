package com.brona.etendue.visualization.simulation;

import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface SimulationGridVisualizer {

    @NotNull
    Painter visualize(@NotNull Transformer transformer);

}
