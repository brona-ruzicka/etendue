package com.brona.etendue.experiments.structure.first.interaction;

import com.brona.etendue.experiments.structure.first.RayMetadata;
import com.brona.etendue.experiments.structure.first.geometry.Vector;

public interface RayInteraction {

    Vector changedDirection();

    RayMetadata changedMetadata();

}
