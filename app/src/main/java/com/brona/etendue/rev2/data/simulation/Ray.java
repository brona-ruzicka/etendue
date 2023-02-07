package com.brona.etendue.rev2.data.simulation;

import com.brona.etendue.rev2.math.bounding.BoundingBox;
import com.brona.etendue.rev2.math.bounding.BoundingBoxAccumulator;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
public class Ray {

    @NotNull
    protected final List<@NotNull Point2> points = new ArrayList<>();

    @NotNull
    protected final List<@NotNull Vector2> directions = new ArrayList<>();

    @NotNull
    protected final BoundingBoxAccumulator boundingBox = BoundingBox.accumulator();

    @NotNull
    public Ray addSection(@NotNull Section section) {
        points.add(section.getPoint());
        directions.add(section.getDirection());
        boundingBox.add(section.getPoint());
        return this;
    }

    @NotNull
    public Ray addSections(@NotNull List<@NotNull Section> sections) {
        sections.forEach(this::addSection);
        return this;
    }

    @NotNull
    public Ray addSections(@NotNull Section... sections) {
        for (Section section : sections)
            this.addSection(section);
        return this;
    }

    @NotNull
    public List<@NotNull Point2> getPoints() {
        return Collections.unmodifiableList(points);
    }

    @NotNull
    public List<@Nullable Vector2> getDirections() {
        return Collections.unmodifiableList(directions);
    }

    @Nullable
    public Vector2 getLastDirection() {
        return directions.get(directions.size() - 1);
    }

    public boolean isEndless() {
        return Objects.nonNull(getLastDirection());
    }

    public boolean isEnding() {
        return Objects.isNull(getLastDirection());
    }

    @NotNull
    public BoundingBox getBoundingBox() {

        Vector2 direction = getLastDirection();

        if (Objects.isNull(direction))
            return boundingBox.createBoundingBox();

        BoundingBoxAccumulator accumulator = BoundingBox.accumulator().add(boundingBox);

        if (direction.getX() > 0)
            accumulator.addX(Float.MAX_VALUE);

        if (direction.getX() < 0)
            accumulator.addX(Float.MIN_VALUE);

        if (direction.getY() > 0)
            accumulator.addY(Float.MAX_VALUE);

        if (direction.getY() < 0)
            accumulator.addY(Float.MIN_VALUE);

        return accumulator.createBoundingBox();
    }
}
