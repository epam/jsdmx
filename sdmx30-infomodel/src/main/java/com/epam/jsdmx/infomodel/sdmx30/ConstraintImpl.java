package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class ConstraintImpl extends MaintainableArtefactImpl implements Constraint {

    private List<ArtefactReference> constrainedArtefacts = new ArrayList<>();
    private ConstraintRoleType constraintRoleType;

    protected ConstraintImpl(Constraint from) {
        super(Objects.requireNonNull(from));
        this.constraintRoleType = Objects.requireNonNull(from.getConstraintRoleType());
        this.constrainedArtefacts = StreamUtils.streamOfNullable(from.getConstrainedArtefacts())
            .map(ArtefactReference::clone)
            .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Constraint)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConstraintImpl)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        ConstraintImpl that = (ConstraintImpl) o;
        return Objects.equals(getConstrainedArtefacts(), that.getConstrainedArtefacts())
            && getConstraintRoleType() == that.getConstraintRoleType();
    }
}
