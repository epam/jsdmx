package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class GeoFeatureSetCodeReader implements CodeReader<GeoFeatureSetCodeImpl> {

    @Override
    public void setCodeAttributes(XMLStreamReader reader, GeoFeatureSetCodeImpl code) throws URISyntaxException {
        CodeReader.super.setCodeAttributes(reader, code);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALUE_LOWER))
            .ifPresent(code::setValue);
    }
}
