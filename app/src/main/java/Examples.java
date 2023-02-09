import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Geometries;
import com.brona.etendue.user.Interactors;
import com.brona.etendue.user.Utils;

import static com.brona.etendue.user.Tuples.p;
import static com.brona.etendue.user.Tuples.v;

public final class Examples {

    public static void example1() {

        Scene scene = Utils.scene(
                /* Interactors */
                Interactors.reflecting(Geometries.polyline(15f, 4f, -1f, 1.5f, -1f, -1.5f, 15f, -4f)),

                /* Emitters */
                Emitters.point(p(0,0), 1000),

                /* Do not zoom in too much */
                Utils.extendViewBox(3)
        );

        EtendueApp.run(scene);

    }

    public static void example2() {

        Scene scene = Utils.scene(
                /* Do not zoom in too much */
                Utils.extendViewBox(10)
        );

        /* Another possible way to add to the scene */
        scene.addMembers(
                /* Interactors, Notice the absorber */
                Interactors.absorbing(Geometries.rectangle(-1, -1, 1, 1)),

                /* Emitters, Notice usage of the Point2 and Vector2 objects instead of passing two floats */
                Emitters.point(p(0,0), 1000),
                Emitters.single(p(-2,0), v(0, 1))
        );

        /* Supply non-default size */
        EtendueApp.run(scene, 500, 500);

    }

    public static void example3() {

        Scene scene = Utils.scene(
                /* Interactors */
                Interactors.reflecting(Geometries.formula(
                        true, -5, 0,
                        -4, 4, 0.01f, x -> x*x
                )),

                /* Emitters */
                Emitters.rectangle(0,0,0.1f, 0.1f, 10, 10, 1000)
        );

        EtendueApp.run(scene);

    }

    public static void main(String[] args) {
//        example1();
        example2();
//        example3();
    }


}
