package com.brona.etendue.rev2;

import com.brona.etendue.rev2.computation.detection.RayDetector;
import com.brona.etendue.rev2.math.tuple.Tuple2;
import com.brona.etendue.rev2.math.tuple.Vector2;
import com.brona.etendue.rev2.visualization.common.ImageCreator;
import com.brona.etendue.rev2.visualization.common.ImagePainter;
import com.brona.etendue.rev2.visualization.common.impl.SimpleImageCreator;
import com.brona.etendue.rev2.visualization.common.impl.SimpleImagePainter;
import com.brona.etendue.rev2.visualization.simulation.impl.ColoredSceneVisualizer;
import com.brona.etendue.rev2.visualization.simulation.impl.SimpleRayVisualizer;
import com.brona.etendue.rev2.window.Window;
import com.brona.etendue.rev2.computation.detection.impl.SimpleDetector;
import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.visualization.Transformer;
import com.brona.etendue.rev2.computation.simulation.RaySimulator;
import com.brona.etendue.rev2.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.rev2.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.rev2.computation.simulation.impl.SingleThreadSimulator;
import com.brona.etendue.rev2.data.scene.Scene;
import com.brona.etendue.rev2.data.simulation.Ray;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EtendueApp implements Runnable {

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 700;

    public static void run(@NotNull Scene scene) {
        new EtendueApp(scene).run();
    }

    public static void run(@NotNull Scene scene, int width, int height) {
        new EtendueApp(scene, width, height).run();
    }


    @NotNull
    protected final Scene scene;
    protected final int width;
    protected final int height;


    public EtendueApp(@NotNull Scene scene) {
        this(scene, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public EtendueApp(@NotNull Scene scene, int width, int height) {
        this.scene = scene;
        this.width = width;
        this.height = height;
    }



    protected Window simulationWindow, etendueWindow, graphWindow;
    protected Transformer transformer;
    protected ImageCreator creator;
    protected ImagePainter painter;
    protected Collection<Ray> rays;
    protected float detectionX = 0;

    protected final Object etendueImageLock = new Object();
    protected BufferedImage etendueImage, etendueDrawingImage;
    protected final Object graphImageLock = new Object();
    protected BufferedImage graphImage, graphDrawingImage;

    protected long lastStart = 0;
    protected Executor executor;

    public void startDetection(long start) {
        if (lastStart != start) return;

        RayDetector detector = new SimpleDetector();
        Collection<Section> sections = detector.detect(rays, detectionX);

        List<Tuple2> detectorData = sections.stream()
                .map(section -> Tuple2.create(
                        (float) Math.atan2(section.getDirection().getY(), section.getDirection().getX()),
                        section.getPoint().getY()
                ))
                .collect(Collectors.toList());

        if (lastStart != start) return;

        float smallestStepX = 2.5f / height / 10;
        float smallestStepY = transformer.getSimulationSize().getY() / height / 10;

        Set<Main.IntTuple2> set = detectorData.stream()
                .map(tuple -> Tuple2.create((float) Math.sin(tuple.getX()), tuple.getY()))
                .map(tuple -> new Main.IntTuple2( Math.round(tuple.getX() / smallestStepX), Math.round(tuple.getY() / smallestStepY) ))
                .collect(Collectors.toSet());

        if (lastStart != start) return;
        synchronized (etendueImageLock) {
            if (lastStart != start) return;

            painter.paint(etendueDrawingImage, graphics -> {
                if (lastStart != start) return;

                graphics.setBackground(Color.WHITE);
                graphics.clearRect(0,0,height,height);

                AffineTransform transform = new AffineTransform();
                transform.preConcatenate(AffineTransform.getTranslateInstance(1.25f, -transformer.getMinPoint().getY()));
                transform.preConcatenate(AffineTransform.getScaleInstance(height / 2.5f, transformer.getRatio()));

                graphics.setColor(Color.RED);

                set.forEach(intTuple2 -> {
                    if (lastStart != start) return;
                    Point2D point = transform.transform(new Point2D.Float(intTuple2.getX() * smallestStepX, intTuple2.getY() * smallestStepY), null);
                    graphics.fillOval((int) Math.round(point.getX() - 1.5), (int) Math.round(point.getY() - 1.5), 3, 3);
                });

                graphics.setColor(Color.BLACK);
                graphics.draw(new Path2D.Float(new Line2D.Float(-1.25f, 0, 1.25f, 0), transform));
                graphics.draw(new Path2D.Float(new Line2D.Float(0, transformer.getMinPoint().getY(), 0, transformer.getMaxPoint().getY()), transform));


                graphics.transform(AffineTransform.getTranslateInstance(0, height - 1));
                graphics.transform(AffineTransform.getScaleInstance(1, -1));

                graphics.drawString("" + set.size(), 10, 10);
            });

            if (lastStart != start) return;

            BufferedImage storage = etendueImage;
            etendueImage = etendueDrawingImage;
            etendueDrawingImage = storage;
            etendueWindow.repaint();
        }


        if (lastStart != start) return;

        float smallestAngleStep = (float) Math.PI / 400;

        Map<Integer, Long> values = detectorData.stream()
                .collect(Collectors.groupingBy(
                        tuple -> Math.round(tuple.getX() / smallestAngleStep),
                        Collectors.counting()
                ));

        if (lastStart != start) return;

        long maxValue = Math.max(values.values().stream().max(Long::compareTo).orElse(0L), 1L);

        if (lastStart != start) return;
        synchronized (graphImageLock) {
            if (lastStart != start) return;

            painter.paint(graphDrawingImage, graphics -> {
                if (lastStart != start) return;

                graphics.setBackground(Color.WHITE);
                graphics.clearRect(0,0,height,height);

                AffineTransform transform = new AffineTransform();
                transform.preConcatenate(AffineTransform.getTranslateInstance(Math.PI * 1.25, 0.1 * maxValue));
                transform.preConcatenate(AffineTransform.getScaleInstance(height / (Math.PI * 2.5f), height / (1.2 * maxValue)));

                graphics.setColor(Color.RED);

                for (int i = -400; i <= 400; i++) {
                    if (lastStart != start) return;

                    long value = values.getOrDefault(i, 0L);

                    graphics.fill(new Path2D.Float(new Rectangle2D.Float((i-0.6f)*smallestAngleStep, 0, 1.2f*smallestAngleStep, value), transform));
                }

                graphics.setColor(Color.BLACK);
                graphics.draw(new Path2D.Float(new Line2D.Float(-4, 0, 4, 0), transform));
                graphics.draw(new Path2D.Float(new Line2D.Float(0, -0.1f * maxValue, 0, 1.1f * maxValue), transform));

            });

            if (lastStart != start) return;

            BufferedImage storage = graphImage;
            graphImage = graphDrawingImage;
            graphDrawingImage = storage;
            graphWindow.repaint();
        }

    }

    @SneakyThrows
    @Override
    public void run() {

        // Simulation
        RaySimulator simulator = new SingleThreadSimulator(new SimpleTracer(new LineOnlyIntersector()));
        rays = simulator.simulate(scene);


        transformer = new Transformer(scene.getBoundingBox(), width, height);
        creator = new SimpleImageCreator();
        painter = new SimpleImagePainter();


        // Simulation visualization

        BufferedImage image = creator.create(transformer.getGraphicsSize());
        painter.paint(image,
                new SimpleRayVisualizer().visualize(rays.stream().filter(__ -> Math.random() < 0.0001f).collect(Collectors.toList()), transformer),
                new ColoredSceneVisualizer().visualize(scene, transformer)
        );

        etendueImage = creator.create(Vector2.create(height, height));
        etendueDrawingImage = creator.create(Vector2.create(height, height));

        graphImage = creator.create(Vector2.create(height, height));
        graphDrawingImage = creator.create(Vector2.create(height, height));


        // This is shit
        simulationWindow = new Window("Simulation window", width, height);
        simulationWindow.addPainter(graphics -> {
            graphics.drawImage(image,null, null);
            graphics.setColor(new Color(0, 200, 0));
            graphics.draw(transformer.transformShape(new Line2D.Float(detectionX, transformer.getMinPoint().getY(), detectionX, transformer.getMaxPoint().getY())));
        });
        simulationWindow.open();

        etendueWindow = new Window("Etendue window", height, height);
        etendueWindow.addPainter(graphics -> {
            if (etendueImage == null)
                return;
            graphics.drawImage(etendueImage,null, null);
        });
        etendueWindow.open();

        graphWindow = new Window("Graph window", height, height);
        graphWindow.addPainter(graphics -> {
            if (graphImage == null)
                return;
            graphics.drawImage(graphImage,null, null);
        });
        graphWindow.open();


        executor = Executors.newSingleThreadExecutor();
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pointChanged(Point2.create(e.getX(), e.getY()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                pointChanged(Point2.create(e.getX(), e.getY()));
            }

            public void pointChanged(Point2 point) {
                detectionX = point.getX() / transformer.getRatio() + transformer.getMinPoint().getX();
                simulationWindow.repaint();

                lastStart = System.nanoTime();
                executor.execute(() -> startDetection(lastStart));
            }

        };

        simulationWindow.addMouseListener(listener);
        simulationWindow.addMouseMotionListener(listener);


        lastStart = System.nanoTime();
        executor.execute(() -> startDetection(lastStart));
    }

}

