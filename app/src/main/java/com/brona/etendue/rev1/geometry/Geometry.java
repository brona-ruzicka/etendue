package com.brona.etendue.rev1.geometry;

import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.bounding.BoundingBoxed;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.util.Collection;

public interface Geometry extends BoundingBoxed {

    int WIND_EVEN_ODD = PathIterator.WIND_EVEN_ODD;
    int WIND_NON_ZERO = PathIterator.WIND_NON_ZERO;


    @NotNull Shape getShape();

    @NotNull Collection<Curve> getCurves();

    int getWindRule();


    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements Geometry {

        @NotNull BoundingBox boundingBox;

        @NotNull Shape shape;

        @NotNull Collection<Curve> curves;

        int windRule;

    }

}
