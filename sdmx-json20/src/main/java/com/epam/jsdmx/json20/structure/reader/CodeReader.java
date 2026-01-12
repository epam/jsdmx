package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public interface CodeReader<T extends CodeImpl> {

    default T getCode(JsonParser parser,
                      T code,
                      NameableReader nameableReader,
                      Map<T, String> codeWithParentId) throws IOException {
        String parentId = null;
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.PARENT:
                    parser.nextToken();
                    parentId = parser.getText();
                    code.setParentId(parentId);
                    break;
                case StructureUtils.ID:
                case StructureUtils.ANNOTATIONS:
                case StructureUtils.NAMES:
                case StructureUtils.DESCRIPTIONS:
                case StructureUtils.DESCRIPTION:
                case StructureUtils.NAME:
                    nameableReader.read(code, parser);
                    break;
                default:
                    readExtendedCodeFields(parser, code);
                    break;
            }
        }
        codeWithParentId.put(code, parentId);
        return code;
    }

    default void readExtendedCodeFields(JsonParser parser, T code) throws IOException {
    }
}
