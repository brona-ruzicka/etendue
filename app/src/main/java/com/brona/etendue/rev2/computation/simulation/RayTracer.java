package com.brona.etendue.rev2.computation.simulation;

import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.data.scene.Interactor;
import com.brona.etendue.rev2.data.simulation.Section;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RayTracer {

    @NotNull Ray trace(@NotNull Section origin, @NotNull Collection<@NotNull Interactor> interactors);

}
