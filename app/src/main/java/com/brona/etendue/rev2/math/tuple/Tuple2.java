package com.brona.etendue.rev2.math.tuple;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Tuple2 implements Point2, Vector2 {

    float x, y;

    
    @NotNull
    public static Tuple2 create(float x, float y) {
        return new Tuple2(x, y);
    }
    

    @NotNull
    @Override
    public Vector2 toVector() {
        return this;
    }

    @NotNull
    @Override
    public Tuple2 toTuple() { return this; }

    @NotNull
    @Override
    public Point2 toPoint() {
        return this;
    }


    @NotNull
    @Override
    public Tuple3 homogenous() {
        return new Tuple3(this.x, this.y, 1);
    }


    @Override
    public float squaredLength() {
        return this.x * this.x + this.y * this.y;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(squaredLength());
    }

    @NotNull
    @Override
    public Tuple2 normalize() {
        float length = length();
        return new Tuple2(this.x / length, this.y / length);
    }

    @NotNull
    @Override
    public Tuple2 invert() {
        return new Tuple2(-this.x, -this.y);
    }

    @NotNull
    @Override
    public Tuple2 plus(@NotNull Vector2 vector) {
        return new Tuple2(this.x + vector.getX(), this.y + vector.getY());
    }

    @NotNull
    @Override
    public Tuple2 minus(@NotNull Point2 point) {
        return minus((Base2) point);
    }

    @NotNull
    @Override
    public Tuple2 minus(@NotNull Vector2 vector) {
        return minus((Base2) vector);
    }

    @NotNull
    public Tuple2 minus(@NotNull Base2 base) {
        return new Tuple2(this.x - base.getX(), this.y - base.getY());
    }

    @NotNull
    @Override
    public Tuple2 times(float a) {
        return new Tuple2(a * this.x, a * this.y);
    }

    @Override
    public float dot(@NotNull Vector2 that) {
        return this.x * that.getX() + this.y * that.getY();
    }

    @NotNull
    @Override
    public Tuple2 rotateAnticlockwise90() {
        //noinspection SuspiciousNameCombination
        return new Tuple2(-this.y, this.x);
    }

    @NotNull
    @Override
    public Tuple2 rotateClockwise90() {
        //noinspection SuspiciousNameCombination
        return new Tuple2(this.y, -this.x);
    }

}
