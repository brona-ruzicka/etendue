package com.brona.etendue.experiments.intersector;

import java.util.Arrays;

import static com.brona.etendue.experiments.intersector.Vectors.*;

public class Intersector {

    @Deprecated
    public static void print(float[] array) {
        System.out.println(Arrays.toString(array));
    }

    @Deprecated
    public static void print(float scalar) {
        System.out.println(scalar);
    }

    
    public static void main(String[] args) {
        float[]     A = v2(-1,-2),
                    v = v2(-1,-0.65f),
                    B = v2(1.2f,0.5f),
                    C = v2(1.5f,-1.9f);

//        float[]     A = v2(0,0),
//                    v = v2(1,1),
//                    B = v2(1,0),
//                    C = v2(1,2);

        print( lineAndSegment (A,v,B,C) );
    }


    public static float[] getIntersection(float[] lineA, float[] lineB) {
        return euclidean(cross(lineA, lineB));
    }

    public static boolean isParallel(float[] intersection) {
        return Float.isInfinite(intersection[0]) || Float.isInfinite(intersection[1]);
    }

    public static boolean isIdentical(float[] intersection) {
        return Float.isNaN(intersection[0]) && Float.isNaN(intersection[1]);
    }


    public static float[] lineAndSegment(float[] A, float[] v, float[] B, float[] C) {
        float t = (v[1]*A[0] - v[0]*A[1] - v[1]*B[0] + v[0]*B[1]) / (v[1]*C[0] - v[0]*C[1] - v[1]*B[0] + v[0]*B[1]);
        t = 0 <= t && t <= 1 ? t : Float.NaN;
        return v2( t*(C[0] - B[0]) + B[0], t*(C[1] - B[1]) + B[1]);
    }

}
