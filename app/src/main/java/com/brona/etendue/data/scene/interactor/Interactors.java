package com.brona.etendue.data.scene.interactor;

import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.math.geometry.Geometry;
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
