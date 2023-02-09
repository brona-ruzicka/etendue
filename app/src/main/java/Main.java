import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.scene.emitter.Emitters;
import com.brona.etendue.data.scene.interactor.Interactors;
import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.geometry.Geometries;
import com.brona.etendue.math.tuple.Point2;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {



    public static void main(String[] args) {

        Scene scene = Scene.of(
                Interactors.reflecting(Geometries.line(-1f, -10f, -1f, 10f)),
                Interactors.reflecting(Geometries.line(-1.5f, 1.5f, 15f, 4f)),
                Interactors.reflecting(Geometries.line(-1.5f, -1.5f, 15f, -4f)),
                () -> BoundingBox.create(-10, -10, 10, 10)
        );

        scene.addMembers(
                IntStream.range(0, 100).boxed()
                        .map(i -> Emitters.point(Point2.create(-0.5f + (i % 10) / 10f, -0.5f + (i / 10) / 10f), 10000))
                .collect(Collectors.toList())
        );

        EtendueApp.run(scene);

    }

}
