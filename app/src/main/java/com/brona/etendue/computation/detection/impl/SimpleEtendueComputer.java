package com.brona.etendue.computation.detection.impl;

import com.brona.etendue.computation.detection.EtendueComputer;
import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleEtendueComputer implements EtendueComputer {

    public static final int DEFAULT_STEP_COUNT = 200;

    int stepCount = DEFAULT_STEP_COUNT;

    @NotNull
    @Override
    public EtendueResult compute(@NotNull Collection<@NotNull Section> sections, float sizeY) {

        float stepX = EtendueComputer.ETENDUE_SIMULATION_WIDTH / stepCount;
        float stepY = sizeY / stepCount;

        Map<Point2,Long> map = sections.stream()
                .map(section -> {
                    CancelableScheduler.check();
                    return Point2.create(
                            stepX * Math.round(section.getDirection().getY() / stepX),
                            stepY * Math.round(section.getPoint().getY() / stepY)
                    );
                })
                .collect(Collectors.groupingBy(p->p,Collectors.counting()));

        float area = map.size() * stepX * stepY;
        float average = map.values().stream().collect(Collectors.averagingLong(l->l)).floatValue();
        float max = map.values().stream().max(Long::compare).orElse(1L);

        return new EtendueResult(map, area, average, max);
    }

}
