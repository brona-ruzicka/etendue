package com.brona.etendue.user;

import com.brona.etendue.data.scene.Member;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import org.jetbrains.annotations.NotNull;

public final class Utils {

    private Utils() { }


    /** A dummy member to direct the auto-scale */
    @NotNull
    public static Member extendViewBox(float r) {
        return () -> BoundingBox.create(-r, -r, r, r);
    }

    /** A dummy member to direct the auto-scale */
    @NotNull
    public static Member extendViewBox(float x, float y) {
        return () -> BoundingBox.point(x, y);
    }

    /** A dummy member to direct the auto-scale */
    @NotNull
    public static Member extendViewBox(Point2 p) {
        return () -> BoundingBox.point(p);
    }

    /** A dummy member to direct the auto-scale */
    @NotNull
    public static Member extendViewBox(float x1, float y1, float x2, float y2) {
        return () -> BoundingBox.create(x1, y1, x2, y2);
    }

    /** A dummy member to direct the auto-scale */
    @NotNull
    public static Member extendViewBox(Point2 p1, Point2 p2) {
        return () -> BoundingBox.create(p1, p2);
    }

}
