package com.brona.etendue.visualization.common.impl;

import com.brona.etendue.visualization.common.Rescheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExecutorRescheduler implements Rescheduler {

    @NotNull
    protected final Executor executor;
    protected long lastId = 0;

    public ExecutorRescheduler(@NotNull Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(@NotNull Consumer<@NotNull Supplier<@NotNull Boolean>> task) {

        final long id = System.nanoTime();
        lastId = id;

        executor.execute(() -> {
            try {
                task.accept(() -> lastId != id);
            } catch (OtherTaskScheduledException ignored) { }
        });

    }

    protected static class OtherTaskScheduledException extends RuntimeException { }

}
