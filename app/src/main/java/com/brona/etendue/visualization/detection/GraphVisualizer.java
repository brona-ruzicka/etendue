package com.brona.etendue.visualization.detection;

import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@FunctionalInterface
public interface GraphVisualizer {

    @NotNull
    Painter visualize(@NotNull Map<@NotNull Float, float[]> data, @NotNull Transformer transformer);

}
