package com.brona.etendue.visualization.simulation.impl;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Texts;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.simulation.SimulationGridVisualizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleSimulationGridVisualizer implements SimulationGridVisualizer {


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
            AffineTransform transform = transformer.createSimulationTransform();

            graphics.setStroke(stroke);
            graphics.setColor(mainColor);

            float ordX = (float) Math.pow(10, Math.floor(Math.log10(transformer.getSimulationSize().getX() / transformer.getMainGraphicsSize().getX() * 40) + 1));
            int minX = Math.round(transformer.getMinPoint().getX() / ordX);
            int maxX = Math.round(transformer.getMaxPoint().getX() / ordX);

            for (int x = minX; x <= maxX; x += 1) {
                float posX = x * ordX;

                if (x != 0) {
                    graphics.setColor(defaultColor);
                }

                Shape line = new Line2D.Float(posX, transformer.getMinPoint().getY(), posX, transformer.getMaxPoint().getY());
                graphics.draw(Transformer.transformShape(line, transform));

                graphics.setColor(mainColor);

                if (x == minX)
                    continue;

                Point2 textPoint = Transformer.transformPoint(Point2.create(posX, 0), transform);

                if (textPoint.getX() < 20 || textPoint.getY() + 60 > transformer.getMainGraphicsSize().getX())
                    continue;
                Texts.drawText(graphics, textPoint.getX() + 5, 3, "" + posX);
            }


            float ordY = (float) Math.pow(10, Math.floor(Math.log10(transformer.getSimulationSize().getY() / transformer.getMainGraphicsSize().getX() * 40) + 1));
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

                if (textPoint.getY() < 20  || textPoint.getY() + 40 > transformer.getMainGraphicsSize().getY())
                    continue;
                Texts.drawText(graphics, 5, textPoint.getY() + 3, "" + posY);
            }

        };

    }
}
