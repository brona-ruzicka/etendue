package com.brona.etendue.scheduling;

import lombok.SneakyThrows;
import lombok.ToString;

@FunctionalInterface
public interface CancelableScheduler extends Scheduler<CancelHandle> {

    /**
     * This weird method is used for checking if the current thread is interrupted.
     * Mainly useful in cancelable background computing threads.
     * */
    static void check() {
        if (Thread.currentThread().isInterrupted())
            throw new CancelledException();
    }

    @ToString
    class CancelledException extends RuntimeException { }

}
