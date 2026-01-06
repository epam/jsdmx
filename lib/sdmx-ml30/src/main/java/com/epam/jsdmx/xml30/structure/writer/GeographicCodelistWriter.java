package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCode;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelist;
import com.epam.jsdmx.infomodel.sdmx30.SdmxUrn;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import org.apache.commons.collections.CollectionUtils;

public class GeographicCodelistWriter extends XmlWriter<GeographicCodelist> {

    private final GeoFeatureSetCodeWriter geoFeatureSetCodeWriter;
    private final CodeListExtensionWriter codeListExtensionWriter;

    protected GeographicCodelistWriter(NameableWriter nameableWriter,
                                       AnnotableWriter annotableWriter,
                                       CommonAttributesWriter commonAttributesWriter,
                                       LinksWriter linksWriter,
                                       GeoFeatureSetCodeWriter geoFeatureSetCodeWriter,
                                       CodeListExtensionWriter codeListExtensionWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.geoFeatureSetCodeWriter = geoFeatureSetCodeWriter;
        this.codeListExtensionWriter = codeListExtensionWriter;
    }

    @Override
    protected void writeAttributes(GeographicCodelist geographicCodelist, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(
            geographicCodelist.getId(),
            geographicCodelist.getOrganizationId(),
            geographicCodelist.getUri(),
            SdmxUrn.toFullUrnString(
                StructureClassImpl.CODELIST,
                geographicCodelist.getOrganizationId(),
                geographicCodelist.getId(),
                geographicCodelist.getVersion()
            ),
            geographicCodelist.getVersion(),
            geographicCodelist.isExternalReference(),
            geographicCodelist.getServiceUrl(),
            geographicCodelist.getStructureUrl(),
            geographicCodelist.getValidToString(),
            geographicCodelist.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(geographicCodelist.isPartial()));
        writer.writeAttribute(XmlConstants.GEO_TYPE, XmlConstants.GEOGRAPHIC_CODELIST);
    }

    @Override
    protected void writeCustomAttributeElements(GeographicCodelist geographicCodelist, XMLStreamWriter writer) throws XMLStreamException {
        writeGeoFeatureCodelists(writer, geographicCodelist);

        List<? extends CodelistExtension> codelistExtensions = geographicCodelist.getExtensions();
        codeListExtensionWriter.writeCodeListExtensions(writer, codelistExtensions);
    }

    private void writeGeoFeatureCodelists(XMLStreamWriter writer, GeographicCodelist geographicCodelist) throws XMLStreamException {
        List<GeoFeatureSetCode> geographicCodelistItems = geographicCodelist.getItems();
        if (CollectionUtils.isNotEmpty(geographicCodelistItems)) {
            for (GeoFeatureSetCode geoFeatureSetCode : geographicCodelistItems) {
                if (geoFeatureSetCode != null) {
                    geoFeatureSetCodeWriter.write(
                        (GeoFeatureSetCodeImpl) geoFeatureSetCode,
                        writer,
                        XmlConstants.GEO_FEATURE_SET_CODE
                    );
                }
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.GEO_CODELIST;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.GEO_CODELISTS;
    }

    @Override
    protected Set<GeographicCodelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getGeographicCodelists();
    }
}
