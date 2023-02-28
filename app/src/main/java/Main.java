import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Geometries;
import com.brona.etendue.user.Interactors;
import com.brona.etendue.user.Utils;

import static com.brona.etendue.user.Tuples.point;

public final class Main {

    /** The main function, for examples see {@link Examples} */
    public static void main(String[] args) {

        // Světlovod z kapitoly 3.2.1
        Scene scene = Scene.create(
                Interactors.reflecting(
                        Geometries.line(point(0, 1), point(5, 2))
                ),
                Interactors.reflecting(
                        Geometries.line(point(0, -1), point(5, -2))
                ),

                Emitters.line(
                        point(0, 0),
                        1.95f,
                        100000
                )
        );
        EtendueApp.run(scene);

    }

}
