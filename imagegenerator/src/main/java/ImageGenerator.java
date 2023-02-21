import com.brona.etendue.EtendueApp;
import com.brona.etendue.computation.detection.impl.KBasedEtendueComputer;
import com.brona.etendue.computation.detection.impl.SimpleRayDetector;
import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.computation.simulation.impl.SingleThreadSimulator;
import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.scene.emitter.PointEmitter;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Utils;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.common.ImageCreator;
import com.brona.etendue.visualization.common.ImagePainter;
import com.brona.etendue.visualization.common.impl.SimpleImageCreator;
import com.brona.etendue.visualization.common.impl.SimpleImagePainter;
import com.brona.etendue.visualization.detection.impl.KBasedEtendueGridVisualizer;
import com.brona.etendue.visualization.detection.impl.DensityBasedEtendueVisualizer;
import com.brona.etendue.visualization.detection.impl.SimpleEtendueVisualizer;
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
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import static com.brona.etendue.user.Tuples.p;
import static com.brona.etendue.user.Tuples.v;

public class ImageGenerator {

    static Scene createBaseScene(Emitter... members) {
        return Utils.scene(
//                Interactors.reflecting(Geometries.formula(
//                        true, -25, 0,
//                        -15, 15, 0.05f, x -> x*x / 10
//                )),
//                Utils.extendViewBox(-25, -10, 25, 10)
        ).addMembers(members);
    }


    public static void main(String[] args) {

        float x = 0;

        Color colorR = Color.RED;
        Color colorG = new Color(0xA748DE);
        Color colorB = new Color(0x0080FF);
        Color colorO = Color.ORANGE;

        Scene sceneR = createBaseScene(
                Emitters.single(p(-1,-3), v(1,0.4f))
//                new Emitter.Simple(
//                        BoundingBox.point(p(-1, 0)),
//                        PointEmitter.generateRays(p(-1, 0), 2000)
//                                .stream()
//                                .filter(section -> section.getDirection().getX() > 0.8f)
//                                .collect(Collectors.toList())
//                )
//                Emitters.line(p(-1, 0), 1, 200000)
        );
        Scene sceneG = createBaseScene(
//                new Emitter.Simple(
//                        BoundingBox.point(p(-1, 0)),
//                        PointEmitter.generateRays(p(-1, 0), 60)
//                                .stream()
//                                .filter(section -> section.getDirection().getX() > 0.8f)
//                                .collect(Collectors.toList())
//                )
                Emitters.single(p(-1.5f,3), v(1,-0.8f))
        );
        Scene sceneB = createBaseScene(
                Emitters.single(p(-2,-1), v(1,-0.8f))
        );
        Scene sceneO = createBaseScene(
                Emitters.single(p(-1,-0.5f), v(1,0.2f))
        );

        BoundingBox box = BoundingBox.combine(
                sceneR.getBoundingBox(),
                sceneG.getBoundingBox(),
                sceneB.getBoundingBox(),
                sceneO.getBoundingBox(),
                BoundingBox.create(-2, -2,2, 2)
        );

        Transformer transformer = new Transformer(box, 300, 300);


        RayTracer tracer = new SimpleTracer(new LineOnlyIntersector());
        RaySimulator simulator = new SingleThreadSimulator(tracer);
//        RaySimulator simulator = new MultiThreadSimulator(tracer, 8);

        Collection<Ray> raysR = simulator.simulate(sceneR);
        Collection<Ray> raysG = simulator.simulate(sceneG);
        Collection<Ray> raysB = simulator.simulate(sceneB);
        Collection<Ray> raysO = simulator.simulate(sceneO);

        saveImage(transformer.getMainGraphicsSize(), "scene",
                new SimpleEmitterVisualizer(colorR, SimpleEmitterVisualizer.DEFAULT_STROKE)
                        .visualize(sceneR, transformer),
                new SimpleEmitterVisualizer(colorG, SimpleEmitterVisualizer.DEFAULT_STROKE)
                        .visualize(sceneG, transformer),
                new SimpleEmitterVisualizer(colorB, SimpleEmitterVisualizer.DEFAULT_STROKE)
                        .visualize(sceneB, transformer),
                new SimpleEmitterVisualizer(colorO, SimpleEmitterVisualizer.DEFAULT_STROKE)
                        .visualize(sceneO, transformer),

                new ColoredInteractorVisualizer().visualize(sceneR, transformer),

                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorR, DensityBasedEtendueVisualizer.DEFAULT_STROKE, new Random())
                        .visualize(raysR, transformer),
                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorG, DensityBasedEtendueVisualizer.DEFAULT_STROKE, new Random())
                        .visualize(raysG, transformer),
                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorB, DensityBasedEtendueVisualizer.DEFAULT_STROKE, new Random())
                        .visualize(raysB, transformer),
                new SimpleRayVisualizer(SimpleRayVisualizer.DEFAULT_MAX_COUNT, colorO, DensityBasedEtendueVisualizer.DEFAULT_STROKE, new Random())
                        .visualize(raysO, transformer),

                new SimpleSimulationGridVisualizer().visualize(transformer),

                graphics -> {
                    graphics.setColor(EtendueApp.DARK_GREEN);
                    Shape line = new Line2D.Float(x, transformer.getMinPoint().getY(), x, transformer.getMaxPoint().getY());
                    graphics.draw(Transformer.transformShape(line, transformer.createSimulationTransform()));
                }
        );


        Collection<Section> sectionsR = new SimpleRayDetector().detect(raysR, x);
        Collection<Section> sectionsG = new SimpleRayDetector().detect(raysG, x);
        Collection<Section> sectionsB = new SimpleRayDetector().detect(raysB, x);
        Collection<Section> sectionsO = new SimpleRayDetector().detect(raysO, x);




        EtendueResult resultR = new KBasedEtendueComputer(200).compute(sectionsR, transformer.getSimulationSize().getY());
        EtendueResult resultG = new KBasedEtendueComputer(200).compute(sectionsG, transformer.getSimulationSize().getY());
        EtendueResult resultB = new KBasedEtendueComputer(200).compute(sectionsB, transformer.getSimulationSize().getY());
        EtendueResult resultO = new KBasedEtendueComputer(200).compute(sectionsO, transformer.getSimulationSize().getY());

        saveImage(transformer.getAuxGraphicsSize(), "etendue",
                new SimpleEtendueVisualizer(100, colorR, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
                        .visualize(resultR, transformer),
                new DensityBasedEtendueVisualizer(100, colorG, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
                        .visualize(resultG, transformer),
                new DensityBasedEtendueVisualizer(100, colorB, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
                        .visualize(resultB, transformer),
                new DensityBasedEtendueVisualizer(100, colorO, DensityBasedEtendueVisualizer.DEFAULT_STROKE)
                        .visualize(resultO, transformer),
                new KBasedEtendueGridVisualizer().visualize(transformer)
        );

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

            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}