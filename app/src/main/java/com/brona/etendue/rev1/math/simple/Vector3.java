package com.brona.etendue.rev1.math.simple;

import org.jetbrains.annotations.NotNull;

public interface Vector3 extends Base3 {


    @NotNull
    static Vector3 create(float x, float y, float z) {
        return new Tuple3(x, y, z);
    }


    @NotNull
    Point3 toPoint();
    @NotNull
    Tuple3 toTuple();


    float squaredLength();

    float length();

    @NotNull
    Vector3 normalize();

    @NotNull
    Vector3 invert();

    @NotNull
    Vector3 plus(@NotNull Vector3 vector);

    @NotNull
    Vector3 minus(@NotNull Vector3 vector);

    @NotNull
    Vector3 times(float a);

    float dot(@NotNull Vector3 vector);

    @NotNull
    Vector3 cross(@NotNull Vector3 vector);

}
