package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Concept;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReferenceImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class ConceptSchemeReader extends XmlReader<ConceptSchemeImpl> {

    private final RepresentationReader representationReader;
    private final List<Concept> items = new ArrayList<>();

    public ConceptSchemeReader(AnnotableReader annotableReader,
                               NameableReader nameableReader,
                               RepresentationReader representationReader) {
        super(annotableReader, nameableReader);
        this.representationReader = representationReader;
    }

    @Override
    protected ConceptSchemeImpl createMaintainableArtefact() {
        return new ConceptSchemeImpl();
    }

    @Override
    protected ConceptSchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ConceptSchemeImpl conceptScheme = super.read(reader);
        conceptScheme.setItems(List.copyOf(items));

        return conceptScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, ConceptSchemeImpl conceptScheme) throws URISyntaxException, XMLStreamException {
        var concept = new ConceptImpl();
        String localName = reader.getLocalName();

        if (!XmlConstants.CONCEPT.equals(localName)) {
            throw new IllegalArgumentException("ConceptScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(concept::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            concept.setUri(new URI(uri));
        }

        readElements(reader, concept);
        items.add(concept);
    }

    @Override
    protected void clean() {
        this.items.clear();
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ConceptSchemeImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(maintainableArtefact::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.CONCEPT_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CONCEPT_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ConceptSchemeImpl> artefacts) {
        artefact.getConceptSchemes().addAll(artefacts);
    }

    private void readElements(XMLStreamReader reader, ConceptImpl concept) throws XMLStreamException, URISyntaxException {
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CONCEPT)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    this.annotableReader.setAnnotations(reader, concept);
                    break;
                case XmlConstants.COM_NAME:
                    this.nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    this.nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.ISOCONCEPT_REFERENCE:
                    readIsoReference(reader, concept);
                    break;
                case XmlConstants.CORE_REPRESENTATION:
                    concept.setCoreRepresentation(representationReader.readRepresentation(reader));
                    break;
                case XmlConstants.PARENT:
                    // skip it. Hierarchical concepts are not supported
                    while (isNotEndingTag(reader, XmlConstants.PARENT)) {
                        moveToNextTag(reader);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Concept " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        concept.setName(names.isEmpty() ? null : new InternationalString(names));
        concept.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));
    }

    private void readIsoReference(XMLStreamReader reader, ConceptImpl concept) throws XMLStreamException {
        var isoConceptReference = new IsoConceptReferenceImpl();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.ISOCONCEPT_REFERENCE)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.CONCEPT_ID:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(isoConceptReference::setConceptId);
                    break;
                case XmlConstants.CONCEPT_AGENCY:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(isoConceptReference::setAgency);
                    break;
                case XmlConstants.CONCEPT_SCHEME_ID:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(isoConceptReference::setSchemeId);
                    break;
                default:
                    throw new IllegalArgumentException("ISOConceptReference " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            concept.setIsoConceptReference(isoConceptReference);
            moveToNextTag(reader);
        }
    }
}
