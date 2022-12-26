package com.brona.etendue.rev1.ray;


import com.brona.etendue.rev1.interact.Interaction;
import com.brona.etendue.rev1.intersect.Intersectors;
import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.scene.InteractingMember;
import com.brona.etendue.rev1.scene.Scene;

import java.util.*;

// TODO rework maybe
public final class RayTracer {

    public static Ray trace(RaySection.Endless section, Scene scene) {

        List<InteractingMember> interactingMembers = scene.getInteractingMembers();

        Ray ray = new Ray();
        ray.add(section);

        for (int i = 0; i < 32; i++) {
            RaySection.Endless next = traceNext(section, interactingMembers);

            if (next == null)
                return ray;

            section = next;
            ray.add(section);
        }

        System.err.println("More than 32 interactions");
        return ray;
    }

    private static RaySection.Endless traceNext(RaySection.Endless ray, List<InteractingMember> interactingMembers) {

        return interactingMembers
                .stream()
                .flatMap(interactingMember -> interactingMember
                        .getGeometry()
                        .getCurves()
                        .stream()
                        .flatMap(curve -> Intersectors
                                .intersect(curve, ray)
                                .stream()
                        )
                        .map(intersection -> Interaction
                                .create(
                                        interactingMember.getProperties(),
                                        intersection
                                )
                        )
                )
                .filter(interaction -> interaction
                        .getIntersection()
                        .getPoint()
                        .minus(
                                ray.getOrigin()
                        )
                        .squaredLength() > 0.01
                )
                .min(Comparator
                        .comparingDouble(interaction -> ray
                                .getOrigin()
                                .minus(interaction
                                        .getIntersection()
                                        .getPoint()
                                )
                                .squaredLength()
                        )
                )
                .map(interaction -> RaySection
                        .endless(
                                interaction.getIntersection().getPoint(),
                                reflect(
                                        ray.getDirection(),
                                        interaction.getIntersection().getNormal()
                                )
                        )
                )
                .orElse(null);

    }

    private static Vector2 reflect(Vector2 ray, Vector2 normal) {
        return (normal).times(2 * (ray).dot(normal)).minus(ray).invert();

    }

}
