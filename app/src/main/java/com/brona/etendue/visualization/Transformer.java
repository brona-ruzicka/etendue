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
import java.awt.geom.Point2D;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class Transformer {

    @NotNull
    Vector2 mainGraphicsSize;

    @NotNull
    Vector2 auxGraphicsSize;

    @NotNull
    Vector2 simulationSize;
    @NotNull
    Point2 minPoint;
    @NotNull
    Point2 maxPoint;

    float mainRatio;
    float auxRatio;


    public Transformer(@NotNull BoundingBox simulationBox, @NotNull Vector2 mainGraphicsSize) {
        this(simulationBox, mainGraphicsSize.getX(), mainGraphicsSize.getY());
    }

    public Transformer(@NotNull BoundingBox simulationBox, @NotNull Vector2 mainGraphicsSize, @NotNull Vector2 auxGraphicsSize) {
        this(simulationBox, mainGraphicsSize.getX(), mainGraphicsSize.getY(), auxGraphicsSize.getX(), auxGraphicsSize.getY());
    }

    public Transformer(@NotNull BoundingBox simulationBox, float graphicsWidth, float graphicsHeight) {
        //noinspection SuspiciousNameCombination
        this(simulationBox, graphicsWidth, graphicsHeight, graphicsHeight, graphicsHeight);
    }

    public Transformer(@NotNull BoundingBox simulationBox, float mainGraphicsWidth, float mainGraphicsHeight, float auxGraphicsWidth, float auxGraphicsHeight) {

        float wantedSimulationWidth = 2f * simulationBox.getWidth();
        float wantedSimulationHeight = 2f * simulationBox.getHeight();

        float wantedSimulationMinX = simulationBox.getMinX() - 0.5f * simulationBox.getWidth();
        float wantedSimulationMinY = simulationBox.getMinY() - 0.5f * simulationBox.getHeight();

        float ratio = Math.min(mainGraphicsWidth / wantedSimulationWidth, mainGraphicsHeight / wantedSimulationHeight);

        float extraGraphicsSpaceX = (mainGraphicsWidth - wantedSimulationWidth * ratio);
        float extraGraphicsSpaceY = (mainGraphicsHeight - wantedSimulationHeight * ratio);

        float simulationMinX = wantedSimulationMinX - extraGraphicsSpaceX / ratio / 2;
        float simulationMinY = wantedSimulationMinY - extraGraphicsSpaceY / ratio / 2;

        float simulationWidth = mainGraphicsWidth / ratio;
        float simulationHeight = mainGraphicsHeight / ratio;


        this.mainGraphicsSize = Vector2.create(mainGraphicsWidth, mainGraphicsHeight);

        this.simulationSize = Vector2.create(simulationWidth, simulationHeight);
        this.minPoint = Point2.create(simulationMinX, simulationMinY);
        this.maxPoint = this.minPoint.plus(this.simulationSize);

        this.mainRatio = ratio;


        this.auxGraphicsSize = Vector2.create(auxGraphicsWidth, auxGraphicsHeight);

        this.auxRatio = auxGraphicsHeight / simulationHeight;

    }

    @NotNull
    public AffineTransform createSimulationTransform() {
        AffineTransform transform = new AffineTransform();
        transform.preConcatenate(AffineTransform.getTranslateInstance(-this.minPoint.getX(), -this.minPoint.getY()));
        transform.preConcatenate(AffineTransform.getScaleInstance(this.mainRatio, this.mainRatio));
        return transform;
    }

    @NotNull
    public AffineTransform createEtendueTransform() {
        AffineTransform transform = new AffineTransform();
        transform.preConcatenate(AffineTransform.getTranslateInstance(1.25f, -this.minPoint.getY()));
        transform.preConcatenate(AffineTransform.getScaleInstance(this.auxGraphicsSize.getX() / 2.5f, this.auxRatio));
        return transform;
    }

    @NotNull
    public AffineTransform createGraphTransform(float maxValue) {
        AffineTransform transform = new AffineTransform();
        transform.preConcatenate(AffineTransform.getTranslateInstance(Math.PI * 0.6, 0.1 * maxValue));
        transform.preConcatenate(AffineTransform.getScaleInstance(this.auxGraphicsSize.getX() / (Math.PI * 1.2f), this.auxGraphicsSize.getY()  / (1.2 * maxValue)));
        return transform;
    }

    @NotNull
    public static Shape transformShape(@NotNull Shape shape, @NotNull AffineTransform transform) {
        return new Path2D.Float(shape, transform);
    }

    @NotNull
    public static Point2 transformPoint(@NotNull Point2 point, @NotNull AffineTransform transform) {
        Point2D transformed = transform.transform(new Point2D.Float(point.getX(), point.getY()), null);
        return Point2.create((float) transformed.getX(), (float) transformed.getY());
    }

}
