package com.brona.etendue.data.scene.emitter;

import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.tuple.Point2;
import com.brona.etendue.math.tuple.Vector2;
import com.brona.etendue.data.scene.Emitter;
import com.brona.etendue.data.simulation.Section;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SimpleEmitter implements Emitter {

    @NotNull
    protected final Point2 origin;
    @NotNull
    protected final Vector2 direction;

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.point(origin);
    }

    @Override
    public @NotNull List<@NotNull Section> getRays() {
        return List.of(new Section(origin, direction));
    }
}
