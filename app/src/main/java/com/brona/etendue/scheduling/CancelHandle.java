package com.brona.etendue.scheduling;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@FunctionalInterface
public interface CancelHandle {
    void cancel();

    static void cancelIfPresent(@Nullable CancelHandle handle) {
        if (Objects.nonNull(handle)) handle.cancel();
    }

}
