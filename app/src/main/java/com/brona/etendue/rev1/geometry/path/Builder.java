package com.brona.etendue.rev1.geometry.path;

import com.brona.etendue.rev1.geometry.Geometry;
import com.brona.etendue.rev1.geometry.Curve;
import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.simple.Point2;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ToString
@EqualsAndHashCode
public final class Builder {

    public static final int WIND_EVEN_ODD = Geometry.WIND_EVEN_ODD;
    public static final int WIND_NON_ZERO = Geometry.WIND_NON_ZERO;

    private static final Segment CLOSE_SINGLETON = new Segment(Segment.Type.CLOSE);


    private int windRule = WIND_EVEN_ODD;
    private final List<Segment> segments = new LinkedList<>();


    Builder() { }

    @NotNull
    public Builder windRule(int windRule) {
        this.windRule = windRule;
        return this;
    }


    @NotNull
    public Builder move(float x, float y) {
        segments.add(new Segment(Segment.Type.MOVE, x, y));
        return this;
    }

    @NotNull
    public Builder move(Point2 p) {
        return move(p.getX(), p.getY());
    }


    @NotNull
    public Builder line(float x, float y) {
        segments.add(new Segment(Segment.Type.LINE, x, y));
        return this;
    }

    @NotNull
    public Builder line(Point2 p) {
        return line(p.getX(), p.getY());
    }

    // public abstract quadraticBezier
    // public abstract cubicBezier
    // public abstract arcSection

    @NotNull
    public Builder close() {
        segments.add(CLOSE_SINGLETON);
        return this;
    }


    @NotNull
    public Path build() {

        if (segments.get(0).getType() != Segment.Type.MOVE)
            throw new IllegalStateException(
                    "First PathSegment must be of type MOVE, "
                            + "found " + segments.get(0).getType().name() + "!"
            );


        BoundingBox boundingBox = BoundingBox.empty();
        Path2D.Float path2d = new Path2D.Float(windRule, segments.size());
        List<Curve> curves = new ArrayList<>(segments.size());

        float startX = Float.NaN, startY = Float.NaN;
        float lastX  = Float.NaN, lastY  = Float.NaN;


        for (Segment segment : segments) {
            float[] arguments = segment.getArguments();

            switch (segment.getType()) {

                case MOVE:
                    path2d.moveTo(arguments[0], arguments[1]);
                    startX = lastX = arguments[0];
                    startY = lastY = arguments[1];
                    break;

                case LINE:
                    path2d.lineTo(arguments[0], arguments[1]);
                    curves.add(Curve.line(lastX, lastY, arguments[0], arguments[1]));
                    boundingBox.combine(BoundingBox.create(lastX, lastY, arguments[0], arguments[1]));
                    lastX = arguments[0];
                    lastY = arguments[1];
                    break;

                // case QUADRATIC_BEZIER:
                // case CUBIC_BEZIER:
                // case ARC_SECTION:

                case CLOSE:
                    path2d.closePath();

                    if (startX == lastX && startY == lastY)
                        break;

                    curves.add(Curve.line(lastX, lastY, startX, startY));
                    boundingBox.combine(BoundingBox.create(lastX, lastY, startX, startY));
                    lastX = startX;
                    lastY = startY;
                    break;

                default:
                    throw new UnsupportedOperationException(
                            "Unknown Segment.Type." + segment.getType().name()
                    );

            }
        }

        return new Path(
                boundingBox,
                path2d,
                Collections.unmodifiableList(curves),
                windRule
        );
    }



}
