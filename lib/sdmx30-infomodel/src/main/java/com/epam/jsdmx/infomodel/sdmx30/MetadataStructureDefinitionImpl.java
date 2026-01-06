package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MetadataStructureDefinitionImpl extends StructureImpl implements MetadataStructureDefinition {

    @Setter
    @Getter
    private MetadataAttributeDescriptor attributeDescriptor;

    public MetadataStructureDefinitionImpl(MetadataStructureDefinition from) {
        super(Objects.requireNonNull(from));
        if (from.getAttributeDescriptor() != null) {
            this.attributeDescriptor = new MetadataAttributeDescriptorImpl(from.getAttributeDescriptor());
        }
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_STRUCTURE;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Optional.ofNullable(attributeDescriptor)
            .stream()
            .map(MetadataAttributeDescriptor::getComponents)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .flatMap(this::toConceptSchemeReference)
            .map(ref -> new CrossReferenceImpl(this, ref))
            .collect(toSet());
    }

    private Stream<ArtefactReference> toConceptSchemeReference(MetadataAttribute attribute) {
        Stream.Builder<ArtefactReference> builder = Stream.builder();
        ArtefactReference conceptReference = attribute.getConceptIdentity();
        builder.add(toReference(conceptReference, StructureClassImpl.CONCEPT_SCHEME));
        StreamUtils.streamOfNullable(attribute.getHierarchy())
            .flatMap(this::toConceptSchemeReference)
            .forEach(builder::add);
        return builder.build();
    }

    @Override
    protected MetadataStructureDefinitionImpl createInstance() {
        return new MetadataStructureDefinitionImpl();
    }

    @Override
    public MetadataStructureDefinition toStub() {
        return toStub(createInstance());
    }

    @Override
    public MetadataStructureDefinition toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    public List<MetadataAttribute> getComponents() {
        return Stream.ofNullable(attributeDescriptor)
            .map(MetadataAttributeDescriptor::getComponents)
            .flatMap(List::stream)
            .collect(toList());
    }

    public MetadataAttribute getComponent(String id) {
        return getComponents().stream()
            .collect(toMap(MetadataAttribute::getId, identity())).get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataStructureDefinition)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataStructureDefinition)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        MetadataStructureDefinitionImpl that = (MetadataStructureDefinitionImpl) o;
        return Objects.equals(getAttributeDescriptor(), that.getAttributeDescriptor());
    }
}
