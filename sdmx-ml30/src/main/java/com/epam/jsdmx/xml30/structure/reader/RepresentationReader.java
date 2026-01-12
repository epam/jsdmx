package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.BaseTextFormatRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.EnumeratedRepresentationImpl;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValue;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValueImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class RepresentationReader {

    private final NameableReader nameableReader;

    public Representation readRepresentation(XMLStreamReader reader) throws XMLStreamException {
        moveToNextTag(reader);
        Representation representation = null;
        while (isNotEndingTag(reader, XmlConstants.CORE_REPRESENTATION, XmlConstants.LOCAL_REPRESENTATION)) {
            String name = reader.getLocalName();

            switch (name) {
                case XmlConstants.ENUMERATION:
                    String artefactReference = reader.getElementText();
                    if (XmlReaderUtils.isNotEmptyOrNullElementText(artefactReference)) {
                        representation = new EnumeratedRepresentationImpl(
                            new MaintainableArtefactReference(artefactReference));
                    }
                    moveToNextTag(reader);
                    break;
                case XmlConstants.TEXT_FORMAT:
                    representation = readTextFormat(reader);
                    break;
                case XmlConstants.ENUMERATION_FORMAT:
                    moveToNextTag(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Representation " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
        }
        return representation;
    }

    public Representation readTextFormat(XMLStreamReader reader) throws XMLStreamException {
        Set<Facet> facets = new HashSet<>();
        if (reader.getAttributeCount() > 0) {
            for (int index = 0; index < reader.getAttributeCount(); index++) {
                readFacets(reader, facets, index);
            }
        }

        List<SentinelValue> sentinelValues = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.TEXT_FORMAT)) {
            String name = reader.getLocalName();
            var sentinelValue = new SentinelValueImpl();
            if (XmlConstants.SENTINEL_VALUE.equals(name)) {
                String value = reader.getAttributeValue(StringUtils.EMPTY, "value");
                sentinelValue.setValue(value);
                readSentinelElements(reader, sentinelValue);
                sentinelValues.add(sentinelValue);
            }
            moveToNextTag(reader);
        }
        if (CollectionUtils.isNotEmpty(sentinelValues)) {
            var baseFacet = new BaseFacetImpl();
            baseFacet.setSentinelValues(sentinelValues);
            facets.add(baseFacet);
        }

        moveToNextTag(reader);
        return new BaseTextFormatRepresentationImpl(facets);
    }

    private void readFacets(XMLStreamReader reader, Set<Facet> facets, int index) {
        QName format = reader.getAttributeName(index);
        String txtFormat = format.getLocalPart();
        String formatValue = reader.getAttributeValue(StringUtils.EMPTY, txtFormat);

        if (XmlConstants.TEXT_TYPE.equals(txtFormat)) {
            if (formatValue != null) {
                var facet = new BaseFacetImpl();
                facet.setValueType(FacetValueType.fromValue(formatValue));
                facets.add(facet);
            }
        } else {
            var facet = new BaseFacetImpl();
            facet.setType(FacetType.valueOf(XmlConstants.MAP_CODING_FORMAT.get(txtFormat)));
            facet.setValue(formatValue);
            facets.add(facet);
        }
    }

    public void readSentinelElements(XMLStreamReader reader, SentinelValueImpl sentinelValue) throws XMLStreamException {
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.SENTINEL_VALUE)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                default:
                    throw new IllegalArgumentException("SentinelValue " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        sentinelValue.setName(new InternationalString(names));
        sentinelValue.setDescription(new InternationalString(descriptions));
    }

}
