package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Agency;
import com.epam.jsdmx.infomodel.sdmx30.AgencyScheme;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Version;

public class AgencySchemeWriter extends XmlWriter<AgencyScheme> {

    private final OrganisationWriter organisationWriter;

    public AgencySchemeWriter(NameableWriter nameableWriter,
                              AnnotableWriter annotableWriter,
                              CommonAttributesWriter commonAttributesWriter,
                              LinksWriter linksWriter,
                              OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(AgencyScheme agencyScheme, XMLStreamWriter writer) throws XMLStreamException {
        AgencySchemeImpl agencySchemeImpl = (AgencySchemeImpl) agencyScheme;
        agencySchemeImpl.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));
        commonAttributesWriter.writeAttributes(
            "AGENCIES",
            "SDMX",
            agencyScheme.getUri(),
            agencySchemeImpl.getUrn(),
            null,
            agencyScheme.isExternalReference(),
            agencyScheme.getServiceUrl(),
            agencyScheme.getStructureUrl(),
            agencyScheme.getValidToString(),
            agencyScheme.getValidFromString(),
            writer
        );
    }

    @Override
    protected void writeCustomAttributeElements(AgencyScheme agencyScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<Agency> agencies = (List<Agency>) agencyScheme.getItems();
        organisationWriter.writeOrganisation(agencies, writer, XmlConstants.AGENCY);
    }

    @Override
    protected String getName() {
        return XmlConstants.AGENCY_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.AGENCY_SCHEMES;
    }

    @Override
    protected Set<AgencyScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getAgencySchemes();
    }

}
