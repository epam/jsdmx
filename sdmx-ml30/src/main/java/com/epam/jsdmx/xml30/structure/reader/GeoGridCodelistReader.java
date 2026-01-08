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
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.GridCodeImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class GeoGridCodelistReader extends XmlReader<GeoGridCodelistImpl> {

    private final CodelistExtensionReader codelistExtensionReader;
    private final GridCodeReader gridCodeReader;
    private final List<GridCodeImpl> codes = new ArrayList<>();
    private final List<CodelistExtension> codelistExtensions = new ArrayList<>();
    Map<GridCodeImpl, String> codeWithParentId = new HashMap<>();

    public GeoGridCodelistReader(AnnotableReader annotableReader,
                                 NameableReader nameableReader,
                                 CodelistExtensionReader codelistExtensionReader,
                                 GridCodeReader gridCodeReader) {
        super(annotableReader, nameableReader);
        this.codelistExtensionReader = codelistExtensionReader;
        this.gridCodeReader = gridCodeReader;
    }

    @Override
    protected GeoGridCodelistImpl createMaintainableArtefact() {
        return new GeoGridCodelistImpl();
    }

    @Override
    protected GeoGridCodelistImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        GeoGridCodelistImpl geoGridCodelist = super.read(reader);

        geoGridCodelist.setItems(List.copyOf(codes));
        geoGridCodelist.setExtensions(new ArrayList<>(codelistExtensions));
        return geoGridCodelist;
    }

    @Override
    protected void read(XMLStreamReader reader, GeoGridCodelistImpl geoGridCodelist) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.GEO_GRID_CODES:
                var code = new GridCodeImpl();
                gridCodeReader.setCode(
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
            case XmlConstants.GRID_DEFINITION:
                geoGridCodelist.setGridDefinition(reader.getElementText());
                break;
            default:
                throw new IllegalArgumentException("GeoGridCodelist " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, GeoGridCodelistImpl geoGridCodelist) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, geoGridCodelist);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(geoGridCodelist::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.GEOGRID_CODELIST;
    }

    @Override
    protected String getNames() {
        return XmlConstants.GEOGRID_CODELISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<GeoGridCodelistImpl> artefacts) {
        artefact.getGeoGridCodelists().addAll(artefacts);
    }
}
