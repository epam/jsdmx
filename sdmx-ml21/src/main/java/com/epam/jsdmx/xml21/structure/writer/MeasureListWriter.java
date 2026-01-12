package com.epam.jsdmx.xml21.structure.writer;

import static java.util.stream.Collectors.joining;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.Representation;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class MeasureListWriter {

    public static final String PRIMARY_MEASURE_URN_CLASS = "urn:sdmx:org.sdmx.infomodel.datastructure.PrimaryMeasure=";
    private final AnnotableWriter annotableWriter;
    private final RepresentationWriter representationWriter;
    private final ReferenceWriter referenceWriter;

    public void writeMeasureList(MeasureDescriptor measureDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (measureDescriptor != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.MEASURE_LIST);
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

    private void writeMeasures(XMLStreamWriter writer, List<Measure> measures) throws XMLStreamException {
        if (CollectionUtils.size(measures) <= 1) {
            for (Measure measure : CollectionUtils.emptyIfNull(measures)) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.PRIMARY_MEASURE);
                XmlWriterUtils.writeIdUriAttributes(writer, measure.getId(), measure.getUri());
                if (measure.getContainer() != null) {
                    final String primaryMeasureUrn = fixPrimaryMeasureUrn(measure);
                    XmlWriterUtils.writeUrn(primaryMeasureUrn, writer);
                }
                annotableWriter.write(measure, writer);
                referenceWriter.writeConceptIdentity(writer, measure);
                Representation localRepresentation = measure.getLocalRepresentation();
                if (localRepresentation != null) {
                    representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
                }
                writer.writeEndElement();
            }
        } else {
            throw new IllegalArgumentException("Only one primary measure is expected, but found: "
                + measures.size() + " measures: "
                + measures.stream().map(Measure::getId).collect(joining(", ")));
        }
    }

    private static String fixPrimaryMeasureUrn(Measure measure) {
        final String measureUrn = measure.getUrn();
        final String measureAgencyIdVersionId = StringUtils.substringAfter(measureUrn, "=");
        return PRIMARY_MEASURE_URN_CLASS + measureAgencyIdVersionId;
    }
}
