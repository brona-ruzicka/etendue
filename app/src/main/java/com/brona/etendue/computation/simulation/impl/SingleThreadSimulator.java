package com.brona.etendue.computation.simulation.impl;

import com.brona.etendue.computation.simulation.RaySimulator;
import com.brona.etendue.computation.simulation.RayTracer;
import com.brona.etendue.data.simulation.Ray;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.scene.Interactor;
import com.brona.etendue.data.scene.Scene;
import com.brona.etendue.data.simulation.Section;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SingleThreadSimulator implements RaySimulator {

    @NotNull
    protected final RayTracer tracer;


    @Override
    @NotNull
    public Collection<@NotNull Ray> simulate(@NotNull Scene scene) {

        Collection<Emitter> emitters = scene.getEmitters();
        Collection<Interactor> interactors = scene.getInteractors();

        List<Section> origins = emitters.stream()
                .flatMap(emitter -> emitter.getRays().stream())
                .collect(Collectors.toList());

        List<Ray> rays = origins.stream()
                .map(origin -> tracer.trace(origin, interactors))
                .collect(Collectors.toList());

        return rays;
    }

}