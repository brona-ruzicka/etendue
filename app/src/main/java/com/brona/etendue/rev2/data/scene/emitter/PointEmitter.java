package com.brona.etendue.rev2.data.scene.emitter;

import com.brona.etendue.rev2.data.scene.Emitter;
import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.bounding.BoundingBox;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
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
    public @NotNull List<@NotNull Section> getRays() {
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
