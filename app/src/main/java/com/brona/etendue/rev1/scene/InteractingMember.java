package com.brona.etendue.rev1.scene;

import com.brona.etendue.rev1.geometry.Geometry;
import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.interact.InteractionProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;


public interface InteractingMember extends Member {

    @NotNull Geometry getGeometry();
    @NotNull InteractionProperties getProperties();

    @NotNull
    @Override
    default BoundingBox getBoundingBox() {
        return getGeometry().getBoundingBox();
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor //TODO @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements InteractingMember {

        @NotNull Geometry geometry;
        @NotNull InteractionProperties properties = new InteractionProperties(0); // TODO Required argument

    }

}
