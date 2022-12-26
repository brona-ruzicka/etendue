package com.brona.etendue.rev0.simulation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Intersections {

    public enum LineLike {
        /** Přímka **/
        LINE {

            @Override
            protected boolean checkPoint(double[] point1, double[] point2, double[] intersection) {
                return true;
            }

        },
        /** Polopřímka **/
        RAY {

            @Override
            protected boolean checkPoint(double[] point1, double[] point2, double[] intersection) {
                double[] intersectionVector = Vectors.minus(intersection, point1);
                double[] rayVector = Vectors.minus(point2, point1);

                return Vectors.dot(intersectionVector, rayVector) > 0d;
            }

        },
        /** Úsečka **/
        SECTION {

            @Override
            protected boolean checkPoint(double[] point1, double[] point2, double[] intersection) {
                double[] point1Vector = Vectors.minus(intersection, point1);
                double[] point2Vector = Vectors.minus(intersection, point2);

                return Vectors.dot(point1Vector, point2Vector) < 0d;
            }

        };

        /** Checks whether or not the intersection is on the right part of the line **/
        protected abstract boolean checkPoint(double[] point1, double[] point2, double[] intersection);
    }


    @Nullable
    public static double[] intersectLineLike(
            @NotNull double[] obj1point1, @NotNull double[] obj1point2, @NotNull Intersections.LineLike obj1type,
            @NotNull double[] obj2point1, @NotNull double[] obj2point2, @NotNull Intersections.LineLike obj2type
    ) {
        double[] intersection = intersectLines(obj1point1, obj1point2, obj2point1, obj2point2);

        if (intersection == null)
            return null;

        if (
                obj1type.checkPoint(obj1point1, obj1point2, intersection) &&
                obj2type.checkPoint(obj2point1, obj2point2, intersection)
        ) return intersection;

        return null;
    }

    @Nullable
    public static double[] intersectLines(
            @NotNull double[] line1point1, @NotNull double[] line1point2,
            @NotNull double[] line2point1, @NotNull double[] line2point2
    ) {
        double[] line1 = Vectors.cross(Vectors.homogenous(line1point1), Vectors.homogenous(line1point2));
        double[] line2 = Vectors.cross(Vectors.homogenous(line2point1), Vectors.homogenous(line2point2));
        double[] intersection = Vectors.euclidean(Vectors.cross(line1, line2));

        if (Double.isFinite(intersection[0]) && Double.isFinite(intersection[1]))
            return intersection;

        return null;
    }


    private Intersections() { }

}
