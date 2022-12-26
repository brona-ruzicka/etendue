package com.brona.etendue.rev1.intersect;

import com.brona.etendue.rev1.geometry.Curve;
import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.intersect.types.LineIntersector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.EnumMap;


public final class Intersectors {

    @NotNull
    private static final EnumMap<Curve.Type, Intersector> MAP = new EnumMap<>(Curve.Type.class);


    private static void register(@NotNull Intersector intersector) {
        MAP.put(intersector.getType(), intersector);
    }

    private static void checkExhaustive() {
        if (MAP.size() != Curve.Type.values().length)
            throw new RuntimeException("Not all curve types have their intersectors!");
    }


    static {
        register(new LineIntersector());
        checkExhaustive();
    }


    @NotNull
    public static Collection<Intersection> intersect(@NotNull Curve curve, @NotNull RaySection ray) {
        return MAP.get(curve.getType()).intersect(curve, ray);
    }


    private Intersectors() { }

}
