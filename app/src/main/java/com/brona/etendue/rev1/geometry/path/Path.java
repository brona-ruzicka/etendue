package com.brona.etendue.rev1.geometry.path;

import com.brona.etendue.rev1.geometry.Geometry;
import com.brona.etendue.rev1.geometry.Curve;
import com.brona.etendue.rev1.math.bounding.BoundingBox;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Path2D;
import java.util.Collection;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public final class Path extends Geometry.Simple {

    Path(@NotNull BoundingBox boundingBox, @NotNull Path2D.Float shape, @NotNull Collection<Curve> curves, int windRule) {
        super(boundingBox, shape, curves, windRule);
    }


    @NotNull
    public static Builder builder() {
        return new Builder();
    }

}
