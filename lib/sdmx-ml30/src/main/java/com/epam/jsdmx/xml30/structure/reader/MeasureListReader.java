package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class MeasureListReader {

    private final RepresentationReader representationReader;
    private final AnnotableReader annotableReader;

    public void read(XMLStreamReader reader, MeasureDescriptorImpl measureDescriptor) throws URISyntaxException, XMLStreamException {
        measureDescriptor.setId(XmlConstants.MEASURE_DESCRIPTOR_ID);
        List<Measure> measureList = new ArrayList<>();
        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            measureDescriptor.setUri(new URI(uri));
        }

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.MEASURE_LIST)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, measureDescriptor);
                    break;
                case XmlConstants.MEASURE:
                    setMeasure(reader, measureList);
                    break;
                default:
                    throw new IllegalArgumentException("MeasureList " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        measureDescriptor.setComponents(CollectionUtils.isEmpty(measureList) ? null : measureList);
    }

    private void setMeasure(XMLStreamReader reader, List<Measure> measureList) throws XMLStreamException, URISyntaxException {
        var measure = new MeasureImpl();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(measure::setId);

        List<ArtefactReference> conceptRoles = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.MEASURE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, measure);
                    break;
                case XmlConstants.CONCEPT_IDENTITY:
                    XmlReaderUtils.readConceptIdentity(reader, measure);
                    break;
                case XmlConstants.LOCAL_REPRESENTATION:
                    measure.setLocalRepresentation(representationReader.readRepresentation(reader));
                    break;
                case XmlConstants.CONCEPT_ROLE:
                    XmlReaderUtils.readConceptRole(reader, conceptRoles);
                    break;
                default:
                    throw new IllegalArgumentException("Measure " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        measure.setConceptRoles(conceptRoles);
        measureList.add(measure);
    }
}
