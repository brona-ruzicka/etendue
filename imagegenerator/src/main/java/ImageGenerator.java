import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.MultiThreadSimulator;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.computation.simulation.impl.SingleThreadSimulator;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Geometries;
import com.brona.etendue.user.Interactors;
import com.brona.etendue.user.Utils;
import com.brona.etendue.visualization.Transformer;
import com.brona.etendue.visualization.common.impl.SimpleImageCreator;
import com.brona.etendue.visualization.common.impl.SimpleImagePainter;
import com.brona.etendue.visualization.simulation.impl.ColoredInteractorVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleEmitterVisualizer;
import com.brona.etendue.visualization.simulation.impl.SimpleRayVisualizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.brona.etendue.user.Tuples.p;
import static com.brona.etendue.user.Tuples.v;

public class ImageGenerator {
    public static void main(String[] args) {

        Scene scene = Utils.scene(
                Interactors.reflecting(Geometries.formula(
                        true, -25, 0,
                        -20, 20, 0.1f, x -> x*x / 20
                )),

                Emitters.line(-20, 0, 10f, 10)
//
//                // Zooming
//                Utils.extendViewBox(-30, -30, 70, 30)
        );


        saveScene(scene, 500, 300, "vymezeniproblemu_aproximace");
    }



    public static void saveScene(Scene scene, int width, int height, String filename) {
        BufferedImage image = generateImage(scene, Math.round(width*1.4f), Math.round(height*1.4f));
        saveImage(image.getSubimage(width / 5, height / 5, width, height), filename);
    }

    public static BufferedImage generateImage(Scene scene, int width, int height) {

        BufferedImage image = new SimpleImageCreator().create(Vector2.create(width, height));
        Transformer transformer = new Transformer(scene.getBoundingBox(), width, height);

        RayTracer tracer = new SimpleTracer(new LineOnlyIntersector());
        RaySimulator simulator = new SingleThreadSimulator(tracer);
//        RaySimulator simulator = new MultiThreadSimulator(tracer, 8);

        Collection<Ray> rays = simulator.simulate(scene);

        new SimpleImagePainter().paint(image,
                new SimpleRayVisualizer().visualize(rays, transformer),
                new SimpleEmitterVisualizer().visualize(scene, transformer),
                new ColoredInteractorVisualizer().visualize(scene, transformer)
        );

        return image;
    }

    public static void saveImage(BufferedImage image, String filename) {

        File file = new File("imagegenerator/src/main/resources/" + filename + ".png" );

        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}