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
import org.apache.commons.collections4.CollectionUtils;

@Getter
@Setter
@NoArgsConstructor
public class RepresentationMapImpl
    extends MaintainableArtefactImpl
    implements RepresentationMap {

    private List<ValueRepresentation> source = new ArrayList<>();
    private List<ValueRepresentation> target = new ArrayList<>();
    private List<RepresentationMapping> representationMappings = new ArrayList<>();

    public RepresentationMapImpl(RepresentationMap from) {
        super(Objects.requireNonNull(from));
        this.source = StreamUtils.streamOfNullable(from.getSource())
            .map(sourceItem -> (ValueRepresentation) sourceItem.clone())
            .collect(toList());

        this.target = StreamUtils.streamOfNullable(from.getTarget())
            .map(targetItem -> (ValueRepresentation) targetItem.clone())
            .collect(toList());

        this.representationMappings = StreamUtils.streamOfNullable(from.getRepresentationMappings())
            .map(RepresentationMappingImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.REPRESENTATION_MAP;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        Set<CrossReference> crossReferences = new HashSet<>();
        populateWithValueRepresentationCrossReferences(crossReferences, source);
        populateWithValueRepresentationCrossReferences(crossReferences, target);
        return crossReferences;
    }

    private void populateWithValueRepresentationCrossReferences(Set<CrossReference> references,
                                                                List<ValueRepresentation> valueRepresentations) {
        if (CollectionUtils.isEmpty(valueRepresentations)) {
            return;
        }
        valueRepresentations.stream()
            .filter(Objects::nonNull)
            .filter(valueRepresentation -> valueRepresentation instanceof ListReferenceValueRepresentation)
            .map(valueRepresentation -> ((ListReferenceValueRepresentation) valueRepresentation).getListReference())
            .map(listReference -> new CrossReferenceImpl(this, listReference))
            .forEach(references::add);
    }

    @Override
    protected RepresentationMapImpl createInstance() {
        return new RepresentationMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentationMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentationMap)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        RepresentationMap that = (RepresentationMapImpl) o;
        return Objects.equals(getSource(), that.getSource())
            && Objects.equals(getTarget(), that.getTarget())
            && Objects.equals(getRepresentationMappings(), that.getRepresentationMappings());
    }

    @Override
    public RepresentationMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public RepresentationMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
