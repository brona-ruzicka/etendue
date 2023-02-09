package com.brona.etendue.scheduling;

@FunctionalInterface
public interface RerunHandle<T> {

    T rerun();

}
