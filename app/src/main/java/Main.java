import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Geometries;
import com.brona.etendue.user.Interactors;
import com.brona.etendue.user.Utils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.brona.etendue.user.Tuples.p;
import static com.brona.etendue.user.Tuples.v;

public final class Main {

    public static void main(String[] args) {

        Scene scene = Utils.scene(
//                Interactors.reflecting(Geometries.polyline(15f, 4f, -1f, 1.5f, -1f, -1.5f, 15f, -4f)),
                Interactors.reflecting(Geometries.formula(
                        true, -5, 0,
                        -4, 4, 0.01f, x -> x*x
                )),

                Utils.extendViewBox(10)
        );

        scene.addMembers(
                IntStream.range(0, 100).boxed()
                        .map(i -> Emitters.point(p(-0.5f + (i % 10) / 10f, -0.5f + (i / 10) / 10f), 10000))
                .collect(Collectors.toList())
        );


        EtendueApp.run(scene);
    }

}
