import com.brona.etendue.computation.detection.impl.SimpleDistributionGraphComputer;
import com.brona.etendue.computation.detection.impl.SimpleEtendueComputer;
import com.brona.etendue.computation.detection.impl.SimpleRayDetector;
import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.MultiThreadSimulator;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.data.detection.EtendueResult;
import com.brona.etendue.data.detection.GraphResult;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.user.*;
import com.brona.etendue.visualization.Painter;
import com.brona.etendue.visualization.Texts;
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
import java.util.Collection;

import static com.brona.etendue.user.Tuples.point;

public class EtendueApp {

//    /** 1A */
//    public static void main(String[] args) {
//
//        Scene scene = Scene.create(
//                Interactors.reflecting(
//                        Geometries.line(point(0, 1), point(5, 2))
//                ),
//                Interactors.reflecting(
//                        Geometries.line(point(0, -1), point(5, -2))
//                ),
//
//                Emitters.line(
//                        point(0, 0),
//                        1.95f,
//                        100000
//                )
//        );
//        EtendueApp.run(scene);
//
//    }
//    static final float xCoordinate = 5f;
//    static final String root = "vysledky_1a";

//    /** 1B */
//    public static void main(String[] args) {
//
//        Scene scene = Scene.create(
//                Interactors.reflecting(
//                        Geometries.line(point(0, 1), point(10, 2))
//                ),
//                Interactors.reflecting(
//                        Geometries.line(point(0, -1), point(10, -2))
//                ),
//
//                Emitters.line(
//                        point(0, 0),
//                        1.9f,
//                        100000
//                )
//        );
//        EtendueApp.run(scene);
//
//    }
//    static final float xCoordinate = 10f;
//    static final String root = "vysledky_1b";

//    /** 2A */
//    public static void main(String[] args) {
//
//        float length = 5f;
//        float offsetRatio = 1f;
//
//        float param = (float) Math.sqrt(offsetRatio);
//        float offset = param / (2 * offsetRatio);
//        float maxX = (float) Math.sqrt( (length+offset) * 2*param );
//
//        Scene scene = Scene.create(
//                Interactors.reflecting(
//                        Geometries.formula(
//                                true,
//                                -offset, 0,
//                                -maxX, -1,
//                                0.05f, x -> x*x / (2*param)
//                        )
//                ),
//                Interactors.reflecting(
//                        Geometries.formula(
//                                true,
//                                -offset, 0,
//                                1, maxX,
//                                0.05f, x -> x*x / (2*param)
//                        )
//                ),
//
//                Emitters.line(
//                        point(0, 0),
//                        1f,
//                        100000
//                )
//        );
//        EtendueApp.run(scene);
//
//    }
//    static final float xCoordinate = 5f;
//    static final String root = "vysledky_2a";

    /** 2B */
    public static void main(String[] args) {

        float length = 5f;
        float offsetRatio = 5 / 4f;

        float param = (float) Math.sqrt(offsetRatio);
        float offset = param / (2 * offsetRatio);
        float maxX = (float) Math.sqrt( (length+offset) * 2*param );

        Scene scene = Scene.create(
                Interactors.reflecting(
                        Geometries.formula(
                                true,
                                -offset, 0,
                                -1, -maxX,
                                -0.05f, x -> x*x / (2*param)
                        )
                ),
                Interactors.reflecting(
                        Geometries.formula(
                                true,
                                -offset, 0,
                                1, maxX,
                                0.05f, x -> x*x / (2*param)
                        )
                ),

                Emitters.line(
                        point(0, 0),
                        1f,
                        100000
                )
        );
        EtendueApp.run(scene);

    }
    static final float xCoordinate = 5f;
    static final String root = "vysledky_2b";

//    /** 2C */
//    public static void main(String[] args) {
//
//        float length = 5f;
//        float offsetRatio = 3 / 5f;
//
//        float param = (float) Math.sqrt(offsetRatio);
//        float offset = param / (2 * offsetRatio);
//        float maxX = (float) Math.sqrt( (length+offset) * 2*param );
//
//        Scene scene = Scene.create(
//                Interactors.reflecting(
//                        Geometries.formula(
//                                true,
//                                -offset, 0,
//                                -maxX, -1,
//                                0.05f, x -> x*x / (2*param)
//                        )
//                ),
//                Interactors.reflecting(
//                        Geometries.formula(
//                                true,
//                                -offset, 0,
//                                1, maxX,
//                                0.05f, x -> x*x / (2*param)
//                        )
//                ),
//
//                Emitters.line(
//                        point(0, 0),
//                        1f,
//                        100000
//                )
//        );
//        EtendueApp.run(scene);
//
//    }
//    static final float xCoordinate = 5f;
//    static final String root = "vysledky_2c";



    static void run(Scene scene) {

        Transformer transformer = new Transformer(
                BoundingBox.combine(
                        scene.getBoundingBox(),
                        BoundingBox.create(-0.1f,-4.1f,10.1f,4.1f)
                ),
                600,
                300,
                300,
                300
        );

        Collection<Ray> rays = new MultiThreadSimulator(new SimpleTracer(new LineOnlyIntersector()), 8).simulate(scene);
        Collection<Section> sections = new SimpleRayDetector().detect(rays, xCoordinate);
        EtendueResult etendue = new SimpleEtendueComputer(100).compute(sections, transformer.getSimulationSize().getY());
        GraphResult graph = new SimpleDistributionGraphComputer(100).compute(sections);

        saveImage(transformer.getMainGraphicsSize(), root + "_scena",
                new SimpleRayVisualizer().visualize(rays, transformer),
                new SimpleEmitterVisualizer().visualize(scene, transformer),
                new ColoredInteractorVisualizer().visualize(scene, transformer),
                new SimpleSimulationGridVisualizer().visualize(transformer),
                graphics -> {
                    graphics.setColor(com.brona.etendue.EtendueApp.DARK_GREEN);
                    Shape line = new Line2D.Float(xCoordinate, transformer.getMinPoint().getY(), xCoordinate, transformer.getMaxPoint().getY());
                    graphics.draw(Transformer.transformShape(line, transformer.createSimulationTransform()));
                }
        );

        saveImage(transformer.getAuxGraphicsSize(), root + "_etendue",
                new DensityBasedEtendueVisualizer(100).visualize(etendue, transformer),
                new SimpleEtendueGridVisualizer().visualize(transformer),
                graphics -> {
                    Texts.drawText(
                            graphics,
                            transformer.getAuxGraphicsSize().getX() - 130,
                            transformer.getAuxGraphicsSize().getY() - 18,
                            "Étendue: " + Math.round(etendue.getArea() * 100) / 100f + " m·rad"
                    );

                    Texts.drawText(
                            graphics,
                            transformer.getAuxGraphicsSize().getX() - 130,
                            transformer.getAuxGraphicsSize().getY() - 38,
                            "Average: " + Math.round(etendue.getAverage() * 100) / 100f + " ray/px"
                    );
                }
        );

        saveImage(transformer.getAuxGraphicsSize(), root + "_graf",
                new SimpleGraphVisualizer().visualize(graph, transformer),
                new SimpleGraphGridVisualizer().visualize(graph.getMaxValue(), transformer),
                graphics -> {
                    Texts.drawText(
                            graphics,
                            transformer.getAuxGraphicsSize().getX() - 130,
                            transformer.getAuxGraphicsSize().getY() - 18,
                            "Total: " + Math.round(graph.getTotal()) + " rays"
                    );

                    Texts.drawText(
                            graphics,
                            transformer.getAuxGraphicsSize().getX() - 130,
                            transformer.getAuxGraphicsSize().getY() - 38,
                            "Maximum: " + Math.round(graph.getMaxValue()) + " rays"
                    );
                }
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
