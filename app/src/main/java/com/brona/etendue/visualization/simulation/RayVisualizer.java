package com.brona.etendue.visualization.simulation;

import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RayVisualizer {

     @NotNull
     Painter visualize(@NotNull Collection<@NotNull Ray> rays, @NotNull Transformer transformer);

}
