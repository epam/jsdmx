package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class StructureUsageImpl
    extends MaintainableArtefactImpl
    implements StructureUsage {

    private ArtefactReference structure;

    protected StructureUsageImpl(StructureUsage from) {
        super(Objects.requireNonNull(from));
        if (from.getStructure() != null) {
            this.structure = new MaintainableArtefactReference(from.getStructure());
        }
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        if (structure != null) {
            return Set.of(new CrossReferenceImpl(this, structure));
        }
        return Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructureUsage)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructureUsage)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        StructureUsageImpl that = (StructureUsageImpl) o;
        return Objects.equals(getStructure(), that.getStructure());
    }
}
