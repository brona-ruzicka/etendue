package com.brona.etendue.computation.detection;

import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.tuple.Point2;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@FunctionalInterface
public interface EtendueComputer {

    @NotNull
    EtendueResult compute(@NotNull Collection<@NotNull Section> sections, float simulationHeight);

}
