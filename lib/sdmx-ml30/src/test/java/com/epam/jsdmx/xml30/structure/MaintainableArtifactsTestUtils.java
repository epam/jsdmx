package com.epam.jsdmx.xml30.structure;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormatImpl;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.ComputationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.ContactImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySetImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponentImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.EnumeratedRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.EpochMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.EpochPeriodType;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCode;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelist;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelist;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.GridCode;
import com.epam.jsdmx.infomodel.sdmx30.GridCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociation;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociationImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.LevelImpl;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MappedValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MappingRoleType;
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
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderScheme;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreement;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreementImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMap;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitScheme;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.infomodel.sdmx30.Process;
import com.epam.jsdmx.infomodel.sdmx30.ProcessArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProcessImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStep;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStepImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreement;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreementImpl;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomy;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMap;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.ResolvePeriod;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.TargetValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.TransitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueItemImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueList;
import com.epam.jsdmx.infomodel.sdmx30.ValueListImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

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
            "CAT_SOURCE"
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
        BaseFacetImpl facet3 = new BaseFacetImpl();
        facet1.setValueType(FacetValueType.ALPHA);
        SentinelValueImpl sentinelValue = new SentinelValueImpl();
        sentinelValue.setValue("val");
        sentinelValue.setName(new InternationalString("sent"));
        sentinelValue.setDescription(new InternationalString("sentinel descr"));
        facet2.setSentinelValues(List.of(sentinelValue));
        facet2.setType(FacetType.SENTINEL_VALUES);
        facet2.setValueType(FacetValueType.ALPHA);
        facet3.setType(FacetType.IS_MULTILINGUAL);
        facet3.setValue("true");
        facetSet.addAll(List.of(facet1, facet2, facet3));
        return new BaseTextFormatRepresentationImpl(facetSet);
    }

    /**
     * @return DataConstraint object
     */
    public static DataConstraintImpl buildDataConstraint() {
        DataConstraintImpl dataConstraint = new DataConstraintImpl();
        setMaintainableArtefact(dataConstraint);
        dataConstraint.setConstraintRoleType(ConstraintRoleType.ALLOWABLE_CONTENT);

        CubeRegionKeyImpl cubeRegionKey1 = new CubeRegionKeyImpl();
        cubeRegionKey1.setComponentId("CubeReg");
        cubeRegionKey1.setIncluded(true);
        cubeRegionKey1.setRemovePrefix(true);
        cubeRegionKey1.setValidFrom(Instant.parse("2020-04-12T12:00:00.000000Z"));
        cubeRegionKey1.setValidTo(Instant.parse("2022-04-12T12:00:00.000000Z"));
        cubeRegionKey1.setSelectionValues(List.of(getMemberValue()));

        CubeRegionImpl cubeRegion = new CubeRegionImpl();
        cubeRegion.setIncluded(true);
        cubeRegion.setMemberSelections(getMemberSelections(true, false, "rangePeriod"));
        cubeRegion.setCubeRegionKeys(List.of(cubeRegionKey1));
        cubeRegion.setAnnotations(getAnnotations());

        dataConstraint.setCubeRegions(List.of(cubeRegion));
        dataConstraint.setReleaseCalendar(createReleaseCalendar());
        dataConstraint.setDataContentKeys(List.of(createDataKeySets()));
        dataConstraint.setConstrainedArtefacts(List.of(buildProvisionAgreement().getDataProvider()));

        return dataConstraint;
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
        dataAttribute.setMaxOccurs(1);
        GroupRelationshipImpl groupRelationship = new GroupRelationshipImpl();
        groupRelationship.setGroupKey("GroupKey");
        dataAttribute.setAttributeRelationship(groupRelationship);
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
        groupDimensionDescriptor1.setDimensions(List.of("DIM1", "TIME_PERIOD"));

        GroupDimensionDescriptorImpl groupDimensionDescriptor2 = new GroupDimensionDescriptorImpl();
        groupDimensionDescriptor2.setId("GroupDimensionDescriptor2");
        groupDimensionDescriptor2.setDimensions(List.of("DIM1"));

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

    /**
     * @return CategorySchemeMap
     */

    public static CategorySchemeMapImpl buildCategorySchemeMap() {
        CategorySchemeMapImpl categorySchemeMap = new CategorySchemeMapImpl();
        setMaintainableArtefact(categorySchemeMap);
        categorySchemeMap.setSource(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.categoryscheme.CategoryScheme=IMF:CatScheme(1.0)"));
        categorySchemeMap.setTarget(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.categoryscheme.CategoryScheme=IMF:CatScheme(1.1)"));
        ItemMap itemMap = createItemMap("IM-1", "S-1");
        ItemMap itemMap2 = createItemMap("IM-2", "S-2");
        categorySchemeMap.setItemMaps(List.of(itemMap, itemMap2));
        return categorySchemeMap;
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

    /**
     * @return ConceptSchemeMap
     */

    public static ConceptSchemeMapImpl buildConceptSchemeMap() {
        ConceptSchemeMapImpl conceptSchemeMap = new ConceptSchemeMapImpl();
        setMaintainableArtefact(conceptSchemeMap);
        conceptSchemeMap.setSource(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=IMF:ConScheme(1.0)"));
        conceptSchemeMap.setTarget(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=IMF:ConScheme(1.1)"));
        ItemMap itemMap = createItemMap("IM-1", "S-1");
        ItemMap itemMap2 = createItemMap("IM-2", "S-2");
        conceptSchemeMap.setItemMaps(List.of(itemMap, itemMap2));
        return conceptSchemeMap;
    }

    /**
     * @return DataConsumerSchemeImpl
     */
    public static DataConsumerSchemeImpl buildDataConsumerScheme() {
        DataConsumerSchemeImpl dataConsumerScheme = new DataConsumerSchemeImpl();
        setMaintainableArtefact(dataConsumerScheme);
        dataConsumerScheme.setVersion(Version.createFromString("1.0"));
        dataConsumerScheme.setId("DATA_CONSUMERS");
        DataConsumerImpl dataConsumer = new DataConsumerImpl();
        dataConsumer.setId("DC");
        dataConsumer.setName(new InternationalString("Data Consumer"));
        dataConsumer.setDescription(new InternationalString("Data Consumer description"));
        dataConsumer.setContacts(getContacts());
        dataConsumerScheme.setItems(List.of(dataConsumer));
        return dataConsumerScheme;
    }

    /**
     * @return RepresentationMap object
     */
    public static RepresentationMapImpl buildRepresentationMap() {
        RepresentationMapImpl representationMap = new RepresentationMapImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(representationMap);
        fillSource(representationMap, "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_SOURCE(1.0)");
        fillTarget(representationMap, "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=IMF:CODELIST_TARGET(1.0)");
        fillRepresentationMappings(representationMap, "2022-07-17T00:00:00.000Z");
        return representationMap;
    }

    public static void fillTarget(RepresentationMapImpl representationMap, String referenceUrn) {
        ListReferenceValueRepresentationImpl valueRepresentation = new ListReferenceValueRepresentationImpl();
        valueRepresentation.setReference(new MaintainableArtefactReference(referenceUrn));
        representationMap.setTarget(List.of(valueRepresentation));
    }

    public static void fillSource(RepresentationMapImpl representationMap, String referenceUrn) {
        ListReferenceValueRepresentationImpl valueRepresentation = new ListReferenceValueRepresentationImpl();
        valueRepresentation.setReference(new MaintainableArtefactReference(referenceUrn));
        FacetValueTypeRepresentationImpl valueRepresentation2 = new FacetValueTypeRepresentationImpl();
        valueRepresentation2.setType(FacetValueType.DATE_TIME);
        representationMap.setSource(List.of(valueRepresentation, valueRepresentation2));
    }

    public static void fillRepresentationMappings(RepresentationMapImpl representationMap, String date) {
        RepresentationMappingImpl representationMapping = new RepresentationMappingImpl();
        Instant instant = Instant.parse(date);
        representationMapping.setValidFrom(instant);
        representationMapping.setValidTo(instant);
        fillSourceValues(representationMapping);
        fillTargetValues(representationMapping);
        representationMap.setRepresentationMappings(List.of(representationMapping));
    }

    static void fillTargetValues(RepresentationMappingImpl representationMapping) {
        TargetValueImpl targetValue1 = new TargetValueImpl();
        targetValue1.setValue("targetValue1");
        TargetValueImpl targetValue2 = new TargetValueImpl();
        targetValue2.setValue("targetValue2");
        representationMapping.setTargetValues(List.of(targetValue1, targetValue2));
    }

    static void fillSourceValues(RepresentationMappingImpl representationMapping) {
        MappedValueImpl mappedValue = new MappedValueImpl();
        mappedValue.setValue("mappedValue");
        mappedValue.setRegEx(true);
        mappedValue.setStartIndex(0);
        mappedValue.setEndIndex(10);
        representationMapping.setSourceValues(List.of(mappedValue));
    }

    /**
     * @return StructureMap object
     */
    public static StructureMapImpl buildStructureMap() {
        StructureMapImpl structureMap = new StructureMapImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(structureMap);
        structureMap.setSource(new MaintainableArtefactReference("DataFSource", "EPM", "3.0", StructureClassImpl.DATAFLOW));
        structureMap.setTarget(new MaintainableArtefactReference("DataFTarget", "EPM", "3.0", StructureClassImpl.DATAFLOW));
        fillEpochMap(structureMap);
        fillFixedValueMap(structureMap);
        fillComponentMap(structureMap);
        fillDatePattern(structureMap);
        return structureMap;
    }

    static void fillDatePattern(StructureMapImpl structureMap) {
        DatePatternMapImpl datePatternMap = new DatePatternMapImpl();
        datePatternMap.setLocale("EN");
        datePatternMap.setSourcePattern("SOURCE");
        datePatternMap.setId("DP1");
        datePatternMap.setResolvePeriod(ResolvePeriod.MID_PERIOD);
        FrequencyFormatMappingImpl frequencyFormatMapping1 = getFrequencyFormatMapping("CODE_1", "2022-11", "FR1");
        FrequencyFormatMappingImpl frequencyFormatMapping2 = getFrequencyFormatMapping("CODE_2", "2022-11", "FR2");
        datePatternMap.setMappedFrequencies(List.of(frequencyFormatMapping1, frequencyFormatMapping2));
        datePatternMap.setFrequencyDimension("Frequency_dim");
        structureMap.setDatePatternMaps(List.of(datePatternMap));
    }

    private static FrequencyFormatMappingImpl getFrequencyFormatMapping(String freqCode, String datePattern, String id) {
        FrequencyFormatMappingImpl frequencyFormatMapping = new FrequencyFormatMappingImpl();
        frequencyFormatMapping.setFrequencyCode(freqCode);
        frequencyFormatMapping.setDatePattern(datePattern);
        frequencyFormatMapping.setId(id);
        return frequencyFormatMapping;
    }

    static void fillComponentMap(StructureMapImpl structureMap) {

        ComponentMapImpl componentMapDP = new ComponentMapImpl();
        componentMapDP.setSource(List.of("CompMapEpochS"));
        componentMapDP.setTarget(List.of("CompMapEpochT"));
        IdentifiableArtefactReferenceImpl reference = new IdentifiableArtefactReferenceImpl(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.EPOCH_MAP,
            "EPOCH1"
        );
        componentMapDP.setRepresentationMap(reference);


        ComponentMapImpl componentMapEpoch = new ComponentMapImpl();
        componentMapEpoch.setSource(List.of("CompMapDPS"));
        componentMapEpoch.setTarget(List.of("CompMapDPT"));
        IdentifiableArtefactReferenceImpl reference2 = new IdentifiableArtefactReferenceImpl(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.DATE_PATTERN_MAP,
            "DP1"
        );
        componentMapEpoch.setRepresentationMap(reference2);

        ComponentMapImpl componentMaRep = new ComponentMapImpl();
        componentMaRep.setSource(List.of("CompMapRepS"));
        componentMaRep.setTarget(List.of("CompMapRepT"));
        MaintainableArtefactReference reference3 = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.structuremapping.RepresentationMap=IMF:ARTEFACT(1.2)");
        componentMaRep.setRepresentationMap(reference3);

        structureMap.setComponentMaps(List.of(componentMapEpoch, componentMapDP, componentMaRep));
    }

    static void fillFixedValueMap(StructureMapImpl structureMap) {
        FixedValueMapImpl fixedValueMap = new FixedValueMapImpl();
        fixedValueMap.setRole(MappingRoleType.TARGET);
        fixedValueMap.setComponent("STRUC_ID");
        fixedValueMap.setValue("value");
        structureMap.setFixedComponentMaps(List.of(fixedValueMap));
    }

    static void fillEpochMap(StructureMapImpl structureMap) {
        EpochMapImpl epochMap = new EpochMapImpl();
        epochMap.setEpochPeriod(EpochPeriodType.MICROSECOND);
        Instant instant = Instant.parse("2022-07-17T10:11:16Z");
        epochMap.setBasePeriod(instant);
        epochMap.setId("EPOCH1");
        epochMap.setResolvePeriod(ResolvePeriod.MID_PERIOD);
        FrequencyFormatMappingImpl frequencyFormatMapping3 = getFrequencyFormatMapping("CODE_3", "2022-11", "FR3");
        epochMap.setMappedFrequencies(List.of(frequencyFormatMapping3));
        epochMap.setFrequencyDimension("Frequency_dim");
        structureMap.setEpochMaps(List.of(epochMap));
    }

    /**
     * @return Header
     */
    public static Header buildHeader() throws URISyntaxException {
        Header header = new Header();
        header.setId("IDREF8124");
        URI uri = new URI("https://raw.githubusercontent.com/sdmx-twg/sdmx-json/master/metadata-message/tools/schemas/2.0.0/sdmx-json-metadata-schema.json");
        header.setSchema(uri);
        Instant instant = Instant.parse("2021-11-02T09:47:41Z");
        header.setPrepared(instant);
        Party sender = new Party();
        sender.setId("unknown");
        header.setSender(sender);
        Locale locale = new Locale("en");
        header.setContentLanguages(List.of(locale));
        return header;
    }

    public static DataProviderSchemeImpl buildDataProviderScheme() {

        DataProviderSchemeImpl dataProviderScheme = new DataProviderSchemeImpl();
        setMaintainableArtefact(dataProviderScheme);
        dataProviderScheme.setVersion(Version.createFromString("1.0"));
        dataProviderScheme.setId("DATA_PROVIDERS");
        dataProviderScheme.setPartial(true);

        DataProviderImpl dataProvider = new DataProviderImpl();
        dataProvider.setContacts(getContacts());
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Ann");
        annotation.setValue("Val");
        dataProvider.setAnnotations(List.of(annotation));
        dataProvider.setId("Prov");
        dataProvider.setName(new InternationalString("D_P"));

        dataProviderScheme.setItems(List.of(dataProvider));
        return dataProviderScheme;
    }

    public static MetadataProviderScheme buildMetadataProviderScheme() {

        MetadataProviderSchemeImpl metadataProviderScheme = new MetadataProviderSchemeImpl();
        setMaintainableArtefact(metadataProviderScheme);
        metadataProviderScheme.setVersion(Version.createFromString("1.0"));
        metadataProviderScheme.setId("METADATA_PROVIDERS");
        metadataProviderScheme.setPartial(true);

        MetadataProviderImpl metadataProvider = new MetadataProviderImpl();
        metadataProvider.setContacts(getContacts());
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Ann");
        annotation.setValue("Val");
        metadataProvider.setAnnotations(List.of(annotation));
        metadataProvider.setId("Prov");
        metadataProvider.setName(new InternationalString("MD_P"));

        metadataProviderScheme.setItems(List.of(metadataProvider));
        return metadataProviderScheme;
    }

    public static HierarchyAssociation buildHierarchyAssociation() {
        HierarchyAssociationImpl hierarchyAssociation = new HierarchyAssociationImpl();
        setMaintainableArtefact(hierarchyAssociation);
        hierarchyAssociation.setLinkedHierarchy(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.codelist.Hierarchy=IMF:ARTEFACT(1.2)"));
        hierarchyAssociation.setContextObject(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=IMF:ARTEFACT(1.2)"));
        hierarchyAssociation.setLinkedObject(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=IMF:ARTEFACT(1.3)"));
        return hierarchyAssociation;
    }

    public static MetadataConstraintImpl buildMetadataConstraint(boolean isJson) {
        MetadataConstraintImpl metadataConstraint = new MetadataConstraintImpl();
        setMaintainableArtefact(metadataConstraint);
        metadataConstraint.setConstraintRoleType(ConstraintRoleType.ALLOWABLE_CONTENT);
        metadataConstraint.setMetadataTargetRegions(createMetadataTargetRegions(isJson));
        metadataConstraint.setReleaseCalendar(createReleaseCalendar());
        metadataConstraint.setConstrainedArtefacts(createMetadataConstrainedArtefacts());
        return metadataConstraint;
    }

    private static List<ArtefactReference> createMetadataConstrainedArtefacts() {
        IdentifiableArtefactReferenceImpl maintainableArtefactReference = new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:org.sdmx.infomodel.base.MetadataProvider=IMF:METADATA_PROVIDERS(1.0).PR");
        return List.of(maintainableArtefactReference);
    }

    private static List<MetadataTargetRegion> createMetadataTargetRegions(boolean isJson) {
        MetadataTargetRegionImpl metadataTargetRegion = new MetadataTargetRegionImpl();
        metadataTargetRegion.setIncluded(true);
        metadataTargetRegion.setValidTo(Instant.parse("2022-07-17T10:11:16Z"));
        metadataTargetRegion.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        List<MemberSelection> memberSelectionList = getMemberSelections(false, isJson, "beforePeriod");
        metadataTargetRegion.setMemberSelections(memberSelectionList);

        return List.of(metadataTargetRegion);
    }

    private static List<MemberSelection> getMemberSelections(boolean isSimple, boolean isJson, String timePeriod) {
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

        if (timePeriod.equals("beforePeriod")) {
            memberSelection2.setSelectionValues(List.of(beforePeriod));
        } else if (timePeriod.equals("afterPeriod")) {
            memberSelection2.setSelectionValues(List.of(afterPeriod));
        } else {
            memberSelection2.setSelectionValues(List.of(rangePeriod));
        }

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
        dataKey.setMemberSelections(getMemberSelections(true, false, "afterPeriod"));
        dataKey.setValidFrom(Instant.parse("2022-07-17T10:11:16Z"));
        dataKey.setValidTo(Instant.parse("2022-07-17T10:11:16Z"));
        dataKeySet.setKeys(List.of(dataKey));

        return dataKeySet;
    }

    public static MetadataProvisionAgreement buildMetadataProvisionAgreement() {
        MetadataProvisionAgreementImpl metadataProvisionAgreement = new MetadataProvisionAgreementImpl();
        setMaintainableArtefact(metadataProvisionAgreement);
        metadataProvisionAgreement.setControlledStructureUsage(new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.metadatastructure.Metadataflow=IMF:ARTEFACT(1.2)"));
        metadataProvisionAgreement.setMetadataProvider(new IdentifiableArtefactReferenceImpl(
            "urn:sdmx:org.sdmx.infomodel.base.MetadataProvider=IMF:METADATA_PROVIDERS(1.0).PR"));
        return metadataProvisionAgreement;
    }

    public static OrganisationSchemeMap buildOrganisationSchemeMap() {
        OrganisationSchemeMapImpl organisationSchemeMap = new OrganisationSchemeMapImpl();
        setMaintainableArtefact(organisationSchemeMap);
        organisationSchemeMap.setSource(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.base.AgencyScheme=SDMX:AGENCIES(1.0)"));
        organisationSchemeMap.setTarget(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.base.AgencyScheme=SDMX:AGENCIES(1.0)"));
        ItemMap itemMap = createItemMap("IM-1", "S-1");
        ItemMap itemMap2 = createItemMap("IM-2", "S-2");
        organisationSchemeMap.setItemMaps(List.of(itemMap, itemMap2));
        return organisationSchemeMap;
    }

    public static Process buildProcess() {
        ProcessImpl process = new ProcessImpl();
        setMaintainableArtefact(process);
        List<ProcessStep> processSteps = new ArrayList<>();
        ProcessStepImpl processStep = new ProcessStepImpl();
        processStep.setId("ProcStep");
        processStep.setName(new InternationalString("ProcessStep"));
        processStep.setDescription(new InternationalString("ProcessStep description"));

        ProcessArtefactImpl processArtefact = new ProcessArtefactImpl();
        processArtefact.setLocalId("Loc");
        processArtefact.setArtefact(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.process.ProcessStep=IMF:Artefact(1.0)"));
        processArtefact.setAnnotations(getAnnotations());
        processStep.setInputs(List.of(processArtefact));
        processStep.setOutputs(List.of(processArtefact));

        TransitionImpl transition = new TransitionImpl();
        transition.setId("Trans");
        transition.setLocalId("Loc");
        transition.setTargetProcessStep("ST");
        transition.setCondition(new InternationalString("Doo"));
        processStep.setTransitions(List.of(transition));

        ComputationImpl computation = new ComputationImpl();
        computation.setLocalId("LocId");
        computation.setSoftwareLanguage("SoftwareLanguage");
        computation.setSoftwarePackage("SoftwarePackage");
        computation.setSoftwareVersion("SoftwareVersion");
        computation.setAnnotations(getAnnotations());
        computation.setDescription(new InternationalString("Computation Description"));
        processStep.setComputation(computation);

        ProcessStepImpl processStepChild = new ProcessStepImpl();
        processStepChild.setId("ProcessChild");
        processStepChild.setAnnotations(getAnnotations());
        processStepChild.setName(new InternationalString("ProcessStepChild"));
        processStep.setChildren(List.of(processStepChild));
        processStep.setAnnotations(getAnnotations());
        processSteps.add(processStep);
        process.setSteps(processSteps);

        return process;
    }


    public static ProvisionAgreement buildProvisionAgreement() {
        ProvisionAgreementImpl provisionAgreement = new ProvisionAgreementImpl();
        setMaintainableArtefact(provisionAgreement);
        provisionAgreement.setControlledStructureUsage(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=IMF:ARTEFACT(1.2)"));
        provisionAgreement.setDataProvider(
            new IdentifiableArtefactReferenceImpl("urn:sdmx:org.sdmx.infomodel.base.DataProvider=IMF:DATA_PROVIDERS(1.0).DP"));
        return provisionAgreement;
    }

    public static ReportingTaxonomy buildReportingTaxonomy() {
        ReportingTaxonomyImpl reportingTaxonomy = new ReportingTaxonomyImpl();
        setMaintainableArtefact(reportingTaxonomy);
        ReportingCategoryImpl reportingCategory1 = new ReportingCategoryImpl();
        reportingCategory1.setId("RC-1");
        reportingCategory1.setName(new InternationalString("ReportingCat"));
        reportingCategory1.setDescription(new InternationalString("ReportingCat description"));
        reportingCategory1.setAnnotations(getAnnotations());
        reportingCategory1.setFlows(List.of(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=EPM:DataS2(3.0)"),
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=EPM:DataS1(3.0)")

        ));
        ReportingCategoryImpl reportingCategory2 = new ReportingCategoryImpl();
        reportingCategory2.setId("RC-2");
        reportingCategory2.setName(new InternationalString("ReportingCat2"));
        reportingCategory2.setDescription(new InternationalString("ReportingCat2 description"));
        reportingCategory2.setAnnotations(getAnnotations());
        reportingCategory2.setStructures(List.of(
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=EPM:DataS2(3.0)"),
            new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=EPM:DataS1(3.0)")
        ));
        reportingCategory1.setHierarchy(List.of(reportingCategory2));
        reportingTaxonomy.setItems(List.of(reportingCategory1));
        return reportingTaxonomy;
    }

    public static ReportingTaxonomyMap buildReportingTaxonomyMap() {
        ReportingTaxonomyMapImpl reportingTaxonomyMap = new ReportingTaxonomyMapImpl();
        setMaintainableArtefact(reportingTaxonomyMap);
        reportingTaxonomyMap.setSource(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.categoryscheme.ReportingTaxonomy=SDMX:REP_SOURCE(1.0)"));
        reportingTaxonomyMap.setTarget(new MaintainableArtefactReference("urn:sdmx:org.sdmx.infomodel.categoryscheme.ReportingTaxonomy=SDMX:REP_TARGET(1.0)"));
        ItemMap itemMap = createItemMap("IM-1", "S-1");
        ItemMap itemMap2 = createItemMap("IM-2", "S-2");
        reportingTaxonomyMap.setItemMaps(List.of(itemMap, itemMap2));
        return reportingTaxonomyMap;
    }

    public static ValueList buildValueList() {
        ValueListImpl valueList = new ValueListImpl();
        setMaintainableArtefact(valueList);
        ValueItemImpl valueItem = new ValueItemImpl();
        valueItem.setId("VAL-1");
        Map<String, String> names = new HashMap<>();
        names.put("en", "value");
        names.put("fr", "val");
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("en", "it is value");
        descriptions.put("fr", "c'est val");
        valueItem.setName(new InternationalString(names));
        valueItem.setDescription(new InternationalString(descriptions));
        valueItem.setAnnotations(getAnnotations());

        ValueItemImpl valueItem2 = new ValueItemImpl();
        valueItem2.setId("VAL-2");
        valueItem2.setName(new InternationalString("smth"));
        valueItem2.setDescription(new InternationalString("smth description"));
        valueItem2.setAnnotations(getAnnotations());
        valueList.setItems(List.of(valueItem, valueItem2));
        return valueList;
    }

    public static GeographicCodelist buildGeographicCodelist() {
        GeographicCodelistImpl geographicCodelist = new GeographicCodelistImpl();
        setMaintainableArtefact(geographicCodelist);
        geographicCodelist.setItems(getGeoFeatureSetCodes());
        geographicCodelist.setExtensions(getExtensions());
        geographicCodelist.setPartial(true);
        return geographicCodelist;
    }

    private static List<GeoFeatureSetCode> getGeoFeatureSetCodes() {
        GeoFeatureSetCodeImpl geoFeatureSetCode1 = new GeoFeatureSetCodeImpl();
        geoFeatureSetCode1.setValue("Value1");
        geoFeatureSetCode1.setDescription(new InternationalString("code 1 desc"));
        geoFeatureSetCode1.setId("CODE1");
        geoFeatureSetCode1.setName(new InternationalString("code 1"));
        geoFeatureSetCode1.setParentId("CODE2");
        GeoFeatureSetCodeImpl geoFeatureSetCode2 = new GeoFeatureSetCodeImpl();
        geoFeatureSetCode2.setValue("Value2");
        geoFeatureSetCode2.setDescription(new InternationalString("code 2 desc"));
        geoFeatureSetCode2.setId("CODE2");
        geoFeatureSetCode2.setName(new InternationalString("code 2"));
        return List.of(geoFeatureSetCode1, geoFeatureSetCode2);
    }

    public static GeoGridCodelist buildGeoGridCodelist() {
        GeoGridCodelistImpl geoGridCodelist = new GeoGridCodelistImpl();
        setMaintainableArtefact(geoGridCodelist);
        geoGridCodelist.setItems(getGridCodes());
        geoGridCodelist.setExtensions(getExtensions());
        geoGridCodelist.setPartial(true);
        geoGridCodelist.setGridDefinition("DEFINITION");
        return geoGridCodelist;
    }

    private static List<GridCode> getGridCodes() {
        GridCodeImpl gridCode = new GridCodeImpl();
        gridCode.setGeoCell("Value1");
        gridCode.setDescription(new InternationalString("code 1 desc"));
        gridCode.setId("CODE1");
        gridCode.setName(new InternationalString("code 1"));
        gridCode.setParentId("CODE2");
        GridCodeImpl gridCode2 = new GridCodeImpl();
        gridCode2.setGeoCell("Value2");
        gridCode2.setDescription(new InternationalString("code 2 desc"));
        gridCode2.setId("CODE2");
        gridCode2.setName(new InternationalString("code 2"));
        return List.of(gridCode, gridCode2);
    }

    private static List<Annotation> getAnnotations() {
        AnnotationImpl annotation = new AnnotationImpl();
        annotation.setId("Annot");
        return List.of(annotation);
    }

    public static OrganisationUnitScheme buildOrganisationUnitScheme() {
        OrganisationUnitSchemeImpl organisationUnitScheme = new OrganisationUnitSchemeImpl();
        setMaintainableArtefact(organisationUnitScheme);
        organisationUnitScheme.setVersion(Version.createFromString("1.0"));
        organisationUnitScheme.setPartial(true);
        OrganisationUnitImpl organisationUnit = new OrganisationUnitImpl();
        organisationUnit.setAnnotations(getAnnotations());
        organisationUnit.setContacts(getContacts());
        organisationUnit.setId("OrgUnit");
        organisationUnit.setName(new InternationalString("OrganisationUnit"));
        organisationUnit.setDescription(new InternationalString("OrganisationUnit description"));
        OrganisationUnitImpl organisationUnit2 = new OrganisationUnitImpl();
        organisationUnit2.setId("OrgUnit2");
        organisationUnit2.setName(new InternationalString("OrganisationUnit"));
        organisationUnit.setHierarchy(List.of(organisationUnit2));
        organisationUnitScheme.setItems(List.of(organisationUnit, organisationUnit2));
        return organisationUnitScheme;
    }

}
