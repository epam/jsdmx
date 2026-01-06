package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdentifiableArtefactReferenceTest {

    @Test
    public void testConceptReference() {
        final IdentifiableArtefactReferenceImpl reference = new IdentifiableArtefactReferenceImpl(
            "id",
            "agnc",
            "1.0.0",
            StructureClassImpl.CONCEPT,
            "item"
        );

        assertThat(reference.getMaintainableStructureClass()).isEqualTo(StructureClassImpl.CONCEPT_SCHEME);
    }

    @Test
    public void testCodeReference() {
        final IdentifiableArtefactReferenceImpl reference = new IdentifiableArtefactReferenceImpl(
            "id",
            "agnc",
            "1.0.0",
            StructureClassImpl.CODE,
            "item"
        );

        assertThat(reference.getMaintainableStructureClass()).isEqualTo(StructureClassImpl.CODELIST);
    }

    @Test
    public void testCategoryReference() {
        final IdentifiableArtefactReferenceImpl reference = new IdentifiableArtefactReferenceImpl(
            "id",
            "agnc",
            "1.0.0",
            StructureClassImpl.CATEGORY,
            "item"
        );

        assertThat(reference.getMaintainableStructureClass()).isEqualTo(StructureClassImpl.CATEGORY_SCHEME);
    }

    @Test
    public void testHierarchicalCode() {
        final IdentifiableArtefactReferenceImpl reference = new IdentifiableArtefactReferenceImpl(
            "id",
            "agnc",
            "1.0.0",
            StructureClassImpl.HIERARCHICAL_CODE,
            "item"
        );

        assertThat(reference.getMaintainableStructureClass()).isEqualTo(StructureClassImpl.HIERARCHY);
    }

}