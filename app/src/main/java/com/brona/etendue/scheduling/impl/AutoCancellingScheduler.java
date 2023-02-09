package com.brona.etendue.scheduling.impl;

import com.brona.etendue.scheduling.CancelHandle;
import com.brona.etendue.scheduling.CancelableScheduler;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class AutoCancellingScheduler implements CancelableScheduler {

    @NotNull
    protected final CancelableScheduler delegate;
    protected CancelHandle handle = null;

    @NotNull
    @Override
    public CancelHandle execute(@NotNull Runnable runnable) {
        CancelHandle.cancelIfPresent(handle);
        return handle = delegate.execute(() -> {
            CancelableScheduler.check();
            runnable.run();
        });
    }
}
