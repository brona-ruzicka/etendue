package com.brona.etendue.visualization.detection;

import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EtendueGridVisualizer {

    @NotNull
    Painter visualize(@NotNull Transformer transformer);

}
