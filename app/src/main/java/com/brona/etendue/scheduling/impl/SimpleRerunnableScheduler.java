package com.brona.etendue.scheduling.impl;

import com.brona.etendue.scheduling.RerunHandle;
import com.brona.etendue.scheduling.RerunnableScheduler;
import com.brona.etendue.scheduling.Scheduler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleRerunnableScheduler<H> implements RerunnableScheduler<H> {

    @NotNull
    final Scheduler<H> delegate;

    @Override
    @NotNull
    public RerunHandle<H> execute(@NotNull Runnable runnable) {
        return () -> delegate.execute(runnable);
    }
}
