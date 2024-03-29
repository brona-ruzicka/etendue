package com.brona.etendue.data.scene;

import com.brona.etendue.math.bounding.BoundingBox;

public interface Member {

    BoundingBox getBoundingBox();


    default boolean isEmitter() {
        return false;
    }

    default Emitter asEmitter() {
        if (!isEmitter())
            return null;
        return (Emitter) this;
    }


    default boolean isInteractor() {
        return false;
    }

    default Interactor asInteractor() {
        if (!isInteractor())
            return null;
        return (Interactor) this;
    }

}
