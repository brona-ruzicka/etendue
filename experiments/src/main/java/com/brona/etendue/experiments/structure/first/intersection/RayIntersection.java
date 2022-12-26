package com.brona.etendue.experiments.structure.first.intersection;

import com.brona.etendue.experiments.structure.first.geometry.Point;
import com.brona.etendue.experiments.structure.first.geometry.Vector;

public interface RayIntersection {

    Vector rayDirection();

    Point intersectionPoint();

    Vector outsideNormal();

}
