package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toSet;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConceptSchemeImpl
    extends ItemSchemeImpl<Concept>
    implements ConceptScheme {

    public ConceptSchemeImpl(ConceptScheme itemScheme) {
        super(Objects.requireNonNull(itemScheme));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CONCEPT_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return getItems().stream()
            .flatMap(this::findEnumeratedConceptReferences)
            .collect(toSet());
    }

    private Stream<CrossReference> findEnumeratedConceptReferences(Concept concept) {
        final var crossRefStreamBuilder = Stream.<CrossReference>builder();

        if (concept.getCoreRepresentation().isEnumerated()) {
            crossRefStreamBuilder.accept(
                new CrossReferenceImpl(this, concept.getCoreRepresentation().enumerated())
            );
        }

        concept.getHierarchy().stream()
            .flatMap(this::findEnumeratedConceptReferences)
            .forEach(crossRefStreamBuilder::add);

        return crossRefStreamBuilder.build();
    }

    @Override
    protected ConceptSchemeImpl createInstance() {
        return new ConceptSchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public ConceptScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public ConceptScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
