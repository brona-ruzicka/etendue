package com.brona.etendue.user;

import com.brona.etendue.math.tuple.*;
import org.jetbrains.annotations.NotNull;

public final class Tuples {

    private Tuples() { }

    /** Shorthand for Point2.create */
    @NotNull
    public static Point2 p(float x, float y) {
        return Point2.create(x, y);
    }

    /** Shorthand for Point3.create */
    @NotNull
    public static Point3 p(float x, float y, float z) {
        return Point3.create(x, y, z);
    }

    /** Shorthand for Vector2.create */
    @NotNull
    public static Vector2 v(float x, float y) {
        return Vector2.create(x, y);
    }

    /** Shorthand for Vector3.create */
    @NotNull
    public static Vector3 v(float x, float y, float z) {
        return Vector3.create(x, y, z);
    }

    /** Shorthand for Tuple2.create */
    @NotNull
    public static Tuple2 t(float x, float y) {
        return Tuple2.create(x, y);
    }

    /** Shorthand for Tuple3.create */
    @NotNull
    public static Tuple3 t(float x, float y, float z) {
        return Tuple3.create(x, y, z);
    }

}
