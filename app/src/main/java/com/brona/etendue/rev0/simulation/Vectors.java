package com.brona.etendue.rev0.simulation;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Vectors {


    public static double[] v2() {
        return new double[2];
    }

    public static double[] v2(double x, double y) {
        return new double[]{ x, y };
    }


    public static double[] v3() {
        return new double[3];
    }

    public static double[] v3(double x, double y, double z) {
        return new double[]{ x, y, z };
    }


    public static double[] vN(double... dimensions) {
        return dimensions;
    }

    public static double[] vN(int n) {
        return new double[n];
    }


    public static double[] copy(@NotNull double[] vector) {
        double[] dest = vN(vector.length);
        internalCopy(vector, dest);
        return dest;
    }

    public static void copy(@NotNull double[] vector, @NotNull double[] dest) {
        requireEqualSize(vector, dest);
        internalCopy(vector, dest);
    }

    protected static void internalCopy(@NotNull double[] vector, @NotNull double[] dest) {
        System.arraycopy(vector, 0, dest, 0, vector.length);
    }


    public static double squaredLength(@NotNull double[] vector) {
        double result = 0d;

        for (double v : vector) {
            result += v * v;
        }

        return result;
    }

    public static double length(@NotNull double[] vector) {
        return Math.sqrt(squaredLength(vector));
    }


    public static double[] plus(@NotNull double[] vectorA, @NotNull double[] vectorB) {
        double[] dest = vN(vectorA.length);
        internalPlus(vectorA, vectorB, dest);
        return dest;
    }

    public static void plus(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireEqualSize(vectorA, dest);
        internalPlus(vectorA, vectorB, dest);
    }

    protected static void internalPlus(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireEqualSize(vectorA, vectorB);
        for (int i = 0; i < vectorA.length; i++)
            dest[i] = vectorA[i] + vectorB[i];
    }


    public static double[] minus(@NotNull double[] vectorA, @NotNull double[] vectorB) {
        double[] dest = vN(vectorA.length);
        internalMinus(vectorA, vectorB, dest);
        return dest;
    }

    public static void minus(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireEqualSize(vectorA, dest);
        internalMinus(vectorA, vectorB, dest);
    }

    protected static void internalMinus(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireEqualSize(vectorA, vectorB);
        for (int i = 0; i < vectorA.length; i++)
            dest[i] = vectorA[i] - vectorB[i];
    }


    public static double[] invert(@NotNull double[] vector) {
        double[] dest = vN(vector.length);
        internalInvert(vector, dest);
        return dest;
    }

    public static void invert(@NotNull double[] vector, @NotNull double[] dest) {
        requireEqualSize(vector, dest);
        internalInvert(vector, dest);
    }

    protected static void internalInvert(@NotNull double[] vector, @NotNull double[] dest) {
        for (int i = 0; i < vector.length; i++)
            dest[i] = -vector[i];
    }


    public static double[] multiply(@NotNull double[] vector, double scalar) {
        double[] dest = new double[vector.length];
        internalMultiply(vector,scalar,dest);
        return dest;
    }

    public static void multiply(@NotNull double[] vector, double scalar, @NotNull double[] dest) {
        requireEqualSize(vector, dest);
        internalMultiply(vector, scalar, dest);
    }

    protected static void internalMultiply(@NotNull double[] vector, double scalar, @NotNull double[] dest) {
        for (int i = 0; i < vector.length; i++)
            dest[i] = vector[i] * scalar;
    }


    public static double[] divide(@NotNull double[] vector, double scalar) {
        double[] dest = new double[vector.length];
        internalDivide(vector,scalar,dest);
        return dest;
    }

    public static void divide(@NotNull double[] vector, double scalar, @NotNull double[] dest) {
        requireEqualSize(vector, dest);
        internalDivide(vector, scalar, dest);
    }

    protected static void internalDivide(@NotNull double[] vector, double scalar, @NotNull double[] dest) {
        for (int i = 0; i < vector.length; i++)
            dest[i] = vector[i] / scalar;
    }


    public static double[] reciprocal(@NotNull double[] vector) {
        double[] dest = vN(vector.length);
        internalReciprocal(vector, dest);
        return dest;
    }

    public static void reciprocal(@NotNull double[] vector, @NotNull double[] dest) {
        requireEqualSize(vector, dest);
        internalReciprocal(vector, dest);
    }

    protected static void internalReciprocal(@NotNull double[] vector, @NotNull double[] dest) {
        for (int i = 0; i < vector.length; i++)
            dest[i] = 1 / vector[i];
    }


    public static double dot(@NotNull double[] vectorA, @NotNull double[] vectorB) {
        requireEqualSize(vectorA, vectorB);

        double result = 0;
        for (int i = 0; i < vectorA.length; i++)
            result += vectorA[i] * vectorB[i];
        return result;
    }


    public static double[] cross(@NotNull double[] vectorA, @NotNull double[] vectorB) {
        double[] dest = v3();
        internalCross(vectorA, vectorB, dest);
        return dest;
    }

    public static void cross(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireVectorSize(3, dest);
        internalCross(vectorA, vectorB, dest);
    }

    protected static void internalCross(@NotNull double[] vectorA, @NotNull double[] vectorB, @NotNull double[] dest) {
        requireVectorSize(3, vectorA);
        requireVectorSize(3, vectorB);

        dest[0] = vectorA[1] * vectorB[2] - vectorB[1] * vectorA[2];
        dest[1] = vectorA[2] * vectorB[0] - vectorB[2] * vectorA[0];
        dest[2] = vectorA[0] * vectorB[1] - vectorB[0] * vectorA[1];
    }


    public static double[] rotate90(@NotNull double[] vector2) {
        double[] dest = v2();
        internalRotate90(vector2, dest);
        return dest;
    }

    public static void rotate90(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(2, dest);
        internalRotate90(vector2, dest);
    }

    protected static void internalRotate90(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(2, vector2);

        dest[0] = -vector2[1];
        dest[1] = vector2[0];
    }


    public static double[] rotate270(@NotNull double[] vector2) {
        double[] dest = v2();
        internalRotate270(vector2, dest);
        return dest;
    }

    public static void rotate270(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(2, dest);
        internalRotate90(vector2, dest);
    }

    protected static void internalRotate270(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(2, vector2);

        dest[0] = vector2[1];
        dest[1] = -vector2[0];
    }


    public static double[] homogenous(@NotNull double[] vector2) {
        double[] dest = v3();
        internalHomogenous(vector2, dest);
        return dest;
    }

    public static void homogenous(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(3, dest);
        internalHomogenous(vector2, dest);
    }

    protected static void internalHomogenous(@NotNull double[] vector2, @NotNull double[] dest) {
        requireVectorSize(2, vector2);

        dest[0] = vector2[0];
        dest[1] = vector2[1];
        dest[2] = 1;
    }


    public static double[] euclidean(@NotNull double[] vector3) {
        double[] dest = v2();
        internalEuclidean(vector3, dest);
        return dest;
    }

    public static void euclidean(@NotNull double[] vector3, @NotNull double[] dest) {
        requireVectorSize(2, dest);
        internalEuclidean(vector3, dest);
    }

    protected static void internalEuclidean(@NotNull double[] vector3, @NotNull double[] dest) {
        requireVectorSize(3, vector3);

        dest[0] = vector3[0] / vector3[2];
        dest[1] = vector3[1] / vector3[2];
    }


    protected static void requireVectorSize(int size, @NotNull double[] vector) {
        if (size != vector.length)
            throw new IllegalArgumentException(
                    "This operation requires " + size + "D vector, got "
                    + vector.length + "D " + Arrays.toString(vector)
            );
    }

    protected static void requireEqualSize(@NotNull double[] vectorA, @NotNull double[] vectorB) {
        if (vectorA.length != vectorB.length)
            throw new IllegalArgumentException(
                    "This operation requires vectors with the same number of dimensions, got "
                    + vectorA.length + "D " + Arrays.toString(vectorA)
                    + " and "
                    + vectorB.length + "D " + Arrays.toString(vectorB)
            );
    }


    private Vectors() { }

}
