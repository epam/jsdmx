package com.epam.jsdmx.infomodel.sdmx30;

import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CATEGORY;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CATEGORY_SCHEME;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CODE;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CODELIST;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CONCEPT;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.CONCEPT_SCHEME;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.HIERARCHICAL_CODE;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.HIERARCHY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StructureItemReferenceTest {

    @Test
    void conceptReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl("id", "agnc", "1.0", CONCEPT_SCHEME, "item");
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CONCEPT);
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CONCEPT_SCHEME);
    }

    @Test
    void codeReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl("id", "agnc", "1.0", CODELIST, "item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CODE);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CODELIST);
    }

    @Test
    void hierarchicalCodeReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl("id", "agnc", "1.0", HIERARCHY, "item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(HIERARCHICAL_CODE);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(HIERARCHY);
    }

    @Test
    void categoryReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl("id", "agnc", "1.0", CATEGORY_SCHEME, "item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CATEGORY);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CATEGORY_SCHEME);
    }


    @Test
    void fromString_conceptReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:" + CONCEPT_SCHEME.getFullyQualifiedName() + "=agnc:id(1.0).item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CONCEPT);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CONCEPT_SCHEME);
    }

    @Test
    void fromString_codeReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:" + CODELIST.getFullyQualifiedName() + "=agnc:id(1.0).item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CODE);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CODELIST);
    }

    @Test
    void fromString_hierarchicalCodeReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:" + HIERARCHY.getFullyQualifiedName() + "=agnc:id(1.0).item");

        assertThat(structureItemReference.getStructureClass()).isEqualTo(HIERARCHICAL_CODE);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(HIERARCHY);
    }

    @Test
    void fromString_categoryReference() {
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:" + CATEGORY_SCHEME.getFullyQualifiedName() + "=agnc:id(1.0).item");
        assertThat(structureItemReference.getStructureClass()).isEqualTo(CATEGORY);
        assertThat(structureItemReference.getItemId()).isEqualTo("item");
        assertThat(structureItemReference.getMaintainableArtefactReference().getStructureClass()).isEqualTo(CATEGORY_SCHEME);
    }

}