package com.brona.etendue.data.scene;

import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.geometry.Geometry;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.data.simulation.Intersection;
import com.brona.etendue.data.simulation.Section;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public interface Interactor extends Member {

    @NotNull
    Geometry getGeometry();

    @Nullable
    Vector2 getNextDirection(@NotNull Section ray, @NotNull Intersection intersection);

    @Override
    default BoundingBox getBoundingBox() {
        return getGeometry().getBoundingBox();
    }

    @Override
    default boolean isInteractor() {
        return true;
    }


    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements Interactor {

        @Getter
        Geometry geometry;

        BiFunction<@NotNull Section, @NotNull Intersection, @Nullable Vector2> interactionFunction;

        @Override
        @Nullable
        public Vector2 getNextDirection(@NotNull Section ray, @NotNull Intersection intersection) {
            return interactionFunction.apply(ray, intersection);
        }
    }

}
