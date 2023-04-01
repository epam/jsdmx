package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ConceptSchemeTest {

    @Test
    void crossReferences_whenThereAreConceptsAndReferences() {
        var conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setVersion(Version.createFromString("1.0"));
        conceptScheme.setItems(getConceptsWithNestingAndReferences());

        Set<CrossReference> crossReferences = conceptScheme.getReferencedArtefacts();

        assertThat(crossReferences).hasSize(3);
        assertThat(crossReferences).extracting(CrossReference::getId)
            .containsExactlyInAnyOrder("CODELIST_1", "CODELIST_2", "CODELIST_3");
    }

    @Test
    void crossReferences_whereThereAreNoConcepts() {
        var conceptScheme = new ConceptSchemeImpl();

        Set<CrossReference> crossReferences = conceptScheme.getReferencedArtefacts();

        assertThat(crossReferences).isEmpty();
    }

    @Test
    void crossReferences_whereThereAreConceptsButNoReferences() {
        var conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setItems(getConceptsWithNestingButWithoutReferences());

        Set<CrossReference> crossReferences = conceptScheme.getReferencedArtefacts();

        assertThat(crossReferences).isEmpty();
    }

    private List<Concept> getConceptsWithNestingAndReferences() {
        var c1 = new ConceptImpl();
        c1.setCoreRepresentation(new EnumeratedRepresentationImpl(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_1(1.0)")));

        var c2 = new ConceptImpl();
        c2.setCoreRepresentation(new BaseTextFormatRepresentationImpl(FacetValueType.STRING));

        var c3 = new ConceptImpl();
        c3.setCoreRepresentation(new EnumeratedRepresentationImpl(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_2(1.0)")));

        c2.addChild(c3);

        var c4 = new ConceptImpl();
        c4.setCoreRepresentation(new EnumeratedRepresentationImpl(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_3(1.0)")
        ));

        c3.addChild(c4);

        return List.of(
            c1,
            c2
        );
    }

    private List<Concept> getConceptsWithNestingButWithoutReferences() {
        var c1 = new ConceptImpl();
        c1.setCoreRepresentation(new BaseTextFormatRepresentationImpl(FacetValueType.STRING));

        var c2 = new ConceptImpl();
        c2.setCoreRepresentation(new BaseTextFormatRepresentationImpl(FacetValueType.STRING));
        var c3 = new ConceptImpl();
        c3.setCoreRepresentation(new BaseTextFormatRepresentationImpl(FacetValueType.STRING));

        c2.addChild(c3);

        var c4 = new ConceptImpl();
        c4.setCoreRepresentation(new BaseTextFormatRepresentationImpl(FacetValueType.STRING));

        c3.addChild(c4);

        return List.of(
            c1,
            c2
        );
    }
}