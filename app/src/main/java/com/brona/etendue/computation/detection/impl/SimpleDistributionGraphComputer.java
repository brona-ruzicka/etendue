package com.brona.etendue.computation.detection.impl;

import com.brona.etendue.computation.detection.DistributionGraphComputer;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleDistributionGraphComputer implements DistributionGraphComputer {

    private static final float[] EMPTY = new float[]{ 0f, 0f };

    public static final int DEFAULT_STEP_COUNT = 160;

    int stepCount = DEFAULT_STEP_COUNT;

    @NotNull
    @Override
    public Map<@NotNull Float, float[]> compute(@NotNull Collection<@NotNull Section> sections) {

        float smallestAngleStep = (float) Math.PI / stepCount;

        CancelableScheduler.check();

        Map<Integer, float[]> nonZeroValues = sections.stream()
                .collect(Collectors.groupingBy(
                        section -> (int) Math.round(
                                Math.atan(section.getDirection().getY() / section.getDirection().getX()) / smallestAngleStep
                        ),
                        Collectors.collectingAndThen(
                                Collectors.partitioningBy(section -> section.getDirection().getX() < 0f, Collectors.counting()),
                                map -> new float[]{ map.get(false), map.get(true) }
                        )
                ));

        CancelableScheduler.check();

        Map<Float, float[]> allValues = IntStream.range(-stepCount/2, stepCount/2 + 1).boxed()
                .collect(Collectors.toMap(
                        i -> i * smallestAngleStep,
                        i -> nonZeroValues.getOrDefault(i, EMPTY)
                ));

        return allValues;
    }


}
