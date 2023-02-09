package com.brona.etendue.computation.detection;

import com.brona.etendue.data.simulation.Section;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@FunctionalInterface
public interface DistributionGraphComputer {

    @NotNull
    Map<@NotNull Float, float[]> compute(@NotNull Collection<@NotNull Section> sections);

}
