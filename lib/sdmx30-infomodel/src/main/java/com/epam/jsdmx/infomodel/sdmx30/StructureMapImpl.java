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
public class StructureMapImpl
    extends MaintainableArtefactImpl
    implements StructureMap {

    private ArtefactReference source;
    private ArtefactReference target;
    private List<ComponentMap> componentMaps = new ArrayList<>();
    private List<FixedValueMap> fixedComponentMaps = new ArrayList<>();
    private List<EpochMap> epochMaps = new ArrayList<>();
    private List<DatePatternMap> datePatternMaps = new ArrayList<>();

    public StructureMapImpl(StructureMap from) {
        super(Objects.requireNonNull(from));
        if (from.getSource() != null) {
            this.source = new MaintainableArtefactReference(from.getSource());
        }
        if (from.getTarget() != null) {
            this.target = new MaintainableArtefactReference(from.getTarget());
        }
        this.componentMaps = StreamUtils.streamOfNullable(from.getComponentMaps())
            .map(ComponentMapImpl::new)
            .collect(toList());

        this.fixedComponentMaps = StreamUtils.streamOfNullable(from.getFixedComponentMaps())
            .map(FixedValueMapImpl::new)
            .collect(toList());

        this.epochMaps = StreamUtils.streamOfNullable(from.getEpochMaps())
            .map(EpochMapImpl::new)
            .collect(toList());

        this.datePatternMaps = StreamUtils.streamOfNullable(from.getDatePatternMaps())
            .map(DatePatternMapImpl::new)
            .collect(toList());
    }

    @Override
    public StructureMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public StructureMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.STRUCTURE_MAP;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        Set<CrossReference> crossReferences = new HashSet<>();

        if (source != null) {
            crossReferences.add(new CrossReferenceImpl(this, source));
        }
        if (target != null) {
            crossReferences.add(new CrossReferenceImpl(this, target));
        }

        StreamUtils.streamOfNullable(componentMaps)
            .filter(Objects::nonNull)
            .map(ComponentMap::getRepresentationMap)
            .filter(Objects::nonNull)
            .map(representationMapReference ->
                new CrossReferenceImpl(this, representationMapReference))
            .forEach(crossReferences::add);

        return crossReferences;
    }

    @Override
    protected StructureMapImpl createInstance() {
        return new StructureMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructureMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructureMap)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        StructureMap that = (StructureMapImpl) o;
        return Objects.equals(getSource(), that.getSource())
            && Objects.equals(getTarget(), that.getTarget())
            && Objects.equals(getComponentMaps(), that.getComponentMaps())
            && Objects.equals(getFixedComponentMaps(), that.getFixedComponentMaps())
            && Objects.equals(getEpochMaps(), that.getEpochMaps())
            && Objects.equals(getDatePatternMaps(), that.getDatePatternMaps());
    }
}
