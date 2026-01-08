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
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimensionImpl;
import com.epam.jsdmx.serializer.util.DimensionUtil;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class DimensionListReader {

    private final RepresentationReader representationReader;
    private final AnnotableReader annotableReader;

    public void read(XMLStreamReader reader, DimensionDescriptorImpl dimensionDescriptor) throws URISyntaxException, XMLStreamException {
        dimensionDescriptor.setId(XmlConstants.DIMENSION_DESCRIPTOR_ID);
        String dimUri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (dimUri != null) {
            dimensionDescriptor.setUri(new URI(dimUri));
        }

        List<DimensionImpl> dimensions = new ArrayList<>();
        List<TimeDimensionImpl> timeDimensions = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.DIMENSION_LIST)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, dimensionDescriptor);
                    break;
                case XmlConstants.TIME_DIMENSION:
                    setTimeDimensions(reader, timeDimensions);
                    break;
                case XmlConstants.DIMENSION:
                    setDimensions(reader, dimensions);
                    break;
                default:
                    throw new IllegalArgumentException("DimensionList " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        List<DimensionComponent> dimensionComponents = new ArrayList<>();
        dimensionComponents.addAll(dimensions);
        dimensionComponents.addAll(timeDimensions);

        DimensionUtil.populateZeroBasedOrder(dimensionComponents);

        dimensionDescriptor.setComponents(CollectionUtils.isEmpty(dimensionComponents) ? null : dimensionComponents);
    }

    private void setTimeDimensions(XMLStreamReader reader, List<TimeDimensionImpl> timeDimensions) throws URISyntaxException, XMLStreamException {
        var timeDimension = new TimeDimensionImpl();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(timeDimension::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            timeDimension.setUri(new URI(uri));
        }

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.TIME_DIMENSION)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, timeDimension);
                    break;
                case XmlConstants.CONCEPT_IDENTITY:
                    XmlReaderUtils.readConceptIdentity(reader, timeDimension);
                    break;
                case XmlConstants.LOCAL_REPRESENTATION:
                    timeDimension.setLocalRepresentation(representationReader.readRepresentation(reader));
                    break;
                default:
                    throw new IllegalArgumentException("TimeDimension " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        timeDimensions.add(timeDimension);
    }

    private void setDimensions(XMLStreamReader reader, List<DimensionImpl> dimensions) throws URISyntaxException, XMLStreamException {
        var dimension = new DimensionImpl();
        List<ArtefactReference> conceptRoles = new ArrayList<>();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(dimension::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            dimension.setUri(new URI(uri));
        }

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.POSITION))
            .map(Integer::parseInt)
            .ifPresent(dimension::setOrder);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.DIMENSION)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, dimension);
                    break;
                case XmlConstants.CONCEPT_IDENTITY:
                    XmlReaderUtils.readConceptIdentity(reader, dimension);
                    break;
                case XmlConstants.LOCAL_REPRESENTATION:
                    dimension.setLocalRepresentation(representationReader.readRepresentation(reader));
                    break;
                case XmlConstants.CONCEPT_ROLE:
                    XmlReaderUtils.readConceptRole(reader, conceptRoles);
                    break;
                default:
                    throw new IllegalArgumentException("Dimension " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        dimension.setConceptRoles(conceptRoles);
        dimensions.add(dimension);
    }
}
