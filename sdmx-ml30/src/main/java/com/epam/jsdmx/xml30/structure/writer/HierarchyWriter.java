package com.epam.jsdmx.xml30.structure.writer;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.Level;

import org.apache.commons.collections4.CollectionUtils;


public class HierarchyWriter extends XmlWriter<Hierarchy> {

    private final UrnWriter urnWriter;

    public HierarchyWriter(NameableWriter nameableWriter,
                           AnnotableWriter annotableWriter,
                           CommonAttributesWriter commonAttributesWriter,
                           LinksWriter linksWriter,
                           UrnWriter urnWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.urnWriter = urnWriter;
    }

    @Override
    protected void writeAttributes(Hierarchy hierarchy, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(hierarchy, writer);
        boolean hasFormalLevels = hierarchy.isHasFormalLevels();
        writer.writeAttribute(XmlConstants.HAS_FORMAL_LEVELS, String.valueOf(hasFormalLevels));
    }

    @Override
    protected void writeCustomAttributeElements(Hierarchy hierarchy, XMLStreamWriter writer) throws XMLStreamException {
        writeLevel(hierarchy.getLevel(), writer);
        writeHierarchicalCode(hierarchy.getCodes(), writer);
    }

    @Override
    protected String getName() {
        return XmlConstants.HIERARCHY;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.HIERARCHIES;
    }

    @Override
    protected Set<Hierarchy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchies();
    }

    private void writeHierarchicalCode(List<HierarchicalCode> codes, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(codes)) {
            for (HierarchicalCode code : codes) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.HIERARCHICAL_CODE);
                writeHierarchicalCode(writer, code);
                writer.writeEndElement();
            }
        }
    }

    private void writeHierarchicalCode(XMLStreamWriter writer, HierarchicalCode code) throws XMLStreamException {

        XmlWriterUtils.writeUri(code.getUri(), writer);

        if (code.getContainer() != null) {
            XmlWriterUtils.writeUrn(code.getUrn(), writer);
        }

        XmlWriterUtils.writeInstant(code.getValidTo(), writer, XmlConstants.VALID_TO);
        XmlWriterUtils.writeInstant(code.getValidFrom(), writer, XmlConstants.VALID_FROM);

        XmlWriterUtils.writeMandatoryAttribute(code.getId(), writer, XmlConstants.ID);

        annotableWriter.write(code, writer);
        ArtefactReference codeRef = code.getCode();
        if (codeRef != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CODE);
            urnWriter.writeUrnCharacters(codeRef, writer);
            writer.writeEndElement();
        }

        String levelId = code.getLevelId();
        if (levelId != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.LEVEL);
            writer.writeCharacters(levelId);
            writer.writeEndElement();
        }

        List<HierarchicalCode> hierarchicalCodes = code.getHierarchicalCodes();
        if (CollectionUtils.isNotEmpty(hierarchicalCodes)) {
            writeHierarchicalCode(hierarchicalCodes, writer);
        }
    }

    private void writeLevel(Level level, XMLStreamWriter writer) throws XMLStreamException {
        if (level != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.LEVEL);
            writer.writeAttribute(XmlConstants.ID, level.getId());

            if (level.getContainer() != null) {
                writer.writeAttribute(XmlConstants.URN, level.getUrn());
            }

            URI uri = level.getUri();
            if (uri != null) {
                writer.writeAttribute(XmlConstants.URI, uri.toString());
            }
            annotableWriter.write(level, writer);
            nameableWriter.write(level, writer);
            List<CodingFormat> codeFormat = level.getCodeFormat();
            if (CollectionUtils.isNotEmpty(codeFormat)) {
                CodingFormat codingFormat = codeFormat.get(0);
                Facet codingFormatInside = codingFormat.getCodingFormat();
                if (codingFormatInside != null && codingFormatInside.getType() != null) {
                    String value = XmlConstants.MAP_FACET_TYPE.get(codingFormatInside.getType().name());
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CODING_FORMAT);
                    writer.writeAttribute(value, codingFormatInside.getValue());
                    writer.writeEndElement();
                }
            }
            Level child = level.getChild();
            if (child != null && child.getId() != null) {
                writeLevel(child, writer);
            }
            writer.writeEndElement();
        }
    }
}