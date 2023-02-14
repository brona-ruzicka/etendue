package com.brona.etendue.data.detection;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class GraphResult {

    @NotNull Map<@NotNull Float, float[]> data;

    float maxValue;

}
