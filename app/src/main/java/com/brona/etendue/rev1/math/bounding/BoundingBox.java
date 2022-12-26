package com.brona.etendue.rev1.math.bounding;

import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class BoundingBox {

    private static final BoundingBox EMPTY = new BoundingBox(
            Float.MAX_VALUE,
            Float.MAX_VALUE,
            Float.MIN_VALUE,
            Float.MIN_VALUE
    );

    private static final BoundingBox FULL = new BoundingBox(
            Float.MIN_VALUE,
            Float.MIN_VALUE,
            Float.MAX_VALUE,
            Float.MAX_VALUE
    );

    float minX, minY, maxX, maxY;

    public float getWidth() {
        return maxX - minX;
    }

    public float getHeight() {
        return maxX - minX;
    }

    @NotNull
    public Point2 getMinPoint() {
        return Point2.create(minX, minY);
    }

    @NotNull
    public Point2 getMaxPoint() {
        return Point2.create(maxX, maxY);
    }

    @NotNull
    public Vector2 getDimensions() {
        return Vector2.create(getWidth(), getHeight());
    }


    @NotNull
    public BoundingBox combine(@NotNull BoundingBox that) {
        return new BoundingBox(
                Math.min(this.minX, that.minX),
                Math.min(this.minY, that.minY),
                Math.max(this.maxX, that.maxX),
                Math.max(this.maxY, that.maxY)
        );
    }


    @NotNull
    public static BoundingBox empty() {
        return EMPTY;
    }

    @NotNull
    public static BoundingBox full() {
        return FULL;
    }

    @NotNull
    public static BoundingBox create(@NotNull Point2 p1, @NotNull Point2 p2) {
        return create(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @NotNull
    public static BoundingBox create(float x1, float y1, float x2, float y2) {
        return new BoundingBox(
                Math.min(x1, x2),
                Math.min(y1, y2),
                Math.max(x1, x2),
                Math.max(y1, y2)
        );
    }

    @NotNull
    public static BoundingBox point(@NotNull Point2 point) {
        return point(point.getX(), point.getY());
    }

    @NotNull
    public static BoundingBox point(float x, float y) {
        return new BoundingBox(
                x,
                y,
                x,
                y
        );
    }


}
