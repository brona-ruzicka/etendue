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

import java.lang.Math;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LineEmitter implements Emitter {

    @NotNull
    protected final Point2 center;
    protected final float length;
    protected final int rayCount;


    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.create(
                center.getX(),
                center.getY() - length / 2,
                center.getX(),
                center.getY() + length / 2
        );
    }

    @Override
    public @NotNull List<@NotNull Section> getRays() {
        return LineEmitter.generateRays(center, length, rayCount);
    }


    @NotNull
    public static List<@NotNull Section> generateRays(@NotNull Point2 center, float length, int rayCount) {
        
        return IntStream.range(0, rayCount)
                .boxed()
                .map(i -> {
                    float y = (float) ((Math.random() - 0.5) * length);
                    //double angle = Math.PI * Math.random();
                    double angle = Math.asin(Math.random()*2 - 1) + Math.PI / 2;
                    Vector2 vector = Vector2.create(
                            (float) Math.sin(angle),
                            (float) Math.cos(angle)
                    );
                    return new Section(Point2.create(center.getX(), center.getY() + y), vector);
                })
                .collect(Collectors.toList());
    }

}
