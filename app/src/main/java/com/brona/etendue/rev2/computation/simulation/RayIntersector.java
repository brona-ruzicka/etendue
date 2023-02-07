package com.brona.etendue.rev2.computation.simulation;

import com.brona.etendue.rev2.data.simulation.Intersection;
import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RayIntersector {

    @NotNull Collection<@NotNull Intersection> intersect(@NotNull Section section, @NotNull Geometry geometry);

}
