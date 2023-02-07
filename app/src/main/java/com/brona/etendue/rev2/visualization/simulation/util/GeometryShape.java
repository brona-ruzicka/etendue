package com.brona.etendue.rev2.visualization.simulation.util;

import com.brona.etendue.rev2.math.curve.Curve;
import com.brona.etendue.rev2.math.geometry.Geometry;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.*;
import java.util.function.Consumer;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class GeometryShape extends Path2D.Float {

    @NotNull
    protected final Geometry geometry;

    public GeometryShape(Geometry geometry) {

        this.geometry = geometry;

        float lastPointX = java.lang.Float.NaN;
        float lastPointY = java.lang.Float.NaN;

        for (Curve curve : geometry.getCurves()) {

            float[] arguments = curve.getArguments();

            float curveStartX;
            float curveStartY;

            float curveEndX;
            float curveEndY;

            Consumer<Path2D.Float> add;

            switch (curve.getType()) {
                case LINE:
                    curveStartX = arguments[0];
                    curveStartY = arguments[1];

                    curveEndX = arguments[2];
                    curveEndY = arguments[3];

                    add = path -> path.lineTo(arguments[2], arguments[3]);
                    break;

                // case QUADRATIC_BEZIER:
                // case CUBIC_BEZIER:
                // case ARC_SECTION:

                default:
                    throw new IllegalStateException("Unknown Curve.Type." + curve.getType().name());

            }

            if (curveStartX != lastPointX || curveStartY != lastPointY)
                this.moveTo(curveStartX, curveStartY);

            add.accept(this);
            lastPointX = curveEndX;
            lastPointY = curveEndY;
        }


    }

}
