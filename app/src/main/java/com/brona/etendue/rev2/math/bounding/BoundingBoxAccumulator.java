package com.brona.etendue.rev2.math.bounding;

import com.brona.etendue.rev2.math.tuple.Point2;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;


@ToString
@EqualsAndHashCode
public class BoundingBoxAccumulator {

    protected float minX = Float.MAX_VALUE;
    protected float minY = Float.MAX_VALUE;

    protected float maxX = Float.MIN_VALUE;
    protected float maxY = Float.MIN_VALUE;

    @NotNull
    public BoundingBoxAccumulator addX(float x) {
        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);

        return this;
    }

    @NotNull
    public BoundingBoxAccumulator addY(float y) {
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);

        return this;
    }

    @NotNull
    public BoundingBoxAccumulator add(float x, float y) {
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);

        return this;
    }

    @NotNull
    public BoundingBoxAccumulator add(@NotNull Point2 point) {
        return add(point.getX(), point.getY());
    }

    @NotNull
    public BoundingBoxAccumulator add(@NotNull BoundingBox box) {
        minX = Math.min(minX, box.getMinX());
        minY = Math.min(minY, box.getMinY());
        maxX = Math.max(maxX, box.getMaxX());
        maxY = Math.max(maxY, box.getMaxY());

        return this;
    }

    @NotNull
    public BoundingBoxAccumulator add(@NotNull BoundingBoxAccumulator box) {
        minX = Math.min(minX, box.minX);
        minY = Math.min(minY, box.minY);
        maxX = Math.max(maxX, box.maxX);
        maxY = Math.max(maxY, box.maxY);

        return this;
    }

    @NotNull
    public BoundingBox createBoundingBox() {
        return new BoundingBox(minX, minY, maxX, maxY);
    }

}
