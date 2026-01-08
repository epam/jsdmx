package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.BaseCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class CodelistExtensionReader {

    public List<CodelistExtension> getCodeListExtensions(JsonParser parser) throws IOException {
        List<CodelistExtension> codelistExtensions = new ArrayList<>();
        int sequence = 0;
        while (!JsonToken.END_ARRAY.equals(parser.nextToken())) {
            if (JsonToken.START_OBJECT.equals(parser.currentToken())) {
                CodelistExtensionImpl codelistExtension = getCodelistExtension(parser, sequence);
                if (codelistExtension != null) {
                    codelistExtensions.add(codelistExtension);
                }
                sequence++;
            }
        }
        return codelistExtensions;
    }

    private CodelistExtensionImpl getCodelistExtension(JsonParser parser, int sequence) throws IOException {
        CodelistExtensionImpl codelistExtension = new CodelistExtensionImpl();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            if (!checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                return null;
            }
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.PREFIX:
                    parser.nextToken();
                    String prefix = parser.getValueAsString();
                    codelistExtension.setPrefix(prefix);
                    break;
                case StructureUtils.SEQUENCE:
                    codelistExtension.setSequence(sequence);
                    break;
                case StructureUtils.CODE_LIST:
                    parser.nextToken();
                    String codelist = parser.getValueAsString();
                    if (codelist != null) {
                        codelistExtension.setCodelist(new MaintainableArtefactReference(codelist));
                    }
                    break;
                case StructureUtils.EXCLUSIVE_CODE_SELECTION:
                    parser.nextToken();
                    ExclusiveCodeSelectionImpl exclusiveCodeSelection = new ExclusiveCodeSelectionImpl();
                    setCodeSelection(parser, exclusiveCodeSelection);
                    codelistExtension.setCodeSelection(exclusiveCodeSelection);
                    break;
                case StructureUtils.INCLUSIVE_CODE_SELECTION:
                    parser.nextToken();
                    InclusiveCodeSelectionImpl inclusiveCodeSelection = new InclusiveCodeSelectionImpl();
                    setCodeSelection(parser, inclusiveCodeSelection);
                    codelistExtension.setCodeSelection(inclusiveCodeSelection);
                    break;
                default:
                    parser.nextToken();
                    break;
            }
        }
        return codelistExtension;
    }

    private void setCodeSelection(JsonParser parser, BaseCodeSelectionImpl selection) throws IOException {
        boolean isWildcardedValues = false;
        boolean isMemberValues = false;
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            if (!checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                return;
            }
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.MEMBER_VALUES:
                    isMemberValues = true;
                    readMemberValues(parser, selection, isWildcardedValues);
                    break;
                case StructureUtils.WILDCARDED_MEMBER_VALUES:
                    isWildcardedValues = true;
                    readWildcardedValues(parser, selection, isMemberValues);
                    break;
                default:
                    throw new IllegalArgumentException("no such argument in Code selection: " + fieldName);
            }
        }
    }

    private void readWildcardedValues(JsonParser parser, BaseCodeSelectionImpl selection, boolean isMemberValues) throws IOException {
        parser.nextToken();
        if (isMemberValues) {
            throw new IllegalArgumentException("Cannot specify both wildcarded and explicit selections");
        }
        List<MemberValue> wildcardedMembers = getWildcardedMembers(parser);
        if (CollectionUtils.isNotEmpty(wildcardedMembers)) {
            selection.setMembers(wildcardedMembers);
        }
    }

    private void readMemberValues(JsonParser parser, BaseCodeSelectionImpl selection, boolean isWildcardedValues) throws IOException {
        parser.nextToken();
        if (isWildcardedValues) {
            throw new IllegalArgumentException("Cannot specify both wildcarded and explicit selections");
        }
        List<MemberValue> memberValues = ReaderUtils.getArray(parser, this::getMemberValue);
        if (CollectionUtils.isNotEmpty(memberValues)) {
            selection.setMembers(memberValues);
        }
        parser.nextToken();
    }

    private List<MemberValue> getWildcardedMembers(JsonParser parser) throws IOException {
        List<String> stringList = ReaderUtils.getListStrings(parser);
        return stringList.stream()
            .filter(Objects::nonNull)
            .map(str -> {
                MemberValueImpl memberValue = new MemberValueImpl();
                memberValue.setValue(str);
                return memberValue;
            })
            .collect(Collectors.toList());
    }

    private MemberValue getMemberValue(JsonParser parser) {
        try {
            MemberValueImpl memberValue = new MemberValueImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                if (!checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                    return null;
                }
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.VALUE:
                        parser.nextToken();
                        String value = parser.getText();
                        memberValue.setValue(value);
                        break;
                    case StructureUtils.CASCADE_VALUES:
                        parser.nextToken();
                        String cascadeSelection = parser.getText();
                        CascadeValue cascadeValue;
                        switch (cascadeSelection) {
                            case StructureUtils.TRUE:
                                cascadeValue = CascadeValue.TRUE;
                                break;
                            case StructureUtils.FALSE:
                                cascadeValue = CascadeValue.FALSE;
                                break;
                            case StructureUtils.EXCLUDE_ROOT:
                                cascadeValue = CascadeValue.EXCLUDE_ROOT;
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected enum constant: " + cascadeSelection);
                        }
                        memberValue.setCascadeValue(cascadeValue);
                        break;
                    default:
                        // unrecognized field, skip it
                        parser.nextToken();
                        break;
                }
            }
            return memberValue;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }
}
