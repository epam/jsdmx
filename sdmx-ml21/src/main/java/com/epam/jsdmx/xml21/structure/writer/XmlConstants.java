package com.epam.jsdmx.xml21.structure.writer;


import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.EpochPeriodType;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.ResolvePeriod;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public final class XmlConstants {

    public static final String SCHEMAS_V_2_1_MESSAGE = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message";
    public static final String SCHEMAS_V_2_1_STRUCTURE = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/structure";
    public static final String SCHEMAS_V_2_1_COMMON = "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common";
    public static final String XMLSCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XML_1998_NAMESPACE = "http://www.w3.org/XML/1998/namespace";

    public static final String COMMON = "com:";
    public static final String STR = "str:";
    public static final String MESSAGE = "mes:";

    public static final String PREFIX = "prefix";
    public static final String REF = "Ref";
    public static final String HEADER = "Header";
    public static final String MES_ID = "ID";
    public static final String MES_TEST = "Test";
    public static final String PREPARED = "Prepared";
    public static final String SENDER = "Sender";
    public static final String RECEIVER = "Receiver";

    public static final String MES_STRUCTURES = "Structures";
    public static final String HIERARCHICAL_CODELISTS = "HierarchicalCodelists";
    public static final String CODELISTS = "Codelists";
    public static final String CONCEPT_SCHEMES = "Concepts";
    public static final String DATA_STRUCTURES = "DataStructures";
    public static final String DATAFLOWS = "Dataflows";
    public static final String METADATA_STRUCTURES = "MetadataStructures";
    public static final String METADATA_FLOWS = "Metadataflows";
    public static final String REPRESENTATION_MAPS = "RepresentationMaps";
    public static final String STRUCTURE_MAPS = "StructureMaps";
    public static final String CATEGORISATIONS = "Categorisations";
    public static final String CATEGORY_SCHEMES = "CategorySchemes";
    public static final String ORGANISATION_SCHEMES = "OrganisationSchemes";
    public static final String CATEGORY_SCHEME_MAPS = "CategorySchemeMaps";
    public static final String CONCEPT_SCHEME_MAPS = "ConceptSchemeMaps";
    public static final String DATA_CONSTRAINTS = "DataConstraints";
    public static final String DATA_CONSUMER_SCHEMES = "DataConsumerSchemes";
    public static final String DATA_PROVIDER_SCHEMES = "DataProviderSchemes";
    public static final String METADATA_PROVIDER_SCHEMES = "MetadataProviderSchemes";
    public static final String HIERARCHY_ASSOCIATIONS = "HierarchyAssociations";
    public static final String METADATA_CONSTRAINTS = "MetadataConstraints";
    public static final String METADATA_PROVISION_AGREEMENTS = "MetadataProvisionAgreements";
    public static final String ORGANISATION_SCHEME_MAPS = "OrganisationSchemeMaps";
    public static final String PROCESSES = "Processes";
    public static final String PROVISION_AGREEMENTS = "ProvisionAgreements";
    public static final String REPORTING_TAXONOMIES = "ReportingTaxonomies";
    public static final String REPORTING_TAXONOMY_MAPS = "ReportingTaxonomyMaps";
    public static final String VALUE_LISTS = "ValueLists";
    public static final String GEO_CODELISTS = "GeographicCodelists";
    public static final String GEOGRID_CODELISTS = "GeoGridCodelists";

    public static final String MES_STRUCTURE = "Structure";
    public static final String CODELIST = "Codelist";
    public static final String HIERARCHAL_CODELIST = "HierarchicalCodelist";
    public static final String CONCEPT_SCHEME = "ConceptScheme";
    public static final String DATA_STRUCTURE = "DataStructure";
    public static final String DATAFLOW = "Dataflow";
    public static final String METADATA_FLOW = "Metadataflow";
    public static final String REPRESENTATION_MAP = "RepresentationMap";
    public static final String STRUCTURE_MAP = "StructureMap";
    public static final String CATEGORISATION = "Categorisation";
    public static final String CATEGORY_SCHEME = "CategoryScheme";
    public static final String AGENCY_SCHEME = "AgencyScheme";
    public static final String CATEGORY_SCHEME_MAP = "CategorySchemeMap";
    public static final String CONCEPT_SCHEME_MAP = "ConceptSchemeMap";
    public static final String DATA_CONSTRAINT = "DataConstraint";
    public static final String CONSTRAINTS = "Constraints";
    public static final String CONTENT_CONSTRAINT = "ContentConstraint";
    public static final String DATA_CONSUMER_SCHEME = "DataConsumerScheme";
    public static final String DATA_PROVIDER_SCHEME = "DataProviderScheme";
    public static final String METADATA_PROVIDER_SCHEME = "MetadataProviderScheme";
    public static final String HIERARCHY_ASSOCIATION = "HierarchyAssociation";
    public static final String METADATA_CONSTRAINT = "MetadataConstraint";
    public static final String ORGANISATION_SCHEME_MAP = "OrganisationSchemeMap";
    public static final String PROCESS = "Process";
    public static final String PROVISION_AGREEMENT = "ProvisionAgreement";
    public static final String REPORTING_TAXONOMY = "ReportingTaxonomy";
    public static final String REPORTING_TAXONOMY_MAP = "ReportingTaxonomyMap";
    public static final String VALUE_LIST = "ValueList";
    public static final String GEO_CODELIST = "GeographicCodelist";
    public static final String GEOGRID_CODELIST = "GeoGridCodelist";

    public static final String INCLUSIVE_CODE_SELECTION = "InclusiveCodeSelection";
    public static final String EXCLUSIVE_CODE_SELECTION = "ExclusiveCodeSelection";
    public static final String MEMBER_VALUE = "MemberValue";
    public static final String CODELIST_EXTENSION = "CodelistExtension";
    public static final String CASCADE_VALUES = "cascadeValues";

    public static final String COM_ANNOTATIONS = "Annotations";
    public static final String COM_ANNOTATION = "Annotation";
    public static final String COM_ANNOTATION_TITLE = "AnnotationTitle";
    public static final String COM_ANNOTATION_TYPE = "AnnotationType";
    public static final String COM_ANNOTATION_URL = "AnnotationURL";
    public static final String COM_ANNOTATION_TEXT = "AnnotationText";
    public static final String COM_ANNOTATION_VALUE = "AnnotationValue";

    public static final String COM_NAME = "Name";
    public static final String COM_DESCRIPTION = "Description";
    public static final String COM_LINK = "Link";
    public static final String STR_CODE = "Code";
    public static final String STRUCTURE_PARENT = "Parent";

    public static final String CANNOT_BE_NULL = " cannot be null!";
    public static final String STRUCTURE_URL = "structureUrl";
    public static final String SERVICE_URL = "serviceUrl";

    public static final String HAS_FORMAL_LEVELS = "hasFormalLevels";
    public static final String DOES_NOT_SUPPORT_ELEMENT = "does not support element: ";


    public static final String CODE = "Code";
    public static final String LEVEL = "Level";
    public static final String CODING_FORMAT = "CodingFormat";
    public static final String HIERARCHICAL_CODE = "HierarchicalCode";

    public static final String CONCEPT = "Concept";

    public static final String CORE_REPRESENTATION = "CoreRepresentation";
    public static final String ENUMERATION = "Enumeration";
    public static final String TEXT_FORMAT = "TextFormat";
    public static final String SENTINEL_VALUE = "SentinelValue";
    public static final String ISOCONCEPT_REFERENCE = "ISOConceptReference";
    public static final String CONCEPT_AGENCY = "ConceptAgency";
    public static final String CONCEPT_SCHEME_ID = "ConceptSchemeID";
    public static final String CONCEPT_ID = "ConceptID";

    public static final String DATA_STRUCTURE_COMPONENTS = "DataStructureComponents";
    public static final String METADATA = "Metadata";


    public static final String ATTRIBUTE_LIST = "AttributeList";
    public static final String METADATA_ATTRIBUTE_USAGE = "MetadataAttributeUsage";
    public static final String METADATA_ATTRIBUTE_REFERENCE = "MetadataAttributeReference";
    public static final String ATTRIBUTE = "Attribute";
    public static final String ASSIGNMENT_STATUS = "assignmentStatus";
    public static final String MANDATORY = "Mandatory";
    public static final String CONDITIONAL = "Conditional";
    public static final String MEASURE_RELATIONSHIP = "MeasureRelationship";
    public static final String PRIMARY_MEASURE = "PrimaryMeasure";
    public static final String DIMENSION = "Dimension";
    public static final String ATTACHMENT_GROUP = "AttachmentGroup";
    public static final String OBSERVATION = "Observation";
    public static final String GROUP = "Group";
    public static final String MEASURE_LIST = "MeasureList";
    public static final String LOCAL_REPRESENTATION = "LocalRepresentation";

    public static final String DIMENSION_LIST = "DimensionList";
    public static final String TIME_DIMENSION = "TimeDimension";
    public static final String POSITION = "position";
    public static final String ATTRIBUTE_RELATIONSHIP = "AttributeRelationship";
    public static final String NONE_RELATIONSHIP = "None";
    public static final BidiMap<String, String> MAP_FACET_TYPE = new DualHashBidiMap<>();
    public static final String CONCEPT_IDENTITY = "ConceptIdentity";
    public static final String CONCEPT_ROLE = "ConceptRole";
    public static final String LANG = "lang";
    public static final String STRUCTURE_UPPER = "Structure";
    public static final String MIN_OCCURS = "minOccurs";
    public static final String MAX_OCCURS = "maxOccurs";
    public static final String IS_PRESENTATIONAL = "isPresentational";
    public static final String METADATA_ATTRIBUTE = "MetadataAttribute";
    public static final String METADATA_ATTRIBUTE_LIST = "MetadataAttributeList";
    public static final String METADATA_STRUCTURE_COMPONENTS = "MetadataStructureComponents";

    public static final String TARGET = "Target";
    public static final String SOURCE_DATA_TYPE = "SourceDataType";
    public static final String TARGET_DATA_TYPE = "TargetDataType";
    public static final String SOURCE_CODELIST = "SourceCodelist";
    public static final String TARGET_CODELIST = "TargetCodelist";
    public static final String REPRESENTATION_MAPPING = "RepresentationMapping";
    public static final String TARGET_VALUE = "TargetValue";
    public static final String SOURCE_VALUE = "SourceValue";

    public static final String MEASURE_DESCRIPTOR_ID = "MeasureDescriptor";
    public static final String ATTRIBUTE_DESCRIPTOR_ID = "AttributeDescriptor";
    public static final String METADATA_ATTRIBUTE_DESCRIPTOR_ID = "MetadataAttributeDescriptor";
    public static final String DIMENSION_DESCRIPTOR_ID = "DimensionDescriptor";
    public static final String GROUP_DIMENSION = "GroupDimension";
    public static final String DIMENSION_REFERENCE = "DimensionReference";

    public static final String SOURCE = "Source";
    public static final String COMPONENT_MAP = "ComponentMap";
    public static final String FIXED_VALUE_MAP = "FixedValueMap";
    public static final String VALUE = "Value";
    public static final String FREQUENCY_ID = "FrequencyId";
    public static final String DATE_PATTERN = "DatePattern";
    public static final String FREQUENCY_FORMAT_MAPPING = "FrequencyFormatMapping";
    public static final String DATE_PATTERN_MAP = "DatePatternMap";
    public static final String EPOCH_MAP = "EpochMap";
    public static final String FREQUENCY_DIMENSION = "FrequencyDimension";
    public static final String TARGET_FREQUENCY_ID = "TargetFrequencyID";
    public static final String MAPPED_FREQUENCIES = "MappedFrequencies";
    public static final String CATEGORY = "Category";

    public static final BidiMap<String, ResolvePeriod> STRING_RESOLVE_PERIOD_MAP = new DualHashBidiMap<>();

    public static final BidiMap<String, EpochPeriodType> STRING_EPOCH_PERIOD_MAP = new DualHashBidiMap<>();

    public static final BidiMap<CascadeValue, String> CASCADE_VALUE_TYPE_STRING = new DualHashBidiMap<>();

    public static final BidiMap<ConstraintRoleType, String> CONSTRAINT_ROLE_STRING = new DualHashBidiMap<>();
    public static final String XML_LANG = "xml:lang";

    public static final String CONTACT = "Contact";
    public static final String NAME = "Name";
    public static final String DEPARTMENT = "Department";
    public static final String ROLE = "Role";
    public static final String TELEPHONE = "Telephone";
    public static final String FAX = "Fax";
    public static final String X_400 = "X400";
    public static final String EMAIL = "Email";
    public static final String AGENCY = "Agency";
    public static final String URI = "URI";
    public static final String ITEM_MAP = "ItemMap";

    public static final String DATA_CONSUMER = "DataConsumer";
    public static final String DATA_CONSUMERS = "DATA_CONSUMERS";
    public static final String DATA_PROVIDER = "DataProvider";
    public static final String DATA_PROVIDERS = "DATA_PROVIDERS";
    public static final String METADATA_PROVIDERS = "METADATA_PROVIDERS";
    public static final String ORGANISATION_UNIT_SCHEMES = "OrganisationUnitSchemes";

    public static final String LINKED_HIERARCHY = "LinkedHierarchy";
    public static final String LINKED_OBJECT = "LinkedObject";
    public static final String CONTEXT_OBJECT = "ContextObject";
    public static final String ROLE_ATTR = "role";
    public static final String CONSTRAINT_ATTACHMENT = "ConstraintAttachment";
    public static final String RELEASE_CALENDAR = "ReleaseCalendar";
    public static final String METADATA_TARGET_REGION = "MetadataTargetRegion";
    public static final String OFFSET = "Offset";
    public static final String PERIODICITY = "Periodicity";
    public static final String TOLERANCE = "Tolerance";
    public static final String IS_INCLUSIVE = "isInclusive";
    public static final String START_PERIOD = "StartPeriod";
    public static final String END_PERIOD = "EndPeriod";
    public static final String BEFORE_PERIOD = "BeforePeriod";
    public static final String AFTER_PERIOD = "AfterPeriod";
    public static final String TIME_RANGE = "TimeRange";
    public static final String INCLUDE = "include";
    public static final String METADATA_TARGET = "metadataTarget";
    public static final String REPORT = "report";
    public static final String COMPONENT = "Component";
    public static final String KEY_VALUE = "KeyValue";
    public static final String REMOVE_PREFIX = "removePrefix";
    public static final String ALLOWED = "Allowed";
    public static final String METADATA_PROVIDER = "MetadataProvider";
    public static final String METADATA_SET = "MetadataSet";
    public static final String METADATA_STRUCTURE = "MetadataStructure";
    public static final String METADATAFLOW = "Metadataflow";
    public static final String METADATA_PROVISION_AGREEMENT = "MetadataProvisionAgreement";
    public static final String ORGANISATION_UNIT_SCHEME = "OrganisationUnitScheme";
    public static final String ORGANISATION_UNIT = "OrganisationUnit";
    public static final String LOCAL_ID = "localID";
    public static final String TARGET_STEP = "TargetStep";
    public static final String CONDITION = "Condition";
    public static final String INPUT = "Input";
    public static final String OUTPUT = "Output";
    public static final String SOFTWARE_LANGUAGE = "softwareLanguage";
    public static final String SOFTWARE_VERSION = "softwareVersion";
    public static final String SOFTWARE_PACKAGE = "softwarePackage";
    public static final String DESCRIPTION = "Description";
    public static final String OBJECT_REFERENCE = "ObjectReference";
    public static final String COMPUTATION = "Computation";
    public static final String TRANSITION = "Transition";
    public static final String PROCESS_STEP = "ProcessStep";
    public static final String VALUE_ITEM = "ValueItem";

    public static final String REPORTING_CATEGORY = "ReportingCategory";
    public static final String PROVISIONING_METADATA = "ProvisioningMetadata";
    public static final String STRUCTURAL_METADATA = "StructuralMetadata";
    public static final String IS_INCLUDED = "isIncluded";
    public static final String KEY = "Key";
    public static final String CUBE_REGION = "CubeRegion";
    public static final String DATA_KEY_SET = "DataKeySet";
    public static final String TEXT_TYPE = "textType";
    public static final String ID = "id";
    public static final String NAME_LOWER = "name";
    public static final String NAMES = "names";
    public static final String VERSION = "version";
    public static final String AGENCY_ID = "agencyID";
    public static final String DESCRIPTION_LOWER = "description";
    public static final String DESCRIPTIONS = "descriptions";
    public static final String VALID_TO = "validTo";
    public static final String VALID_FROM = "validFrom";
    public static final String IS_PARTIAL = "isPartial";
    public static final String IS_EXTERNAL_REFERENCE = "isExternalReference";
    public static final String URN = "urn";
    public static final String TYPE = "type";
    public static final String REL = "rel";
    public static final String URL = "url";
    public static final String DEFAULT_VERSION = "1.0";

    public static final String HIERARCHY = "Hierarchy";
    public static final String OBS_VALUE = "OBS_VALUE";

    static {
        STRING_RESOLVE_PERIOD_MAP.put("startOfPeriod", ResolvePeriod.START_OF_PERIOD);
        STRING_RESOLVE_PERIOD_MAP.put("endOfPeriod", ResolvePeriod.END_OF_PERIOD);
        STRING_RESOLVE_PERIOD_MAP.put("midPeriod", ResolvePeriod.MID_PERIOD);
    }

    static {
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.TRUE, "true");
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.FALSE, "false");
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.EXCLUDE_ROOT, "excluderoot");
    }


    static {
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ALLOWABLE_CONTENT, ALLOWED);
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ACTUAL_CONTENT, "Actual");
    }

    static {
        STRING_EPOCH_PERIOD_MAP.put("day", EpochPeriodType.DAY);
        STRING_EPOCH_PERIOD_MAP.put("second", EpochPeriodType.SECOND);
        STRING_EPOCH_PERIOD_MAP.put("microsecond", EpochPeriodType.MICROSECOND);
        STRING_EPOCH_PERIOD_MAP.put("millisecond", EpochPeriodType.MILLISECOND);
        STRING_EPOCH_PERIOD_MAP.put("nanosecond", EpochPeriodType.NANOSECOND);
    }

    static {
        MAP_FACET_TYPE.put(FacetType.INTERVAL.name(), "interval");
        MAP_FACET_TYPE.put(FacetType.DECIMALS.name(), "decimals");
        MAP_FACET_TYPE.put(FacetType.IS_MULTILINGUAL.name(), "isMultilingual");
        MAP_FACET_TYPE.put(FacetType.END_VALUE.name(), "endValue");
        MAP_FACET_TYPE.put(FacetType.END_TIME.name(), "endTime");
        MAP_FACET_TYPE.put(FacetType.IS_SEQUENCE.name(), "isSequence");
        MAP_FACET_TYPE.put(FacetType.MAX_LENGTH.name(), "maxLength");
        MAP_FACET_TYPE.put(FacetType.MAX_VALUE.name(), "maxValue");
        MAP_FACET_TYPE.put(FacetType.MIN_LENGTH.name(), "minLength");
        MAP_FACET_TYPE.put(FacetType.MIN_VALUE.name(), "minValue");
        MAP_FACET_TYPE.put(FacetType.PATTERN.name(), "pattern");
        MAP_FACET_TYPE.put(FacetType.SENTINEL_VALUES.name(), "sentinelValues");
        MAP_FACET_TYPE.put(FacetType.START_TIME.name(), "startTime");
        MAP_FACET_TYPE.put(FacetType.START_VALUE.name(), "startValue");
        MAP_FACET_TYPE.put(FacetType.TIME_INTERVAL.name(), "timeInterval");
    }


    private XmlConstants() {
    }
}
