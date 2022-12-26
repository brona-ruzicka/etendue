package com.brona.etendue.rev1.intersect.types;


import com.brona.etendue.rev1.geometry.Curve;
import com.brona.etendue.rev1.intersect.Intersection;
import com.brona.etendue.rev1.intersect.Intersector;
import com.brona.etendue.rev1.math.simple.*;
import com.brona.etendue.rev1.ray.RaySection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;


public class LineIntersector implements Intersector {

    @NotNull
    @Override
    public Curve.Type getType() {
        return Curve.Type.LINE;
    }

    @NotNull
    @Override
    public Collection<Intersection> intersect(@NotNull Curve curve, @NotNull RaySection section) {

        float[] arguments = curve.getArguments();

        // Calculate intersection between two lines
        Point2 line1 = Point2.create(arguments[0], arguments[1]);
        Point2 line2 = Point2.create(arguments[2], arguments[3]);
        Tuple3 line = (line1.homogenous().toTuple()).cross(line2.homogenous().toTuple());

        Point2 ray1 = section.getOrigin();
        Point2 ray2 = (section.getOrigin()).plus(section.getDirection());
        Tuple3 ray = (ray1.homogenous().toTuple()).cross(ray2.homogenous().toTuple());

        Point3 intersectionHomogenous = (line).cross(ray);


        // Check for corner case (parallel lines)
        if (intersectionHomogenous.getZ() == 0)
            return Collections.emptyList();


        // Calculate the existing intersection
        Point2 intersection = intersectionHomogenous.euclidean();


        if (    // Check if this intersection lies on the specified line curve
                ((intersection).minus(line1)).dot((intersection).minus(line2)) > 0
                ||
                !Intersector.checkIntersectionLiesOnRaySection(intersection, section)
        )
            return Collections.emptyList();


        // Calculate the normal
        Vector2 normal = (line1).minus(line2).normalize().rotateAnticlockwise90();

        // Return the intersections
        return Collections.singletonList(Intersection.create(intersection, normal));

    }

}
