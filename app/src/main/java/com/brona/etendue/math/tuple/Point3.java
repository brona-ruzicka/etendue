package com.brona.etendue.math.tuple;

import org.jetbrains.annotations.NotNull;

public interface Point3 extends Base3 {

    @NotNull
    static Point3 create(float x, float y, float z) {
        return new Tuple3(x, y, z);
    }


    @NotNull
    Tuple3 toTuple();
    @NotNull
    Vector3 toVector();


    @NotNull
    Point2 euclidean();

    @NotNull
    Point3 plus(@NotNull Vector3 vector);

    @NotNull
    Point3 minus(@NotNull Vector3 vector);

    @NotNull
    Vector3 minus(@NotNull Point3 point);

}
