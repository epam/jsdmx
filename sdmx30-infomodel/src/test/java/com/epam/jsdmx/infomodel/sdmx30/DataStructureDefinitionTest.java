package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class DataStructureDefinitionTest {

    @Test
    void getCrossReferences_metadataReference() {
        var subject = new DataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setMetadataStructure(new MaintainableArtefactReference("meta", "META", "1.0", StructureClassImpl.METADATA_STRUCTURE));

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences)
            .extracting(CrossReference::getId).containsExactly("meta");
        assertThat(crossReferences)
            .extracting(CrossReference::getOrganisationId).containsExactly("META");
        assertThat(crossReferences)
            .extracting(CrossReference::getVersionString).containsExactly("1.0");
        assertThat(crossReferences)
            .extracting(CrossReference::getStructureClass).containsExactly(StructureClassImpl.METADATA_STRUCTURE);
    }

    @Test
    void getCrossReferences_conceptSchemeReferencesFromMeasureConcepts() {
        var subject = new DataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setMeasureDescriptor(getMeasureDescriptor());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences).hasSize(3);

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_2(1.1)"
            );
    }

    private MeasureDescriptorImpl getMeasureDescriptor() {
        var d = new MeasureDescriptorImpl();

        var m1 = new MeasureImpl();
        m1.setConceptIdentity(new IdentifiableArtefactReferenceImpl("MEASURE_CONCEPT_SCH_1", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt"));

        var m2 = new MeasureImpl();
        m2.setConceptIdentity(new IdentifiableArtefactReferenceImpl("MEASURE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt1"));
        var m3 = new MeasureImpl();
        m3.setConceptIdentity(new IdentifiableArtefactReferenceImpl("MEASURE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt2"));

        var m4 = new MeasureImpl();
        m4.setConceptIdentity(new IdentifiableArtefactReferenceImpl("MEASURE_CONCEPT_SCH_2", "AGNC", "1.1", StructureClassImpl.CONCEPT, "cncpt3"));

        d.setComponents(List.of(m1, m2, m3, m4));
        return d;
    }

    @Test
    void getCrossReferences_conceptSchemeReferencesFromDimensionDescriptor() {
        var subject = new DataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setDimensionDescriptor(getDimenstionDescriptor());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences).hasSize(3);

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_2(1.1)"
            );
    }

    private DimensionDescriptorImpl getDimenstionDescriptor() {
        var dd = new DimensionDescriptorImpl();

        var d1 = new DimensionImpl();
        d1.setConceptIdentity(new IdentifiableArtefactReferenceImpl("DIMENSION_CONCEPT_SCH_1", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt"));

        var d2 = new DimensionImpl();
        d2.setConceptIdentity(new IdentifiableArtefactReferenceImpl("DIMENSION_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt1"));

        var d3 = new DimensionImpl();
        d3.setConceptIdentity(new IdentifiableArtefactReferenceImpl("DIMENSION_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt2"));

        var d4 = new DimensionImpl();
        d4.setConceptIdentity(new IdentifiableArtefactReferenceImpl("DIMENSION_CONCEPT_SCH_2", "AGNC", "1.1", StructureClassImpl.CONCEPT, "cncpt3"));

        dd.setComponents(List.of(d1, d2, d3, d4));
        return dd;
    }

    @Test
    void getCrossReferences_conceptSchemeReferencesFromAttributeDescriptor() {
        var subject = new DataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setAttributeDescriptor(getAttributeDescriptor());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences).hasSize(3);

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.1)"
            );
    }

    private AttributeDescriptorImpl getAttributeDescriptor() {
        AttributeDescriptorImpl ad = new AttributeDescriptorImpl();

        var a1 = new DataAttributeImpl();
        a1.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_1", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt"));

        var a2 = new DataAttributeImpl();
        a2.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt1"));

        var a3 = new DataAttributeImpl();
        a3.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.0", StructureClassImpl.CONCEPT, "cncpt2"));

        var a4 = new DataAttributeImpl();
        a4.setConceptIdentity(new IdentifiableArtefactReferenceImpl("ATTRIBUTE_CONCEPT_SCH_2", "AGNC", "1.1", StructureClassImpl.CONCEPT, "cncpt3"));

        ad.setComponents(List.of(a1, a2, a3, a4));
        return ad;
    }

    @Test
    void getCrossReferences_returnsAll() {
        var subject = new DataStructureDefinitionImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setMetadataStructure(new MaintainableArtefactReference("meta", "META", "1.0", StructureClassImpl.METADATA_STRUCTURE));
        subject.setMeasureDescriptor(getMeasureDescriptor());
        subject.setDimensionDescriptor(getDimenstionDescriptor());
        subject.setAttributeDescriptor(getAttributeDescriptor());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences).extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:MEASURE_CONCEPT_SCH_2(1.1)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:DIMENSION_CONCEPT_SCH_2(1.1)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=AGNC:ATTRIBUTE_CONCEPT_SCH_2(1.1)",
                "urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataStructure=META:meta(1.0)");
    }

}