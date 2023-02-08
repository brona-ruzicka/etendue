package com.brona.etendue.visualization;

import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class Transformer {

    @NotNull
    Vector2 graphicsSize;

    @NotNull
    Vector2 simulationSize;
    @NotNull
    Point2 minPoint;
    @NotNull
    Point2 maxPoint;

    float ratio;

    @NotNull
    AffineTransform transform;


    public Transformer(BoundingBox simulationBox, float graphicsWidth, float graphicsHeight) {

        float wantedSimulationWidth = 1.2f * simulationBox.getWidth();
        float wantedSimulationHeight = 1.2f * simulationBox.getHeight();

        float wantedSimulationMinX = simulationBox.getMinX() - 0.1f * simulationBox.getWidth();
        float wantedSimulationMinY = simulationBox.getMinY() - 0.1f * simulationBox.getHeight();

        float ratio = Math.min(graphicsWidth / wantedSimulationWidth, graphicsHeight / wantedSimulationHeight);

        float extraGraphicsSpaceX = (graphicsWidth - wantedSimulationWidth * ratio);
        float extraGraphicsSpaceY = (graphicsHeight - wantedSimulationHeight * ratio);

        float simulationMinX = wantedSimulationMinX - extraGraphicsSpaceX / ratio / 2;
        float simulationMinY = wantedSimulationMinY - extraGraphicsSpaceY / ratio / 2;

        float simulationWidth = graphicsWidth / ratio;
        float simulationHeight = graphicsHeight / ratio;


        this.graphicsSize = Vector2.create(graphicsWidth, graphicsHeight);

        this.simulationSize = Vector2.create(simulationWidth, simulationHeight);
        this.minPoint = Point2.create(simulationMinX, simulationMinY);
        this.maxPoint = this.minPoint.plus(this.simulationSize);

        this.ratio = ratio;

        this.transform = new AffineTransform();
        this.transform.preConcatenate(AffineTransform.getTranslateInstance(-this.minPoint.getX(), -this.minPoint.getY()));
        this.transform.preConcatenate(AffineTransform.getScaleInstance(this.ratio, this.ratio));

    }


    @NotNull
    public Shape transformShape(@NotNull Shape shape) {
        return new Path2D.Float(shape, this.transform);
    }

}
