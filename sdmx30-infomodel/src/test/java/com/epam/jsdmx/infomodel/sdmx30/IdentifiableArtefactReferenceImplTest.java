package com.epam.jsdmx.infomodel.sdmx30;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class IdentifiableArtefactReferenceImplTest {
    @Test
    void testToString_happy() {
        var subject = new IdentifiableArtefactReferenceImpl("id", "organisation", "1.0.0", StructureClassImpl.CONCEPT, "itemId");

        Assertions.assertThat(subject.toString()).isEqualTo("Concept=organisation:id(1.0.0).itemId");
    }

    @Test
    void testToString_itemIdIsNull() {
        var subject = new IdentifiableArtefactReferenceImpl("id", "organisation", "1.0.0", StructureClassImpl.CONCEPT, null);

        Assertions.assertThat(subject.toString()).isEqualTo("Concept=organisation:id(1.0.0).null");
    }
}