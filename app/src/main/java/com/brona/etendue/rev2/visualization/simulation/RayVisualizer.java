package com.brona.etendue.rev2.visualization.simulation;

import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.visualization.Painter;
import com.brona.etendue.rev2.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;

@FunctionalInterface
public interface RayVisualizer {

     @NotNull
     Painter visualize(@NotNull Collection<@NotNull Ray> rays, @NotNull Transformer transformer);

}
