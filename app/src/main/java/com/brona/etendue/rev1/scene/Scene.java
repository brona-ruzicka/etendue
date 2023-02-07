package com.brona.etendue.rev1.scene;

import com.brona.etendue.rev1.math.bounding.BoundingBox;
import com.brona.etendue.rev1.math.bounding.BoundingBoxed;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Scene implements BoundingBoxed {

    @NotNull
    protected final List<DetectingMember> detectingMembers = new ArrayList<>();

    @NotNull
    protected final List<EmittingMember> emittingMembers = new ArrayList<>();

    @NotNull
    protected final List<InteractingMember> interactingMembers = new ArrayList<>();


    @NotNull
    protected BoundingBox boundingBox = BoundingBox.empty();

    @NotNull
    public Scene addMember(@NotNull Member member) {
        boolean added = false;

        if (member instanceof DetectingMember) {
            detectingMembers.add((DetectingMember) member);
            added = true;
        }

        if (member instanceof EmittingMember) {
            emittingMembers.add((EmittingMember) member);
            added = true;
        }

        if (member instanceof InteractingMember) {
            interactingMembers.add((InteractingMember) member);
            added = true;
        }

        if (!added) {
            throw new UnsupportedOperationException("Unknown member type " + member.getClass().getSimpleName() + "!");
        }

        boundingBox = boundingBox.combine(member.getBoundingBox());

        return this;
    }

    @NotNull
    public Scene addMembers(@NotNull Collection<? extends Member> members) {
        members.forEach(this::addMember);
        return this;
    }

    @NotNull
    public Scene addMembers(@NotNull Member ...members) {
        for (Member member : members)
            addMember(member);
        return this;
    }

}
