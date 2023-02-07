package com.brona.etendue.rev1;

import com.brona.etendue.rev1.detect.EtendueDetector;
import com.brona.etendue.rev1.emit.PointEmitter;
import com.brona.etendue.rev1.geometry.Geometry;
import com.brona.etendue.rev1.geometry.path.Builder;
import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.simple.Point2;
import com.brona.etendue.rev1.math.simple.Vector2;
import com.brona.etendue.rev1.ray.Ray;
import com.brona.etendue.rev1.ray.RayTracer;
import com.brona.etendue.rev1.scene.DetectingMember;
import com.brona.etendue.rev1.scene.EmittingMember;
import com.brona.etendue.rev1.scene.InteractingMember;
import com.brona.etendue.rev1.scene.Scene;
import com.brona.etendue.rev1.ray.RaySection;
import com.brona.etendue.rev1.util.HSLColor;
import com.brona.etendue.rev1.visualization.GeometryPainter;
import com.brona.etendue.rev1.window.AppWindow;
import com.brona.etendue.rev1.window.AppCanvas;
import com.brona.etendue.rev1.geometry.path.Path;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test1 {

    private static final Geometry PARABOLA = Path.builder()
            .move(367.5f,-105f)
            .line(300.833f,-95f)
            .line(240.833f,-85f)
            .line(187.5f,-75f)
            .line(140.833f,-65f)
            .line(100.833f,-55f)
            .line(67.5f,-45f)
            .line(40.833f,-35f)
            .line(20.833f,-25f)
            .line(7.5f,-15f)
            .line(0.833f,-5f)
            .line(0.833f,5f)
            .line(7.5f,15f)
            .line(20.833f,25f)
            .line(40.833f,35f)
            .line(67.5f,45f)
            .line(100.833f,55f)
            .line(140.833f,65f)
            .line(187.5f,75f)
            .line(240.833f,85f)
            .line(300.833f,95f)
            .line(367.5f,105f)
            .build();

    private static final Geometry TRIANGLE = Path.builder()
            .move(800, -200)
            .line(500, 0)
            .line(800, 200)
            .build();


    private static Scene setupScene() {

        return new Scene()
                // Emitters
                .addMembers(
                        new PointEmitter(
                                Point2.create(100, 0),
                                4096 * 64
                        )
//                        new PointEmitter(
//                                Point2.create(300, 0),
//                                4096 * 16
//                        )

//                        new EmittingMember.Simple(
//                                IntStream.range(0, 5)
//                                        .boxed()
//                                        .map(__ -> {
//                                                double angle = Math.random() * Math.PI * 2;
//                                                Vector2 vector = Vector2.create(
//                                                        (float) Math.sin(angle),
//                                                        (float) Math.cos(angle)
//                                                );
//                                                return RaySection.endless(Point2.create(100, 0), vector);
//                                        })
//                                        .collect(Collectors.toList())
//                                ,
//                                BoundingBox.point(100, 0)
//                        )

//                        new EmittingMember.Simple(
//                                IntStream.range(-200, 200)
//                                        .boxed()
//                                        .flatMap(i -> IntStream.range(0, 1000)
//                                                .boxed()
//                                                .map(__ -> RaySection.endless(
//                                                        Point2.create(100, i),
//                                                        Vector2.create(1, (float) Math.random() / 2f).normalize()
//                                                ))
//                                        )
//                                        .collect(Collectors.toList()),
//                                BoundingBox.create(100, -200, 100, 200)
//                        )
                )
                // Interactors
                .addMembers(
                        new InteractingMember.Simple(PARABOLA)
//                        new InteractingMember.Simple(TRIANGLE)
                )
                // Detectors
                .addMembers(
                        EtendueDetector.createWithNormal(
                                Point2.create(450, 0),
                                Vector2.create(1, 0)
                        )
                );

    }


    public static void main(String[] args) {

        AppWindow window = new AppWindow("TEST rev1", 1600, 1000);
        window.open();

        Scene scene = setupScene();

        System.out.println(System.currentTimeMillis());

        List<Ray> rays = scene
                .getEmittingMembers()
                .stream()
                .flatMap(emitter -> emitter.getRays().stream())
                .map(ray -> RayTracer.trace(ray, scene))
                .collect(Collectors.toList());

        System.out.println(System.currentTimeMillis());

        GeometryPainter rayPainter = new GeometryPainter( // Todo dont require to create intermediate geometries, aka replace with ray painter
                new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL)
        ) {
            final Random random = new Random();
            @Override
            protected void drawGeometry(Graphics2D graphics, Geometry geometry) {
                graphics.setPaint(new HSLColor(random.nextInt(360), 80, 50).getRGB());
                super.drawGeometry(graphics, geometry);
            }
        };
        rayPainter.addGeometries(rays
                .stream()
                .filter(oneIn(1024))
                .map(Test1::convertRay)
                .collect(Collectors.toList())
        );


        GeometryPainter interactorPainter = new GeometryPainter(
                Color.BLACK,
                new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        );
        interactorPainter.addGeometries(scene
                .getInteractingMembers()
                .stream()
                .map(InteractingMember::getGeometry)
                .collect(Collectors.toList())
        );


        List<DetectingMember> detectors = scene.getDetectingMembers();
        Consumer<RaySection> detect = section -> detectors.forEach(detector -> detector.detect(section));
        rays.stream().flatMap(Ray::stream).forEach(detect);

        List<Point2> points = ((EtendueDetector) detectors.get(0)).graphPoints;
        doGraphingStuff(points);

        // TODO infer from objects
        interactorPainter.addGeometry(Path.builder().move(450, Integer.MIN_VALUE).line(450, Integer.MAX_VALUE).build());
        interactorPainter.addGeometry(Path.builder().move(-1000, 0).line(1000, 0).build());


        AppCanvas canvas = window.getCanvas();
        AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
        transform.translate(canvas.getWidth() / 2f, -canvas.getHeight() / 2f);
        canvas.setTransform(transform);

        canvas.addPainter(rayPainter);
        canvas.addPainter(interactorPainter);
        canvas.repaint();

    }

    private static void doGraphingStuff(Collection<Point2> points) {
        AppWindow window = new AppWindow("Graphing Stuff", 800, 800);
        window.open();

        AppCanvas canvas = window.getCanvas();
        AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
        transform.translate(canvas.getWidth() / 2f, -canvas.getHeight() / 2f);
        canvas.setTransform(transform);

        canvas.addPainter(g -> {
            g.setPaint(Color.RED);

            points.forEach(point -> {
                float x = point.getX() * canvas.getWidth() * 2 / 5;
                float y = point.getY();

                g.drawRect(
                        (int) Math.floor(x),
                        (int) Math.floor(y),
                        1,
                        1
                );
            });
        });

        canvas.addPainter(g -> {
            g.setPaint(Color.BLACK);
            g.drawLine(0, -1000, 0, 1000);
            g.drawLine(-1000, 0, 1000, 0);
        });

        canvas.repaint();
    }

    private static <T> Predicate<T> oneIn(int bound) {
        return o -> Math.floor(Math.random() * bound) == 0;
    }

    private static Geometry convertRay(Ray ray) {

        List<Point2> points = ray.getPoints();
        Vector2 direction = ray.getDirection();

        Builder builder = Path.builder();


        if (points.size() < 1)
            return builder.build();

        builder.move(points.get(0));
        points.subList(1, ray.size()).forEach(builder::line);


        if (direction == null)
            return builder.build();


        builder.line(
                points.get(points.size() - 1).plus(direction.times(10_000))
        );

        return builder.build();
    }




}

