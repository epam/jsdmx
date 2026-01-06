package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getLocalizedField;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseTextFormatRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.EnumeratedRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValue;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValueImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

@NoArgsConstructor
public class RepresentationReader {

    public Representation getRepresentations(JsonParser parser) throws IOException {
        parser.nextToken();
        if (ReaderUtils.isNullValue(parser)) {
            return null;
        }
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return null;
        }
        String enumeration = null;
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.ENUMERATION:
                    enumeration = ReaderUtils.getFieldAsString(parser);
                    if (enumeration != null) {
                        parser.nextToken();
                        return new EnumeratedRepresentationImpl(new MaintainableArtefactReference(enumeration));
                    }
                    return null;
                case StructureUtils.ENUMERATION_FORMAT:
                    parser.nextToken();
                    parser.skipChildren();
                    break;
                case StructureUtils.FORMAT:
                    final Representation representation = readFormat(parser);
                    parser.nextToken();
                    return representation;
                case StructureUtils.MIN_OCCURS:
                case StructureUtils.MAX_OCCURS:
                    parser.nextToken();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Representation: " + fieldName);
            }
        }

        return null;
    }

    private Representation readFormat(JsonParser parser) throws IOException {
        parser.nextToken();
        Set<Facet> facets = new HashSet<>();
        List<SentinelValue> sentinelValues = new ArrayList<>();
        FacetValueType facetValueType = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.currentName();
            switch (fieldName) {
                case StructureUtils.DATA_TYPE:
                    parser.nextToken();
                    String dataType = parser.getText();
                    facetValueType = FacetValueType.fromValue(dataType);
                    facets.add(new BaseFacetImpl(facetValueType));
                    break;
                case StructureUtils.IS_SEQUENCE:
                    facets.add(setFacet(FacetType.IS_SEQUENCE, parser));
                    break;
                case StructureUtils.INTERVAL:
                    facets.add(setFacet(FacetType.INTERVAL, parser));
                    break;
                case StructureUtils.TIME_INTERVAL:
                    facets.add(setFacet(FacetType.TIME_INTERVAL, parser));
                    break;
                case StructureUtils.START_VALUE:
                    facets.add(setFacet(FacetType.START_VALUE, parser));
                    break;
                case StructureUtils.END_VALUE:
                    facets.add(setFacet(FacetType.END_VALUE, parser));
                    break;
                case StructureUtils.MIN_LENGTH:
                    facets.add(setFacet(FacetType.MIN_LENGTH, parser));
                    break;
                case StructureUtils.MAX_LENGTH:
                    facets.add(setFacet(FacetType.MAX_LENGTH, parser));
                    break;
                case StructureUtils.MIN_VALUE:
                    facets.add(setFacet(FacetType.MIN_VALUE, parser));
                    break;
                case StructureUtils.MAX_VALUE:
                    facets.add(setFacet(FacetType.MAX_VALUE, parser));
                    break;
                case StructureUtils.PATTERN:
                    facets.add(setFacet(FacetType.PATTERN, parser));
                    break;
                case StructureUtils.DECIMALS:
                    facets.add(setFacet(FacetType.DECIMALS, parser));
                    break;
                case StructureUtils.IS_MULTI_LINGUAL:
                    facets.add(setFacet(FacetType.IS_MULTILINGUAL, parser));
                    break;
                case StructureUtils.SENTINEL_VALUES:
                    List<SentinelValue> sentinelValueList = ReaderUtils.getArray(parser, (this::getSentinelValue));
                    if (CollectionUtils.isNotEmpty(sentinelValueList)) {
                        sentinelValues.addAll(sentinelValueList);
                    }
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN
                        + "CodingFormat:" + fieldName);
            }
        }
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(sentinelValues)) {
            BaseFacetImpl baseFacet = new BaseFacetImpl();
            baseFacet.setType(FacetType.SENTINEL_VALUES);
            baseFacet.setValueType(facetValueType);
            baseFacet.setSentinelValues(sentinelValues);
            facets.add(baseFacet);
        }

        return new BaseTextFormatRepresentationImpl(facets);
    }

    private SentinelValue getSentinelValue(JsonParser parser) {
        try {
            SentinelValueImpl sentinelValue = new SentinelValueImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.currentName();
                switch (fieldName) {
                    case StructureUtils.VALUE:
                        String value = getStringJsonField(parser);
                        if (value != null) {
                            sentinelValue.setValue(value);
                        }
                        break;
                    case StructureUtils.NAMES:
                        Map<String, String> localizedNames = getLocalizedField(parser);
                        if (localizedNames != null) {
                            sentinelValue.setName(new InternationalString(localizedNames));
                        }
                        break;
                    case StructureUtils.DESCRIPTION:
                    case StructureUtils.NAME:
                        parser.nextToken();
                        parser.skipChildren();
                        break;
                    case StructureUtils.DESCRIPTIONS:
                        Map<String, String> localizedDescriptions = getLocalizedField(parser);
                        if (localizedDescriptions != null) {
                            sentinelValue.setDescription(new InternationalString(localizedDescriptions));
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " SentinelValue: " + fieldName);
                }
            }
            return sentinelValue;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Facet setFacet(FacetType type, JsonParser parser) throws IOException {
        final var baseFacetStartValue = new BaseFacetImpl();
        baseFacetStartValue.setType(type);
        baseFacetStartValue.setValue(ReaderUtils.getFieldAsString(parser));
        return baseFacetStartValue;
    }
}
