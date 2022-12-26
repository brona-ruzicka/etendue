package com.brona.etendue.rev0.internal;


public final class Util {


    public static int[][] getOffscreenPoints(int[][] coords) {
        int dx = (coords[1][0] - coords[0][0]) * 1000;
        int dy = (coords[1][1] - coords[0][1]) * 1000;

        return new int[][]{
                new int[]{
                        coords[0][0] - dx,
                        coords[0][1] - dy
                },
                new int[]{
                        coords[1][0] + dx,
                        coords[1][1] + dy,
                }
        };
    }


    private Util() { }

}
