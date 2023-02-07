package com.brona.etendue.rev2.computation.simulation;

import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.data.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RaySimulator {

    @NotNull Collection<@NotNull Ray> simulate(@NotNull Scene scene);

}
