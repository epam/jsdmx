package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociation;

public class HierarchyAssociationWriter extends XmlWriter<HierarchyAssociation> {

    public HierarchyAssociationWriter(NameableWriter nameableWriter,
                                      AnnotableWriter annotableWriter,
                                      CommonAttributesWriter commonAttributesWriter,
                                      LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(HierarchyAssociation hierarchyAssociation, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(hierarchyAssociation, writer);
    }

    @Override
    protected void writeCustomAttributeElements(HierarchyAssociation artefact, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.LINKED_HIERARCHY);
        ArtefactReference linkedHierarchy = artefact.getLinkedHierarchy();
        if (linkedHierarchy != null) {
            writeCharacters(linkedHierarchy.getUrn(), writer);
        }
        writer.writeEndElement();

        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.LINKED_OBJECT);
        ArtefactReference linkedObject = artefact.getLinkedObject();
        if (linkedObject != null) {
            writeCharacters(linkedObject.getUrn(), writer);
        }
        writer.writeEndElement();

        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CONTEXT_OBJECT);
        ArtefactReference contextObject = artefact.getContextObject();
        if (contextObject != null) {
            writeCharacters(contextObject.getUrn(), writer);
        }
        writer.writeEndElement();
    }

    @Override
    protected String getName() {
        return XmlConstants.HIERARCHY_ASSOCIATION;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.HIERARCHY_ASSOCIATIONS;
    }

    @Override
    protected Set<HierarchyAssociation> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchyAssociations();
    }
}
