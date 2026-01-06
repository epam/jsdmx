package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetadataConstraintImpl extends ConstraintImpl implements MetadataConstraint {

    private List<MetadataTargetRegion> metadataTargetRegions = new ArrayList<>();
    private ReleaseCalendar releaseCalendar;

    public MetadataConstraintImpl(MetadataConstraint from) {
        super(Objects.requireNonNull(from));
        this.releaseCalendar = Objects.requireNonNull(from.getReleaseCalendar());
        this.metadataTargetRegions = StreamUtils.streamOfNullable(from.getMetadataTargetRegions())
            .map(cubeRegion -> (MetadataTargetRegion) cubeRegion.clone())
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_CONSTRAINT;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        var references = new HashSet<CrossReference>();

        getConstrainedArtefacts()
            .forEach(artefactReference -> references.add(new CrossReferenceImpl(this, artefactReference)));

        return references;
    }

    @Override
    protected MetadataConstraintImpl createInstance() {
        return new MetadataConstraintImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataConstraint)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataConstraint)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        var that = (MetadataConstraint) o;
        return Objects.equals(getMetadataTargetRegions(), that.getMetadataTargetRegions())
            && Objects.equals(getReleaseCalendar(), that.getReleaseCalendar());
    }

    @Override
    public MetadataConstraint toStub() {
        return toStub(createInstance());
    }

    @Override
    public MetadataConstraint toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
