package com.brona.etendue.data.scene.emitter;

import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
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
public class RectangleEmitter implements Emitter {

    @NotNull
    protected final Point2 center;
    @NotNull
    protected final Vector2 step;

    protected final int countX, countY;
    protected final int rayCount;

    @NotNull
    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.create(
                center.getX() - step.getX() * countX / 2,
                center.getY() - step.getY() * countY / 2,
                center.getX() + step.getX() * countX / 2,
                center.getY() + step.getY() * countY / 2
        );
    }

    @NotNull
    @Override
    public List<@NotNull Section> getRays() {
        float originX = center.getX() - step.getX() * countX / 2;
        float originY = center.getY() - step.getY() * countX / 2;

        return IntStream.range(0, countX)
                .boxed()
                .flatMap(x ->
                        IntStream.range(0, countY)
                                .boxed()
                                .flatMap(y ->
                                        PointEmitter.generateRays(
                                                Point2.create(
                                                        originX + x * step.getX(),
                                                        originY + y * step.getY()
                                                ),
                                                rayCount
                                        )
                                        .stream()
                                )
                )
                .collect(Collectors.toList());
    }
}
