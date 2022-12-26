package com.brona.etendue.rev1.math.simple;

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
    Point3 plus(Vector3 vector);

    @NotNull
    Point3 minus(Vector3 vector);

    @NotNull
    Vector3 minus(Point3 point);

}
