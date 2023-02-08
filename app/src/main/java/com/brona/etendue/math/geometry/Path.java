package com.brona.etendue.math.geometry;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.curve.Curve;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Path {

    public static final int WIND_EVEN_ODD = Geometry.WIND_EVEN_ODD;
    public static final int WIND_NON_ZERO = Geometry.WIND_NON_ZERO;

    protected static final Segment CLOSE_SINGLETON = new Segment(SegmentType.CLOSE);


    private int windRule = WIND_EVEN_ODD;
    private final List<Segment> segments = new LinkedList<>();


    public Path() { }

    @NotNull
    public Path windRule(int windRule) {
        this.windRule = windRule;
        return this;
    }


    @NotNull
    public Path move(float x, float y) {
        segments.add(new Segment(SegmentType.MOVE, x, y));
        return this;
    }

    @NotNull
    public Path move(@NotNull Point2 p) {
        return move(p.getX(), p.getY());
    }


    @NotNull
    public Path line(float x, float y) {
        segments.add(new Segment(SegmentType.LINE, x, y));
        return this;
    }

    @NotNull
    public Path line(@NotNull Point2 p) {
        return line(p.getX(), p.getY());
    }

    // public abstract quadraticBezier
    // public abstract cubicBezier
    // public abstract arcSection

    @NotNull
    public Path close() {
        segments.add(CLOSE_SINGLETON);
        return this;
    }


    @NotNull
    public Geometry build() {

        if (segments.get(0).getType() != SegmentType.MOVE)
            throw new IllegalStateException(
                    "First PathSegment must be of type MOVE, "
                            + "found " + segments.get(0).getType().name() + "!"
            );


        BoundingBox boundingBox = BoundingBox.empty();
        List<Curve> curves = new ArrayList<>(segments.size());

        float startX = Float.NaN, startY = Float.NaN;
        float lastX  = Float.NaN, lastY  = Float.NaN;


        for (Segment segment : segments) {
            float[] arguments = segment.getArguments();

            switch (segment.getType()) {

                case MOVE:
                    startX = lastX = arguments[0];
                    startY = lastY = arguments[1];
                    break;

                case LINE:
                    curves.add(Curve.line(lastX, lastY, arguments[0], arguments[1]));
                    BoundingBox.combine(boundingBox, BoundingBox.create(lastX, lastY, arguments[0], arguments[1]));
                    lastX = arguments[0];
                    lastY = arguments[1];
                    break;

                // case QUADRATIC_BEZIER:
                // case CUBIC_BEZIER:
                // case ARC_SECTION:

                case CLOSE:

                    if (startX == lastX && startY == lastY)
                        break;

                    curves.add(Curve.line(lastX, lastY, startX, startY));
                    BoundingBox.combine(boundingBox, BoundingBox.create(lastX, lastY, startX, startY));
                    lastX = startX;
                    lastY = startY;
                    break;

                default:
                    throw new UnsupportedOperationException(
                            "Unknown Path.SegmentType." + segment.getType().name()
                    );

            }
        }

        return new Geometry.Simple(
                boundingBox,
                Collections.unmodifiableList(curves),
                windRule
        );
    }


    @Getter
    @ToString
    @AllArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public enum SegmentType {

        MOVE(2),
        LINE(2),
        // QUADRATIC_BEZIER(4),
        // CUBIC_BEZIER(6),
        // ARC_SECTION(7),
        CLOSE(0),
        ;

        int argumentCount;

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static final class Segment {

        @NotNull
        Path.SegmentType type;
        float[] arguments;

        Segment(@NotNull Path.SegmentType type, float... arguments) {
            if (arguments.length != type.getArgumentCount())
                throw new IllegalArgumentException(
                        "Wrong number of arguments for Path.SegmentType." + type.name() + ", "
                                + "got " + arguments.length + ", "
                                + "expected" + type.getArgumentCount() + "!"
                );

            this.type = type;
            this.arguments = arguments;
        }

    }
}
