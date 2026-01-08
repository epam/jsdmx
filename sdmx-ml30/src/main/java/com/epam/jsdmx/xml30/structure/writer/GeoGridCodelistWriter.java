package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelist;
import com.epam.jsdmx.infomodel.sdmx30.GridCode;
import com.epam.jsdmx.infomodel.sdmx30.SdmxUrn;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import org.apache.commons.collections.CollectionUtils;

public class GeoGridCodelistWriter extends XmlWriter<GeoGridCodelist> {

    private final CodeListExtensionWriter codeListExtensionWriter;
    private final GridCodeWriter gridCodeWriter;

    protected GeoGridCodelistWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    LinksWriter linksWriter,
                                    CodeListExtensionWriter codeListExtensionWriter,
                                    GridCodeWriter gridCodeWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.codeListExtensionWriter = codeListExtensionWriter;
        this.gridCodeWriter = gridCodeWriter;
    }

    @Override
    protected void writeAttributes(GeoGridCodelist geoGridCodelist, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(
            geoGridCodelist.getId(),
            geoGridCodelist.getOrganizationId(),
            geoGridCodelist.getUri(),
            SdmxUrn.toFullUrnString(
                StructureClassImpl.CODELIST,
                geoGridCodelist.getOrganizationId(),
                geoGridCodelist.getId(),
                geoGridCodelist.getVersion()
            ),
            geoGridCodelist.getVersion(),
            geoGridCodelist.isExternalReference(),
            geoGridCodelist.getServiceUrl(),
            geoGridCodelist.getStructureUrl(),
            geoGridCodelist.getValidToString(),
            geoGridCodelist.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(geoGridCodelist.isPartial()));
        writer.writeAttribute(XmlConstants.GEO_TYPE, XmlConstants.GEOGRID_CODELIST_TYPE);
    }

    @Override
    protected void writeCustomAttributeElements(GeoGridCodelist geoGridCodelist, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends CodelistExtension> codelistExtensions = geoGridCodelist.getExtensions();
        codeListExtensionWriter.writeCodeListExtensions(writer, codelistExtensions);

        String gridDefinition = geoGridCodelist.getGridDefinition();
        if (gridDefinition != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.GRID_DEFINITION);
            writer.writeCharacters(gridDefinition);
            writer.writeEndElement();
        }

        List<GridCode> geoGridCodelistItems = geoGridCodelist.getItems();
        if (CollectionUtils.isNotEmpty(geoGridCodelistItems)) {
            for (GridCode gridCode : geoGridCodelistItems) {
                if (gridCode != null) {
                    gridCodeWriter.write(
                        gridCode,
                        writer,
                        XmlConstants.GEO_GRID_CODES
                    );
                }
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.GEOGRID_CODELIST;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.GEOGRID_CODELISTS;
    }

    @Override
    protected Set<GeoGridCodelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getGeoGridCodelists();
    }
}
