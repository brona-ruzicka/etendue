package com.brona.etendue.rev1.detect;

import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.intersect.Intersector;
import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Point3;
import com.brona.etendue.rev1.math.simple.Tuple3;
import com.brona.etendue.rev1.math.simple.Vector2;
import com.brona.etendue.rev1.scene.DetectingMember;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class EtendueDetector implements DetectingMember {

    // Geometric properties
    protected final Point2 origin;

    protected final Vector2 tangent;
    protected final Vector2 normal;
    protected final Tuple3 line;

    //TODO
    public final List<Point2> graphPoints = new LinkedList<>();


    protected EtendueDetector(Point2 origin, Vector2 tangent, Vector2 normal) {
        this.origin = origin;
        this.tangent = tangent;
        this.normal = normal;

        this.line = (
                origin.homogenous().toTuple()
        ).cross(
                ((origin).plus(tangent)).homogenous().toTuple()
        );
    }


    @NotNull
    public static EtendueDetector createWithTangent(Point2 origin, Vector2 tangent) {
        tangent = tangent.normalize();
        return new EtendueDetector(origin, tangent, tangent.rotateClockwise90());
    }

    @NotNull
    public static EtendueDetector createWithNormal(Point2 origin, Vector2 normal) {
        normal = normal.normalize();
        return new EtendueDetector(origin, normal.rotateAnticlockwise90(), normal);
    }

    @Override
    public void detect(RaySection section) {
        Point2 ray1 = section.getOrigin();
        Point2 ray2 = (section.getOrigin()).plus(section.getDirection());
        Tuple3 ray = (ray1.homogenous().toTuple()).cross(ray2.homogenous().toTuple());

        Point3 intersectionHomogenous = (line).cross(ray);

        // Check for corner case (parallel lines)
        if (intersectionHomogenous.getZ() == 0)
            return;

        // Calculate the existing intersection
         Point2 intersection = intersectionHomogenous.euclidean();

        if (!Intersector.checkIntersectionLiesOnRaySection(intersection, section))
            return;

        float x = intersection.minus(origin).dot(tangent);
        float sin_a = section.getDirection().dot(tangent);

        //TODO
        graphPoints.add(Point2.create(sin_a, x));
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.create(
                origin.plus(tangent.times(+1_000)),
                origin.plus(tangent.times(-1_000))
        );
    }
}
