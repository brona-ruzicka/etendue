package com.brona.etendue.computation.detection.impl;

import com.brona.etendue.computation.detection.EtendueComputer;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleEtendueComputer implements EtendueComputer {

    public static final int DEFAULT_STEP_COUNT = 700;


    final float simulationHeight;

    int stepCount = DEFAULT_STEP_COUNT;


    @NotNull
    @Override
    public Map.Entry<@NotNull Float, @NotNull Collection<@NotNull Point2>> compute(@NotNull Collection<@NotNull Section> sections) {
        float stepX = 1.2f * 2 / stepCount;
        float stepY = simulationHeight / stepCount;

        Set<Point2> set = sections.stream()
                .map(section -> {
                    CancelableScheduler.check();
                    return Point2.create(
                            stepX * Math.round(section.getDirection().getY() / stepX),
                            stepY * Math.round(section.getPoint().getY() / stepY)
                    );
                })
                .collect(Collectors.toSet());

        float area = set.size() * stepX * stepY;

        return Map.entry(area,set);
    }

}
