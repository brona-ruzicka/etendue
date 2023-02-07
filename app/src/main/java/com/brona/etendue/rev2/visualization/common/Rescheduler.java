package com.brona.etendue.rev2.visualization.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Rescheduler {
    void execute(@NotNull Consumer<@NotNull Supplier<@NotNull Boolean>> task);

    default void execute(@NotNull Runnable task) {
        execute(__ -> task.run());
    }

}
