package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Dimension;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimension;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class DimensionListWriter {
    private final AnnotableWriter annotableWriter;
    private final RepresentationWriter representationWriter;
    private final ReferenceWriter referenceWriter;

    public void writeDimensionList(DimensionDescriptor dimensionDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (dimensionDescriptor != null) {
            List<DimensionComponent> components = dimensionDescriptor.getComponents();
            writer.writeStartElement(XmlConstants.STR + XmlConstants.DIMENSION_LIST);
            XmlWriterUtils.writeIdUriAttributes(writer, XmlConstants.DIMENSION_DESCRIPTOR_ID, dimensionDescriptor.getUri());
            if (dimensionDescriptor.getContainer() != null) {
                XmlWriterUtils.writeUrn(dimensionDescriptor.getUrn(), writer);
            }
            annotableWriter.write(dimensionDescriptor, writer);
            if (CollectionUtils.isNotEmpty(components)) {
                List<DimensionComponent> timeDimension = components.stream().filter(DimensionComponent::isTimeDimension).collect(Collectors.toList());
                List<DimensionComponent> dimensions = (List<DimensionComponent>) CollectionUtils.subtract(components, timeDimension);
                writeDimensions(writer, dimensions);
                writeTimeDimensions(writer, timeDimension);
            }
            writer.writeEndElement();
        }
    }

    private void writeTimeDimensions(XMLStreamWriter writer, List<DimensionComponent> timeDimensionComponents) throws XMLStreamException {
        List<TimeDimension> timeDimensions = Optional.ofNullable(timeDimensionComponents)
            .stream()
            .flatMap(List::stream)
            .filter(d -> TimeDimension.class.isAssignableFrom(d.getClass()))
            .map(TimeDimension.class::cast)
            .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(timeDimensions)) {
            for (TimeDimension timeDimension : timeDimensions) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.TIME_DIMENSION);
                XmlWriterUtils.writeIdUriAttributes(writer, timeDimension.getId(), timeDimension.getUri());
                if (timeDimension.getContainer() != null) {
                    XmlWriterUtils.writeUrn(timeDimension.getUrn(), writer);
                }

                this.annotableWriter.write(timeDimension, writer);
                referenceWriter.writeConceptIdentity(writer, timeDimension);
                Representation localRepresentation = timeDimension.getLocalRepresentation();
                if (localRepresentation != null) {
                    representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
                }
                writer.writeEndElement();
            }
        }
    }

    private void writeDimensions(XMLStreamWriter writer, List<DimensionComponent> dimensionComponents) throws XMLStreamException {
        List<Dimension> dimensions = Optional.ofNullable(dimensionComponents)
            .stream()
            .flatMap(List::stream)
            .filter(d -> Dimension.class.isAssignableFrom(d.getClass()))
            .map(Dimension.class::cast)
            .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(dimensions)) {
            for (Dimension dimension : dimensions) {
                writeDimension(writer, dimension);
            }
        }
    }

    private void writeDimension(XMLStreamWriter writer, Dimension dimension) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + XmlConstants.DIMENSION);

        XmlWriterUtils.writeIdUriAttributes(writer, dimension.getId(), dimension.getUri());
        if (dimension.getContainer() != null) {
            XmlWriterUtils.writeUrn(dimension.getUrn(), writer);
        }
        writer.writeAttribute(XmlConstants.POSITION, String.valueOf(dimension.getOrder()));
        this.annotableWriter.write(dimension, writer);
        referenceWriter.writeConceptIdentity(writer, dimension);
        Representation localRepresentation = dimension.getLocalRepresentation();
        if (localRepresentation != null) {
            representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
        }
        List<ArtefactReference> conceptRoles = dimension.getConceptRoles();
        referenceWriter.writeConceptRoles(writer, conceptRoles);

        writer.writeEndElement();
    }
}