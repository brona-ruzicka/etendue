package com.brona.etendue.computation.util;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Point3;
import com.brona.etendue.math.tuple.Tuple3;
import com.brona.etendue.math.tuple.Vector2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComputationUtil {

    private ComputationUtil() { }


    @NotNull
    public static Tuple3 lineFromPoints(@NotNull Point2 a, @NotNull Point2 b) {
        return (a.homogenous().toTuple()).cross(b.homogenous().toTuple());
    }

    @NotNull
    public static Tuple3 lineInDirection(@NotNull Point2 a, @NotNull Vector2 dir) {
        return (a.homogenous().toTuple()).cross((a).plus(dir).homogenous().toTuple());
    }


    @Nullable
    public static Point2 intersectLines(@NotNull Tuple3 a, @NotNull Tuple3 b) {

        Point3 intersection = (a).cross(b);

        if (intersection.getZ() == 0)
            return null;

        return intersection.euclidean();
    }

    public static boolean checkBetween(@NotNull Point2 a, @NotNull Point2 b, @NotNull Point2 p) {
        return checkDissimilarDirection( (p).minus(a), (p).minus(b) );
    }

    public static boolean checkInDirection(@NotNull Point2 a, @NotNull Point2 b, @NotNull Point2 p) {
        return checkSimilarDirection( (p).minus(a), (b).minus(a) );
    }

    public static boolean checkInDirection(@NotNull Point2 a, @NotNull Vector2 dir, @NotNull Point2 p) {
        return checkSimilarDirection( (p).minus(a), dir );
    }

    public static boolean checkSimilarDirection(@NotNull Vector2 a, @NotNull Vector2 b) {
        return (a).dot(b) > 0f;
    }

    public static boolean checkDissimilarDirection(@NotNull Vector2 a, @NotNull Vector2 b) {
        return (a).dot(b) < 0f;
    }


}
