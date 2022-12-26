package com.brona.etendue.rev1.emit;

import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.scene.EmittingMember;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PointEmitter extends EmittingMember.Simple {

    public PointEmitter(@NotNull Point2 origin, int count) {
        super(
                IntStream.range(0, count)
                        .boxed()
                        .map(i -> {
                            double angle = Math.PI * 2 / count * i;
                            Vector2 vector = Vector2.create(
                                    (float) Math.sin(angle),
                                    (float) Math.cos(angle)
                            );
                            return RaySection.endless(origin, vector);
                        })
                        .collect(Collectors.toList()),
                BoundingBox.point(origin)
        );
    }

}
