package com.epam.jsdmx.json10.structure;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.AgencyImpl;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.AnnotationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseTextFormatRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormatImpl;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.ContactImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySetImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponentImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.EnumeratedRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.LevelImpl;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRefImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreement;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreementImpl;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.json10.structure.writer.AnnotableWriter;
import com.epam.jsdmx.json10.structure.writer.AttributeListWriter;
import com.epam.jsdmx.json10.structure.writer.CategorisationWriter;
import com.epam.jsdmx.json10.structure.writer.CategorySchemeWriter;
import com.epam.jsdmx.json10.structure.writer.CodeWriterImpl;
import com.epam.jsdmx.json10.structure.writer.CodelistWriter;
import com.epam.jsdmx.json10.structure.writer.ComponentWriter;
import com.epam.jsdmx.json10.structure.writer.ConceptRoleWriter;
import com.epam.jsdmx.json10.structure.writer.ConceptSchemeWriter;
import com.epam.jsdmx.json10.structure.writer.ContactsWriter;
import com.epam.jsdmx.json10.structure.writer.DataStructureDefinitionWriter;
import com.epam.jsdmx.json10.structure.writer.DataflowWriter;
import com.epam.jsdmx.json10.structure.writer.DefaultHeaderProvider;
import com.epam.jsdmx.json10.structure.writer.DimensionListWriter;
import com.epam.jsdmx.json10.structure.writer.GroupDimensionListWriter;
import com.epam.jsdmx.json10.structure.writer.HierarchicalCodelistWriter;
import com.epam.jsdmx.json10.structure.writer.IdentifiableWriter;
import com.epam.jsdmx.json10.structure.writer.Json10StructureWriter;
import com.epam.jsdmx.json10.structure.writer.LinksWriter;
import com.epam.jsdmx.json10.structure.writer.MeasureListWriter;
import com.epam.jsdmx.json10.structure.writer.MetaWriter;
import com.epam.jsdmx.json10.structure.writer.NameableWriter;
import com.epam.jsdmx.json10.structure.writer.OrganisationWriter;
import com.epam.jsdmx.json10.structure.writer.RepresentationWriter;
import com.epam.jsdmx.json10.structure.writer.VersionableWriter;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.StubDataStructureLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class MaintainableArtifactsTestUtils {

    /**
     * sets common fields for maintainable artefact
     *
     * @param artefact - artefact to set fields for
     */
    public static void setMaintainableArtefact(MaintainableArtefactImpl artefact) {
        artefact.setId("ARTEFACT");
        artefact.setVersion(Version.createFromString("1.2"));
        artefact.setOrganizationId("IMF");
        artefact.setName(new InternationalString("Maintanable artefact"));
        artefact.getName().add(Locale.CANADA_FRENCH.toLanguageTag(), "artefact");
        String date = "2022-07-17T10:11:16Z";

        artefact.setValidTo(date);
        artefact.setValidFrom(date);
        artefact.setDescription(new InternationalString("Some artefact"));
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("1");
        annotation.setText(new InternationalString("text"));
        annotation.setType("type1");
        artefact.addAnnotation(annotation);
    }

    /**
     * @return Categorisation object
     */
    public static CategorisationImpl buildCategorisation() {
        CategorisationImpl categorisation = new CategorisationImpl();
        setMaintainableArtefact(categorisation);
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.CATEGORY,
            "CAT_TARGET"
        );

        categorisation.setCategorizedBy(structureItemReference);
        IdentifiableArtefactReferenceImpl structureReference = new IdentifiableArtefactReferenceImpl(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.CATEGORISATION,
            null
        );
        categorisation.setCategorizedArtefact(structureReference);
        return categorisation;
    }

    /**
     * @return CategoryScheme object
     */
    public static CategorySchemeImpl buildCategoryScheme() {
        CategorySchemeImpl categoryScheme = new CategorySchemeImpl();
        setMaintainableArtefact(categoryScheme);
        categoryScheme.setPartial(true);
        CategoryImpl category = new CategoryImpl();
        category.setDescription(new InternationalString("description"));
        category.setId("Category-1");
        category.setName(new InternationalString("Category1"));
        category.setDescription(new InternationalString("description category 1"));
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Annot");
        category.setAnnotations(List.of(annotation));
        CategoryImpl category2 = new CategoryImpl();
        category2.setDescription(new InternationalString("description category2"));
        category2.setId("Category-2");
        category2.setName(new InternationalString("Category2"));
        AnnotationImpl annotation2 = new AnnotationImpl();
        annotation2.setId("Annot2");
        category2.setAnnotations(List.of(annotation, annotation2));
        CategoryImpl category3 = new CategoryImpl();
        category3.setDescription(new InternationalString("description category3"));
        category3.setId("Category-3");
        category3.setName(new InternationalString("Category3"));
        category.setHierarchy(List.of(category2, category3));

        CategoryImpl category4 = new CategoryImpl();
        category4.setId("Category-4");
        category4.setName(new InternationalString("Category4"));
        categoryScheme.setItems(List.of(category, category4));
        return categoryScheme;
    }

    /**
     * @return Codelist object
     */
    public static CodelistImpl buildCodeList() {
        CodelistImpl codelist = new CodelistImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(codelist);
        codelist.setPartial(true);
        codelist.setExtensions(getExtensions());
        fillCodes(codelist);
        return codelist;
    }

    private static void fillCodes(CodelistImpl codelist) {
        CodeImpl code = new CodeImpl();
        code.setDescription(new InternationalString("code 1 desc"));
        code.setId("CODE1");
        code.setName(new InternationalString("code 1"));
        code.setParentId("CODE2");
        CodeImpl code2 = new CodeImpl();
        code2.setDescription(new InternationalString("code 2 desc"));
        code2.setId("CODE2");
        code2.setName(new InternationalString("code 2"));
        code2.setHierarchy(List.of(code));
        codelist.setItems(List.of(code, code2));
    }

    private static List<CodelistExtension> getExtensions() {
        CodelistExtensionImpl inclusiveCodeListExtension = fillInclusiveCodeListExtension();
        CodelistExtensionImpl exclusiveCodeListExtension = fillExclusiveCodeListExtension();
        return List.of(inclusiveCodeListExtension, exclusiveCodeListExtension);
    }

    private static CodelistExtensionImpl fillExclusiveCodeListExtension() {
        CodelistExtensionImpl codelistExtension = new CodelistExtensionImpl();
        ExclusiveCodeSelectionImpl exclusiveCodeSelection = new ExclusiveCodeSelectionImpl();
        MemberValueImpl memberValue = new MemberValueImpl();
        memberValue.setCascadeValue(CascadeValue.FALSE);
        memberValue.setValue("mem2");
        exclusiveCodeSelection.setMembers(List.of(memberValue));
        return fillCodeListExtension(codelistExtension, exclusiveCodeSelection);
    }

    private static CodelistExtensionImpl fillInclusiveCodeListExtension() {
        CodelistExtensionImpl codelistExtension = new CodelistExtensionImpl();
        InclusiveCodeSelectionImpl inclusiveCodeSelection = new InclusiveCodeSelectionImpl();
        MemberValueImpl memberValue = new MemberValueImpl();
        memberValue.setCascadeValue(CascadeValue.TRUE);
        memberValue.setValue("mem1");
        inclusiveCodeSelection.setMembers(List.of(memberValue));
        return fillCodeListExtension(codelistExtension, inclusiveCodeSelection);
    }

    private static CodelistExtensionImpl fillCodeListExtension(CodelistExtensionImpl codelistExtension, BaseCodeSelectionImpl codeSelection) {
        codelistExtension.setPrefix("PREF2");
        MaintainableArtefactReference maintainableReference = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_AGE(2.1.1)");
        codelistExtension.setCodelist(maintainableReference);
        codelistExtension.setCodeSelection(codeSelection);
        return codelistExtension;
    }

    /**
     * @return ConceptScheme object
     */
    public static ConceptSchemeImpl buildConceptScheme() {
        ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        setMaintainableArtefact(conceptScheme);
        ConceptImpl concept = new ConceptImpl();
        concept.setId("COUNTRY");
        concept.setName(new InternationalString("Country"));
        IsoConceptReferenceImpl isoConceptReference = new IsoConceptReferenceImpl();
        isoConceptReference.setConceptId("Conn");
        isoConceptReference.setAgency("QHB");
        isoConceptReference.setSchemeId("Schema");
        concept.setIsoConceptReference(isoConceptReference);
        concept.setCoreRepresentation(getRepresentation());
        conceptScheme.setItems(List.of(concept));
        conceptScheme.setPartial(true);
        return conceptScheme;
    }

    private static Representation getRepresentation() {
        Set<Facet> facetSet = new HashSet<>();
        BaseFacetImpl facet1 = new BaseFacetImpl();
        BaseFacetImpl facet2 = new BaseFacetImpl();
        facet1.setValueType(FacetValueType.ALPHA);
        SentinelValueImpl sentinelValue = new SentinelValueImpl();
        sentinelValue.setValue("val");
        sentinelValue.setName(new InternationalString("sent"));
        sentinelValue.setDescription(new InternationalString("sentinel descr"));
        facet2.setSentinelValues(List.of(sentinelValue));
        facet2.setType(FacetType.SENTINEL_VALUES);
        facet2.setValueType(FacetValueType.ALPHA);
        facetSet.addAll(List.of(facet1, facet2));
        return new BaseTextFormatRepresentationImpl(facetSet);
    }

    /**
     * @return DataConstraint object
     */
    public static DataConstraintImpl buildDataConstraint() {
        DataConstraintImpl dataConstraint = new DataConstraintImpl();
        setMaintainableArtefact(dataConstraint);
        dataConstraint.setConstraintRoleType(ConstraintRoleType.ALLOWABLE_CONTENT);

        LocalisedMemberValueImpl selectionValue = new LocalisedMemberValueImpl();
        selectionValue.setValue("selectionValue local");
        selectionValue.setLocale("en");

        MemberValueImpl selectionValue1 = new MemberValueImpl();
        selectionValue1.setValue("selectionValue1");

        MemberSelectionImpl memberSelection1 = new MemberSelectionImpl();
        memberSelection1.setSelectionValues(List.of(selectionValue, selectionValue1));
        memberSelection1.setComponentId("some component id 1");
        memberSelection1.setIncluded(true);
        memberSelection1.setRemovePrefix(true);

        MemberValueImpl selectionValue2 = new MemberValueImpl();
        selectionValue2.setValue("selectionValue2");
        selectionValue2.setCascadeValue(CascadeValue.TRUE);
        selectionValue2.setValidTo(Instant.parse("2020-05-12T12:00:00.000000Z"));
        selectionValue2.setValidFrom(Instant.parse("2020-04-12T12:00:00.000000Z"));
        MemberValueImpl selectionValue5 = new MemberValueImpl();
        selectionValue5.setValue("selectionValue5");

        MemberSelectionImpl memberSelection2 = new MemberSelectionImpl();
        memberSelection2.setSelectionValues(List.of(selectionValue2, selectionValue5));
        memberSelection2.setComponentId("some component id 2");
        memberSelection2.setIncluded(true);
        memberSelection2.setRemovePrefix(true);

        BeforePeriodImpl selectionValue3 = new BeforePeriodImpl();
        selectionValue3.setPeriod("2020");
        AfterPeriodImpl selectionValue4 = new AfterPeriodImpl();
        selectionValue4.setPeriod("2021");

        CubeRegionKeyImpl cubeRegionKey1 = new CubeRegionKeyImpl();
        cubeRegionKey1.setComponentId("123");
        cubeRegionKey1.setIncluded(true);
        cubeRegionKey1.setRemovePrefix(true);
        cubeRegionKey1.setValidFrom(Instant.parse("2020-04-12T12:00:00.000000Z"));
        cubeRegionKey1.setValidTo(Instant.parse("2022-04-12T12:00:00.000000Z"));
        cubeRegionKey1.setSelectionValues(List.of(selectionValue3, selectionValue4));

        CubeRegionImpl cubeRegion = new CubeRegionImpl();
        cubeRegion.setIncluded(true);
        cubeRegion.setMemberSelections(List.of(memberSelection1, memberSelection2));
        cubeRegion.setCubeRegionKeys(List.of(cubeRegionKey1));
        cubeRegion.setAnnotations(getAnnotations());

        dataConstraint.setCubeRegions(List.of(cubeRegion));
        dataConstraint.setReleaseCalendar(createReleaseCalendar());
        dataConstraint.setDataContentKeys(List.of(createDataKeySets()));
        dataConstraint.setConstrainedArtefacts(List.of(buildProvisionAgreement().getDataProvider()));

        return dataConstraint;
    }

    /**
     * @return MetaDataConstraint object
     */
    public static MetadataConstraintImpl buildMetadataConstraint() {
        MetadataConstraintImpl metadataConstraint = new MetadataConstraintImpl();
        setMaintainableArtefact(metadataConstraint);
        metadataConstraint.setConstraintRoleType(ConstraintRoleType.ALLOWABLE_CONTENT);
        metadataConstraint.setReleaseCalendar(createReleaseCalendar());

        IdentifiableArtefactReferenceImpl maintainableArtefactReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataStructure=QUANTHUB:ECOFIN_DSD(1.2)");
        metadataConstraint.setConstrainedArtefacts(List.of(buildProvisionAgreement().getDataProvider(), maintainableArtefactReference));

        MetadataTargetRegionImpl metadataTargetRegion = new MetadataTargetRegionImpl();
        metadataTargetRegion.setIncluded(true);
        List<MemberSelection> memberSelectionList = getMemberSelections(false, true);
        metadataTargetRegion.setMemberSelections(memberSelectionList);
        metadataConstraint.setMetadataTargetRegions(List.of(metadataTargetRegion));

        return metadataConstraint;
    }

    /**
     * @return DataStructureDefinition object
     */
    public static DataStructureDefinitionImpl buildDataStructureDefinition() {
        DataStructureDefinitionImpl dataStructureDefinition = new DataStructureDefinitionImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(dataStructureDefinition);
        MaintainableArtefactReference maintainableReference = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataStructure=QUANTHUB:ECOFIN_DSD(1.2)");
        dataStructureDefinition.setMetadataStructure(maintainableReference);
        dataStructureDefinition.setDimensionDescriptor(fillDimensionDescriptor());
        dataStructureDefinition.setAttributeDescriptor(fillAttributeDescriptor());
        dataStructureDefinition.setMeasureDescriptor(fillMeasureDescriptor());
        dataStructureDefinition.setGroupDimensionDescriptor(fillGroupDimensionDescriptor());
        return dataStructureDefinition;
    }

    /**
     * @return MetadataStructureDefinition object
     */
    public static MetadataStructureDefinitionImpl buildMetadataStructureDefinition() {
        MetadataStructureDefinitionImpl metadataStructureDefinition = new MetadataStructureDefinitionImpl();
        setMaintainableArtefact(metadataStructureDefinition);
        MetadataAttributeDescriptorImpl metadataAttributeDescriptor = new MetadataAttributeDescriptorImpl();
        metadataAttributeDescriptor.setId("MetadataAttributeDescriptor");
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Annot-1");
        MetadataAttributeImpl metadataAttribute = getMetadataAttribute("meta-1", "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST(1.0)");
        metadataAttribute.setHierarchy(List.of(getMetadataAttribute("meta-2", "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST(1.0)")));
        metadataAttributeDescriptor.setComponents(List.of(metadataAttribute));
        metadataAttributeDescriptor.addAnnotation(annotation);
        metadataStructureDefinition.setAttributeDescriptor(metadataAttributeDescriptor);
        return metadataStructureDefinition;
    }

    private static MetadataAttributeImpl getMetadataAttribute(String id, String reference) {
        MetadataAttributeImpl metadataAttribute = new MetadataAttributeImpl();
        metadataAttribute.setId(id);
        metadataAttribute.setMaxOccurs(20);
        metadataAttribute.setMinOccurs(10);
        metadataAttribute.setPresentational(true);
        Representation representation = new EnumeratedRepresentationImpl(
            new MaintainableArtefactReference(reference));
        metadataAttribute.setLocalRepresentation(representation);
        metadataAttribute.setConceptIdentity(
            new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:DIMENSION(1.6.1).DIM"));
        return metadataAttribute;
    }

    private static List<GroupDimensionDescriptorImpl> fillGroupDimensionDescriptor() {
        GroupDimensionDescriptorImpl d = new GroupDimensionDescriptorImpl();
        d.setId("GROUP1");
        d.setDimensions(List.of("DIM1"));
        return List.of(d);
    }

    /**
     * @return AttributeDescriptor
     */
    public static AttributeDescriptorImpl fillAttributeDescriptor() {
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId("AttributeDescriptor");
        fillMetadata(attributeDescriptor);
        fillComponents(attributeDescriptor);
        return attributeDescriptor;
    }


    private static void fillComponents(AttributeDescriptorImpl attributeDescriptor) {
        DataAttributeImpl dataAttribute = new DataAttributeImpl();
        dataAttribute.setId("DA1");
        dataAttribute.setMinOccurs(0);
        DimensionRelationshipImpl rel = new DimensionRelationshipImpl();
        rel.setDimensions(List.of("DIM1"));
        rel.setGroupKeys(List.of("GROUP1"));
        dataAttribute.setAttributeRelationship(rel);
        BaseFacetImpl baseFacet = new BaseFacetImpl();
        baseFacet.setValueType(FacetValueType.ALPHA_NUMERIC);
        dataAttribute.setLocalRepresentation(new BaseTextFormatRepresentationImpl(Set.of(baseFacet)));
        dataAttribute.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER_DIAGNOSTICS_COLLECTION_SCHEME(1.5.1).DATE_VALUE"));
        dataAttribute.setConceptRoles(
            List.of(
                new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER(1.5.1).DATE_VALUE"),
                new IdentifiableArtefactReferenceImpl(
                    "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER_DIAGNOSTICS(1.5.2).DATE_VALUE")
            ));
        MeasureRelationshipImpl measureRelationship = new MeasureRelationshipImpl();
        measureRelationship.setMeasures(List.of("MO"));
        dataAttribute.setMeasureRelationship(measureRelationship);
        attributeDescriptor.setComponents(List.of(dataAttribute));
    }

    private static void fillMetadata(AttributeDescriptorImpl attributeDescriptor) {
        MetadataAttributeRefImpl attributeRef1 = new MetadataAttributeRefImpl();
        attributeRef1.setId("REFERENCE-1");
        attributeRef1.setMetadataRelationship(new ObservationRelationshipImpl());
        MetadataAttributeRefImpl attributeRef2 = new MetadataAttributeRefImpl();
        attributeRef2.setId("REFERENCE-2");
        DimensionRelationshipImpl dimensionRelationship = new DimensionRelationshipImpl();
        dimensionRelationship.setDimensions(List.of("dim1", "dim2"));
        attributeRef2.setMetadataRelationship(dimensionRelationship);
        MetadataAttributeRefImpl attributeRef3 = new MetadataAttributeRefImpl();
        attributeRef3.setId("REFERENCE-3");
        attributeRef3.setMetadataRelationship(null);
        attributeDescriptor.setMetadataAttributes(List.of(attributeRef1, attributeRef2, attributeRef3));
    }

    /**
     * @return List<GroupDimensionDescriptorImpl>
     */
    public static List<GroupDimensionDescriptorImpl> fillListGroupDimensionDescriptor() {
        GroupDimensionDescriptorImpl groupDimensionDescriptor1 = new GroupDimensionDescriptorImpl();
        AnnotationImpl annotation1 = new AnnotationImpl();
        annotation1.setId("id1");
        annotation1.setValue("val");
        AnnotationImpl annotation2 = new AnnotationImpl();
        annotation2.setId("id2");
        annotation2.setValue("val");
        groupDimensionDescriptor1.setId("GroupDimensionDescriptor1");
        groupDimensionDescriptor1.setAnnotations(List.of(annotation1, annotation2));
        groupDimensionDescriptor1.setDimensions(List.of("dim1", "dim12"));

        GroupDimensionDescriptorImpl groupDimensionDescriptor2 = new GroupDimensionDescriptorImpl();
        groupDimensionDescriptor2.setId("GroupDimensionDescriptor2");
        groupDimensionDescriptor2.setDimensions(List.of("dim21", "dim22"));

        return List.of(groupDimensionDescriptor1, groupDimensionDescriptor2);
    }

    /**
     * @return DimensionDescriptor
     */
    public static DimensionDescriptorImpl fillDimensionDescriptor() {
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        dimensionDescriptor.setId("DimensionDescriptor");
        fillDimensionComponents(dimensionDescriptor);
        return dimensionDescriptor;
    }

    private static void fillDimensionComponents(DimensionDescriptorImpl dimensionDescriptor) {
        DimensionImpl dimensionComponent = new DimensionImpl();
        dimensionComponent.setOrder(1);
        dimensionComponent.setId("DIM1");
        dimensionComponent.setConceptRoles(List.of(new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER(1.5.1).DATE_VALUE")));
        EnumeratedRepresentationImpl enumeratedRepresentation = new EnumeratedRepresentationImpl(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST(1.0)"));
        dimensionComponent.setLocalRepresentation(enumeratedRepresentation);
        dimensionComponent.setConceptIdentity(
            new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:DIMENSION(1.6.1).DATE_VALUE"));
        DimensionComponentImpl dimensionTimeComponent = new TimeDimensionImpl();
        dimensionTimeComponent.setId("TIME_PERIOD");
        dimensionTimeComponent.setOrder(2);
        dimensionTimeComponent.setConceptIdentity(
            new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:TIME_DIMENSION(1.6.1).DATE_VALUE"));
        BaseFacetImpl baseFacet = new BaseFacetImpl();
        baseFacet.setValueType(FacetValueType.BASIC_TIME_PERIOD);
        dimensionTimeComponent.setLocalRepresentation(new BaseTextFormatRepresentationImpl(Set.of(baseFacet)));
        dimensionDescriptor.setComponents(List.of(dimensionComponent, dimensionTimeComponent));
    }

    /**
     * @return MeasureDescriptor
     */
    public static MeasureDescriptorImpl fillMeasureDescriptor() {
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId("MeasureDescriptor");
        fillMeasureComponents(measureDescriptor);
        return measureDescriptor;
    }

    private static void fillMeasureComponents(MeasureDescriptorImpl measureDescriptor) {
        MeasureImpl measure = new MeasureImpl();
        measure.setId("MES1");
        measure.setConceptIdentity(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:MEASURE(1.5.2).DATE_VALUE"));
        measure.setConceptRoles(
            List.of(
                new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER(1.5.1).DATE_VALUE"),
                new IdentifiableArtefactReferenceImpl(
                    "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=IMF:ADVANCED_RELEASE_CALANDER_DIAGNOSTICS(1.5.2).DATE_VALUE")
            ));
        BaseFacetImpl baseFacet = new BaseFacetImpl();
        baseFacet.setValueType(FacetValueType.BASIC_TIME_PERIOD);
        measure.setLocalRepresentation(new BaseTextFormatRepresentationImpl(Set.of(baseFacet)));
        measureDescriptor.setComponents(List.of(measure));
    }

    /**
     * @return Hierarchy object
     */
    public static HierarchyImpl buildHierarchy() {
        HierarchyImpl hierarchy = new HierarchyImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(hierarchy);
        hierarchy.setHasFormalLevels(true);
        HierarchicalCodeImpl code = new HierarchicalCodeImpl();
        code.setId("id");
        code.setCode(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.codelist.Code=SDMX:CL_AGE(2.1.1).CL1"));
        code.setLevelId("Lev1");
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("id");
        code.setAnnotations(List.of(annotation));
        String dateStr = "2022-07-17T10:11:16.345Z";
        code.setValidTo(Instant.parse(dateStr));
        code.setValidFrom(Instant.parse(dateStr));
        HierarchicalCodeImpl code2 = new HierarchicalCodeImpl();
        code2.setId("hi-2");
        code2.setLevelId("Lev2");
        code2.setCode(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.codelist.Code=SDMX:CL_AGE(2.1.1).CL2"));
        code2.setValidTo(Instant.parse(dateStr));
        code2.setValidFrom(Instant.parse(dateStr));
        hierarchy.setCodes(List.of(code, code2));
        LevelImpl level = new LevelImpl();
        level.setDescription(new InternationalString("hierarchy desc"));
        level.setName(new InternationalString("hierarchy name"));
        level.setId("levId");
        level.setCodeFormat(List.of(fillListOfCodingFormats().get(0)));
        hierarchy.setLevel(level);
        return hierarchy;
    }

    public static HierarchyImpl buildHierarchyWithInnerHierarchy() {
        HierarchyImpl hierarchy = new HierarchyImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(hierarchy);
        hierarchy.setHasFormalLevels(true);
        HierarchicalCodeImpl code = new HierarchicalCodeImpl();
        code.setId("id");
        code.setCode(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.codelist.Code=SDMX:CL_AGE(2.1.1).CL1"));
        code.setLevelId("Lev1");
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("id");
        code.setAnnotations(List.of(annotation));
        String dateStr = "2022-07-17T10:11:16.345Z";
        code.setValidTo(Instant.parse(dateStr));
        code.setValidFrom(Instant.parse(dateStr));
        HierarchicalCodeImpl hierarchicalCode = new HierarchicalCodeImpl();
        hierarchicalCode.setCode(new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.codelist.Code=SDMX:CL_AGE(2.1.1).CL1"));
        hierarchicalCode.setId("1");
        hierarchicalCode.setValidTo(Instant.parse(dateStr));
        hierarchicalCode.setValidFrom(Instant.parse(dateStr));
        code.setHierarchicalCodes(List.of(hierarchicalCode));
        hierarchy.setCodes(List.of(code));
        LevelImpl level = new LevelImpl();
        level.setDescription(new InternationalString("hierarchy desc"));
        level.setName(new InternationalString("hierarchy name"));
        level.setId("levId");
        level.setCodeFormat(List.of(fillListOfCodingFormats().get(0)));
        hierarchy.setLevel(level);
        LevelImpl levelUn = new LevelImpl();
        levelUn.setId("levUn");
        levelUn.setName(new InternationalString("underLevel"));
        level.setChild(levelUn);
        return hierarchy;
    }

    private static List<CodingFormat> fillListOfCodingFormats() {
        List<CodingFormat> codingFormatList = new ArrayList<>();
        codingFormatList.add(addCodingFormat(FacetType.INTERVAL, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.IS_SEQUENCE, String.valueOf(true)));
        codingFormatList.add(addCodingFormat(FacetType.MAX_LENGTH, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.MIN_LENGTH, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.MAX_VALUE, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.MIN_VALUE, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.PATTERN, "a"));
        codingFormatList.add(addCodingFormat(FacetType.START_VALUE, String.valueOf(1)));
        codingFormatList.add(addCodingFormat(FacetType.END_VALUE, String.valueOf(1)));

        CodingFormatImpl codingFormat = new CodingFormatImpl();
        codingFormat.setCodingFormat(new BaseFacetImpl(FacetValueType.fromValue("Alpha")));
        codingFormatList.add(codingFormat);
        return codingFormatList;
    }

    private static CodingFormat addCodingFormat(FacetType facetType, String value) {
        CodingFormatImpl codingFormat = new CodingFormatImpl();
        BaseFacetImpl baseFacet = new BaseFacetImpl();
        baseFacet.setType(facetType);
        baseFacet.setValue(value);
        codingFormat.setCodingFormat(baseFacet);
        return codingFormat;
    }

    /**
     * @return Metadataflow object
     */
    public static MetadataflowImpl buildMetadataFlow() {
        MetadataflowImpl metadataflow = new MetadataflowImpl();
        setMaintainableArtefact(metadataflow);
        metadataflow.setStructure(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.metadatastructure.MetadataStructure=IMF:METADATA(1.0.0)"));
        IdentifiableObjectSelectionImpl objectSelection = new IdentifiableObjectSelectionImpl();
        MaintainableArtefactReference maintainableArtefactReference = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=QUANTHUB:ECOFIN_DSD(1.2)");

        MaintainableArtefactReference maintainableArtefactReference2 = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=QUANTHUB:ECOFIN_DSD(1.3)");
        objectSelection.setResolvesTo(List.of(maintainableArtefactReference, maintainableArtefactReference2));
        metadataflow.setSelections(List.of(objectSelection));
        return metadataflow;
    }

    /**
     * @return Dataflow object
     */
    public static DataflowImpl buildDataFlow() {
        DataflowImpl dataflow = new DataflowImpl();
        dataflow.setId("DF");
        dataflow.setStructure(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=QUANTHUB:ECOFIN_DSD(1.2)"));
        setMaintainableArtefact(dataflow);
        return dataflow;
    }

    /**
     * @return AgencyScheme
     */

    public static AgencySchemeImpl buildAgencyScheme() {
        AgencySchemeImpl agencyScheme = new AgencySchemeImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(agencyScheme);
        agencyScheme.setId("AGENCIES");
        agencyScheme.setOrganizationId("SDMX");
        agencyScheme.setVersion(Version.createFromString("1.0"));
        AgencyImpl agency1 = createAgency("AG-1");
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Annot");
        agency1.setAnnotations(List.of(annotation));
        AgencyImpl agency2 = createAgency("AG-2");


        agencyScheme.setItems(List.of(agency1, agency2));
        return agencyScheme;
    }

    private static AgencyImpl createAgency(String id) {
        AgencyImpl agency1 = new AgencyImpl();
        agency1.setId(id);
        agency1.setName(new InternationalString("Agency"));
        agency1.setDescription(new InternationalString("Agency"));

        List<Contact> contacts = getContacts();

        agency1.setContacts(contacts);
        return agency1;
    }

    private static List<Contact> getContacts() {
        ContactImpl contact1 = new ContactImpl();
        contact1.setName("AG_1");
        contact1.setTelephone("9458673082");
        contact1.setFax("somedatafax");
        contact1.setEmail("test@email.com");
        contact1.setOrganizationUnit("IT");
        Map<String, String> resp = new HashMap<>();
        resp.put("en", "writing");
        resp.put("fr", "ecrire");
        contact1.setResponsibility(new InternationalString(resp));
        contact1.setX400("X400");

        ContactImpl contact2 = new ContactImpl();
        contact2.setName("AG_2");
        contact2.setTelephone("9458673082");
        contact2.setFax("somedatafax2");
        contact2.setEmail("test@email.com");
        contact2.setOrganizationUnit("IT");
        contact2.setResponsibility(new InternationalString(resp));
        contact2.setX400("X400");
        return List.of(contact1, contact2);
    }

    private static ItemMap createItemMap(String target, String source) {
        ItemMapImpl itemMap = new ItemMapImpl();
        itemMap.setTarget(target);
        itemMap.setSource(source);
        itemMap.setValidTo(Instant.parse("2022-07-17T00:00:00Z"));
        itemMap.setValidFrom(Instant.parse("2022-07-17T00:00:00Z"));
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Ann");
        itemMap.setAnnotations(List.of(annotation));
        return itemMap;
    }

    public static VersionableWriter getVersionableWriter() {
        final NameableWriter nameableWriter = getNameableWriter();
        return new VersionableWriter(nameableWriter);
    }

    public static NameableWriter getNameableWriter() {
        final IdentifiableWriter identifiableWriter = getIdentifiableWriter();
        return new NameableWriter(identifiableWriter);
    }

    public static IdentifiableWriter getIdentifiableWriter() {
        final AnnotableWriter annotableWriter = getAnnotableWriter();
        return new IdentifiableWriter(annotableWriter);
    }

    public static AnnotableWriter getAnnotableWriter() {
        return new AnnotableWriter(getLinksWriter());
    }

    public static RepresentationWriter getRepresentationWriter() {
        return new RepresentationWriter(getReferenceResolver(), new DefaultReferenceAdapter());
    }

    public static ComponentWriter getComponentWriter() {
        return new ComponentWriter(getIdentifiableWriter(), getRepresentationWriter(), getReferenceResolver());
    }

    public static ConceptRoleWriter getConceptRoleWriter() {
        return new ConceptRoleWriter(getReferenceResolver());
    }

    public static AttributeListWriter getAttributeListWriter() {
        return new AttributeListWriter(getIdentifiableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    public static DimensionListWriter getDimensionListWriter() {
        return new DimensionListWriter(getIdentifiableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    public static GroupDimensionListWriter getGroupDimensionListWriter() {
        return new GroupDimensionListWriter(getIdentifiableWriter());
    }

    public static MeasureListWriter getMeasureListWriter() {
        return new MeasureListWriter(getIdentifiableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    public static LinksWriter getLinksWriter() {
        return new LinksWriter(getReferenceAdapter());
    }

    public static ContactsWriter getContactsWriter() {
        return new ContactsWriter();
    }

    public static OrganisationWriter getOrganisationWriter() {
        return new OrganisationWriter(getContactsWriter(), getNameableWriter());
    }

    public static Json10StructureWriter createStructureWriter(
        ByteArrayOutputStream outputStream,
        ObjectMapper mapper,
        DefaultHeaderProvider defaultHeaderProvider) {
        ReferenceResolver res = getReferenceResolver();
        final var referenceAdapter = new DefaultReferenceAdapter();
        final var linksWriter = new LinksWriter(referenceAdapter);
        final var headerWriter = new MetaWriter(List.of(), defaultHeaderProvider);
        final var representationWriter = new RepresentationWriter(res, referenceAdapter);
        final var annotableWriter = new AnnotableWriter(linksWriter);
        final var identifiableWriter = new IdentifiableWriter(annotableWriter);
        final var nameableWriter = new NameableWriter(identifiableWriter);
        final var versionableWriter = new VersionableWriter(nameableWriter);
        final var componentWriter = new ComponentWriter(identifiableWriter, representationWriter, res);
        final var conceptRoleWriter = new ConceptRoleWriter(res);
        final var attributeListWriter = new AttributeListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final var dimensionListWriter = new DimensionListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final var measureListWriter = new MeasureListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final var groupDimensionListWriter = new GroupDimensionListWriter(identifiableWriter);
        final var categorisationWriter = new CategorisationWriter(versionableWriter, linksWriter, res, referenceAdapter);
        final var categorySchemeWriter = new CategorySchemeWriter(versionableWriter, linksWriter, nameableWriter);
        final var conceptSchemeWriter = new ConceptSchemeWriter(versionableWriter, linksWriter, nameableWriter, representationWriter);
        final var codeImplWriter = new CodeWriterImpl();
        final var codeListWriter = new CodelistWriter(versionableWriter, linksWriter, codeImplWriter, nameableWriter);
        final var dataFlowWriter = new DataflowWriter(versionableWriter, linksWriter, res);
        final var dataStructureDefinitionWriter = new DataStructureDefinitionWriter(
            versionableWriter,
            linksWriter,
            attributeListWriter,
            dimensionListWriter,
            measureListWriter,
            groupDimensionListWriter,
            dsd -> dsd,
            new StubDataStructureLocalRepresentationAdapter()
        );
        final var hierarchyWriter = new HierarchicalCodelistWriter(versionableWriter, linksWriter, identifiableWriter, nameableWriter, res, referenceAdapter);

        return new Json10StructureWriter(
            mapper,
            outputStream,
            headerWriter,
            List.of(categorisationWriter,
                categorySchemeWriter,
                codeListWriter,
                conceptSchemeWriter,
                dataStructureDefinitionWriter,
                hierarchyWriter,
                dataFlowWriter
            )
        );
    }

    public static void assertMaintainableArtefactsInJson(String json) {
        assertThatJson(json).isPresent();
        assertThatJson(json).node("id")
            .isString()
            .isEqualTo("ARTEFACT");
        assertThatJson(json).node("version")
            .isString()
            .isEqualTo("1.2");
        assertThatJson(json).node("agencyID")
            .isString()
            .isEqualTo("IMF");
        assertThatJson(json).node("name")
            .isString()
            .isEqualTo("Maintanable artefact");
        assertThatJson(json).node("names")
            .isPresent()
            .isObject()
            .containsEntry("en", "Maintanable artefact");
        assertThatJson(json).node("names")
            .isPresent()
            .isObject()
            .containsEntry("fr-CA", "artefact");
        assertThatJson(json).node("description")
            .isString()
            .isEqualTo("Some artefact");
        assertThatJson(json).node("descriptions")
            .isPresent()
            .isObject()
            .containsEntry("en", "Some artefact");
        assertThatJson(json).node("annotations")
            .isArray()
            .element(0)
            .node("type")
            .isEqualTo("type1");
        assertThatJson(json).node("annotations")
            .isArray()
            .element(0)
            .node("id")
            .isString()
            .isEqualTo("1");
    }

    private static List<MemberSelection> getMemberSelections(boolean isSimple, boolean isJson) {
        MemberSelectionImpl memberSelection = new MemberSelectionImpl();
        memberSelection.setIncluded(false);
        memberSelection.setRemovePrefix(true);
        memberSelection.setComponentId("MS");

        MemberValueImpl memberValue;
        if (isSimple) {
            memberValue = getMemberValueSimple();
        } else {
            memberValue = getMemberValue();
        }

        LocalisedMemberValueImpl localisedMemberValue = new LocalisedMemberValueImpl();
        localisedMemberValue.setValue("local");
        localisedMemberValue.setLocale("en");

        memberSelection.setSelectionValues(List.of(memberValue, localisedMemberValue));

        MemberSelectionImpl memberSelection2 = new MemberSelectionImpl();
        memberSelection2.setIncluded(false);
        memberSelection2.setRemovePrefix(true);
        memberSelection2.setComponentId("MS2");
        BeforePeriodImpl beforePeriod = new BeforePeriodImpl();
        beforePeriod.setInclusive(true);
        beforePeriod.setPeriod("2023");
        if (!isJson) {
            beforePeriod.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
            beforePeriod.setValidTo(Instant.parse("2022-09-17T10:11:16Z"));
        }

        AfterPeriodImpl afterPeriod = new AfterPeriodImpl();
        afterPeriod.setInclusive(false);
        afterPeriod.setPeriod("2028");
        afterPeriod.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        afterPeriod.setValidTo(Instant.parse("2022-09-17T10:11:16Z"));

        RangePeriodImpl rangePeriod = new RangePeriodImpl();
        TimeRangePeriodImpl timeRangePeriod = new TimeRangePeriodImpl();
        timeRangePeriod.setInclusive(true);
        timeRangePeriod.setPeriod("2022");
        timeRangePeriod.setValidTo(Instant.parse("2022-09-17T10:11:16Z"));
        timeRangePeriod.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));

        rangePeriod.setStartPeriod(timeRangePeriod);
        timeRangePeriod.setInclusive(false);
        rangePeriod.setEndPeriod(timeRangePeriod);
        rangePeriod.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        rangePeriod.setValidTo(Instant.parse("2022-09-17T10:11:16Z"));

        memberSelection2.setSelectionValues(List.of(beforePeriod));
        return List.of(memberSelection, memberSelection2);
    }

    private static MemberValueImpl getMemberValue() {
        MemberValueImpl memberValue = new MemberValueImpl();
        memberValue.setValue("MEM");
        memberValue.setCascadeValue(CascadeValue.EXCLUDE_ROOT);
        memberValue.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        memberValue.setValidTo(Instant.parse("2022-09-17T10:11:16Z"));
        return memberValue;
    }

    private static MemberValueImpl getMemberValueSimple() {
        MemberValueImpl memberValue = new MemberValueImpl();
        memberValue.setValue("MEM");
        return memberValue;
    }

    private static ReleaseCalendar createReleaseCalendar() {
        ReleaseCalendarImpl releaseCalendar = new ReleaseCalendarImpl();
        releaseCalendar.setOffset("Offset");
        releaseCalendar.setTolerance("Tolerance");
        releaseCalendar.setPeriodicity("Periodicity");
        return releaseCalendar;
    }

    private static DataKeySet createDataKeySets() {
        DataKeySetImpl dataKeySet = new DataKeySetImpl();
        dataKeySet.setIncluded(true);
        DataKeyImpl dataKey = new DataKeyImpl();
        dataKey.setIncluded(true);
        ComponentValueImpl componentValue = new ComponentValueImpl();
        componentValue.setIncluded(true);
        componentValue.setComponentId("C1");
        componentValue.setRemovePrefix(true);
        componentValue.setValue("Value");
        dataKey.setKeyValues(List.of(componentValue));
        dataKey.setMemberSelections(getMemberSelections(true, true));
        dataKey.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        dataKey.setValidTo(Instant.parse("2022-07-17T10:11:16Z"));
        dataKeySet.setKeys(List.of(dataKey));

        return dataKeySet;
    }

    public static ProvisionAgreement buildProvisionAgreement() {
        ProvisionAgreementImpl provisionAgreement = new ProvisionAgreementImpl();
        setMaintainableArtefact(provisionAgreement);
        provisionAgreement.setControlledStructureUsage(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=IMF:ARTEFACT(1.2)")
        );
        provisionAgreement.setDataProvider(
            new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.base.DataProvider=IMF:DATA_PROVIDERS(1.0).DP")
        );
        return provisionAgreement;
    }

    private static List<Annotation> getAnnotations() {
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Annot");
        return List.of(annotation);
    }

    public static CodeWriterImpl getCodeImplWriter() {
        return new CodeWriterImpl();
    }

    public static ReferenceResolver getReferenceResolver() {
        // this should be anonymous class, but it is not possible to mock lambda
        return new ReferenceResolver() {
            @Override
            public ArtefactReference resolve(ArtefactReference reference) {
                return reference;
            }
        };
    }

    public static ReferenceAdapter getReferenceAdapter() {
        return new DefaultReferenceAdapter();
    }
}
