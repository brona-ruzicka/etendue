package com.brona.etendue.rev0.internal;

import org.jetbrains.annotations.Nullable;

public interface CoordinateConverter {

    int[] toScreenCoords(@Nullable double[] simulationCoords);

    double[] toSimulationCoords(@Nullable int[] screenCoords);


    CoordinateConverter INVERTED_Y = new CoordinateConverter() {

        @Override
        @Nullable
        public int[] toScreenCoords(@Nullable double[] simulationCoords) {
            if (simulationCoords == null) return null;
            return new int[]{ (int) Math.round(simulationCoords[0]), (int) -Math.round(simulationCoords[1]) };
        }

        @Override
        @Nullable
        public double[] toSimulationCoords(@Nullable int[] screenCoords) {
            if (screenCoords == null) return null;
            return new double[]{ screenCoords[0], -screenCoords[1] };
        }

    };

}

