import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.user.*;

import static com.brona.etendue.user.Tuples.point;
import static com.brona.etendue.user.Tuples.vector;

public final class Examples {

    public static void example1() {

        Scene scene = Scene.create(
                /* Interactors */
                Interactors.reflecting(Geometries.polyline(15f, 4f, -1f, 1.5f, -1f, -1.5f, 15f, -4f)),

                /* Emitters */
                Emitters.point(Tuples.point(0,0), 1000),

                /* Do not zoom in too much */
                Utils.extendViewBox(10)
        );

        EtendueApp.run(scene);

    }

    public static void example2() {

        Scene scene = Scene.create(
                /* Do not zoom in too much */
                Utils.extendViewBox(3)
        );

        /* Another possible way to add to the scene */
        scene.addMembers(
                /* Interactors, Notice the absorber */
                Interactors.absorbing(Geometries.rectangle(-1, -1, 1, 1)),

                /* Emitters, Notice usage of the Point2 and Vector2 objects instead of passing two floats */
                Emitters.point(Tuples.point(0,0), 1000),
                Emitters.single(Tuples.point(-2,0), vector(0, 1))
        );

        /* Supply non-default size */
        EtendueApp.run(scene, 500, 500);

    }

    public static void example3() {

        Scene scene = Scene.create(
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
//        example2();
//        example3();
    }


}
