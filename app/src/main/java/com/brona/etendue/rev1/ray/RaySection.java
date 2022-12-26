package com.brona.etendue.rev1.ray;

import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface RaySection {

    @NotNull Point2 getOrigin();
    @Nullable Point2 getEnd();

    @NotNull Vector2 getDirection();



    static RaySection.Endless endless(Point2 origin, Vector2 direction) {
        return new RaySection.Endless(origin, direction);
    }

    static RaySection.Ending ending(Point2 origin, Point2 end, Vector2 direction) {
        return new RaySection.Ending(origin, end, direction);
    }


    @Value
    class Endless implements RaySection {

        @NotNull Point2 origin;
        @NotNull Vector2 direction;

        @Nullable
        @Override
        public Point2 getEnd() {
            return null;
        }
    }

    @Value
    class Ending implements RaySection {

        @NotNull Point2 origin;
        @NotNull Point2 end;
        @NotNull Vector2 direction;

    }

}
