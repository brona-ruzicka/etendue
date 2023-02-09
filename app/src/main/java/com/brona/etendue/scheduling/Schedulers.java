package com.brona.etendue.scheduling;

import com.brona.etendue.scheduling.impl.AutoCancellingScheduler;
import com.brona.etendue.scheduling.impl.SimpleRerunnableScheduler;
import com.brona.etendue.scheduling.impl.WrappingScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

public final class Schedulers {

    private Schedulers() { }


    @NotNull
    public static CancelableScheduler wrap(@NotNull ExecutorService service) {
        return new WrappingScheduler(service);
    }

    @NotNull
    public static CancelableScheduler autoCanceling(@NotNull CancelableScheduler delegate) {
        return new AutoCancellingScheduler(delegate);
    }

    @NotNull
    public static <T> RerunnableScheduler<T> rerunnable(@NotNull Scheduler<T> delegate) {
        return new SimpleRerunnableScheduler<>(delegate);
    }

    @NotNull
    public static RerunnableScheduler<CancelHandle> rerunnableAutoCanceling(@NotNull CancelableScheduler delegate) {
        return Schedulers.rerunnable(Schedulers.autoCanceling(delegate));
    }

}
