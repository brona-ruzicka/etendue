package com.brona.etendue.rev2;

import com.brona.etendue.rev2.data.scene.Scene;
import com.brona.etendue.rev2.data.scene.emitter.Emitters;
import com.brona.etendue.rev2.data.scene.interactor.Interactors;
import com.brona.etendue.rev2.math.geometry.Geometries;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
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
                IntStream.range(0, 50).boxed()
                        .map(i -> Emitters.point(Point2.create(-0.9f, 1f - 2f / 50 * i), 3000))
                        .collect(Collectors.toList())
        );

        EtendueApp.run(scene);

    }

    @Value
    public static class IntTuple2 {

        int x, y;

    }

}
