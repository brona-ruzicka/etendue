import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Emitter;
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

    /** The main function, for examples see {@link Examples} */
    public static void main(String[] args) {

        Scene scene = Utils.scene(
                /* Interactors */
                Interactors.reflecting(Geometries.formula(
                        true, -5, 0,
                        -4, 4, 0.01f, x -> x*x
                )),

                /* Emitters */
                Emitters.rectangle(0,0,0.1f, 0.1f, 10, 10, 1000),


                /* Do not zoom in too much */
                Utils.extendViewBox(10)
        );

        EtendueApp.run(scene);
    }

}
