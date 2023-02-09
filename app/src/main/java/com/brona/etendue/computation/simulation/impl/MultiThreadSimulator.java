package com.brona.etendue.computation.simulation.impl;

import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.simulation.Section;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class MultiThreadSimulator implements RaySimulator {

    @NotNull RayTracer tracer;

    int threadCount;


    @Override
    @NotNull
    public Collection<@NotNull Ray> simulate(@NotNull Scene scene) {

        Collection<Emitter> emitters = scene.getEmitters();
        Collection<Interactor> interactors = scene.getInteractors();

        List<Section> origins = emitters.stream()
                .flatMap(emitter -> emitter.getRays().stream())
                .collect(Collectors.toList());

        int originCount = origins.size();
        int originsPerThread = originCount / threadCount + 1;

        List<Ray> rays = Collections.synchronizedList(new LinkedList<>());

        List<Thread> threads = IntStream.range(0, threadCount).boxed()
                .map(i ->
                        origins.subList(
                                Math.min(i * originsPerThread, originCount),
                                Math.min((i+1) * originsPerThread, originCount)
                        )
                )
                .map(subOrigins -> new Thread(() -> {
                    List<Ray> subRays = subOrigins.stream()
                            .map(origin -> tracer.trace(origin, interactors))
                            .collect(Collectors.toList());
                    rays.addAll(subRays);
                }))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);

        try {
            for(Thread thread: threads)
                thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rays;
    }


}
