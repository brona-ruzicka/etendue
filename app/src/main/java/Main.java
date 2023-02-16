import com.brona.etendue.EtendueApp;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.user.Emitters;
import com.brona.etendue.user.Geometries;
import com.brona.etendue.user.Interactors;
import com.brona.etendue.user.Utils;


public final class Main {

    /** The main function, for examples see {@link Examples} */
    public static void main(String[] args) {

        Scene scene = Utils.scene(
                // Interactors
                Interactors.reflecting(Geometries.formula(
                        true, -25, 0,
                        -20, 20, 0.1f, x -> x*x / 20
                )),

                Emitters.line(-20, 0, 10f, 1000000),

                // Zooming
                Utils.extendViewBox(-30, -30, 70, 30)
        );



        EtendueApp.run(scene);
    }

}
