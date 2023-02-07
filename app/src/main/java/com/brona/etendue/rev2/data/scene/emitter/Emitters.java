package com.brona.etendue.rev2.data.scene.emitter;

import com.brona.etendue.rev2.data.scene.Emitter;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
import org.jetbrains.annotations.NotNull;

public final class Emitters {

    private Emitters() {}

    @NotNull
    public static Emitter simple(@NotNull Point2 origin, @NotNull Vector2 direction) {
        return new SimpleEmitter(origin, direction);
    }

    @NotNull
    public static Emitter point(@NotNull Point2 origin, int rayCount) {
        return new PointEmitter(origin, rayCount);
    }

}
