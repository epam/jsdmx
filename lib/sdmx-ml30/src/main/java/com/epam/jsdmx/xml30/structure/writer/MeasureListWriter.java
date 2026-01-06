package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.Representation;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class MeasureListWriter {

    private final AnnotableWriter annotableWriter;
    private final RepresentationWriter representationWriter;

    public void writeMeasureList(MeasureDescriptor measureDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (measureDescriptor != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MEASURE_LIST);
            XmlWriterUtils.writeIdUriAttributes(
                writer,
                XmlConstants.MEASURE_DESCRIPTOR_ID,
                measureDescriptor.getUri()
            );
            if (measureDescriptor.getContainer() != null) {
                XmlWriterUtils.writeUrn(measureDescriptor.getUrn(), writer);
            }
            annotableWriter.write(measureDescriptor, writer);
            List<Measure> components = measureDescriptor.getComponents();
            writeMeasures(writer, components);
            writer.writeEndElement();
        }
    }

    private void writeMeasures(XMLStreamWriter writer, List<Measure> components) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(components)) {
            for (Measure measure : components) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MEASURE);
                XmlWriterUtils.writeIdUriAttributes(writer, measure.getId(), measure.getUri());
                if (measure.getContainer() != null) {
                    XmlWriterUtils.writeUrn(measure.getUrn(), writer);
                }
                annotableWriter.write(measure, writer);
                XmlWriterUtils.writeConceptIdentity(writer, measure);
                Representation localRepresentation = measure.getLocalRepresentation();
                if (localRepresentation != null) {
                    representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
                }
                XmlWriterUtils.writeConceptRoles(writer, measure.getConceptRoles());
                writer.writeEndElement();
            }
        }
    }
}
