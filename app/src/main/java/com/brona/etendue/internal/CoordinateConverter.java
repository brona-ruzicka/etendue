package com.brona.etendue.internal;

public interface CoordinateConverter {

    int[] toScreenCoords(double[] simulationCoords);

    double[] toSimulationCoords(int[] screenCoords);


    CoordinateConverter INVERSED_Y = new CoordinateConverter() {
        @Override
        public int[] toScreenCoords(double[] simulationCoords) {
            return new int[]{ (int) Math.round(simulationCoords[0]), (int) -Math.round(simulationCoords[1]) };
        }

        @Override
        public double[] toSimulationCoords(int[] screenCoords) {
            return new double[]{ screenCoords[0], -screenCoords[1] };
        }
    };

}

