package com.brona.etendue.scheduling.impl;

import com.brona.etendue.scheduling.CancelHandle;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class WrappingScheduler implements CancelableScheduler {

    @NotNull
    final ExecutorService service;

    @NotNull
    @Override
    public CancelHandle execute(@NotNull Runnable runnable) {
        Future<?> future = service.submit(() -> {
            try {
                runnable.run();
            } catch (CancelledException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return () -> future.cancel(true);
    }

}
