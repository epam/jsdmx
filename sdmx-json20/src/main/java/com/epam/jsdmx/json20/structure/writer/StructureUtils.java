package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StructureUtils {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NAMES = "names";
    public static final String VERSION = "version";
    public static final String AGENCY_ID = "agencyID";
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTIONS = "descriptions";
    public static final String VALID_TO = "validTo";
    public static final String VALID_FROM = "validFrom";

    public static final String CATEGORIES = "categories";

    public static final String ANNOTATIONS = "annotations";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String VALUE = "value";
    public static final String LANG = "lang";
    public static final String TEXT = "text";
    public static final String TEXTS = "texts";
    public static final String LINKS = "links";
    public static final String URN = "urn";
    public static final String TYPE = "type";
    public static final String REL = "rel";

    public static final String IS_PARTIAL = "isPartial";

    public static final String MAX_OCCURS = "maxOccurs";
    public static final String MIN_OCCURS = "minOccurs";
    public static final String CONCEPT_IDENTITY = "conceptIdentity";
    public static final String IS_PRESENTATIONAL = "isPresentational";
    public static final String LOCAL_REPRESENTATION = "localRepresentation";
    public static final String CORE_REPRESENTATION = "coreRepresentation";

    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static final String PREFIX = "prefix";
    public static final String SEQUENCE = "sequence";
    public static final String CODE_LIST = "codelist";

    public static final String HAS_FORMAT_LEVELS = "hasFormalLevels";
    public static final String LEVEL = "level";

    public static final String METADATA_ATTRIBUTE_USAGES = "metadataAttributeUsages";
    public static final String METADATA_ATTRIBUTE_REFERENCE = "metadataAttributeReference";
    public static final String ATTRIBUTE_LIST = "attributeList";
    public static final String DIMENSION_LIST = "dimensionList";
    public static final String DIMENSIONS = "dimensions";
    public static final String GROUP_DIMENSIONS = "groupDimensions";
    public static final String ATTRIBUTES = "attributes";
    public static final String TIME_DIMENSIONS = "timeDimensions";
    public static final String MEASURE_LIST = "measureList";
    public static final String MEASURES = "measures";
    public static final String MEASURE_RELATIONSHIP = "measureRelationship";
    public static final String ATTRIBUTE_RELATIONSHIP = "attributeRelationship";
    public static final String CONCEPT_ROLES = "conceptRoles";
    public static final String IS_MANDATORY = "isMandatory";
    public static final String POSITION = "position";
    public static final String METADATA = "metadata";
    public static final String DIMENSION = "dimension";
    public static final String OBSERVATION = "observation";
    public static final String NONE = "none";

    public static final String METADATA_STRUCTURE_COMPONENTS = "metadataStructureComponents";
    public static final String METADATA_ATTRIBUTE_LIST = "metadataAttributeList";
    public static final String METADATA_ATTRIBUTES = "metadataAttributes";
    public static final String ENUMERATION = "enumeration";

    public static final String IS_REG_EX = "isRegEx";
    public static final String SOURCE_VALUES = "sourceValues";
    public static final String TARGET_VALUES = "targetValues";
    public static final String REPRESENTATION_MAPPINGS = "representationMappings";
    public static final String START_INDEX = "startIndex";
    public static final String END_INDEX = "endIndex";
    public static final String DATA_TYPE = "dataType";
    public static final String EPOCH_MAPS = "epochMaps";
    public static final String BASE_PERIOD = "basePeriod";
    public static final String EPOCH_PERIOD = "epochPeriod";

    public static final String CONCEPTS = "concepts";

    public static final String CODE = "code";
    public static final String CODES = "codes";
    public static final String CODE_LIST_EXTENSIONS = "codelistExtensions";
    public static final String INCLUSIVE_CODE_SELECTION = "inclusiveCodeSelection";
    public static final String EXCLUSIVE_CODE_SELECTION = "exclusiveCodeSelection";
    public static final String MEMBER_VALUES = "memberValues";
    public static final String CASCADE_VALUES = "cascadeValues";
    public static final String WILDCARDED_MEMBER_VALUES = "wildcardedMemberValues";
    public static final String PARENT = "parent";
    public static final String STRUCTURE = "structure";

    public static final String DATA_CONSTRAINTS = "dataConstraints";
    public static final String ROLE = "role";
    public static final String RELEASE_CALENDAR = "releaseCalendar";
    public static final String TOLERANCE = "tolerance";
    public static final String OFFSET = "offset";
    public static final String PERIODICITY = "periodicity";
    public static final String INCLUDE = "include";
    public static final String CONSTRAINT_ATTACHMENT = "constraintAttachment";
    public static final String DATA_PROVIDER = "dataProvider";
    public static final String PROVISION_AGREEMENTS = "provisionAgreements";
    public static final String DATA_KEY_SETS = "dataKeySets";
    public static final String KEYS = "keys";
    public static final String KEY_VALUES = "keyValues";
    public static final String REMOVE_PREFIX = "removePrefix";
    public static final String COMPONENTS = "components";
    public static final String VALUES = "values";
    public static final String TIME_RANGE = "timeRange";
    public static final String START_PERIOD = "startPeriod";
    public static final String END_PERIOD = "endPeriod";
    public static final String BEFORE_PERIOD = "beforePeriod";
    public static final String AFTER_PERIOD = "afterPeriod";
    public static final String PERIOD = "period";
    public static final String IS_INCLUSIVE = "isInclusive";
    public static final String CUBE_REGIONS = "cubeRegions";
    public static final String IS_INCLUDED = "isIncluded";

    public static final String DATA_STRUCTURE_COMPONENTS = "dataStructureComponents";

    public static final String HIERARCHICAL_CODES = "hierarchicalCodes";
    public static final String METADATA_FLOW = "metadataflow";
    public static final String TARGETS = "targets";
    public static final String FORMAT = "format";
    public static final String DECIMALS = "decimals";
    public static final String IS_MULTI_LINGUAL = "isMultiLingual";
    public static final String IS_SEQUENCE = "isSequence";
    public static final String INTERVAL = "interval";
    public static final String START_VALUE = "startValue";
    public static final String END_VALUE = "endValue";
    public static final String TIME_INTERVAL = "timeInterval";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String MIN_LENGTH = "minLength";
    public static final String MAX_LENGTH = "maxLength";
    public static final String MIN_VALUE = "minValue";
    public static final String MAX_VALUE = "maxValue";
    public static final String PATTERN = "pattern";

    public static final String DATA = "data";
    public static final String CATEGORISATIONS = "categorisations";
    public static final String CATEGORY_SCHEMES = "categorySchemes";
    public static final String CODE_LISTS = "codelists";
    public static final String CONCEPT_SCHEMES = "conceptSchemes";
    public static final String DATA_STRUCTURES = "dataStructures";
    public static final String HIERARCHIES = "hierarchies";
    public static final String METADATAFLOWS = "metadataflows";
    public static final String METADATA_SETS = "metadataSets";
    public static final String METADATA_STRUCTURES = "metadataStructures";
    public static final String REPRESENTATION_MAPS = "representationMaps";
    public static final String DATAFLOWS = "dataflows";
    public static final String STRUCTURE_MAP = "structureMaps";
    public static final String DATE_PATTERN_MAPS = "datePatternMaps";
    public static final String LOCALE = "locale";
    public static final String SOURCE_PATTERN = "sourcePattern";
    public static final String COMPONENT_MAPS = "componentMaps";
    public static final String REPRESENTATION_MAP = "representationMap";
    public static final String FIXED_VALUE_MAPS = "fixedValueMaps";

    public static final String FREQUENCY_FORMAT_MAPPINGS = "frequencyFormatMappings";
    public static final String FREQUENCY_ID = "frequencyId";
    public static final String DATE_PATTERN = "datePattern";
    public static final String RESOLVE_PERIOD = "resolvePeriod";
    public static final String FREQUENCY_DIMENSION = "frequencyDimension";
    public static final String YEAR_START = "yearStart";
    public static final String DAY_OF_MONTH = "dayOfMonth";
    public static final String MONTH_OF_YEAR = "monthOfYear";
    public static final String MAPPED_COMPONENTS = "mappedComponents";
    public static final String METADATA_PROVISION_AGREEMENT = "metadataProvisionAgreement";
    public static final String METADATA_PROVISION_AGREEMENTS = "metadataProvisionAgreements";
    public static final String ISOCONCEPTREFERENCE = "isoConceptReference";
    public static final String ENUMERATION_FORMAT = "enumerationFormat";
    public static final String CODING_FORMAT = "codingFormat";
    public static final String NO_SUCH_PROPERTY_IN = "no such property in ";
    public static final String TARGET_FREQUENCY_ID = "targetFrequencyID";
    public static final String MAPPED_FREQUENCIES = "mappedFrequencies";
    public static final String SCHEMA = "schema";
    public static final String TEST = "test";
    public static final String PREPARED = "prepared";
    public static final String CONTENT_LANGUAGES = "contentLanguages";
    public static final String SENDER = "sender";
    public static final String IS_EXTERNAL_REFERENCE = "isExternalReference";
    public static final String META = "meta";
    public static final String GROUPS = "groups";
    public static final String TITLES = "titles";
    public static final String HREF = "href";
    public static final String HREFLANG = "hreflang";
    public static final String URI = "uri";
    public static final String BASIC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ZONE = "Z";

    public static final String CONTACTS = "contacts";
    public static final String DEPARTMENT = "department";
    public static final String TELEPHONES = "telephones";
    public static final String FAXES = "faxes";
    public static final String X_400_S = "x400s";
    public static final String URIS = "uris";
    public static final String EMAILS = "emails";
    public static final String AGENCIES = "agencies";
    public static final String AGENCIES_ID = "AGENCIES";
    public static final String DATA_CONSUMERS = "dataConsumers";
    public static final String DATA_PROVIDERS = "dataProviders";
    public static final String METADATA_PROVIDERS = "metadataProviders";
    public static final String VALUE_ITEMS = "valueItems";

    public static final String LINKED_OBJECT = "linkedObject";
    public static final String LINKED_HIERARCHY = "linkedHierarchy";
    public static final String CONTEXT_OBJECT = "contextObject";
    public static final String ORGANISATION_UNITS = "organisationUnits";
    public static final String REPORTING_CATEGORIES = "reportingCategories";
    public static final String STRUCTURAL_METADATA = "structuralMetadata";
    public static final String PROVISIONING_METADATA = "provisioningMetadata";
    public static final String ITEM_MAPS = "itemMaps";
    public static final String SOURCE_VALUE = "sourceValue";
    public static final String TARGET_VALUE = "targetValue";
    public static final String DATAFLOW = "dataflow";
    public static final String METADATAFLOW = "metadataflow";
    public static final String METADATA_PROVIDER = "metadataProvider";
    public static final String CONDITION = "condition";
    public static final String CONDITIONS = "conditions";
    public static final String LOCAL_ID = "localID";
    public static final String TARGET_STEP = "targetStep";
    public static final String TRANSITIONS = "transitions";
    public static final String OBJECT_REFERENCE = "objectReference";
    public static final String SOFTWARE_LANGUAGE = "softwareLanguage";
    public static final String SOFTWARE_PACKAGE = "softwarePackage";
    public static final String SOFTWARE_VERSION = "softwareVersion";
    public static final String COMPUTATION = "computation";
    public static final String OUTPUTS = "outputs";
    public static final String INPUTS = "inputs";
    public static final String PROCESS_STEPS = "processSteps";
    public static final String METADATA_TARGET_REGIONS = "metadataTargetRegions";

    public static final String SENTINEL_VALUES = "sentinelValues";
    public static final String ISO_CONCEPT_REFERENCE = "isoConceptReference";
    public static final String CONCEPT_SCHEME_ID = "conceptSchemeID";
    public static final String CONCEPT_ID = "conceptID";
    public static final String CONCEPT_AGENCY = "conceptAgency";
    public static final String GROUP = "group";
    public static final String AGENCY_SCHEMES = "agencySchemes";
    public static final String CATEGORY_SCHEME_MAPS = "categorySchemeMaps";
    public static final String CONCEPT_SCHEME_MAPS = "conceptSchemeMaps";
    public static final String DATA_CONSUMER_SCHEMES = "dataConsumerSchemes";
    public static final String DATA_PROVIDER_SCHEMES = "dataProviderSchemes";
    public static final String METADATA_PROVIDER_SCHEMES = "metadataProviderSchemes";
    public static final String HIERARCHY_ASSOCIATIONS = "hierarchyAssociations";
    public static final String METADATA_CONSTRAINTS = "metadataConstraints";
    public static final String ORGANISATION_SCHEME_MAPS = "organisationSchemeMaps";
    public static final String PROCESSES = "processes";
    public static final String REPORTING_TAXONOMIES = "reportingTaxonomies";
    public static final String REPORTING_TAXONOMY_MAPS = "reportingTaxonomyMaps";
    public static final String VALUE_LISTS = "valueLists";
    public static final String ORGANISATION_UNIT_SCHEMES = "organisationUnitSchemes";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String EXCLUDE_ROOT = "exclude_root";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String YEAR_QUART_PATTERN = "yyyy-QQ";
    public static final String REGEX = "-";
    public static final String ATTRIBUTE_DESCRIPTOR_ID = "AttributeDescriptor";
    public static final String DIMENSION_DESCRIPTOR_ID = "DimensionDescriptor";
    public static final String MEASURE_DESCRIPTOR_ID = "MeasureDescriptor";


    public static final String DATA_CONSUMERS_ID = "DATA_CONSUMERS";
    public static final String DATA_PROVIDERS_ID = "DATA_PROVIDERS";
    public static final String DEFAULT_VERSION = "1.0";

    public static final String GEOGRAPHIC_CODELISTS = "geographicCodelists";
    public static final String GEO_GRID_CODELISTS = "geoGridCodelists";
    public static final String GEO_TYPE = "geoType";
    public static final String GEOGRAPHIC_CODELIST = "GeographicCodelist";
    public static final String GEO_GRID_CODELIST = "GeoGridCodelist";
    public static final String GEO_FEATURE_SET_CODES = "geoFeatureSetCodes";
    public static final BidiMap<CascadeValue, String> CASCADE_VALUE_TYPE_STRING = new DualHashBidiMap<>();
    public static final BidiMap<String, CascadeValue> STRING_CASCADE_VALUE_TYPE = CASCADE_VALUE_TYPE_STRING.inverseBidiMap();
    public static final BidiMap<ConstraintRoleType, String> CONSTRAINT_ROLE_STRING = new DualHashBidiMap<>();
    public static final BidiMap<String, ConstraintRoleType> STRING_CONSTRAINT_ROLE = CONSTRAINT_ROLE_STRING.inverseBidiMap();

    public static final String GRID_DEFINITION = "gridDefinition";
    public static final String GEO_GRID_CODES = "geoGridCodes";
    public static final String GEO_CELL = "geoCell";

    static {
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.TRUE, TRUE);
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.FALSE, FALSE);
        CASCADE_VALUE_TYPE_STRING.put(CascadeValue.EXCLUDE_ROOT, "excluderoot");
    }

    static {
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ALLOWABLE_CONTENT, "Allowed");
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ACTUAL_CONTENT, "Actual");
    }

    public static void writeInternationalString(JsonGenerator jsonGenerator,
                                                InternationalString string,
                                                String fieldName) throws IOException {
        if (string != null) {
            jsonGenerator.writeFieldName(fieldName);
            jsonGenerator.writeStartObject();
            string.getAllAsStream().forEach(entry -> {
                final String text = entry.getValue();
                final String locale = entry.getKey();
                if (StringUtils.isNotEmpty(text)) {
                    try {
                        jsonGenerator.writeStringField(locale, text);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            });

            jsonGenerator.writeEndObject();
        }
    }

    public static String mapInstantToString(Instant time) {
        return time == null ? null : time.toString();
    }

    public static Optional<ArtefactReference> getArtefactReferenceByStructureClass(List<ArtefactReference> constrainedArtefacts,
                                                                                   StructureClassImpl structureClass) {
        return constrainedArtefacts.stream()
            .filter(Objects::nonNull)
            .filter(reference -> reference.getStructureClass().equals(structureClass))
            .findAny();
    }

    public static List<ArtefactReference> getArtefactReferencesByStructureClass(List<ArtefactReference> constrainedArtefacts,
                                                                                StructureClassImpl structureClass) {
        return constrainedArtefacts.stream()
            .filter(Objects::nonNull)
            .filter(reference -> reference.getStructureClass().equals(structureClass))
            .collect(Collectors.toList());
    }

    public static void writeArtefactReferences(JsonGenerator jsonGenerator,
                                               List<ArtefactReference> references,
                                               String refName,
                                               ReferenceAdapter adapter) throws IOException {
        if (CollectionUtils.isNotEmpty(references)) {
            jsonGenerator.writeFieldName(refName);
            jsonGenerator.writeStartArray();
            for (ArtefactReference reference : references) {
                if (reference != null) {
                    jsonGenerator.writeString(adapter.toAdaptedUrn(reference));
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    public static void writeCommonFormatAttributes(JsonGenerator jsonGenerator, TextFormatImpl textFormat) throws IOException {
        Optional<FacetValueType> valueType = textFormat.getValueType();
        if (valueType.isPresent()) {
            jsonGenerator.writeStringField(StructureUtils.DATA_TYPE, valueType.get().value());
        }
        Optional<Boolean> sequence = textFormat.getIsSequence();
        if (sequence.isPresent()) {
            jsonGenerator.writeBooleanField(StructureUtils.IS_SEQUENCE, sequence.get());
        }
        Optional<BigDecimal> interval = textFormat.getInterval();
        if (interval.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.INTERVAL, interval.get());
        }
        Optional<BigDecimal> startValue = textFormat.getStartValue();
        if (startValue.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.START_VALUE, startValue.get());
        }
        Optional<BigDecimal> endValue = textFormat.getEndValue();
        if (endValue.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.END_VALUE, endValue.get());
        }
        Optional<String> timeInterval = textFormat.getTimeInterval();
        if (timeInterval.isPresent()) {
            jsonGenerator.writeStringField(StructureUtils.TIME_INTERVAL, timeInterval.get());
        }
        Optional<Instant> startTime = textFormat.getStartTime();
        if (startTime.isPresent()) {
            jsonGenerator.writeStringField(StructureUtils.START_TIME, startTime.get().toString());
        }
        Optional<Instant> endTime = textFormat.getEndTime();
        if (endTime.isPresent()) {
            jsonGenerator.writeStringField(StructureUtils.END_TIME, endTime.get().toString());
        }
        Optional<BigInteger> minLength = textFormat.getMinLength();
        if (minLength.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.MIN_LENGTH, minLength.get());
        }
        Optional<BigInteger> maxLength = textFormat.getMaxLength();
        if (maxLength.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.MAX_LENGTH, maxLength.get());
        }
        Optional<BigDecimal> minValue = textFormat.getMinValue();
        if (minValue.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.MIN_VALUE, minValue.get());
        }
        Optional<BigDecimal> maxValue = textFormat.getMaxValue();
        if (maxValue.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.MAX_VALUE, maxValue.get());
        }
        Optional<String> pattern = textFormat.getPattern();
        if (pattern.isPresent()) {
            jsonGenerator.writeStringField(StructureUtils.PATTERN, pattern.get());
        }
    }

    public static String getLocalisedValue(InternationalString i18nString, List<Locale.LanguageRange> languagePriorities) {
        final String prioritised = i18nString.getForRanges(languagePriorities);
        return StringUtils.isNotEmpty(prioritised) ? prioritised : i18nString.getForDefaultLocale();
    }

}
