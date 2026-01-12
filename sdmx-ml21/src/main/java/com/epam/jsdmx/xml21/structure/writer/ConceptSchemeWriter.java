package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Concept;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReference;

import org.apache.commons.collections4.CollectionUtils;

public class ConceptSchemeWriter extends XmlWriter<ConceptScheme> {

    private final RepresentationWriter representationWriter;

    public ConceptSchemeWriter(NameableWriter nameableWriter,
                               AnnotableWriter annotableWriter,
                               CommonAttributesWriter commonAttributesWriter,
                               RepresentationWriter representationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.representationWriter = representationWriter;
    }

    @Override
    protected void writeAttributes(ConceptScheme conceptScheme, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(conceptScheme, writer);
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(conceptScheme.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(ConceptScheme conceptScheme, XMLStreamWriter writer) throws XMLStreamException {
        writeConcepts(conceptScheme, writer);
    }

    @Override
    protected String getName(ConceptScheme unused) {
        return XmlConstants.CONCEPT_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CONCEPT_SCHEMES;
    }

    @Override
    protected Set<ConceptScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getConceptSchemes();
    }

    private void writeConcepts(ConceptScheme conceptScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends Concept> concepts = conceptScheme.getItems();
        if (CollectionUtils.isNotEmpty(concepts)) {
            for (Concept concept : concepts) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT);
                XmlWriterUtils.writeMandatoryAttribute(concept.getId(), writer, XmlConstants.ID);
                if (concept.getContainer() != null) {
                    XmlWriterUtils.writeUrn(concept.getUrn(), writer);
                }
                XmlWriterUtils.writeUri(concept.getUri(), writer);
                annotableWriter.write(concept, writer);
                nameableWriter.write(concept, writer);
                representationWriter.writeRepresentation(writer, concept.getCoreRepresentation(), XmlConstants.CORE_REPRESENTATION);
                writeIsoConcept(writer, concept);
                writer.writeEndElement();
            }
        }
    }


    private void writeIsoConcept(XMLStreamWriter writer, Concept concept) throws XMLStreamException {
        IsoConceptReference isoConceptReference = concept.getIsoConceptReference();
        if (isoConceptReference != null) {

            writer.writeStartElement(XmlConstants.STR + XmlConstants.ISOCONCEPT_REFERENCE);

            String conceptId = isoConceptReference.getConceptId();
            String agency = isoConceptReference.getAgency();
            String schemeId = isoConceptReference.getSchemeId();

            if (agency != null) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT_AGENCY);
                writer.writeCharacters(agency);
                writer.writeEndElement();
            }

            if (schemeId != null) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT_SCHEME_ID);
                writer.writeCharacters(schemeId);
                writer.writeEndElement();
            }

            if (conceptId != null) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT_ID);
                writer.writeCharacters(conceptId);
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }
}