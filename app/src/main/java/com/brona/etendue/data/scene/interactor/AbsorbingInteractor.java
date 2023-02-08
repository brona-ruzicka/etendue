package com.brona.etendue.data.scene.interactor;

import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.data.simulation.Intersection;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.geometry.Geometry;
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
public class AbsorbingInteractor implements Interactor {

    @NotNull
    Geometry geometry;

    @Override
    @Nullable
    public Vector2 getNextDirection(@NotNull Section ray, @NotNull Intersection intersection) {
        return null;
    }
}
