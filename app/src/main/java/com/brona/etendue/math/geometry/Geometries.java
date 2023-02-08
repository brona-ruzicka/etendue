package com.brona.etendue.math.geometry;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.curve.Curve;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public final class Geometries {

    private Geometries() { }


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
    public static Path path() {
        return new Path();
    }

}
