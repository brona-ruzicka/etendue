package com.brona.etendue.computation.detection;

import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.simulation.Section;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface EtendueComputer {

    float ETENDUE_SIMULATION_WIDTH = 2.5f;

    @NotNull
    EtendueResult compute(@NotNull Collection<@NotNull Section> sections, float sizeY);

}
