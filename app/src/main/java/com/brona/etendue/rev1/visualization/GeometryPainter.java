package com.brona.etendue.rev1.visualization;

import com.brona.etendue.rev1.window.AppCanvas;
import com.brona.etendue.rev1.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// TODO temporary
public class GeometryPainter implements AppCanvas.Painter {

    public static final Paint DEFAULT_PAINT = Color.BLACK;
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1f);

    protected final List<Geometry> geometries = new ArrayList<>();
    protected final Paint paint;
    protected final Stroke stroke;


    public GeometryPainter() {
        this(DEFAULT_PAINT, DEFAULT_STROKE);
    }

    public GeometryPainter(Paint paint) {
        this(paint, DEFAULT_STROKE);
    }

    public GeometryPainter(Stroke stroke) {
        this(DEFAULT_PAINT, stroke);
    }

    public GeometryPainter(Paint paint, Stroke stroke) {
        this.paint = paint;
        this.stroke = stroke;
    }


    @NotNull
    public GeometryPainter addGeometry(@NotNull Geometry geometry) {
        geometries.add(geometry);
        return this;
    }

    @NotNull
    public GeometryPainter addGeometries(@NotNull Collection<? extends Geometry> geometries) {
        this.geometries.addAll(geometries);
        return this;
    }

    @NotNull
    public GeometryPainter addGeometries(@NotNull Geometry... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
        return this;
    }

    @Override
    public void paint(@NotNull Graphics2D graphics) {
        graphics.setPaint(paint);
        graphics.setStroke(stroke);

        geometries.forEach(geometry -> drawGeometry(graphics, geometry));
    }

    protected void drawGeometry(Graphics2D graphics, Geometry geometry) {
        graphics.draw(geometry.getShape());
    }


}
