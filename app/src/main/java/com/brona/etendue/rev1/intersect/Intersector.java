package com.brona.etendue.rev1.intersect;

import com.brona.etendue.rev1.geometry.Curve;
import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.math.simple.Point2;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Intersector {

    @NotNull
    Curve.Type getType();

    @NotNull
    Collection<Intersection> intersect(@NotNull Curve curve, @NotNull RaySection section);


    static boolean checkIntersectionLiesOnRaySection(Point2 intersection, RaySection section) {
        if (section.getEnd() == null) {
            return ( (intersection).minus(section.getOrigin()) ).dot( section.getDirection() ) > 0f;
        } else {
            return ( (intersection).minus(section.getOrigin()) ).dot( (intersection).minus(section.getEnd()) ) < 0f;
        }
    }

}
