package com.brona.etendue.rev2.math.geometry;

import com.brona.etendue.rev2.math.bounding.BoundingBox;
import com.brona.etendue.rev2.math.curve.Curve;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.PathIterator;
import java.util.Collection;

public interface Geometry {

    int WIND_EVEN_ODD = PathIterator.WIND_EVEN_ODD;
    int WIND_NON_ZERO = PathIterator.WIND_NON_ZERO;

    @NotNull BoundingBox getBoundingBox();

    @NotNull Collection<@NotNull Curve> getCurves();

    int getWindRule();


    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements Geometry {

        @NotNull BoundingBox boundingBox;

        @NotNull Collection<@NotNull Curve> curves;

        int windRule;


        public Simple(@NotNull BoundingBox boundingBox, @NotNull Collection<@NotNull Curve> curves) {
            this(boundingBox, curves, Geometry.WIND_EVEN_ODD);
        }

    }

}
