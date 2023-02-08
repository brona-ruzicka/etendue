package com.brona.etendue.computation.simulation.impl;

import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.data.simulation.Intersection;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.computation.simulation.RayIntersector;
import com.brona.etendue.computation.simulation.RayTracer;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@AllArgsConstructor
public class SimpleTracer implements RayTracer {

    public static final int MAX_INTERACTIONS = 32;

    @NotNull
    protected final RayIntersector intersector;

    @NotNull
    @Override
    public Ray trace(@NotNull Section rayPart, @NotNull Collection<@NotNull Interactor> interactors) {

        Ray ray = new Ray();
        ray.addSection(rayPart);

        for (int i = 0; Objects.nonNull(rayPart.getDirection()); i++) {

            final Section finalRayPart = rayPart;

            TracerTuple tuple = interactors.stream()
                    .flatMap(interactor -> intersector
                            .intersect(finalRayPart, interactor.getGeometry())
                            .stream()
                            .map(intersection -> new TracerTuple(
                                    interactor,
                                    intersection,
                                    ( intersection.getPoint() ).minus( finalRayPart.getPoint() ).squaredLength()
                            ))
                    )
                    .filter(a -> a.getSquaredDistance() > 0.01)
                    .min(Comparator.comparing(TracerTuple::getSquaredDistance, Float::compare))
                    .orElse(null);

            if (Objects.isNull(tuple))
                break;

            Vector2 direction = null;
            if (i < MAX_INTERACTIONS)
                direction = tuple.getInteractor().getNextDirection(rayPart, tuple.getIntersection());

            rayPart = new Section(tuple.getIntersection().getPoint(), direction);
            ray.addSection(rayPart);
        }


        return ray;
    }

    @Value
    private static class TracerTuple {
        @NotNull
        Interactor interactor;
        @NotNull
        Intersection intersection;
        float squaredDistance;

    }

}
