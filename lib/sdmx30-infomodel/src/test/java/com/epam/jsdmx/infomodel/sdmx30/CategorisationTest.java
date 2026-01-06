package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

class CategorisationTest {

    @Test
    void getCrossReferences_includesCategorySchemeAndCategorizedStructure() {
        var subject = new CategorisationImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setCategorizedBy(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=IMF:CatScheme(1.0).root"));
        subject.setCategorizedArtefact(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_2(1.0)"));

        final Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.categoryscheme.CategoryScheme=IMF:CatScheme(1.0)",
                "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_2(1.0)"
            );
    }
}