package com.brona.etendue.computation.simulation;

import com.brona.etendue.data.simulation.Intersection;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RayIntersector {

    @NotNull Collection<@NotNull Intersection> intersect(@NotNull Section section, @NotNull Geometry geometry);

}
