package com.brona.etendue.experiments.structure.first.intersection;

import com.brona.etendue.experiments.structure.first.geometry.Rectangle;

public interface SimulationShape {

    Rectangle getBoundingBox();

    RayIntersection calculateIntersection();

}
