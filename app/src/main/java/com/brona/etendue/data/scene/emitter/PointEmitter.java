package com.brona.etendue.data.scene.emitter;

import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.simulation.Section;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PointEmitter implements Emitter {

    @NotNull
    protected final Point2 origin;
    protected final int rayCount;


    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.point(origin);
    }

    @Override
    public List<@NotNull Section> getRays() {
        return PointEmitter.generateRays(origin, rayCount);
    }


    @NotNull
    public static List<@NotNull Section> generateRays(@NotNull Point2 origin, int rayCount) {
        return IntStream.range(0, rayCount)
                .boxed()
                .map(i -> {
                    double angle = Math.PI * 2 / rayCount * i;
                    Vector2 vector = Vector2.create(
                            (float) Math.sin(angle),
                            (float) Math.cos(angle)
                    );
                    return new Section(origin, vector);
                })
                .collect(Collectors.toList());
    }

}
