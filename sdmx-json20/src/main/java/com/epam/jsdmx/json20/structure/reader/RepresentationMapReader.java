package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getInstantObj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MappedValue;
import com.epam.jsdmx.infomodel.sdmx30.MappedValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapping;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.TargetValue;
import com.epam.jsdmx.infomodel.sdmx30.TargetValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueRepresentation;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class RepresentationMapReader extends MaintainableReader<RepresentationMapImpl> {

    public RepresentationMapReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected RepresentationMapImpl createMaintainableArtefact() {
        return new RepresentationMapImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, RepresentationMapImpl representationMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.SOURCE:
                List<ValueRepresentation> valueRepresentationSource = getValueRepresentation(parser);
                if (CollectionUtils.isNotEmpty(valueRepresentationSource)) {
                    representationMap.setSource(valueRepresentationSource);
                }
                break;
            case StructureUtils.TARGET:
                List<ValueRepresentation> valueRepresentationTarget = getValueRepresentation(parser);
                if (CollectionUtils.isNotEmpty(valueRepresentationTarget)) {
                    representationMap.setTarget(valueRepresentationTarget);
                }
                break;
            case StructureUtils.REPRESENTATION_MAPPINGS:
                List<RepresentationMapping> representationMappings = getRepresentationMappings(parser);
                if (CollectionUtils.isNotEmpty(representationMappings)) {
                    representationMap.setRepresentationMappings(representationMappings);
                }
                break;
            default:
                throw new IllegalArgumentException("no such argument in RepresentationMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.REPRESENTATION_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<RepresentationMapImpl> artefacts) {
        artefact.getRepresentationMaps().addAll(artefacts);
    }

    private List<RepresentationMapping> getRepresentationMappings(JsonParser parser) throws IOException {
        List<RepresentationMapping> representationMappings = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT)) {
                RepresentationMapping representationMapping = getRepresentationMapping(parser);
                representationMappings.add(representationMapping);
            }
        }
        return representationMappings;
    }

    private RepresentationMappingImpl getRepresentationMapping(JsonParser parser) throws IOException {
        RepresentationMappingImpl representationMapping = new RepresentationMappingImpl();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.VALID_TO:
                    representationMapping.setValidTo(getInstantObj(parser));
                    break;
                case StructureUtils.VALID_FROM:
                    representationMapping.setValidFrom(getInstantObj(parser));
                    break;
                case StructureUtils.TARGET_VALUES:
                    List<TargetValue> targetValues = getTargetValues(parser);
                    if (CollectionUtils.isNotEmpty(targetValues)) {
                        representationMapping.setTargetValues(targetValues);
                    }
                    break;
                case StructureUtils.SOURCE_VALUES:
                    List<MappedValue> sourceValues = getSourceValues(parser);
                    if (CollectionUtils.isNotEmpty(sourceValues)) {
                        representationMapping.setSourceValues(sourceValues);
                    }
                    break;
                default:
                    AnnotableReader annotableReader = new AnnotableReader();
                    annotableReader.read(representationMapping, parser);
                    break;
            }
        }
        return representationMapping;
    }

    private List<MappedValue> getSourceValues(JsonParser parser) throws IOException {
        List<MappedValue> mappedValues = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT)) {
                MappedValue mappedValue = getMappedValue(parser);
                mappedValues.add(mappedValue);
            }
        }
        return mappedValues;
    }

    private MappedValue getMappedValue(JsonParser parser) throws IOException {
        MappedValueImpl mappedValue = new MappedValueImpl();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.IS_REG_EX:
                    mappedValue.setRegEx(ReaderUtils.getBooleanJsonField(parser));
                    break;
                case StructureUtils.START_INDEX:
                    mappedValue.setStartIndex(ReaderUtils.getIntJsonField(parser));
                    break;
                case StructureUtils.END_INDEX:
                    mappedValue.setEndIndex(ReaderUtils.getIntJsonField(parser));
                    break;
                case StructureUtils.VALUE:
                    mappedValue.setValue(ReaderUtils.getStringJsonField(parser));
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MappedValue:" + fieldName);
            }
        }
        return mappedValue;
    }

    private List<TargetValue> getTargetValues(JsonParser parser) throws IOException {
        List<String> stringList = ReaderUtils.getListStrings(parser);
        return stringList.stream()
            .filter(Objects::nonNull)
            .map(str -> {
                TargetValueImpl targetValue = new TargetValueImpl();
                targetValue.setValue(str);
                return targetValue;
            })
            .collect(Collectors.toList());
    }

    private List<ValueRepresentation> getValueRepresentation(JsonParser parser) throws IOException {
        List<ValueRepresentation> valueRepresentations = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT)) {
                setValueRepresentation(parser, valueRepresentations);
            }
        }
        return valueRepresentations;
    }

    private void setValueRepresentation(JsonParser parser, List<ValueRepresentation> valueRepresentations) throws IOException {
        boolean isCodeList = false;
        boolean isDataType = false;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.CODE_LIST:
                    isCodeList = true;
                    valueRepresentations.add(readCodeList(parser, isDataType));
                    break;
                case StructureUtils.DATA_TYPE:
                    isDataType = true;
                    valueRepresentations.add(readDataType(parser, isCodeList));
                    break;
                default:
                    throw new IllegalArgumentException("ElementDto must contain codelist or dataType");
            }
        }
    }

    private ValueRepresentation readDataType(JsonParser parser, boolean isSecondRepresentation) throws IOException {
        if (isSecondRepresentation) {
            throw new IllegalArgumentException("ElementDto should not contain both codelist and dataType at the same time");
        }
        String dataType = ReaderUtils.getStringJsonField(parser);
        if (dataType != null) {
            FacetValueTypeRepresentationImpl facetValueRepresentation = new FacetValueTypeRepresentationImpl();
            facetValueRepresentation.setType(FacetValueType.fromValue(dataType));
            return facetValueRepresentation;
        }
        return null;
    }

    private ValueRepresentation readCodeList(JsonParser parser, boolean isDataType) throws IOException {
        if (isDataType) {
            throw new IllegalArgumentException("ElementDto should not contain both codelist and dataType at the same time");
        }
        String urn = ReaderUtils.getStringJsonField(parser);
        if (urn != null) {
            ListReferenceValueRepresentationImpl listValueRepresentation = new ListReferenceValueRepresentationImpl();
            listValueRepresentation.setReference(new MaintainableArtefactReference(urn));
            return listValueRepresentation;
        }
        return null;
    }
}
