package com.brona.etendue.visualization.detection;

import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GraphGridVisualizer {

    @NotNull
    Painter visualize(float maxValue, @NotNull Transformer transformer);

}
