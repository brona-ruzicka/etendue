package com.brona.etendue.data.simulation;

import com.brona.etendue.math.curve.Curve;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class Intersection {

    @NotNull Point2 point;

    @NotNull Vector2 normal;

    @NotNull Curve curve;

}
