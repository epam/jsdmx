package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCode;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelistImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class GeographicCodelistReader extends XmlReader<GeographicCodelistImpl> {

    private final GeoFeatureSetCodeReader geoFeatureSetCodeReader;
    private final CodelistExtensionReader codelistExtensionReader;
    private final List<GeoFeatureSetCode> codes = new ArrayList<>();
    private final List<CodelistExtension> codelistExtensions = new ArrayList<>();
    Map<GeoFeatureSetCodeImpl, String> codeWithParentId = new HashMap<>();

    public GeographicCodelistReader(AnnotableReader annotableReader,
                                    NameableReader nameableReader,
                                    GeoFeatureSetCodeReader geoFeatureSetCodeReader,
                                    CodelistExtensionReader codelistExtensionReader) {
        super(annotableReader, nameableReader);
        this.geoFeatureSetCodeReader = geoFeatureSetCodeReader;
        this.codelistExtensionReader = codelistExtensionReader;
    }

    @Override
    protected GeographicCodelistImpl createMaintainableArtefact() {
        return new GeographicCodelistImpl();
    }

    @Override
    protected GeographicCodelistImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        GeographicCodelistImpl geographicCodelist = super.read(reader);
        geographicCodelist.setItems(List.copyOf(codes));
        geographicCodelist.setExtensions(new ArrayList<>(codelistExtensions));
        return geographicCodelist;
    }

    @Override
    protected void read(XMLStreamReader reader, GeographicCodelistImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.GEO_FEATURE_SET_CODE:
                var code = new GeoFeatureSetCodeImpl();
                geoFeatureSetCodeReader.setCode(
                    reader,
                    code,
                    nameableReader,
                    annotableReader,
                    codeWithParentId
                );
                codes.add(code);
                break;
            case XmlConstants.CODELIST_EXTENSION:
                String prefix = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.PREFIX);
                CodelistExtensionImpl codelistExtension = codelistExtensionReader.getCodelistExtension(reader);
                codelistExtension.setPrefix(prefix);
                codelistExtensions.add(codelistExtension);
                break;
            default:
                throw new IllegalArgumentException("GeographicalCodelist " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, GeographicCodelistImpl geographicCodelist) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, geographicCodelist);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(geographicCodelist::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.GEO_CODELIST;
    }

    @Override
    protected String getNames() {
        return XmlConstants.GEO_CODELISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<GeographicCodelistImpl> artefacts) {
        artefact.getGeographicCodelists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        codes.clear();
    }
}
