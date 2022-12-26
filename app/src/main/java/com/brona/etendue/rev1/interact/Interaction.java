package com.brona.etendue.rev1.interact;

import com.brona.etendue.rev1.intersect.Intersection;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

@Value(staticConstructor = "create")
public class Interaction {

    @NotNull InteractionProperties properties;
    @NotNull Intersection intersection;

}
