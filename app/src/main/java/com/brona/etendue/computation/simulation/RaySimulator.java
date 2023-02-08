package com.brona.etendue.computation.simulation;

import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RaySimulator {

    @NotNull Collection<@NotNull Ray> simulate(@NotNull Scene scene);

}
