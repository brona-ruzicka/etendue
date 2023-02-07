package com.brona.etendue.rev2.data.scene;

import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.bounding.BoundingBox;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Emitter extends Member {

    @NotNull
    BoundingBox getBoundingBox();

    @NotNull
    List<@NotNull Section> getRays();

    @Override
    default boolean isEmitter() {
        return true;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
    class Simple implements Emitter {

        @NotNull
        BoundingBox boundingBox;

        @NotNull
        List<@NotNull Section> rays;

    }

}
