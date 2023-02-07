package com.brona.etendue.rev2.data.scene.emitter;

import com.brona.etendue.rev2.data.scene.Emitter;
import com.brona.etendue.rev2.data.simulation.Section;
import com.brona.etendue.rev2.math.bounding.BoundingBox;
import com.brona.etendue.rev2.math.tuple.Point2;
import com.brona.etendue.rev2.math.tuple.Vector2;
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
