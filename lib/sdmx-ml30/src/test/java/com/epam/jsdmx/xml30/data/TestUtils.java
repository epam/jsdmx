package com.epam.jsdmx.xml30.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseTextFormatRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponentImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.EnumeratedRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.infomodel.sdmx30.VersionReference;
import com.epam.jsdmx.serializer.sdmx30.common.ActionType;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetHeader;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetHeaderImpl;

import org.apache.commons.lang.StringUtils;

public class TestUtils {

    public static final String ORGANISATION_ID = "IMF";
    public static final String VERSION = "1.0.0";

    public static DatasetHeader getDatasetHeader() {
        return DatasetHeaderImpl.builder()
            .datasetId("1")
            .action(ActionType.INFORMATION)
            .build();
    }

    private static ConceptSchemeImpl getConceptSchemeAttributes(String conceptSchemeId,
                                                                String concept1Id,
                                                                String concept2Id,
                                                                String artefactRef1Id,
                                                                String artefactRef2Id) {
        ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setId(conceptSchemeId);
        conceptScheme.setOrganizationId(ORGANISATION_ID);
        ConceptImpl concept = getConceptWithEnumeratedRepresentation(concept1Id, artefactRef1Id, StringUtils.EMPTY);
        ConceptImpl concept2 = getConceptWithEnumeratedRepresentation(concept2Id, artefactRef2Id, StringUtils.EMPTY);
        conceptScheme.setItems(List.of(concept, concept2));
        conceptScheme.setVersion(Version.createFromString(VERSION));
        return conceptScheme;
    }

    public static ConceptImpl getConceptWithEnumeratedRepresentation(String conceptId, String artefactRefId, String artefactItemId) {
        ConceptImpl concept = getConcept(conceptId);
        IdentifiableArtefactReferenceImpl artefactReference = getIdentifiableArtefactReference(artefactRefId, artefactItemId);
        EnumeratedRepresentationImpl enumeratedRepresentation = new EnumeratedRepresentationImpl(artefactReference);
        concept.setCoreRepresentation(enumeratedRepresentation);
        return concept;
    }

    public static ConceptImpl getConceptWithBaseRepresentation(String conceptId, FacetType facetType, FacetValueType facetValueType) {
        ConceptImpl concept = getConcept(conceptId);
        BaseTextFormatRepresentationImpl baseTextFormatRepresentation = new BaseTextFormatRepresentationImpl(facetValueType);

        if (facetType != null) {
            Iterator<Facet> iterator = baseTextFormatRepresentation.nonEnumerated().iterator();
            if (iterator.hasNext()) {
                BaseFacetImpl next = (BaseFacetImpl) iterator.next();
                next.setType(facetType);
            }
        }

        concept.setCoreRepresentation(baseTextFormatRepresentation);
        return concept;
    }

    private static IdentifiableArtefactReferenceImpl getIdentifiableArtefactReference(String artefactRefId,
                                                                                      String artefactItemId) {
        IdentifiableArtefactReferenceImpl artefactReference = new IdentifiableArtefactReferenceImpl();
        artefactReference.setOrganisationId(ORGANISATION_ID);
        artefactReference.setVersion(VersionReference.createFromString(VERSION));
        artefactReference.setId(artefactRefId);
        artefactReference.setStructureClass(StructureClassImpl.CODELIST);
        if (!artefactItemId.equals(StringUtils.EMPTY)) {
            artefactReference.setItemId(artefactItemId);
        }
        return artefactReference;
    }

    private static ConceptImpl getConcept(String concept1Id) {
        ConceptImpl concept = new ConceptImpl();
        concept.setId(concept1Id);
        concept.setName(new InternationalString(concept1Id));
        return concept;
    }

    public static Artefacts getArtefacts() {
        CodelistImpl codelist_Country = createCountryCodelist();
        CodelistImpl codelist_Indicators = createIndicatorCodelist();
        CodelistImpl codelist_Units = createUnitCodelist();
        CodelistImpl codelist_ATTR_1 = createAttr1Codelist();
        CodelistImpl codelist_ATTR_2 = createAttr2Codelist();
        ConceptSchemeImpl conceptSchemeDim = getConceptSchemeDimensions("CS1");
        ConceptSchemeImpl conceptSchemeAttr = getConceptSchemeAttributes(
            "CS2",
            "attr_val1",
            "attr_val2",
            "CL-ATTR_1",
            "CL-ATTR_2"
        );
        DataStructureDefinitionImpl dsd = getDataStructureDefinition("DSD1", "Countries",
            "Indicators",
            "Units", "MES",
            "CS1",
            "CS2"
        );
        DataflowImpl dataflow = getDataflow("DSD1", "DFlow");
        Artefacts artefacts = new ArtefactsImpl();
        artefacts.getCodelists().addAll(List.of(
            codelist_Country,
            codelist_Indicators,
            codelist_Units,
            codelist_ATTR_1,
            codelist_ATTR_2
        ));
        artefacts.getConceptSchemes().addAll(List.of(conceptSchemeDim, conceptSchemeAttr));
        artefacts.getDataStructures().add(dsd);
        artefacts.getDataflows().add(dataflow);
        return artefacts;
    }

    public static DataflowImpl getDataflow(String dsdId, String dataflowId) {
        DataflowImpl dataflow = new DataflowImpl();
        dataflow.setId(dataflowId);
        dataflow.setVersion(Version.createFromString("1.0"));
        dataflow.setOrganizationId("QH");
        dataflow.setName(new InternationalString("DataFlow"));
        dataflow.setStructure(new IdentifiableArtefactReferenceImpl(dsdId, "QH", VERSION,
            StructureClassImpl.DATA_STRUCTURE, dsdId
        ));
        return dataflow;
    }

    public static DataStructureDefinitionImpl getDataStructureDefinition(String dsdId,
                                                                         String dimComponent1Id,
                                                                         String dimComponent2Id,
                                                                         String dimComponent3Id,
                                                                         String measureDescrId,
                                                                         String conceptScheme1Id,
                                                                         String conceptScheme2Id
    ) {
        DataStructureDefinitionImpl dsd = new DataStructureDefinitionImpl();
        dsd.setId(dsdId);
        dsd.setOrganizationId("QH");
        dsd.setVersion(Version.createFromString(VERSION));
        dsd.setName(new InternationalString("DSD"));
        setDimensionDescriptor(dimComponent1Id, dimComponent2Id, dimComponent3Id, conceptScheme1Id, dsd);
        setAttributeDescriptor(conceptScheme2Id, dsd);
        setMeasureDescriptor(conceptScheme1Id, dsd, measureDescrId, "Obs_value");
        return dsd;
    }

    private static void setMeasureDescriptor(String conceptScheme1Id,
                                             DataStructureDefinitionImpl dsd,
                                             String measureDescriptorId,
                                             String measureId) {
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId(measureDescriptorId);
        List<Measure> measures = new ArrayList<>();
        MeasureImpl measure = new MeasureImpl();
        measure.setId(measureId);
        measure.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            conceptScheme1Id, ORGANISATION_ID, VERSION, StructureClassImpl.CONCEPT_SCHEME, "obs_value"
        ));
        measures.add(measure);
        measureDescriptor.setComponents(measures);
        dsd.setMeasureDescriptor(measureDescriptor);
    }

    private static void setAttributeDescriptor(String conceptScheme2Id, DataStructureDefinitionImpl dsd) {
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId("AD");
        DataAttributeImpl dataAttribute1 = getDataAttribute("ATTR1", "attr_val1", conceptScheme2Id);
        DataAttributeImpl dataAttribute2 = getDataAttribute("ATTR2", "attr_val2", conceptScheme2Id);
        attributeDescriptor.setComponents(List.of(dataAttribute1, dataAttribute2));
        dsd.setAttributeDescriptor(attributeDescriptor);
    }

    private static void setDimensionDescriptor(String dimComponent1Id,
                                               String dimComponent2Id,
                                               String dimComponent3Id,
                                               String conceptScheme1Id,
                                               DataStructureDefinitionImpl dsd) {
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        List<DimensionComponent> dimensionComponents = new ArrayList<>();
        DimensionComponentImpl dimensionComponent1 = getDimensionComponent(dimComponent1Id, conceptScheme1Id);
        DimensionComponentImpl dimensionComponent2 = getDimensionComponent(dimComponent2Id, conceptScheme1Id);
        DimensionComponentImpl dimensionComponent3 = getDimensionComponent(dimComponent3Id, conceptScheme1Id);
        dimensionComponents.add(dimensionComponent1);
        dimensionComponents.add(dimensionComponent2);
        dimensionComponents.add(dimensionComponent3);
        dimensionDescriptor.setComponents(dimensionComponents);
        dsd.setDimensionDescriptor(dimensionDescriptor);
    }

    public static DataAttributeImpl getDataAttribute(String attrId, String attrVal, String conceptschemeId) {
        DataAttributeImpl dataAttribute = new DataAttributeImpl();
        dataAttribute.setId(attrId);
        dataAttribute.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            conceptschemeId, ORGANISATION_ID, VERSION, StructureClassImpl.CONCEPT_SCHEME, attrVal
        ));
        return dataAttribute;
    }

    private static DimensionComponentImpl getDimensionComponent(String dimComponent1Id, String conceptScheme1Id) {
        DimensionComponentImpl dimensionComponent = new DimensionImpl();
        dimensionComponent.setId(dimComponent1Id);
        dimensionComponent.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            conceptScheme1Id, ORGANISATION_ID, VERSION, StructureClassImpl.CONCEPT, dimComponent1Id
        ));
        return dimensionComponent;
    }

    private static ConceptSchemeImpl getConceptSchemeDimensions(String conceptSchemeId) {
        ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setId(conceptSchemeId);
        conceptScheme.setOrganizationId(ORGANISATION_ID);
        ConceptImpl concept = getConceptWithEnumeratedRepresentation("Countries", "CL-Countries", StringUtils.EMPTY);
        ConceptImpl concept2 = getConceptWithEnumeratedRepresentation("Indicators", "CL-Indicators", StringUtils.EMPTY);
        ConceptImpl concept3 = getConceptWithEnumeratedRepresentation("Units", "CL-Units", StringUtils.EMPTY);
        ConceptImpl concept4 = getConceptWithBaseRepresentation("obs_value", null, FacetValueType.STRING);
        conceptScheme.setItems(List.of(concept, concept2, concept3, concept4));
        conceptScheme.setVersion(Version.createFromString(VERSION));
        return conceptScheme;
    }

    public static CodelistImpl createCountryCodelist() {
        CodelistImpl codelist = getCodelist("CL-Countries");
        CodeImpl code = getCode("111");
        CodeImpl code2 = getCode("112");
        CodeImpl code3 = getCode("110");
        codelist.setItems(List.of(code, code2, code3));
        return codelist;
    }

    public static CodeImpl getCode(String id) {
        CodeImpl code = new CodeImpl();
        code.setId(id);
        return code;
    }

    public static CodelistImpl getCodelist(String id) {
        CodelistImpl codelist = new CodelistImpl();
        codelist.setId(id);
        codelist.setOrganizationId(ORGANISATION_ID);
        codelist.setVersion(Version.createFromString(VERSION));
        return codelist;
    }

    public static CodelistImpl createIndicatorCodelist() {
        CodelistImpl codelist = getCodelist("CL-Indicators");
        CodeImpl code = getCode("LP");
        CodeImpl code2 = getCode("KP");
        codelist.setItems(List.of(code, code2));
        return codelist;
    }

    public static CodelistImpl createUnitCodelist() {
        CodelistImpl codelist = getCodelist("CL-Units");
        CodeImpl code = getCode("Person");
        CodeImpl code2 = getCode("No_Person");
        codelist.setItems(List.of(code, code2));
        return codelist;
    }

    public static CodelistImpl createAttr1Codelist() {
        CodelistImpl codelist = getCodelist("CL-ATTR_1");
        CodeImpl code = getCode("K");
        CodeImpl code2 = getCode("P");
        codelist.setItems(List.of(code, code2));
        return codelist;
    }

    public static CodelistImpl createAttr2Codelist() {
        CodelistImpl codelist = getCodelist("CL-ATTR_2");
        CodeImpl code = getCode("L");
        CodeImpl code2 = getCode("N");
        codelist.setItems(List.of(code, code2));
        return codelist;
    }

    public static MetadataStructureDefinition createMdsd() {
        MetadataStructureDefinitionImpl mdsd = new MetadataStructureDefinitionImpl();
        mdsd.setId("MDSD1");
        mdsd.setVersion(Version.createFromString("1.0.0"));
        mdsd.setOrganizationId("IMF");
        mdsd.setName(new InternationalString("MDSD"));
        MetadataAttributeDescriptor attributeDescriptor = new MetadataAttributeDescriptorImpl();
        MetadataAttributeImpl dataAttribute = new MetadataAttributeImpl();
        dataAttribute.setId("Multi");
        dataAttribute.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT_SCHEME, "MULT"
        ));
        attributeDescriptor.getComponents().add(dataAttribute);
        mdsd.setAttributeDescriptor(attributeDescriptor);
        return mdsd;
    }
}
