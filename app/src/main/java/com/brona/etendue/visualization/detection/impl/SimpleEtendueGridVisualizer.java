package com.brona.etendue.visualization.detection.impl;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Texts;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.detection.EtendueGridVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.stream.DoubleStream;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleEtendueGridVisualizer implements EtendueGridVisualizer {

    @NotNull
    public static final Color DEFAULT_COLOR = new Color(0f,0f,0f,0.1f);

    @NotNull
    public static final Color MAIN_COLOR = Color.BLACK;
    @NotNull
    public static final Stroke DEFAULT_STROKE = new BasicStroke();


    @NotNull Color defaultColor = DEFAULT_COLOR;
    @NotNull Color mainColor = MAIN_COLOR;
    @NotNull Stroke stroke = DEFAULT_STROKE;

    @Override
    public @NotNull Painter visualize(@NotNull Transformer transformer) {

        return graphics -> {
            AffineTransform transform = transformer.createEtendueTransform();
            int height = (int) transformer.getAuxGraphicsSize().getY();

            graphics.setStroke(stroke);
            graphics.setColor(mainColor);

//            int[] degrees = new int[]{ -90, -60, -45, -30, -20, -10, 0, 10, 20, 30, 45, 60, 90 };
            int[] degrees = new int[]{ -90, -45, -20, 0, 20, 45, 90 };
            for (int d : degrees) {
                float sin = (float) Math.sin(Math.PI / 180 * d);

                if (d != 0) {
                    graphics.setColor(defaultColor);
                }

                float xPos = Transformer.transformPoint(Point2.create(sin, 0), transform).getX();
                graphics.draw(new Line2D.Float(xPos, 0, xPos, height));

                graphics.setColor(mainColor);

                Texts.drawText(graphics, xPos + 5, 3, "" + d);
            }


            float ordY = (float) DoubleStream.of(
//                    Math.pow(2, Math.floor(Math.log(transformer.getSimulationSize().getY() / transformer.getAuxGraphicsSize().getY() * 40) / Math.log(2) )),
                    Math.pow(5, Math.floor(Math.log(transformer.getSimulationSize().getY() / transformer.getAuxGraphicsSize().getY() * 40) / Math.log(5) + 1)),
                    Math.pow(10, Math.floor(Math.log(transformer.getSimulationSize().getY() / transformer.getAuxGraphicsSize().getY() * 40) / Math.log(10) + 1))
            ).min().orElse(0);
            int minY = Math.round(transformer.getMinPoint().getY() / ordY);
            int maxY = Math.round(transformer.getMaxPoint().getY() / ordY);

            for (int y = minY; y <= maxY; y += 1) {
                float posY = y * ordY;

                if (y != 0) {
                    graphics.setColor(defaultColor);
                }

                Shape line = new Line2D.Float(transformer.getMinPoint().getX(), y * ordY, transformer.getMaxPoint().getX(), y * ordY);
                graphics.draw(Transformer.transformShape(line, transform));

                graphics.setColor(mainColor);
                Point2 textPoint = Transformer.transformPoint(Point2.create(0, posY), transform);

                if (textPoint.getY() < 20  || textPoint.getY() + 40 > transformer.getAuxGraphicsSize().getY())
                    continue;
//                Texts.drawText(graphics, 5, textPoint.getY() + 3, "" + posY);
                Texts.drawText(graphics, 5, textPoint.getY() + 3, "" + Math.round(posY));
            }

            graphics.setColor(mainColor);
            Texts.drawText(
                    graphics,
                    transformer.getAuxGraphicsSize().getX() - 40,
                    23,
                    "α [ ° ]"
            );
            Texts.drawText(
                    graphics,
                    5,
                    transformer.getAuxGraphicsSize().getY() - 18,
                    "y [ m ]"
            );

        };
    }
}
