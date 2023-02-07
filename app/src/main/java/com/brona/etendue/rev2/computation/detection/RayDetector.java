package com.brona.etendue.rev2.computation.detection;


import com.brona.etendue.rev2.data.simulation.Ray;
import com.brona.etendue.rev2.data.simulation.Section;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@FunctionalInterface
public interface RayDetector {

    @NotNull
    Collection<@NotNull Section> detect(@NotNull Collection<@NotNull Ray> rays, float xCoordinate);

}
