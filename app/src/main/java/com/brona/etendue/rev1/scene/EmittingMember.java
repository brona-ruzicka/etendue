package com.brona.etendue.rev1.scene;

import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.ray.RaySection;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface EmittingMember extends Member {

    @NotNull Collection<RaySection.Endless> getRays();


    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements EmittingMember {

        @NotNull Collection<RaySection.Endless> rays;
        @NotNull BoundingBox boundingBox;

    }

}
