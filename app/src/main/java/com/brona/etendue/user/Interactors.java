package com.brona.etendue.user;

import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.data.scene.interactor.AbsorbingInteractor;
import com.brona.etendue.data.scene.interactor.ReflectingInteractor;
import com.brona.etendue.math.geometry.Geometry;
import org.jetbrains.annotations.NotNull;

public final class Interactors {

    private Interactors() {}

    /** Absorbs any ray */
    @NotNull
    public static Interactor absorbing(@NotNull Geometry geometry) {
        return new AbsorbingInteractor(geometry);
    }

    /** Reflects all rays, a mirror */
    @NotNull
    public static Interactor reflecting(@NotNull Geometry geometry) {
        return new ReflectingInteractor(geometry);
    }

}
