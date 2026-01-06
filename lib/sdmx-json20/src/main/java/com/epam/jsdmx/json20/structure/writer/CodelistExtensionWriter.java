package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.CodeSelection;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.StreamUtils;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class CodelistExtensionWriter {

    private final ReferenceAdapter referenceAdapter;

    public CodelistExtensionWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    public void writeCodeListExtentions(JsonGenerator jsonGenerator, List<? extends CodelistExtension> extensions) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.CODE_LIST_EXTENSIONS);
        jsonGenerator.writeStartArray();
        if (CollectionUtils.isNotEmpty(extensions)) {
            for (CodelistExtension codelistExtension : extensions) {
                if (codelistExtension != null) {
                    write(jsonGenerator, codelistExtension);
                }
            }
        }
        jsonGenerator.writeEndArray();
    }

    public void write(JsonGenerator jsonGenerator, CodelistExtension codelistExtension) throws IOException {
        jsonGenerator.writeStartObject();
        writeFields(jsonGenerator, codelistExtension);
        jsonGenerator.writeEndObject();
    }

    protected void writeFields(JsonGenerator jsonGenerator, CodelistExtension codelistExtension) throws IOException {
        jsonGenerator.writeStringField(StructureUtils.PREFIX, codelistExtension.getPrefix());
        ArtefactReference maintainableReference = codelistExtension.getCodelist();
        if (maintainableReference != null) {
            jsonGenerator.writeStringField(StructureUtils.CODE_LIST, referenceAdapter.toAdaptedUrn(maintainableReference));
        }
        if (codelistExtension.getCodeSelection() instanceof InclusiveCodeSelectionImpl) {
            writeCodeSelection(jsonGenerator, codelistExtension.getCodeSelection(), StructureUtils.INCLUSIVE_CODE_SELECTION);
        } else if (codelistExtension.getCodeSelection() instanceof ExclusiveCodeSelectionImpl) {
            writeCodeSelection(jsonGenerator, codelistExtension.getCodeSelection(), StructureUtils.EXCLUSIVE_CODE_SELECTION);
        }
    }

    private void writeCodeSelection(JsonGenerator jsonGenerator, CodeSelection codeSelection, String codeSelectionType) throws IOException {
        jsonGenerator.writeFieldName(codeSelectionType);
        jsonGenerator.writeStartObject();
        List<MemberValue> members = codeSelection.getMembers();
        if (members != null && !members.isEmpty()) {
            if (CollectionUtils.size(members) == 1 && StringUtils.endsWith(members.get(0).getValue(), "%")) {
                writeWildcardedMemberValues(jsonGenerator, members);
            } else {
                writeMemberValues(jsonGenerator, members);
            }
        }
        jsonGenerator.writeEndObject();
    }

    private void writeMemberValues(JsonGenerator jsonGenerator, List<MemberValue> members) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.MEMBER_VALUES);
        jsonGenerator.writeStartArray();
        List<MemberValue> nonNullMemberValues = StreamUtils.streamOfNullable(members)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        if (!nonNullMemberValues.isEmpty()) {
            for (MemberValue memberValue : nonNullMemberValues) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(StructureUtils.VALUE, memberValue.getValue());
                CascadeValue cascadeValue = memberValue.getCascadeValue();
                if (StructureUtils.CASCADE_VALUE_TYPE_STRING.get(cascadeValue) != null) {
                    jsonGenerator.writeStringField(StructureUtils.CASCADE_VALUES, StructureUtils.CASCADE_VALUE_TYPE_STRING.get(cascadeValue));
                }
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeWildcardedMemberValues(JsonGenerator jsonGenerator, List<MemberValue> members) throws IOException {
        List<String> wildcardedMemberValues = members.stream()
            .map(MemberValue::getValue)
            .collect(Collectors.toList());
        jsonGenerator.writeFieldName(StructureUtils.WILDCARDED_MEMBER_VALUES);
        jsonGenerator.writeStartArray();
        if (!wildcardedMemberValues.isEmpty()) {
            for (String member : wildcardedMemberValues) {
                jsonGenerator.writeString(member);
            }
        }
        jsonGenerator.writeEndArray();
    }

}
