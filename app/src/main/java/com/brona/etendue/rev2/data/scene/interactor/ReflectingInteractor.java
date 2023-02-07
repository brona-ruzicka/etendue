package com.brona.etendue.rev2.data.scene.interactor;

import com.brona.etendue.rev2.data.scene.Interactor;
import com.brona.etendue.rev2.data.simulation.Intersection;
import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.geometry.Geometry;
import com.brona.etendue.rev2.math.tuple.Vector2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class ReflectingInteractor implements Interactor {

    @NotNull
    Geometry geometry;

    @Override
    @Nullable
    public Vector2 getNextDirection(@NotNull Section ray, @NotNull Intersection intersection) {
        return ( intersection.getNormal() ).times(2 * (ray.getDirection()).dot( intersection.getNormal()) ).minus( ray.getDirection() ).invert();
    }
}
