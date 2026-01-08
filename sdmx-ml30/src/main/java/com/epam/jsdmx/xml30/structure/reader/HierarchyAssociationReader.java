package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociationImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class HierarchyAssociationReader extends XmlReader<HierarchyAssociationImpl> {

    public HierarchyAssociationReader(AnnotableReader annotableReader,
                                      NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected HierarchyAssociationImpl createMaintainableArtefact() {
        return new HierarchyAssociationImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, HierarchyAssociationImpl hierarchyAssociation) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.LINKED_HIERARCHY:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(hierarchyAssociation::setLinkedHierarchy);
                break;
            case XmlConstants.LINKED_OBJECT:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(hierarchyAssociation::setLinkedObject);
                break;
            case XmlConstants.CONTEXT_OBJECT:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(hierarchyAssociation::setContextObject);
                break;
            default:
                throw new IllegalArgumentException("HierarchyAssociation " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

    }

    @Override
    protected void setAttributes(XMLStreamReader reader, HierarchyAssociationImpl hierarchyAssociation) {
        setCommonAttributes(reader, hierarchyAssociation);
    }

    @Override
    protected String getName() {
        return XmlConstants.HIERARCHY_ASSOCIATION;
    }

    @Override
    protected String getNames() {
        return XmlConstants.HIERARCHY_ASSOCIATIONS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<HierarchyAssociationImpl> artefacts) {
        artefact.getHierarchyAssociations().addAll(artefacts);
    }

}
