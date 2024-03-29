package com.brona.etendue.visualization.detection.impl;

import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Texts;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.detection.GraphGridVisualizer;
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
public class SimpleGraphGridVisualizer implements GraphGridVisualizer {

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
    public @NotNull Painter visualize(float maxValue, @NotNull Transformer transformer) {

        return graphics -> {
            AffineTransform transform = transformer.createGraphTransform(maxValue);

            graphics.setStroke(stroke);
            graphics.setColor(mainColor);

            float width = transformer.getAuxGraphicsSize().getX();
            float height = transformer.getAuxGraphicsSize().getY();


//            int[] degrees = new int[]{ -90, -60, -45, -30, -20, -10, 0, 10, 20, 30, 45, 60, 90 };
            int[] degrees = new int[]{ -90, -60, -30, 0, 30, 60, 90 };
            for (int d : degrees) {
                float rads = (float) Math.PI / 180 * d;

                if (d != 0) {
                    graphics.setColor(defaultColor);
                }

                float xPos = Transformer.transformPoint(Point2.create(rads, 0), transform).getX();
                graphics.draw(new Line2D.Float(xPos, 0, xPos, height));

                graphics.setColor(mainColor);

                Texts.drawText(graphics, xPos + 5, 3, "" + d);
            }


//            for (int y = 0; y <= 10; y += 1) {
//                float posY = (y + 1) * height / 12;
//
//                if (y != 0 && y != 5) {
//                    graphics.setColor(defaultColor);
//                }
            for (int y = 0; y <= 4; y += 1) {
                float posY = (y * 2.5f + 2) * height / 14;

                if (y != 0 && y != 2) {
                    graphics.setColor(defaultColor);
                }

                graphics.draw(new Line2D.Float(0, posY, width, posY));

                graphics.setColor(mainColor);
                Point2 textPoint = Point2.create(0, posY);

//                Texts.drawText(graphics, 5, textPoint.getY() + 3, "" + (y*10));
                Texts.drawText(graphics, 5, textPoint.getY() + 3, "" + (y*25));
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
                    "N / Nₘₐₓ [ % ]"
            );


        };

    }
}
