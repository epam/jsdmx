package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.CodeSelection;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelection;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.StreamUtils;

import org.apache.commons.collections.CollectionUtils;

public class CodeListExtensionWriter {

    private final UrnWriter urnWriter;

    public CodeListExtensionWriter(UrnWriter urnWriter) {
        this.urnWriter = urnWriter;
    }

    public void writeCodeListExtensions(XMLStreamWriter writer, List<? extends CodelistExtension> codelistExtensions) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(codelistExtensions)) {
            for (CodelistExtension extension : codelistExtensions) {
                writeExtension(extension, writer);
            }
        }
    }

    public void writeExtension(CodelistExtension extension, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CODELIST_EXTENSION);

        String prefix = extension.getPrefix();
        if (prefix != null) {
            writer.writeAttribute(XmlConstants.PREFIX, prefix);
        }

        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CODELIST);
        urnWriter.writeUrnCharacters(extension.getCodelist(), writer);
        writer.writeEndElement();

        writeCodeSelection(writer, extension.getCodeSelection());

        writer.writeEndElement();
    }

    private void writeCodeSelection(XMLStreamWriter writer, CodeSelection codeSelection) throws XMLStreamException {
        if (codeSelection instanceof ExclusiveCodeSelection || codeSelection instanceof InclusiveCodeSelection) {
            List<MemberValue> members = codeSelection.getMembers();
            String localNamePart = codeSelection instanceof ExclusiveCodeSelection
                ? XmlConstants.EXCLUSIVE_CODE_SELECTION
                : XmlConstants.INCLUSIVE_CODE_SELECTION;

            writer.writeStartElement(XmlConstants.STRUCTURE + localNamePart);
            writeMemberValues(writer, members);
            writer.writeEndElement();
        }
    }

    private void writeMemberValues(XMLStreamWriter writer, List<MemberValue> members) throws XMLStreamException {
        List<MemberValue> nonNullMemberValues = StreamUtils.streamOfNullable(members)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        for (MemberValue memberValue : nonNullMemberValues) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MEMBER_VALUE);
            writer.writeAttribute(XmlConstants.CASCADE_VALUES, memberValue.getCascadeValue().name().toLowerCase());
            String value = memberValue.getValue();
            if (value != null) {
                writer.writeCharacters(value);
            }
            writer.writeEndElement();
        }
    }
}
