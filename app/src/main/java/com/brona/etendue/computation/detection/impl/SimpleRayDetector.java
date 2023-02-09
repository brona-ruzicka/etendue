package com.brona.etendue.computation.detection.impl;

import com.brona.etendue.computation.detection.RayDetector;
import com.brona.etendue.computation.util.ComputationUtil;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Tuple3;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class SimpleRayDetector implements RayDetector {

    @NotNull
    @Override
    public Collection<@NotNull Section> detect(@NotNull Collection<@NotNull Ray> rays, float xCoordinate) {
        return rays.stream()
                .flatMap(ray -> this.detect(ray, xCoordinate).stream())
                .collect(Collectors.toList());
    }

    private Collection<Section> detect(Ray ray, float xCoordinate) {
        CancelableScheduler.check();

        List<Section> parts = new ArrayList<>();

        Iterator<Point2> pointIterator = ray.getPoints().iterator();

        if (!pointIterator.hasNext())
            return Collections.emptyList();

        Point2 origin = pointIterator.next();

        for (Vector2 direction : ray.getDirections()) {
            CancelableScheduler.check();

            Point2 end = pointIterator.hasNext() ? pointIterator.next() : null;

            if (Objects.isNull(end) && Objects.isNull(direction))
                break;

            Section part = this.detect(origin, end, direction, xCoordinate);
            if (Objects.nonNull(part))
                parts.add(part);

            origin = end;
        }

        return parts;
    }

    private Section detect(Point2 origin, Point2 end, Vector2 direction, float xCoordinate) {
        Tuple3 line = Tuple3.create(-1, 0, xCoordinate);
        Tuple3 ray = ComputationUtil.lineInDirection(origin, direction);
        Point2 intersection = ComputationUtil.intersectLines(line, ray);

        if (Objects.isNull(intersection)
                || Objects.nonNull(end) && !ComputationUtil.checkBetween(origin, end, intersection)
                || Objects.isNull(end) && !ComputationUtil.checkInDirection(origin, direction, intersection)
        )
            return null;

        return new Section(intersection, direction);
    }

}
