package com.brona.etendue.data.simulation;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class Section {

    @NotNull Point2 point;

    @Nullable Vector2 direction;


}
