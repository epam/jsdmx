package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

/**
 * Enumeration of SDMX artefact types
 */
@RequiredArgsConstructor
public enum StructureClassImpl implements StructureClass {

    AGENCY("base", "Agency", "Agency"),
    AGENCY_SCHEME("base", "AgencyScheme", "Agency Scheme"),
    DATA_CONSUMER("base", "DataConsumer", "Data Consumer"),
    DATA_CONSUMER_SCHEME("base", "DataConsumerScheme", "Data Consumer Scheme"),
    DATA_PROVIDER("base", "DataProvider", "Data Provider"),
    DATA_PROVIDER_SCHEME("base", "DataProviderScheme", "Data Provider Scheme"),
    METADATA_PROVIDER("base", "MetadataProvider", "Metadata Provider"),
    METADATA_PROVIDER_SCHEME("base", "MetadataProviderScheme", "Metadata Provider Scheme"),
    ORGANISATION_UNIT("base", "OrganisationUnit", "Organisation Unit"),
    ORGANISATION_UNIT_SCHEME("base", "OrganisationUnitScheme", "Organisation Unit Scheme"),

    ATTRIBUTE_DESCRIPTOR("datastructure", "AttributeDescriptor", "Attribute Descriptor"),
    DATA_ATTRIBUTE("datastructure", "DataAttribute", "Data Attribute"),
    DATAFLOW("datastructure", "Dataflow", "Dataflow"),
    DATA_STRUCTURE("datastructure", "DataStructure", "Data Structure Definition"),
    DIMENSION("datastructure", "Dimension", "Dimension"),
    DIMENSION_DESCRIPTOR("datastructure", "DimensionDescriptor", "Dimension Descriptor"),
    GROUP_DIMENSION_DESCRIPTOR("datastructure", "GroupDimensionDescriptor", "Group Dimension Descriptor"),
    MEASURE("datastructure", "Measure", "Measure"),
    MEASURE_DESCRIPTOR("datastructure", "MeasureDescriptor", "Measure Descriptor"),
    TIME_DIMENSION("datastructure", "TimeDimension", "Time Dimension"),

    METADATA_ATTRIBUTE("metadatastructure", "MetadataAttribute", "Metadata Attribute"),
    METADATA_ATTRIBUTE_DESCRIPTOR("metadatastructure", "MetadataAttributeDescriptor", "Metadata Attribute Descriptor"),
    METADATA_STRUCTURE("metadatastructure", "MetadataStructure", "Metadata Structure Definition"),
    METADATAFLOW("metadatastructure", "Metadataflow", "Metadataflow"),
    METADATA_SET("metadatastructure", "MetadataSet", "Metadata Set"),

    PROCESS("process", "Process", "Process"),
    PROCESS_STEP("process", "ProcessStep", "Process Step"),
    TRANSITION("process", "Transition", "Transition"),

    DATA_CONSTRAINT("registry", "DataConstraint", "Data Constraint"),
    METADATA_CONSTRAINT("registry", "MetadataConstraint", "Metadata Constraint"),
    METADATA_PROVISION_AGREEMENT("registry", "MetadataProvisionAgreement", "Metadata Provision Agreement"),
    PROVISION_AGREEMENT("registry", "ProvisionAgreement", "Provision Agreement"),
    SUBSCRIPTION("registry", "Subscription", "Subscription"),

    CATEGORY_SCHEME_MAP("structuremapping", "CategorySchemeMap", "Category Scheme Map"),
    CONCEPT_SCHEME_MAP("structuremapping", "ConceptSchemeMap", "Concept Scheme Map"),
    ORGANISATION_SCHEME_MAP("structuremapping", "OrganisationSchemeMap", "Organisation Scheme Map"),
    REPORTING_TAXONOMY_MAP("structuremapping", "ReportingTaxonomyMap", "Reporting Taxonomy Map"),
    REPRESENTATION_MAP("structuremapping", "RepresentationMap", "Representation Map"),
    STRUCTURE_MAP("structuremapping", "StructureMap", "Structure Map"),
    DATE_PATTERN_MAP("structuremapping", "DatePatternMap", "Date Pattern Map"),
    FREQUENCY_FORMAT_MAPPING("structuremapping", "FrequencyFormatMapping", "Frequency Format Mapping"),
    EPOCH_MAP("structuremapping", "EpochMap", "Epoch Map"),

    CODELIST("codelist", "Codelist", "Codelist"),
    CODE("codelist", "Code", "Code"),
    GEO_GRID_CODELIST("codelist", "GeoGridCodelist", "Geographical Grid Codelist"),
    GEOGRAPHIC_CODELIST("codelist", "GeographicCodelist", "Geographic Codelist"),
    HIERARCHICAL_CODE("codelist", "HierarchicalCode", "Hierarchical Code"),
    HIERARCHY("codelist", "Hierarchy", "Hierarchy"),
    HIERARCHY_ASSOCIATION("codelist", "HierarchyAssociation", "Hierarchy Association"),
    LEVEL("codelist", "Level", "Level"),
    VALUE_LIST("codelist", "ValueList", "Valuelist"),

    CATEGORISATION("categoryscheme", "Categorisation", "Categorisation"),
    CATEGORY("categoryscheme", "Category", "Category"),
    CATEGORY_SCHEME("categoryscheme", "CategoryScheme", "Category Scheme"),
    REPORTING_CATEGORY("categoryscheme", "ReportingCategory", "Reporting Category"),
    REPORTING_TAXONOMY("categoryscheme", "ReportingTaxonomy", "Reporting Taxonomy"),

    CONCEPT_SCHEME("conceptscheme", "ConceptScheme", "Concept Scheme"),
    CONCEPT("conceptscheme", "Concept", "Concept"),

    CUSTOM_TYPE("transformation", "CustomType", "Custom Type"),
    CUSTOM_TYPE_SCHEME("transformation", "CustomTypeScheme", "Custom Type Scheme"),
    NAME_PERSONALISATION("transformation", "NamePersonalisation", "Name Personalisation"),
    NAME_PERSONALISATION_SCHEME("transformation", "NamePersonalisationScheme", "Name Personalisation Scheme"),
    RULESET("transformation", "Ruleset", "Ruleset"),
    RULESET_SCHEME("transformation", "RulesetScheme", "Ruleset Scheme"),
    USER_DEFINED_OPERATOR("transformation", "UserDefinedOperator", "User Defined Operator"),
    USER_DEFINED_OPERATOR_SCHEME("transformation", "UserDefinedOperatorScheme", "User Defined Operator Scheme"),
    VLT_CODELIST_MAPPING("transformation", "VltCodelistMapping", "VLT Codelist Mapping"),
    VLT_CONCEPT_MAPPING("transformation", "VltConceptMapping", "VLT Concept Mapping"),
    VLT_DATAFLOW_MAPPING("transformation", "VltDataflowMapping", "VLT Dataflow Mapping"),
    VLT_MAPPING_SCHEME("transformation", "VltMappingScheme", "VLT Mapping Scheme");

    private static final String PACKAGE_PREFIX = "org.sdmx.infomodel.";

    private final String packageName;
    private final String simpleName;
    private final String label;

    /**
     * @param name -  full artefact type path artefact must be related to
     * @return artefact type related to such path
     */
    public static Optional<? extends StructureClass> getByFullyQualifiedName(String name) {
        return Stream.of(values())
            .filter(artefactType -> artefactType.getFullyQualifiedName().equalsIgnoreCase(name))
            .findFirst();
    }

    /**
     * @param name - the name of the enum constant
     * @return artefact type for such name if exist
     */
    public static Optional<? extends StructureClass> findByName(String name) {
        return Stream.of(values())
            .filter(artefactType -> artefactType.name().equalsIgnoreCase(name))
            .findFirst();
    }

    /**
     * @param simpleName - the simple name of the enum constant
     * @return artefact type for such name if exist
     */
    public static Optional<? extends StructureClass> findBySimpleName(String simpleName) {
        return Stream.of(values())
            .filter(artefactType -> artefactType.getSimpleName().equalsIgnoreCase(simpleName))
            .findFirst();
    }

    /**
     * @return packages in the SDMX Information Model. These packages act as convenient compartments
     * for the various sub models in the SDMX-IM
     */
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * @return full artefact type path defined in SDMX-IM
     */
    public String getFullyQualifiedName() {
        return PACKAGE_PREFIX + packageName + "." + simpleName;
    }

    @Override
    public String getPrintableName() {
        return label;
    }

    @Override
    public String toString() {
        return getSimpleName();
    }
}
