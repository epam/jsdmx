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

@NoArgsConstructor
@Getter
@Setter
public class DataConstraintImpl extends ConstraintImpl implements DataConstraint {

    private List<CubeRegion> cubeRegions = new ArrayList<>();
    private List<DataKeySet> dataContentKeys = new ArrayList<>();
    private ReleaseCalendar releaseCalendar;

    public DataConstraintImpl(DataConstraint from) {
        super(Objects.requireNonNull(from));
        this.releaseCalendar = Objects.requireNonNull(from.getReleaseCalendar());

        this.cubeRegions = StreamUtils.streamOfNullable(from.getCubeRegions())
            .map(CubeRegion::clone)
            .collect(toList());

        this.dataContentKeys = StreamUtils.streamOfNullable(from.getDataContentKeys())
            .map(DataKeySet::clone)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_CONSTRAINT;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        var references = new HashSet<CrossReference>();

        getConstrainedArtefacts()
            .forEach(artefactReference -> references.add(new CrossReferenceImpl(this, artefactReference)));

        return references;
    }

    @Override
    protected DataConstraintImpl createInstance() {
        return new DataConstraintImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataConstraint)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataConstraint)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        DataConstraintImpl that = (DataConstraintImpl) o;
        return Objects.equals(getCubeRegions(), that.getCubeRegions())
            && Objects.equals(getDataContentKeys(), that.getDataContentKeys())
            && Objects.equals(getReleaseCalendar(), that.getReleaseCalendar());
    }

    @Override
    public DataConstraint toStub() {
        return toStub(createInstance());
    }

    @Override
    public DataConstraint toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
