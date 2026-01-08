package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class CubeRegionReader {

    private final MemberSelectionReader memberSelectionReader;
    private final AnnotableReader annotableReader;

    public CubeRegionReader(MemberSelectionReader memberSelectionReader, AnnotableReader annotableReader) {
        this.memberSelectionReader = memberSelectionReader;
        this.annotableReader = annotableReader;
    }

    public CubeRegion getCubeRegion(JsonParser parser) {
        try {
            CubeRegionImpl cubeRegion = new CubeRegionImpl();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }
            while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.INCLUDE:
                        boolean include = ReaderUtils.getBooleanJsonField(parser);
                        cubeRegion.setIncluded(include);
                        parser.nextToken();
                        break;
                    case StructureUtils.COMPONENTS:
                        List<MemberSelection> memberSelections = ReaderUtils.getArray(parser, (this::getMemberSelection));
                        if (CollectionUtils.isNotEmpty(memberSelections)) {
                            cubeRegion.setMemberSelections(memberSelections);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.KEY_VALUES:
                        List<CubeRegionKey> cubeRegionKeys = ReaderUtils.getArray(parser, (this::getKeyValue));
                        if (CollectionUtils.isNotEmpty(cubeRegionKeys)) {
                            cubeRegion.setCubeRegionKeys(cubeRegionKeys);
                        }
                        parser.nextToken();
                        break;
                    default:
                        annotableReader.read(cubeRegion, parser);
                        parser.nextToken();
                        break;
                }
            }
            return cubeRegion;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private MemberSelection getMemberSelection(JsonParser parser) {
        return memberSelectionReader.getMemberSelection(parser);
    }

    public CubeRegionKey getKeyValue(JsonParser parser) {
        try {
            CubeRegionKeyImpl cubeRegionKey = new CubeRegionKeyImpl();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }
            List<SelectionValue> selectionValues = new ArrayList<>();
            while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.ID:
                        cubeRegionKey.setComponentId(ReaderUtils.getFieldAsString(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.VALID_FROM:
                        cubeRegionKey.setValidFrom(ReaderUtils.getInstantObj(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.VALID_TO:
                        cubeRegionKey.setValidTo(ReaderUtils.getInstantObj(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.INCLUDE:
                        boolean include = ReaderUtils.getBooleanJsonField(parser);
                        cubeRegionKey.setIncluded(include);
                        parser.nextToken();
                        break;
                    case StructureUtils.REMOVE_PREFIX:
                        boolean removePrefix = ReaderUtils.getBooleanJsonField(parser);
                        cubeRegionKey.setRemovePrefix(removePrefix);
                        parser.nextToken();
                        break;
                    case StructureUtils.TIME_RANGE:
                        List<SelectionValue> timeRanges = memberSelectionReader.getTimeRanges(parser);
                        if (CollectionUtils.isNotEmpty(timeRanges)) {
                            selectionValues.addAll(timeRanges);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.VALUES:
                        List<SelectionValue> values = ReaderUtils.getArray(parser, (memberSelectionReader::getMemberValue));
                        selectionValues.addAll(values);
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " CubeRegionKey: " + fieldName);
                }
            }
            cubeRegionKey.setSelectionValues(selectionValues);
            return cubeRegionKey;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }
}
