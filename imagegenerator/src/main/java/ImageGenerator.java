import com.brona.etendue.EtendueApp;
import com.brona.etendue.computation.detection.EtendueComputer;
import com.brona.etendue.computation.detection.impl.KBasedEtendueComputer;
import com.brona.etendue.computation.detection.impl.SimpleDistributionGraphComputer;
import com.brona.etendue.computation.detection.impl.SimpleRayDetector;
import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.computation.simulation.impl.SingleThreadSimulator;
import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.user.*;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.common.ImageCreator;
import com.brona.etendue.visualization.common.ImagePainter;
import com.brona.etendue.visualization.common.impl.SimpleImageCreator;
import com.brona.etendue.visualization.common.impl.SimpleImagePainter;
import com.brona.etendue.visualization.detection.impl.*;
import com.brona.etendue.visualization.simulation.impl.ColoredInteractorVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleEmitterVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleRayVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleSimulationGridVisualizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.brona.etendue.user.Tuples.point;
import static com.brona.etendue.user.Tuples.vector;

public class ImageGenerator {

    static float k = 1.2f;
    static Scene createBaseScene(Emitter... members) {
        return Scene.create(
//                Interactors.reflecting(Geometries.formula(
//                        true, -25, 0,
//                        -15, 15, 0.05f, x -> x*x / 10
//                )),
//                Interactors.reflecting(Geometries.formula(
//                        true, -25, 0,
//                        -20, 20, 0.f, k -> k*k / 10
//                )),
//                Interactors.reflecting(Geometries.line(0, 1, 5,k)),
//                Interactors.reflecting(Geometries.line(0, -1, 5,-k))
//                Interactors.absorbing(Geometries.line(-1f, -1f, 1f, -1f))
//                Utils.extendViewBox(0, 0, 120, 0)
//                Interactors.reflecting(Geometries.line(-1, -1, 4, -3.1f))
//                Interactors.absorbing(Geometries.line(-1, -1, 4, -3.1f))
        ).addMembers(members);
    }

    static Emitter paralelEmitter(float x, float y, float height, int count) {

        float miny = y - height /2;
        float maxy = y + height /2;
        float step = height / count;

        List<Section> sections = IntStream.range(0, count + 1).boxed()
                .map(i -> new Section(Tuples.point(x, miny + i*step), vector(1, 0)))
                .collect(Collectors.toList());

        return new Emitter.Simple(
//                BoundingBox.create(x, miny, x, maxy),
                BoundingBox.empty(),
                sections
        );
    }


    public static void main(String[] args) {

        float x = 0f;

//        Color colorR = Color.RED;
//        Color colorG = new Color(0xA748DE);
//        Color colorB = new Color(0x0080FF);
//        Color colorO = Color.ORANGE;

        Color colorR = Color.RED;
//        Color colorG = new Color(0xA748DE);
//        Color colorB = new Color(0x0080FF);
//        Color colorO = Color.ORANGE;

//        Scene sceneR = createBaseScene(
//                Emitters.single(point(-1,-3), vector(1,0.4f))
////                new Emitter.Simple(
////                        BoundingBox.point(p(0, 0)),
////                        PointEmitter.generateRays(p(0, 0), 12)
////                                .stream()
////                                .filter(section -> section.getDirection().getX() > 0.2f)
////                                .collect(Collectors.toList())
////                ),
////                new Emitter.Simple(
////                        BoundingBox.point(p(0, -0.5f)),
////                        PointEmitter.generateRays(p(0, -0.5f), 14)
////                                .stream()
////                                .filter(section -> section.getDirection().getX() > 0.2f)
////                                .collect(Collectors.toList())
////                ),
////                new Emitter.Simple(
////                        BoundingBox.point(p(0, 0.5f)),
////                        PointEmitter.generateRays(p(0, 0.5f), 14)
////                                .stream()
////                                .filter(section -> section.getDirection().getX() > 0.2f)
////                                .collect(Collectors.toList())
////                )
////                Emitters.line(Tuples.point(-1, 0), 2, 2000000)
////                Emitters.point(p(-1.5f, 0.5f), 0)
////                Emitters.line(0.05f, 0, 1.8f, 40000)
////                paralelEmitter(0, 0, 1.9f, 5),
////                new Emitter.Simple(BoundingBox.create(0, -1, 0, 1), Collections.emptyList())
//        );
//        Scene sceneG = createBaseScene(
////                new Emitter.Simple(
////                        BoundingBox.point(p(0, 0)),
////                        PointEmitter.generateRays(p(0, 0), 2000)
////                                .stream()
////                                .filter(section -> section.getDirection().getX() > 0.8f)
////                                .collect(Collectors.toList())
////                )
//                Emitters.single(point(-1.5f,3), vector(1,-0.8f))
////                Emitters.single(p(-1.5f, 0.5f), v(1, -0.75f)),
////                Emitters.single(p(-1.5f, 0.5f), v(1, -1.5f)),
////                Emitters.single(p(-1.5f, 0.5f), v(1, -10f))
////                paralelEmitter(0, 0, 2, 40000)
////                Emitters.line(0, 0, 2f, 40000)
//        );
//        Scene sceneB = createBaseScene(
//                Emitters.single(point(-2,-1), vector(1,-0.8f))
//        );
//        Scene sceneO = createBaseScene(
//                Emitters.single(point(-1,-0.5f), vector(1,0.2f))
//        );

//        // Simple Emitter
//        Scene scene = Scene.create(
//                Emitters.single(point(1, 1), vector(1, -1)),
//                Utils.extendViewBox(point(0, -1), point(2, 1))
//        );

//        // Point Emitter
//        Scene scene = Scene.create(
//                Emitters.point(point(1, 1), 10),
//                Utils.extendViewBox(point(0, -1), point(2, 1))
//        );

        // Line Emitter
        Scene scene = Scene.create(
                Interactors.reflecting(
                        Geometries.line(point(0, 1), point(5, 2))
                ),
                Interactors.reflecting(
                        Geometries.line(point(0, -1), point(5, -2))
                ),
                Emitters.line(point(0, 0), 2, 10)
        );


        Scene sceneR = scene;
        BoundingBox box = BoundingBox.combine(
                sceneR.getBoundingBox(),
//                sceneG.getBoundingBox(),
//                sceneB.getBoundingBox(),
//                sceneO.getBoundingBox(),
//                BoundingBox.create(-1, -3,4, 3),
                BoundingBox.empty()
        );

        Transformer transformer = new Transformer(box, 300, 300, 300, 300);


        RayTracer tracer = new SimpleTracer(new LineOnlyIntersector());
        RaySimulator simulator = new SingleThreadSimulator(tracer);
//        RaySimulator simulator = new MultiThreadSimulator(tracer, 8);





        Collection<Ray> raysR = simulator.simulate(sceneR);

//        Map<Boolean, List<Ray>> rays = raysR.stream().collect(Collectors.partitioningBy(ray -> ray.getPoints().size() > 1, Collectors.toList()));
//
//        List<Ray> raysG = rays.get(false);
//        List<Ray> raysB = rays.get(true);


//        Collection<Ray> raysG = simulator.simulate(sceneG);
//        Collection<Ray> raysB = simulator.simulate(sceneB);
//        Collection<Ray> raysO = simulator.simulate(sceneO);

        saveImage(transformer.getMainGraphicsSize(), "scene",
                new SimpleEmitterVisualizer(colorR, SimpleEmitterVisualizer.DEFAULT_STROKE)
                        .visualize(sceneR, transformer),
//                new SimpleEmitterVisualizer(colorG, SimpleEmitterVisualizer.DEFAULT_STROKE)
//                        .visualize(sceneG, transformer),
//                new SimpleEmitterVisualizer(colorB, SimpleEmitterVisualizer.DEFAULT_STROKE)
//                        .visualize(sceneB, transformer),
//                new SimpleEmitterVisualizer(colorO, SimpleEmitterVisualizer.DEFAULT_STROKE)
//                        .visualize(sceneO, transformer),

                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorR, SimpleRayVisualizer.DEFAULT_STROKE, new Random())
                        .visualize(raysR, transformer),
//                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorG, SimpleRayVisualizer.DEFAULT_STROKE, new Random())
//                        .visualize(raysG, transformer),
//                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorB, SimpleRayVisualizer.DEFAULT_STROKE, new Random())
//                        .visualize(raysB, transformer),
//                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorO, SimpleRayVisualizer.DEFAULT_STROKE, new Random())
//                        .visualize(raysO, transformer),

                new ColoredInteractorVisualizer().visualize(sceneR, transformer),

                new SimpleSimulationGridVisualizer().visualize(transformer),

                graphics -> {
//                    graphics.setColor(EtendueApp.DARK_GREEN);
//                    Shape line = new Line2D.Float(x, transformer.getMinPoint().getY(), x, transformer.getMaxPoint().getY());
//                    graphics.draw(Transformer.transformShape(line, transformer.createSimulationTransform()));

//                    Shape line2 = new Line2D.Float(0, transformer.getMinPoint().getY(), 0, transformer.getMaxPoint().getY());
//                    graphics.draw(Transformer.transformShape(line2 , transformer.createSimulationTransform()));
//
//                    Shape line3 = new Line2D.Float(2, transformer.getMinPoint().getY(), 2, transformer.getMaxPoint().getY());
//                    graphics.draw(Transformer.transformShape(line3 , transformer.createSimulationTransform()));
//
//                    Shape line4 = new Line2D.Float(4, transformer.getMinPoint().getY(), 4, transformer.getMaxPoint().getY());
//                    graphics.draw(Transformer.transformShape(line4 , transformer.createSimulationTransform()));

                }
        );

//
//        Collection<Section> sectionsR = new SimpleRayDetector().detect(raysR, x);
//        Collection<Section> sectionsG = new SimpleRayDetector().detect(raysG, x);
//        Collection<Section> sectionsB = new SimpleRayDetector().detect(raysB, x);
//        Collection<Section> sectionsO = new SimpleRayDetector().detect(raysO, x);
//
//
//
//
//        EtendueResult resultR = new KBasedEtendueComputer(100).compute(sectionsR, transformer.getSimulationSize().getY());
//        EtendueResult resultG = new KBasedEtendueComputer(100).compute(sectionsG, transformer.getSimulationSize().getY());
//        EtendueResult resultB = new KBasedEtendueComputer(100).compute(sectionsB, transformer.getSimulationSize().getY());
//        EtendueResult resultO = new KBasedEtendueComputer(100).compute(sectionsO, transformer.getSimulationSize().getY());
////
////        float max = Math.max(resultG.getMax(), resultB.getMax());
////        EtendueResult resultG_ = new EtendueResult(resultG.getPoints(), resultG.getArea(), resultG.getAverage(), max);
////        EtendueResult resultB_ = new EtendueResult(resultB.getPoints(), resultB.getArea(), resultB.getAverage(), max);
//
//        saveImage(transformer.getAuxGraphicsSize(), "etendue",
//                new SimpleEtendueVisualizer(50, colorR, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
//                        .visualize(resultR, transformer),
//                new SimpleEtendueVisualizer(50, colorG, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
//                        .visualize(resultG, transformer),
//                new SimpleEtendueVisualizer(50, colorB, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
//                        .visualize(resultB, transformer),
//                new SimpleEtendueVisualizer(50, colorO, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
//                        .visualize(resultO, transformer),
//                new KBasedEtendueGridVisualizer().visualize(transformer)
//        );

//        GraphResult result = new SimpleDistributionGraphComputer(50).compute(sectionsR);
//
//        saveImage(transformer.getAuxGraphicsSize(), "distribution",
//                new SimpleGraphVisualizer(new Color[]{Color.RED}, SimpleGraphVisualizer.DEFAULT_STROKE).visualize(result, transformer),
//                new SimpleGraphGridVisualizer().visualize(result.getMaxValue(), transformer)
//        );

    }



    static final ImageCreator IMAGE_CREATOR = new SimpleImageCreator();
    static final ImagePainter IMAGE_PAINTER = new SimpleImagePainter();

    static void saveImage(Vector2 dimension, String filename, Painter... painters) {

        BufferedImage image = IMAGE_CREATOR.create(dimension.times(4));

        IMAGE_PAINTER.paint(image, graphics -> {
            graphics.transform(AffineTransform.getScaleInstance(4, 4));
            for(Painter p : painters) p.paint(graphics);
        });

        try {

            File file = new File("imagegenerator/src/main/resources/" + filename + ".png" );

//            image = image.getSubimage(
//                    Math.round(dimension.getX()),
//                    Math.round(dimension.getY()),
//                    Math.round(dimension.getX() * 2),
//                    Math.round(dimension.getY() * 2)
//            );

            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}