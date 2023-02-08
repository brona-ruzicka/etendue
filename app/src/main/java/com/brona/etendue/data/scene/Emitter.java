package com.brona.etendue.data.scene;

import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.data.simulation.Section;
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
