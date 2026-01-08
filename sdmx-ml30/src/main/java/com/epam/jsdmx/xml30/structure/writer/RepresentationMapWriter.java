package com.epam.jsdmx.xml30.structure.writer;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentation;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentation;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.MappedValue;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapping;
import com.epam.jsdmx.infomodel.sdmx30.TargetValue;
import com.epam.jsdmx.infomodel.sdmx30.ValueRepresentation;

import org.apache.commons.collections4.CollectionUtils;

public class RepresentationMapWriter extends XmlWriter<RepresentationMap> {

    private final UrnWriter urnWriter;

    public RepresentationMapWriter(NameableWriter nameableWriter,
                                   AnnotableWriter annotableWriter,
                                   CommonAttributesWriter commonAttributesWriter,
                                   LinksWriter linksWriter,
                                   UrnWriter urnWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.urnWriter = urnWriter;
    }

    @Override
    protected void writeAttributes(RepresentationMap representationMap, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(representationMap, writer);
    }

    @Override
    protected void writeCustomAttributeElements(RepresentationMap artefact, XMLStreamWriter writer) throws XMLStreamException {
        List<RepresentationMapping> representationMappings = artefact.getRepresentationMappings();
        List<ValueRepresentation> target = artefact.getTarget();
        List<ValueRepresentation> source = artefact.getSource();

        writeValueRepresentation(writer, source, XmlConstants.SOURCE_DATA_TYPE, XmlConstants.SOURCE_CODELIST);
        writeValueRepresentation(writer, target, XmlConstants.TARGET_DATA_TYPE, XmlConstants.TARGET_CODELIST);

        writeRepresentationMappings(writer, representationMappings);
    }

    @Override
    protected String getName() {
        return XmlConstants.REPRESENTATION_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.REPRESENTATION_MAPS;
    }

    @Override
    protected Set<RepresentationMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getRepresentationMaps();
    }

    private void writeValueRepresentation(XMLStreamWriter writer,
                                          List<ValueRepresentation> valueRepresentations,
                                          String dataType,
                                          String origin) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(valueRepresentations)) {
            for (ValueRepresentation valueRepresentation : valueRepresentations) {
                if (valueRepresentation instanceof FacetValueTypeRepresentation) {
                    FacetValueTypeRepresentationImpl valueTypeRepresentation = (FacetValueTypeRepresentationImpl) valueRepresentation;
                    FacetValueType type = valueTypeRepresentation.getType();
                    if (type != null) {
                        writer.writeStartElement(XmlConstants.STRUCTURE + dataType);
                        XmlWriterUtils.writeCharacters(type.value(), writer);
                        writer.writeEndElement();
                    }
                } else if (valueRepresentation instanceof ListReferenceValueRepresentation) {
                    ListReferenceValueRepresentationImpl listReferenceValueRepresentation = (ListReferenceValueRepresentationImpl) valueRepresentation;
                    ArtefactReference reference = listReferenceValueRepresentation.getReference();
                    if (reference != null) {
                        writer.writeStartElement(XmlConstants.STRUCTURE + origin);
                        urnWriter.writeUrnCharacters(reference, writer);
                        writer.writeEndElement();
                    }
                }
            }
        }
    }

    private void writeRepresentationMappings(XMLStreamWriter writer, List<RepresentationMapping> representationMappings) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(representationMappings)) {
            for (RepresentationMapping representationMapping : representationMappings) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.REPRESENTATION_MAPPING);

                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());
                Instant validFrom = representationMapping.getValidFrom();
                if (validFrom != null) {
                    writer.writeAttribute(XmlConstants.VALID_FROM, formatter.format(validFrom));
                }

                Instant validTo = representationMapping.getValidTo();
                if (validTo != null) {
                    writer.writeAttribute(XmlConstants.VALID_TO, formatter.format(validTo));
                }

                this.annotableWriter.write(representationMapping, writer);

                List<MappedValue> sourceValues = representationMapping.getSourceValues();
                List<TargetValue> targetValues = representationMapping.getTargetValues();

                writeSourceValues(writer, sourceValues);
                writeTargetValues(writer, targetValues);
                writer.writeEndElement();
            }
        }
    }

    private void writeTargetValues(XMLStreamWriter writer, List<TargetValue> targetValues) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(targetValues)) {
            for (TargetValue targetValue : targetValues) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TARGET_VALUE);
                XmlWriterUtils.writeCharacters(targetValue.getValue(), writer);
                writer.writeEndElement();
            }
        }
    }

    private void writeSourceValues(XMLStreamWriter writer, List<MappedValue> sourceValues) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(sourceValues)) {
            for (MappedValue mappedValue : sourceValues) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.SOURCE_VALUE);
                writer.writeAttribute(XmlConstants.IS_REG_EX, String.valueOf(mappedValue.isRegEx()));
                writer.writeAttribute(XmlConstants.START_INDEX, String.valueOf(mappedValue.getStartIndex()));
                writer.writeAttribute(XmlConstants.END_INDEX, String.valueOf(mappedValue.getEndIndex()));
                String value = mappedValue.getValue();
                if (value != null) {
                    writer.writeCharacters(value);
                }
                writer.writeEndElement();
            }
        }
    }
}