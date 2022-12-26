package com.brona.etendue.rev1.geometry.path;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Segment {

    @NotNull
    Segment.Type type;
    float[] arguments;

    Segment(@NotNull Segment.Type type, float ...arguments) {
        if (arguments.length != type.getArgumentCount())
            throw new IllegalArgumentException(
                    "Wrong number of arguments for Segment.Type." + type.name() + ", "
                    + "got " + arguments.length + ", "
                    + "expected" + type.getArgumentCount() + "!"
            );

        this.type = type;
        this.arguments = arguments;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public enum Type {

        MOVE(2),
        LINE(2),
        // QUADRATIC_BEZIER(4),
        // CUBIC_BEZIER(6),
        // ARC_SECTION(7),
        CLOSE(0),
        ;

        int argumentCount;

    }
}
