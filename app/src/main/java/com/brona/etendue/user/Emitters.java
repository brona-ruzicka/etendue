package com.brona.etendue.user;

import com.brona.etendue.data.scene.emitter.PointEmitter;
import com.brona.etendue.data.scene.emitter.RectangleEmitter;
import com.brona.etendue.data.scene.emitter.SimpleEmitter;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.data.scene.Emitter;
import org.jetbrains.annotations.NotNull;

import static com.brona.etendue.user.Tuples.p;

public final class Emitters {

    private Emitters() { }

    /** Emits single ray */
    @NotNull
    public static Emitter single(float px, float py, float vx, float vy) {
        return new SimpleEmitter(Point2.create(px, py), Vector2.create(vx, vy).normalize());
    }

    /** Emits single ray */
    @NotNull
    public static Emitter single(@NotNull Point2 origin, @NotNull Vector2 direction) {
        return new SimpleEmitter(origin, direction.normalize());
    }

    /** Emits single n rays from origin in all directions */
    @NotNull
    public static Emitter point(float x, float y, int rayCount) {
        return new PointEmitter(Point2.create(x, y), rayCount);
    }

    /** Emits single n rays from origin in all directions */
    @NotNull
    public static Emitter point(@NotNull Point2 origin, int rayCount) {
        return new PointEmitter(origin, rayCount);
    }

    /** Behaves like point emitters in a grid */
    @NotNull
    public static Emitter rectangle(float centerX, float centerY, float stepX, float stepY, int countX, int countY, int rayCount) {
        return new RectangleEmitter(Point2.create(centerX, centerY), Vector2.create(stepX, stepY), countX, countY, rayCount);
    }

    /** Behaves like point emitters in a grid */
    @NotNull
    public static Emitter rectangle(@NotNull Point2 center, @NotNull Vector2 step, int countX, int countY, int rayCount) {
        return new RectangleEmitter(center, step, countX, countY, rayCount);
    }

}
