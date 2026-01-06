package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySetImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class DataKeySetReader {

    private final AnnotableReader annotableReader;
    private final MemberSelectionReader memberSelectionReader;

    public DataKeySetReader(AnnotableReader annotableReader,
                            MemberSelectionReader memberSelectionReader) {
        this.annotableReader = annotableReader;
        this.memberSelectionReader = memberSelectionReader;
    }

    public DataKeySet getDataKeySet(JsonParser parser) {
        try {
            parser.nextToken();
            DataKeySetImpl dataKeySet = new DataKeySetImpl();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }
            while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.IS_INCLUDED:
                        dataKeySet.setIncluded(ReaderUtils.getBooleanJsonField(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.KEYS:
                        List<DataKey> dataKeys = ReaderUtils.getArray(parser, this::getKey);
                        if (CollectionUtils.isNotEmpty(dataKeys)) {
                            dataKeySet.setKeys(dataKeys);
                        }
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " DataKeySet: " + fieldName);
                }
            }
            return dataKeySet;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private DataKey getKey(JsonParser parser) {
        try {
            parser.nextToken();
            DataKeyImpl dataKey = new DataKeyImpl();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }

            while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.VALID_FROM:
                        dataKey.setValidFrom(ReaderUtils.getInstantObj(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.VALID_TO:
                        dataKey.setValidTo(ReaderUtils.getInstantObj(parser));
                        parser.nextToken();
                        break;
                    case StructureUtils.INCLUDE:
                        boolean include = ReaderUtils.getBooleanJsonField(parser);
                        dataKey.setIncluded(include);
                        parser.nextToken();
                        break;
                    case StructureUtils.COMPONENTS:
                        List<MemberSelection> memberSelections = ReaderUtils.getArray(parser, (this::getMemberSelection));
                        if (CollectionUtils.isNotEmpty(memberSelections)) {
                            dataKey.setMemberSelections(memberSelections);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.KEY_VALUES:
                        List<ComponentValue> keyValues = ReaderUtils.getArray(parser, (this::getKeyValue));
                        if (CollectionUtils.isNotEmpty(keyValues)) {
                            dataKey.setKeyValues(keyValues);
                        }
                        parser.nextToken();
                        break;
                    default:
                        annotableReader.read(dataKey, parser);
                        parser.nextToken();
                        break;
                }
            }
            return dataKey;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private ComponentValue getKeyValue(JsonParser parser) {
        try {
            parser.nextToken();
            ComponentValueImpl componentValue = new ComponentValueImpl();

            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }

            while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.ID:
                        String id = ReaderUtils.getStringJsonField(parser);
                        componentValue.setComponentId(id);
                        parser.nextToken();
                        break;
                    case StructureUtils.INCLUDE:
                        boolean include = ReaderUtils.getBooleanJsonField(parser);
                        componentValue.setIncluded(include);
                        parser.nextToken();
                        break;
                    case StructureUtils.REMOVE_PREFIX:
                        boolean removePrefix = ReaderUtils.getBooleanJsonField(parser);
                        componentValue.setRemovePrefix(removePrefix);
                        parser.nextToken();
                        break;
                    case StructureUtils.VALUE:
                        String value = ReaderUtils.getStringJsonField(parser);
                        componentValue.setValue(value);
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " ComponentValue: " + fieldName);
                }
            }
            return componentValue;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private MemberSelection getMemberSelection(JsonParser parser) {
        return memberSelectionReader.getMemberSelection(parser);
    }
}
