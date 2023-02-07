package com.brona.etendue.rev2.math.curve;

import com.brona.etendue.rev2.math.tuple.Point2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Curve {

    @NotNull
    Curve.Type type;
    float[] arguments;

    Curve(@NotNull Curve.Type type, float ...arguments) {
        if (arguments.length != type.getArgumentCount())
            throw new IllegalArgumentException(
                    "Wrong number of arguments for Curve.Type." + type.name() + ", "
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

        LINE(4),
        // QUADRATIC_BEZIER,
        // CUBIC_BEZIER,
        // ARC_SECTION
        ;

        int argumentCount;

    }


    @NotNull
    public static Curve line(float x1, float y1, float x2, float y2) {
        return new Curve(Type.LINE, x1, y1, x2, y2);
    }

    @NotNull
    public static Curve line(@NotNull Point2 p1, @NotNull Point2 p2) {
        return line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

}
