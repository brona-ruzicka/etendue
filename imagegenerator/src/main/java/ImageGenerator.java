package com.brona.etendue;

import com.brona.etendue.computation.simulation.impl.LineOnlyIntersector;
import com.brona.etendue.computation.simulation.impl.MultiThreadSimulator;
import com.brona.etendue.computation.simulation.impl.SimpleTracer;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
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

public class ImageGenerator {
    public static void main(String[] args) {

        Scene scene = Utils.scene(
                Interactors.reflecting(Geometries.line(-3,-1,3,-2)),
                Interactors.reflecting(Geometries.line(-3, 1,3, 2)),

                Emitters.line(-3,0,1.9f, 3)
        );

        Vector2 dimension = Vector2.create(500, 300);


        saveScene(scene, 500, 300, "first");
    }

    public static void saveScene(Scene scene, int width, int height, String filename) {
        BufferedImage image = generateImage(scene, width, height);
        saveImage(image, filename);
    }

    public static BufferedImage generateImage(Scene scene, int width, int height) {

        BufferedImage image = new SimpleImageCreator().create(Vector2.create(width, height));
        Transformer transformer = new Transformer(scene.getBoundingBox(), width, height);

        Collection<Ray> rays = new MultiThreadSimulator(
                new SimpleTracer(new LineOnlyIntersector()),
                8
        ).simulate(scene);

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