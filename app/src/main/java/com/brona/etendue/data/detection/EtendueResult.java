package com.brona.etendue.data.detection;

import com.brona.etendue.math.tuple.Point2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class EtendueResult {

    @NotNull Map<@NotNull Point2, @NotNull Long> points;

    float area;
    float average;

    float max;

}
