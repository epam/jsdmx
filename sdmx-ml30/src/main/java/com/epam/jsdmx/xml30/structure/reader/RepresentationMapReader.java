package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MappedValue;
import com.epam.jsdmx.infomodel.sdmx30.MappedValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapping;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.TargetValue;
import com.epam.jsdmx.infomodel.sdmx30.TargetValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueRepresentation;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class RepresentationMapReader extends XmlReader<RepresentationMapImpl> {

    private final List<RepresentationMapping> representationMappings = new ArrayList<>();
    private final List<ValueRepresentation> target = new ArrayList<>();
    private final List<ValueRepresentation> source = new ArrayList<>();

    public RepresentationMapReader(AnnotableReader annotableReader,
                                   NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected RepresentationMapImpl createMaintainableArtefact() {
        return new RepresentationMapImpl();
    }

    @Override
    protected void clean() {
        this.target.clear();
        this.source.clear();
        this.representationMappings.clear();
    }

    @Override
    protected void read(XMLStreamReader reader, RepresentationMapImpl representationMap) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE_CODELIST:
                addListReferenceValueRepresentation(reader, source);
                break;
            case XmlConstants.SOURCE_DATA_TYPE:
                addFacetValueTypeRepresentation(reader, source);
                break;
            case XmlConstants.TARGET_CODELIST:
                addListReferenceValueRepresentation(reader, target);
                break;
            case XmlConstants.TARGET_DATA_TYPE:
                addFacetValueTypeRepresentation(reader, target);
                break;
            case XmlConstants.REPRESENTATION_MAPPING:
                setRepresentationMapping(reader, representationMappings);
                break;
            default:
                throw new IllegalArgumentException("RepresentationMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

        representationMap.setSource(List.copyOf(source));
        representationMap.setTarget(List.copyOf(target));
        representationMap.setRepresentationMappings(List.copyOf(representationMappings));
    }

    private void setRepresentationMapping(XMLStreamReader reader,
                                          List<RepresentationMapping> representationMappings) throws XMLStreamException, URISyntaxException {
        var representationMapping = new RepresentationMappingImpl();

        representationMapping.setValidFrom(XmlReaderUtils.getDate(reader, XmlConstants.VALID_FROM));
        representationMapping.setValidTo(XmlReaderUtils.getDate(reader, XmlConstants.VALID_TO));

        List<TargetValue> targetValues = new ArrayList<>();
        List<MappedValue> sourceValues = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.REPRESENTATION_MAPPING)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, representationMapping);
                    break;
                case XmlConstants.SOURCE_VALUE:
                    addSourceValue(reader, sourceValues);
                    break;
                case XmlConstants.TARGET_VALUE:
                    addTargetValue(reader, targetValues);
                    break;
                default:
                    throw new IllegalArgumentException("RepresentationMapping " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }

        representationMapping.setTargetValues(targetValues);
        representationMapping.setSourceValues(sourceValues);
        representationMappings.add(representationMapping);
    }

    private void addSourceValue(XMLStreamReader reader, List<MappedValue> sourceValues) throws XMLStreamException {
        var mappedValue = new MappedValueImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_REG_EX))
            .map(Boolean::parseBoolean)
            .ifPresent(mappedValue::setRegEx);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.START_INDEX))
            .map(Integer::parseInt)
            .ifPresent(mappedValue::setStartIndex);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.END_INDEX))
            .map(Integer::parseInt)
            .ifPresent(mappedValue::setEndIndex);

        Optional.ofNullable(reader.getElementText())
            .ifPresent(mappedValue::setValue);

        sourceValues.add(mappedValue);
    }

    private void addTargetValue(XMLStreamReader reader, List<TargetValue> targetValues) throws XMLStreamException {
        var targetValue = new TargetValueImpl();
        Optional.ofNullable(reader.getElementText())
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .ifPresent(targetValue::setValue);

        targetValues.add(targetValue);
    }

    private void addFacetValueTypeRepresentation(XMLStreamReader reader, List<ValueRepresentation> valueRepresentations) throws XMLStreamException {
        var valueTypeRepresentation = new FacetValueTypeRepresentationImpl();
        Optional.ofNullable(reader.getElementText())
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .map(FacetValueType::fromValue)
            .ifPresent(valueTypeRepresentation::setType);

        valueRepresentations.add(valueTypeRepresentation);
    }

    private void addListReferenceValueRepresentation(XMLStreamReader reader, List<ValueRepresentation> valueRepresentations) throws XMLStreamException {
        var listReferenceValueRepresentation = new ListReferenceValueRepresentationImpl();
        Optional.ofNullable(reader.getElementText())
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .map(MaintainableArtefactReference::new)
            .ifPresent(listReferenceValueRepresentation::setReference);

        valueRepresentations.add(listReferenceValueRepresentation);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, RepresentationMapImpl maintainableArtefact) {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.REPRESENTATION_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.REPRESENTATION_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<RepresentationMapImpl> artefacts) {
        artefact.getRepresentationMaps().addAll(artefacts);
    }

}