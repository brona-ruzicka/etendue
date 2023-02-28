package com.brona.etendue.computation.detection.impl;

import com.brona.etendue.computation.detection.DistributionGraphComputer;
import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
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
    public GraphResult compute(@NotNull Collection<@NotNull Section> sections) {

        float smallestAngleStep = DistributionGraphComputer.GRAPH_SIMULATION_WIDTH / stepCount;

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

        Map<Float, float[]> allValues = IntStream.rangeClosed(-stepCount/2, stepCount/2).boxed()
                .collect(Collectors.toMap(
                        i -> i * smallestAngleStep,
                        i -> nonZeroValues.getOrDefault(i, EMPTY)
                ));


        Function<float[], Float> getMaximalFloatValue = floats -> {
            if (floats.length == 0)
                throw new IndexOutOfBoundsException();

            float max = Float.MIN_VALUE;
            for (float f: floats)
                if (f > max)
                    max = f;

            return max;
        };

        float maxValue = Math.max(
                1f,
                allValues.values().stream()
                        .map(getMaximalFloatValue)
                        .max(Float::compare)
                        .orElse(0f)
        );

        float total = (float) allValues.values().stream()
                .mapToDouble(floats -> {
                    float value = 0;
                    for(float f: floats)
                        value += f;
                    return value;
                })
                .sum();

        return new GraphResult(allValues, maxValue, total);
    }


}
