package com.brona.etendue.scheduling;

@FunctionalInterface
public interface RerunnableScheduler<T> extends Scheduler<RerunHandle<T>> {

}