package com.brona.etendue.rev1.ray;

import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Ray extends AbstractCollection<RaySection> {

    @NotNull
    final List<Point2> points = new LinkedList<>();

    @Nullable
    Vector2 direction;


    @Override
    public java.util.Iterator<RaySection> iterator() {
        return new Iterator();
    }

    @Override
    public int size() {
        return direction != null ? points.size() : points.size() - 1;
    }

    @Override
    public boolean add(@NotNull RaySection section) {
        points.add(section.getOrigin());

        Point2 end = section.getEnd();
        if (end != null) {
            points.add(end);
            direction = null;
        } else {
            direction = section.getDirection();
        }

        return true;
    }

    public boolean add(@NotNull Point2 point) {
        points.add(point);
        direction = null;
        return true;
    }

    public boolean add(@NotNull Vector2 direction) {
        this.direction = direction;
        return true;
    }

    protected class Iterator implements java.util.Iterator<RaySection> {

        protected ListIterator<Point2> pointIterator = points.listIterator();

        @Override
        public boolean hasNext() {

            if (!pointIterator.hasNext())
                return false;

            if (direction != null)
                return true;

            pointIterator.next();
            boolean has = pointIterator.hasNext();
            pointIterator.previous();
            return has;
        }

        @Override
        public RaySection next() {
            Point2 origin = pointIterator.next();

            if (pointIterator.hasNext()) {
                Point2 end = pointIterator.next();
                pointIterator.previous();
                return RaySection.ending(origin, end,
                        /* TODO this is garbage: */ end.minus(origin).normalize()
                        // TODO need a way to capture the refractive index
                );
            }

            if (direction != null)
                return RaySection.endless(origin, direction);

            throw new IndexOutOfBoundsException();
        }
    }

}
