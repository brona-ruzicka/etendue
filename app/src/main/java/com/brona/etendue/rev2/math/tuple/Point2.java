package com.brona.etendue.rev2.math.tuple;

import org.jetbrains.annotations.NotNull;


public interface Point2 extends Base2 {

    @NotNull
    static Point2 create(float x, float y) {
        return new Tuple2(x, y);
    }


    @NotNull
    Tuple2 toTuple();
    @NotNull
    Vector2 toVector();


    @NotNull
    Point3 homogenous();

    @NotNull
    Point2 plus(@NotNull Vector2 vector);

    @NotNull
    Point2 minus(@NotNull Vector2 vector);

    @NotNull
    Vector2 minus(@NotNull Point2 point);

}
