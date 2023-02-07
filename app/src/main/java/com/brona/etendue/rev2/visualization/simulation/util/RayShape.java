package com.brona.etendue.rev2.visualization.simulation.util;

import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.math.bounding.BoundingBox;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RayShape implements Shape {

    @NotNull
    protected final Ray ray;

    @Override
    public Rectangle2D getBounds2D() {
        BoundingBox box = ray.getBoundingBox();
        return new Rectangle2D.Float(box.getMinX(), box.getMinY(), box.getWidth(), box.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new RayShapeIterator(ray, affineTransform);
    }

    @ToString
    @EqualsAndHashCode
    protected static class RayShapeIterator implements PathIterator {

        protected int index = 0;

        @NotNull
        protected final List<@NotNull Point2> points;
        @Nullable
        protected final Vector2 direction;
        protected final boolean endless;


        @Nullable
        protected final AffineTransform transform;

        public RayShapeIterator(@NotNull Ray ray, @Nullable AffineTransform transform) {
            points = ray.getPoints();
            direction = ray.getLastDirection();
            endless = Objects.nonNull(direction);

            this.transform = transform;
        }

        @Override
        public int getWindingRule() {
            return PathIterator.WIND_NON_ZERO;
        }

        @Override
        public boolean isDone() {
            return endless ? index > points.size() : index >= points.size();
        }

        @Override
        public void next() {
            ++index;
        }

        @Override
        public int currentSegment(float[] floats) {
            if (index < points.size()) {
                Point2 point = points.get(index);
                floats[0] = point.getX();
                floats[1] = point.getY();

            } else if (index == points.size() && endless) {
                Point2 point = points.get(points.size() - 1);
                point = point.plus(direction.normalize().times(10000f));
                floats[0] = point.getX();
                floats[1] = point.getY();

            } else {
                throw new IllegalStateException("Index out of bounds");
            }

            if (this.transform != null)
                this.transform.transform(floats, 0, floats, 0, 1);

            return index == 0 ? 0 : 1;
        }

        @Override
        public int currentSegment(double[] doubles) {
            if (index < points.size()) {
                Point2 point = points.get(index);
                doubles[1] = point.getX();
                doubles[2] = point.getY();

            } else if (index == points.size() && endless) {
                Point2 point = points.get(points.size() - 1);
                point = point.plus(direction.normalize().times(10000f));
                doubles[1] = point.getX();
                doubles[2] = point.getY();

            } else {
                throw new IllegalStateException("Index out of bounds");
            }

            if (this.transform != null)
                this.transform.transform(doubles, 0, doubles, 0, 1);

            return index == 0 ? 0 : 1;
        }
    }


    @Override
    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    @Override
    public boolean contains(double v, double v1) {
        return false;
    }

    @Override
    public boolean contains(Point2D point2D) {
        return false;
    }

    @Override
    public boolean intersects(double v, double v1, double v2, double v3) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D) {
        return false;
    }

    @Override
    public boolean contains(double v, double v1, double v2, double v3) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double v) {
        return getPathIterator(affineTransform);
    }
}
