package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
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
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
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
    public static final String VALUE = "value";
    public static final String TEXT = "text";
    public static final String TEXTS = "texts";
    public static final String LINKS = "links";
    public static final String URN = "urn";
    public static final String TYPE = "type";
    public static final String REL = "rel";

    public static final String IS_PARTIAL = "isPartial";

    public static final String CONCEPT_IDENTITY = "conceptIdentity";
    public static final String LOCAL_REPRESENTATION = "localRepresentation";
    public static final String CORE_REPRESENTATION = "coreRepresentation";

    public static final String SOURCE = "source";
    public static final String TARGET = "target";

    public static final String LEVEL = "level";

    public static final String ATTRIBUTE_LIST = "attributeList";
    public static final String DIMENSION_LIST = "dimensionList";
    public static final String DIMENSIONS = "dimensions";
    public static final String ATTACHMENT_GROUPS = "attachmentGroups";
    public static final String GROUP_DIMENSIONS = "groupDimensions";
    public static final String ATTRIBUTES = "attributes";
    public static final String TIME_DIMENSIONS = "timeDimensions";
    public static final String MEASURE_LIST = "measureList";
    public static final String ATTRIBUTE_RELATIONSHIP = "attributeRelationship";
    public static final String CONCEPT_ROLES = "conceptRoles";
    public static final String POSITION = "position";
    public static final String DIMENSION = "dimension";
    public static final String NONE = "none";

    public static final String ENUMERATION = "enumeration";


    public static final String CONCEPTS = "concepts";

    public static final String CODE = "code";
    public static final String CODE_ID = "codeID";
    public static final String CODES = "codes";
    public static final String PARENT = "parent";
    public static final String STRUCTURE = "structure";

    public static final String ROLE = "role";

    public static final String DATA_STRUCTURE_COMPONENTS = "dataStructureComponents";

    public static final String HIERARCHICAL_CODES = "hierarchicalCodes";
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
    public static final String HIERARCHICAL_CODELISTS = "hierarchicalCodelists";
    public static final String DATAFLOWS = "dataflows";
    public static final String METADATAFLOWS = "metadataflows";
    public static final String METADATA_SETS = "metadataSets";
    public static final String METADATA_STRUCTURES = "metadataStructures";

    public static final String CODING_FORMAT = "codingFormat";
    public static final String SCHEMA = "schema";
    public static final String TEST = "test";
    public static final String PREPARED = "prepared";
    public static final String CONTENT_LANGUAGES = "contentLanguages";
    public static final String SENDER = "sender";
    public static final String META = "meta";
    public static final String URI = "uri";

    public static final String CONTACTS = "contacts";
    public static final String DEPARTMENT = "department";
    public static final String TELEPHONES = "telephones";
    public static final String FAXES = "faxes";
    public static final String X_400_S = "x400s";
    public static final String URIS = "uris";
    public static final String EMAILS = "emails";
    public static final String AGENCIES = "agencies";
    public static final String AGENCIES_ID = "AGENCIES";

    public static final String GROUP = "group";
    public static final String GROUPS = "groups";
    public static final String AGENCY_SCHEMES = "agencySchemes";
    public static final String ATTRIBUTE_DESCRIPTOR_ID = "AttributeDescriptor";
    public static final String DIMENSION_DESCRIPTOR_ID = "DimensionDescriptor";
    public static final String MEASURE_DESCRIPTOR_ID = "MeasureDescriptor";

    public static final String CONTENT_CONSTRAINT = "contentconstraint";
    public static final String METADATA_TARGET_REGIONS = "metadataTargetRegions";
    public static final String RELEASE_CALENDAR = "releaseCalendar";
    public static final String TOLERANCE = "tolerance";
    public static final String OFFSET = "offset";
    public static final String PERIODICITY = "periodicity";
    public static final String CONSTRAINT_ATTACHMENT = "constraintAttachment";
    public static final String DATA_PROVIDER = "dataProvider";
    public static final String PROVISION_AGREEMENTS = "provisionAgreements";
    public static final String DATA_KEY_SETS = "dataKeySets";
    public static final String KEYS = "keys";
    public static final String KEY_VALUES = "keyValues";

    public static final String CUBE_REGIONS = "cubeRegions";
    public static final String IS_INCLUDED = "isIncluded";
    public static final String INCLUDE = "include";
    public static final String VALUES = "values";
    public static final String TIME_RANGE = "timeRange";
    public static final String START_PERIOD = "startPeriod";
    public static final String END_PERIOD = "endPeriod";
    public static final String BEFORE_PERIOD = "beforePeriod";
    public static final String AFTER_PERIOD = "afterPeriod";
    public static final String PERIOD = "period";
    public static final String IS_INCLUSIVE = "isInclusive";
    public static final BidiMap<ConstraintRoleType, String> CONSTRAINT_ROLE_STRING = new DualHashBidiMap<>();

    public static final String IS_FINAL = "isFinal";

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

    public static void writeCommonFormatAttributes(JsonGenerator jsonGenerator, TextFormatImpl textFormat) throws IOException {
        Optional<FacetValueType> valueType = textFormat.getValueType();
        if (valueType.isPresent()) {
            jsonGenerator.writeStringField("textType", valueType.get().value());
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

    public static void writeStringField(JsonGenerator jg, String name, String value) {
        if (value != null) {
            try {
                jg.writeStringField(name, value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    static {
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ALLOWABLE_CONTENT, "Allowed");
        CONSTRAINT_ROLE_STRING.put(ConstraintRoleType.ACTUAL_CONTENT, "Actual");
    }
}
