package com.brona.etendue.rev1.intersect;

import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import lombok.Value;
import org.jetbrains.annotations.NotNull;


@Value(staticConstructor = "create")
public class Intersection {

    @NotNull Point2 point;
    @NotNull Vector2 normal;

}
