package com.brona.etendue.scheduling;

import lombok.SneakyThrows;

@FunctionalInterface
public interface CancelableScheduler extends Scheduler<CancelHandle> {

    /**
     * This weird method is used for checking if the current thread is interrupted.
     * Mainly useful in cancelable background computing threads.
     * */
    @SneakyThrows
    static void check() {
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException();
    }

}
