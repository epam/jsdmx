package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class MetadataStructureDefinitionTest {

    @Test
    void getCrossReferences_returnsUnique() {
        var subject = new MetadataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setAttributeDescriptor(getAttributeDescriptor());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.1)"
            );
    }

    @Test
    void getCrossReferences_returnsNested() {
        var subject = new MetadataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        MetadataAttributeDescriptorImpl attributeDescriptor = getAttributeDescriptor();
        var nested1 = new MetadataAttributeImpl();
        nested1.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_4", "AGNC", "3.0", StructureClassImpl.CONCEPT, "cncpt"));
        var nested0 = new MetadataAttributeImpl();
        nested0.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_3", "AGNC", "2.0", StructureClassImpl.CONCEPT, "cncpt"));
        nested0.setHierarchy(List.of(nested1));
        var metadataAttribute = (MetadataAttributeImpl) attributeDescriptor.getComponents().get(0);
        metadataAttribute.setHierarchy(List.of(nested0));
        subject.setAttributeDescriptor(attributeDescriptor);
        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();
        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.1)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_3(2.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_4(3.0)"
            );
    }

    private MetadataAttributeDescriptorImpl getAttributeDescriptor() {
        var descriptor = new MetadataAttributeDescriptorImpl();

        var a1 = new MetadataAttributeImpl();
        a1.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_1", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt"));

        var a2 = new MetadataAttributeImpl();
        a2.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt1"));

        var a3 = new MetadataAttributeImpl();
        a3.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt2"));

        var a4 = new MetadataAttributeImpl();
        a4.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.1", StructureClassImpl.CONCEPT, "cncpt3"));

        descriptor.setComponents(List.of(a1, a2, a3, a4));
        return descriptor;
    }

}