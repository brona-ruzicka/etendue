package com.brona.etendue;

import com.brona.etendue.computation.detection.DistributionGraphComputer;
import com.brona.etendue.computation.detection.EtendueComputer;
import com.brona.etendue.computation.detection.RayDetector;
import com.brona.etendue.computation.detection.impl.SimpleEtendueComputer;
import com.brona.etendue.computation.detection.impl.SimpleDistributionGraphComputer;
import com.brona.etendue.computation.detection.impl.SimpleRayDetector;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.computation.simulation.impl.MultiThreadSimulator;
import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.scheduling.CancelableScheduler;
import com.brona.etendue.scheduling.RerunHandle;
import com.brona.etendue.scheduling.Schedulers;
import com.brona.etendue.visualization.Texts;
import com.brona.etendue.visualization.common.ImageCreator;
import com.brona.etendue.visualization.common.ImagePainter;
import com.brona.etendue.visualization.common.ImageRegenerator;
import com.brona.etendue.visualization.common.impl.SimpleImageCreator;
import com.brona.etendue.visualization.common.impl.SimpleImagePainter;
import com.brona.etendue.visualization.common.impl.SimpleImageRegenerator;
import com.brona.etendue.visualization.detection.EtendueGridVisualizer;
import com.brona.etendue.visualization.detection.GraphGridVisualizer;
import com.brona.etendue.visualization.detection.GraphVisualizer;
import com.brona.etendue.visualization.detection.EtendueVisualizer;
import com.brona.etendue.visualization.detection.impl.SimpleEtendueGridVisualizer;
import com.brona.etendue.visualization.detection.impl.SimpleGraphGridVisualizer;
import com.brona.etendue.visualization.detection.impl.SimpleGraphVisualizer;
import com.brona.etendue.visualization.detection.impl.DensityBasedEtendueVisualizer;
import com.brona.etendue.visualization.simulation.EmitterVisualizer;
import com.brona.etendue.visualization.simulation.InteractorVisualizer;
import com.brona.etendue.visualization.simulation.RayVisualizer;
import com.brona.etendue.visualization.simulation.SimulationGridVisualizer;
import com.brona.etendue.visualization.simulation.impl.ColoredInteractorVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleEmitterVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleRayVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleSimulationGridVisualizer;
import com.brona.etendue.window.Window;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.window.WindowListener;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class EtendueApp implements Runnable {


    /** Actually starts the application */
    public static void run(@NotNull Scene scene) {
        new EtendueApp(scene).run();
    }

    /** Actually starts the application */
    public static void run(@NotNull Scene scene, int width, int height) {
        new EtendueApp(scene, width, height).run();
    }


    @NotNull
    public static final Color DARK_GREEN = new Color(0, 200, 0);

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 700;


    public EtendueApp(@NotNull Scene scene) {
        this(scene, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public EtendueApp(@NotNull Scene scene, int width, int height) {
        this.scene = scene;
        this.transformer = new Transformer(scene.getBoundingBox(), width, height);
    }



    protected final Scene scene;
    protected final Transformer transformer;

    protected ImageRegenerator simulationImage, etendueImage, graphImage;
    protected Window simulationWindow, etendueWindow, graphWindow;


    protected float xCoordinate = 0;

    protected Collection<Ray> rays;
    protected Collection<Section> sections;

    protected RerunHandle<?> detectionTask, etendueTask, graphTask;


    @Override
    public void run() {

        // Utility classes
        ImageCreator creator = new SimpleImageCreator();
        ImagePainter painter = new SimpleImagePainter();


        // Do the least to show the current scene
        simulationImage = new SimpleImageRegenerator(creator, painter, transformer.getMainGraphicsSize());
        EmitterVisualizer emitterVisualizer = new SimpleEmitterVisualizer();
        InteractorVisualizer interactorVisualizer = new ColoredInteractorVisualizer();
        SimulationGridVisualizer simulationGridVisualizer = new SimpleSimulationGridVisualizer();

        // Create scene image
        simulationImage.regenerate(
                emitterVisualizer.visualize(scene, transformer),
                interactorVisualizer.visualize(scene, transformer),
                simulationGridVisualizer.visualize(transformer)
        );

        // Open main window
        simulationWindow = new Window("Scene preview window", transformer.getMainGraphicsSize());
        simulationWindow.addPainter(simulationImage.toPainter());
        simulationWindow.open();


        // Simulate the scene
        RayTracer tracer = new SimpleTracer(new LineOnlyIntersector());
//        RaySimulator simulator = new SingleThreadSimulator(tracer);
        RaySimulator simulator = new MultiThreadSimulator(tracer, 8);
        rays = simulator.simulate(scene);

        new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                Thread.sleep(10000);
            }
        }.run();

        // Update simulation image
        RayVisualizer rayVisualizer = new SimpleRayVisualizer();
        simulationImage.regenerate(
                rayVisualizer.visualize(rays, transformer),
                emitterVisualizer.visualize(scene,transformer),
                interactorVisualizer.visualize(scene, transformer),
                simulationGridVisualizer.visualize(transformer)
        );


        // Create etendue window variables
        etendueImage = new SimpleImageRegenerator(creator, painter, transformer.getAuxGraphicsSize());
        etendueWindow = new Window("Étendue window", transformer.getAuxGraphicsSize());
        etendueWindow.addPainter(etendueImage.toPainter());

        // Create graph window variables
        graphImage = new SimpleImageRegenerator(creator, painter, transformer.getAuxGraphicsSize());
        graphWindow = new Window("Graph window", transformer.getAuxGraphicsSize());
        graphWindow.addPainter(graphImage.toPainter());


        // Setup scheduling
        CancelableScheduler mainScheduler = Schedulers.wrap(Executors.newFixedThreadPool(3));
        detectionTask = Schedulers.rerunnableAutoCanceling(mainScheduler).execute(new DetectionTask(
                new SimpleRayDetector()
        ));
        etendueTask = Schedulers.rerunnableAutoCanceling(mainScheduler).execute(new EtendueTask(
                new SimpleEtendueComputer(),
                new DensityBasedEtendueVisualizer(),
                new SimpleEtendueGridVisualizer()
        ));
        graphTask = Schedulers.rerunnableAutoCanceling(mainScheduler).execute(new GraphTask(
                new SimpleDistributionGraphComputer(),
                new SimpleGraphVisualizer(),
                new SimpleGraphGridVisualizer()
        ));


        // Run detection for the first time
        detectionTask.rerun();


        // Close so that the window can stay on top
        simulationWindow.close();

        // Setup x coordinate selection
        WindowListener listener = new WindowListener(point -> {
            xCoordinate = point.getX() / transformer.getMainRatio() + transformer.getMinPoint().getX();
            simulationWindow.repaint();
            detectionTask.rerun();
        });
        simulationWindow.addMouseListener(listener);
        simulationWindow.addMouseMotionListener(listener);

        simulationWindow.addPainter(graphics -> {
            graphics.setColor(DARK_GREEN);
            Shape line = new Line2D.Float(xCoordinate, transformer.getMinPoint().getY(), xCoordinate, transformer.getMaxPoint().getY());
            graphics.draw(Transformer.transformShape(line, transformer.createSimulationTransform()));
        });
        simulationWindow.setTitle("Simulation window");


        // Open all windows
        simulationWindow.open();
        graphWindow.open();
        etendueWindow.open();

        // Repaint the simulation window
        simulationWindow.repaint();
    }

    @AllArgsConstructor
    protected final class DetectionTask implements Runnable {

        RayDetector rayDetector;

        @Override
        public void run() {
            sections = rayDetector.detect(rays, xCoordinate);

            etendueTask.rerun();
            graphTask.rerun();
        }

    }

    @AllArgsConstructor
    protected final class EtendueTask implements Runnable {

        EtendueComputer etendueComputer;
        EtendueVisualizer etendueVisualizer;
        EtendueGridVisualizer etendueGridVisualizer;

        @Override
        public void run() {
            EtendueResult result = etendueComputer.compute(sections, transformer.getSimulationSize().getY());
            etendueImage.regenerate(
                    etendueVisualizer.visualize(result, transformer),
                    etendueGridVisualizer.visualize(transformer),
                    graphics -> {
                        Texts.drawText(
                                graphics,
                                transformer.getAuxGraphicsSize().getX() - 130,
                                transformer.getAuxGraphicsSize().getY() - 18,
                                "Étendue: " + Math.round(result.getArea() * 100) / 100f + " m·rad"
                        );

                        Texts.drawText(
                                graphics,
                                transformer.getAuxGraphicsSize().getX() - 130,
                                transformer.getAuxGraphicsSize().getY() - 38,
                                "Average: " + Math.round(result.getAverage() * 100) / 100f + " ray/px"
                        );
                    }
            );

            etendueWindow.repaint();
        }

    }

    @AllArgsConstructor
    protected final class GraphTask implements Runnable {

        DistributionGraphComputer graphComputer;
        GraphVisualizer graphVisualizer;
        GraphGridVisualizer graphGridVisualizer;


        @Override
        public void run() {
           GraphResult result = graphComputer.compute(sections);
            graphImage.regenerate(
                    graphVisualizer.visualize(result, transformer),
                    graphGridVisualizer.visualize(result.getMaxValue(), transformer),
                    graphics -> {
                        Texts.drawText(
                                graphics,
                                transformer.getAuxGraphicsSize().getX() - 130,
                                transformer.getAuxGraphicsSize().getY() - 18,
                                "Total: " + Math.round(result.getTotal()) + " rays"
                        );

                        Texts.drawText(
                                graphics,
                                transformer.getAuxGraphicsSize().getX() - 130,
                                transformer.getAuxGraphicsSize().getY() - 38,
                                "Maximum: " + Math.round(result.getMaxValue()) + " rays"
                        );
                    }
            );

            graphWindow.repaint();
        }

    }

}

