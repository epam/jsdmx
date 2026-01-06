package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdentifiableArtefactImplTest {

    @Test
    void toString_identifiableWithoutContainer() {
        final ConceptImpl concept = new ConceptImpl();
        concept.setId("OBS_VALUE");

        assertThat(concept.toString()).isEqualTo("Concept=OBS_VALUE");
    }

    @Test
    void toString_identifiableWithContainer() {
        final ConceptImpl concept = new ConceptImpl();
        concept.setId("OBS_VALUE");
        concept.setContainer(new MaintainableArtefactReference("CROSS_DOMAIN_CONCEPTS", "SDMX", "1.0", StructureClassImpl.CONCEPT_SCHEME));

        assertThat(concept.toString()).isEqualTo("Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE");
    }

    @Test
    void toString_maintainable() {
        final ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setId("CROSS_DOMAIN_CONCEPTS");
        conceptScheme.setOrganizationId("SDMX");
        conceptScheme.setVersion(Version.createFromString("1.0"));

        assertThat(conceptScheme.toString()).isEqualTo("ConceptScheme=SDMX:CROSS_DOMAIN_CONCEPTS(1.0)");
    }

}