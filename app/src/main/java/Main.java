import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.scene.emitter.Emitters;
import com.brona.etendue.data.scene.interactor.Interactors;
import com.brona.etendue.math.geometry.Geometries;
import com.brona.etendue.math.tuple.Point2;
import lombok.Value;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Scene scene = Scene.of(
                Interactors.reflecting(
                        Geometries.line(-1f, -10f, -1f, 10f)
                ),

                Interactors.reflecting(
                        Geometries.line(-1.5f, 1.5f, 4f, 3f)
                ),
                Interactors.reflecting(
                        Geometries.line(-1.5f, -1.5f, 4f, -3f)
                )
        );

        scene.addMembers(
                IntStream.range(0, 10).boxed()
                        .flatMap(x ->  IntStream.range(0, 10).boxed()
                                .map(y -> Emitters.point(Point2.create(-0.5f + x / 10f, -0.5f + y / 10f), 3000)))
                        .collect(Collectors.toList())
        );

        EtendueApp.run(scene);

    }

    @Value
    public static class IntTuple2 {

        int x, y;

    }

}
