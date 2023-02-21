package com.brona.etendue.visualization.detection;

import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GraphVisualizer {

    @NotNull
    Painter visualize(@NotNull GraphResult result, @NotNull Transformer transformer);

}
