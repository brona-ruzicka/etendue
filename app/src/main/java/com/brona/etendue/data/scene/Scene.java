package com.brona.etendue.data.scene;


import com.brona.etendue.math.bounding.BoundingBox;
import com.brona.etendue.math.bounding.BoundingBoxAccumulator;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class Scene {

    @NotNull
    protected final List<@NotNull Member> members = new ArrayList<>();

    @NotNull
    protected final BoundingBoxAccumulator boundingBox = BoundingBox.accumulator();

    @NotNull
    public Scene addMember(@NotNull Member member) {
        members.add(member);
        boundingBox.add(member.getBoundingBox());
        return this;
    }

    @NotNull
    public Scene addMembers(@NotNull Collection<? extends @NotNull Member> members) {
        members.forEach(this::addMember);
        return this;
    }

    @NotNull
    public Scene addMembers(@NotNull Member... members) {
        for (Member member : members)
            this.addMember(member);
        return this;
    }


    @NotNull
    public Collection<@NotNull Member> getMembers() {
        return Collections.unmodifiableList(this.members);
    }

    @NotNull
    public Collection<@NotNull Emitter> getEmitters() {
        return members.stream()
                .map(Member::asEmitter)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    @NotNull
    public Collection<@NotNull Interactor> getInteractors() {
        return members.stream()
                .map(Member::asInteractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    @NotNull
    public BoundingBox getBoundingBox() {
        return boundingBox.createBoundingBox();
    }

}
