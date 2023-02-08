package com.brona.etendue.math.tuple;

import org.jetbrains.annotations.NotNull;

public interface Vector2 extends Base2 {


    @NotNull
    static Vector2 create(float x, float y) {
        return new Tuple2(x, y);
    }


    @NotNull
    Point2 toPoint();
    @NotNull
    Tuple2 toTuple();


    float squaredLength();

    float length();

    @NotNull
    Vector2 normalize();

    @NotNull
    Vector2 invert();

    @NotNull
    Vector2 plus(@NotNull Vector2 vector);

    @NotNull
    Vector2 minus(@NotNull Vector2 vector);

    @NotNull
    Vector2 times(float a);

    float dot(@NotNull Vector2 vector);

    @NotNull
    Tuple2 rotateAnticlockwise90();

    @NotNull
    Tuple2 rotateClockwise90();

}
