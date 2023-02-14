package com.brona.etendue.visualization.detection;

import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface EtendueVisualizer {

    @NotNull
    Painter visualize(@NotNull EtendueResult etendue, @NotNull Transformer transformer);

}
