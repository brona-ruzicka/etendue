package com.brona.etendue.experiments.structure.first.interaction;

import com.brona.etendue.experiments.structure.first.RayMetadata;
import com.brona.etendue.experiments.structure.first.intersection.RayIntersection;

public interface RayInteractor {

    RayInteraction calculateInteraction(RayMetadata metadata, RayIntersection intersection);

}
