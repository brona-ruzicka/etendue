package com.brona.etendue.user;

import com.brona.etendue.math.geometry.Geometry;
import com.brona.etendue.math.geometry.Path;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.curve.Curve;
import com.brona.etendue.math.tuple.Vector2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.UnaryOperator;

public final class Geometries {

    private Geometries() { }

    @NotNull
    public static Path path() {
        return new Path();
    }


    @NotNull
    public static Geometry line(float x1, float y1, float x2, float y2) {
        return new Geometry.Simple(
                BoundingBox.create(x1, y1, x2, y2),
                List.of(Curve.line(x1, y1, x2, y2))
        );
    }

    @NotNull
    public static Geometry line(@NotNull Point2 p1, @NotNull Point2 p2) {
        return Geometries.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @NotNull
    public static Geometry rectangle(float x1, float y1, float x2, float y2) {

        BoundingBox box = BoundingBox.create(x1, y1, x2, y2);
        Collection<Curve> curves = List.of(
                Curve.line(box.getMinX(), box.getMinY(), box.getMaxX(), box.getMinY()),
                Curve.line(box.getMaxX(), box.getMinY(), box.getMaxX(), box.getMaxY()),
                Curve.line(box.getMaxX(), box.getMaxY(), box.getMinX(), box.getMaxY()),
                Curve.line(box.getMinX(), box.getMaxY(), box.getMinX(), box.getMinY())
        );

        return new Geometry.Simple(box, curves);
    }

    @NotNull
    public static Geometry rectangle(@NotNull Point2 p1, @NotNull Point2 p2) {
        return Geometries.rectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @NotNull
    public static Geometry polyline(float... coords) {
        if (coords.length % 2 != 0 || coords.length < 4)
            throw new IllegalArgumentException("Wrong number of polyline coords: " + coords.length);

        Path path = Geometries.path();
        path.move(coords[0], coords[1]);

        for (int i = 2; i < coords.length; i += 2)
            path.line(coords[i], coords[i+1]);

        return path.build();
    }

    @NotNull
    public static Geometry polyline(@NotNull Point2... points) {
        if (points.length < 2)
            throw new IllegalArgumentException("Wrong number of polyline points: " + points.length);

        Path path = Geometries.path();
        path.move(points[0]);
        Arrays.stream(points).skip(1).forEach(path::line);
        return path.build();
    }

    @NotNull
    public static Geometry polyline(@NotNull List<@NotNull Point2> points) {
        if (points.size() < 2)
            throw new IllegalArgumentException("Wrong number of polyline points: " + points.size());

        Path path = Geometries.path();
        path.move(points.get(0));
        points.stream().skip(1).forEach(path::line);
        return path.build();
    }

    @NotNull
    public static Geometry formula(
            boolean xDependsOnY, float originX, float originY,
            float min, float max, float step, @NotNull UnaryOperator<Float> formula
    ) {

        LinkedList<Point2> points = new LinkedList<>();

        for (int i = 0; min + i * step <= max; i++) {
            float current = min + i * step;
            float dependent = formula.apply(current);

            if (xDependsOnY) {
                points.add(Point2.create(dependent + originX, current + originY));
            } else {
                points.add(Point2.create(current + originX, current + originY));
            }
        }

        return Geometries.polyline(points);
    }

    // TODO More geometries

}
