package com.brona.etendue.computation.detection;

import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.data.simulation.Section;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface DistributionGraphComputer {

    float GRAPH_SIMULATION_WIDTH = (float) Math.PI * 1.2f;

    @NotNull
    GraphResult compute(@NotNull Collection<@NotNull Section> sections);

}
