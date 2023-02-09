package com.brona.etendue.scheduling;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Scheduler<T> {

    @NotNull
    T execute(@NotNull Runnable runnable);

}
