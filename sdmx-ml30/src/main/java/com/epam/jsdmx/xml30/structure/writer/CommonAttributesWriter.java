package com.epam.jsdmx.xml30.structure.writer;

import java.net.URI;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Version;


public class CommonAttributesWriter {

    private final UrnWriter urnWriter;

    public CommonAttributesWriter(UrnWriter urnWriter) {
        this.urnWriter = urnWriter;
    }

    public void writeAttributes(MaintainableArtefact artefact,
                                XMLStreamWriter writer) throws XMLStreamException {

        writeAttributes(
            artefact.getId(),
            artefact.getOrganizationId(),
            artefact.getUri(),
            artefact.getUrn(),
            artefact.getVersion(),
            artefact.isExternalReference(),
            artefact.getServiceUrl(),
            artefact.getStructureUrl(),
            artefact.getValidToString(),
            artefact.getValidFromString(),
            writer
        );
    }

    public void writeAttributes(String id,
                                String agencyId,
                                URI uri,
                                String urn,
                                Version version,
                                boolean isExternalReference,
                                URL serviceUrl,
                                URL structureUrl,
                                String validTo,
                                String validFrom,
                                XMLStreamWriter writer) throws XMLStreamException {

        XmlWriterUtils.writeUri(uri, writer);

        urnWriter.writeUrn(urn, writer);

        XmlWriterUtils.writeMandatoryAttribute(agencyId, writer, XmlConstants.AGENCY_ID);
        XmlWriterUtils.writeMandatoryAttribute(id, writer, XmlConstants.ID);

        XmlWriterUtils.writeVersion(version, writer);

        writer.writeAttribute(XmlConstants.IS_EXTERNAL_REFERENCE, String.valueOf(isExternalReference));

        XmlWriterUtils.writeUrl(serviceUrl, writer, XmlConstants.SERVICE_URL);
        XmlWriterUtils.writeUrl(structureUrl, writer, XmlConstants.STRUCTURE_URL);

        XmlWriterUtils.writeAttributeIfNotNull(validTo, writer, XmlConstants.VALID_TO);
        XmlWriterUtils.writeAttributeIfNotNull(validFrom, writer, XmlConstants.VALID_FROM);
    }
}
