package com.brona.etendue.rev2.data.scene.interactor;

import com.brona.etendue.rev2.data.scene.Interactor;
import com.brona.etendue.rev2.math.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

public final class Interactors {

    private Interactors() {}

    @NotNull
    public static Interactor absorbing(@NotNull Geometry geometry) {
        return new AbsorbingInteractor(geometry);
    }

    @NotNull
    public static Interactor reflecting(@NotNull Geometry geometry) {
        return new ReflectingInteractor(geometry);
    }

}
