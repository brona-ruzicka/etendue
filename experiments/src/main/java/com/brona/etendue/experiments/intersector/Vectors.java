package com.brona.etendue.experiments.intersector;

public class Vectors {

    public static float[] copy(float[] vector) {
        assert vector != null;

        float[] dest = new float[vector.length];
        System.arraycopy(vector, 0, dest, 0, vector.length);

        return dest;
    }

    public static float[] v2(float x, float y) {
        return new float[]{ x, y };
    }

    public static float[] v3(float x, float y, float z) {
        return new float[]{ x, y, z };
    }


    public static float[] plus(float[] vectorA, float[] vectorB) {
        assert vectorA.length == vectorB.length;
        float[] dest = new float[vectorA.length];
        for (int i = 0; i < vectorA.length; i++)
            dest[i] = vectorA[i] + vectorB[i];
        return dest;
    }

    public static float[] minus(float[] vectorA, float[] vectorB) {
        assert vectorA.length == vectorB.length;
        float[] dest = new float[vectorA.length];
        for (int i = 0; i < vectorA.length; i++)
            dest[i] = vectorA[i] - vectorB[i];
        return dest;
    }

    public static float[] invert(float[] vector) {
        float[] dest = new float[vector.length];
        for (int i = 0; i < vector.length; i++)
            dest[i] = -vector[i];
        return dest;
    }

    public static float[] multiply(float[] vector, float scalar) {
        float[] dest = new float[vector.length];
        for (int i = 0; i < vector.length; i++)
            dest[i] = vector[i] * scalar;
        return dest;
    }

    public static float[] divide(float[] vector, float scalar) {
        float[] dest = new float[vector.length];
        for (int i = 0; i < vector.length; i++)
            dest[i] = vector[i] / scalar;
        return dest;
    }

    public static float[] reciprocal(float[] vector) {
        float[] dest = new float[vector.length];
        for (int i = 0; i < vector.length; i++)
            dest[i] = 1 / vector[i];
        return dest;
    }

    public static float dot(float[] vectorA, float[] vectorB) {
        assert vectorA.length == vectorB.length;
        float result = 0;
        for (int i = 0; i < vectorA.length; i++)
            result += vectorA[i] * vectorB[i];
        return result;
    }

    public static float[] cross(float[] vectorA, float[] vectorB) {
        assert vectorA.length == 3 && vectorB.length == 3;
        return v3(
                vectorA[1] * vectorB[2] - vectorB[1] * vectorA[2],
                vectorA[2] * vectorB[0] - vectorB[2] * vectorA[0],
                vectorA[0] * vectorB[1] - vectorB[0] * vectorA[1]
        );
    }

    public static float[] perpendicular(float[] vector) {
        assert vector.length == 2;
        return v2(-vector[1], vector[0]);
    }

    public static float[] perpendicularClockwise(float[] vector) {
        assert vector.length == 2;
        return v2(vector[1], -vector[0]);
    }

    public static float[] homogenous(float[] vector2) {
        assert vector2.length == 2;
        return v3( vector2[0], vector2[1], 1 );
    }

    public static float[] euclidean(float[] vector3) {
        assert vector3.length == 3;
        return v2( vector3[0] / vector3[2], vector3[1] / vector3[2] );
    }

}
