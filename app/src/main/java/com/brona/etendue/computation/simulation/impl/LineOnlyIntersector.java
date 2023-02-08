package com.brona.etendue.computation.simulation.impl;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Tuple3;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.computation.simulation.RayIntersector;
import com.brona.etendue.computation.util.ComputationUtil;
import com.brona.etendue.data.simulation.Intersection;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.curve.Curve;
import com.brona.etendue.math.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineOnlyIntersector implements RayIntersector {

    @Override
    public @NotNull Collection<@NotNull Intersection> intersect(@NotNull Section section, @NotNull Geometry geometry) {
        return geometry.getCurves().stream()
                .map(curve -> this.intersect(section, curve))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Intersection intersect(Section section, Curve curve) {

        if (Objects.isNull(section.getDirection()))
            return null;

        float[] arguments = curve.getArguments();

        Point2 lineA = Point2.create(arguments[0], arguments[1]);
        Point2 lineB = Point2.create(arguments[2], arguments[3]);
        Tuple3 line = ComputationUtil.lineFromPoints(lineA, lineB);

        Tuple3 ray = ComputationUtil.lineInDirection(section.getPoint(), section.getDirection());

        Point2 intersection = ComputationUtil.intersectLines(line, ray);

        if (Objects.isNull(intersection)
                || !ComputationUtil.checkBetween(lineA, lineB, intersection)
                || !ComputationUtil.checkInDirection(section.getPoint(), section.getDirection(), intersection)
        )
            return null;

        Vector2 normal = (lineB).minus(lineA).rotateAnticlockwise90().normalize();
        if ( ComputationUtil.checkSimilarDirection(normal, section.getDirection()) )
            normal = normal.invert();

        return new Intersection(intersection, normal, curve);
    }


}
