package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setExternalReference;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setId;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setServiceUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setStructureUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setUri;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidFrom;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidTo;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Agency;
import com.epam.jsdmx.infomodel.sdmx30.AgencyImpl;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class AgencySchemeReader extends XmlReader<AgencySchemeImpl> {

    private final OrganisationReader organisationReader;

    private final List<Agency> agencies = new ArrayList<>();

    public AgencySchemeReader(AnnotableReader annotableReader,
                              NameableReader nameableReader,
                              OrganisationReader organisationReader) {
        super(annotableReader, nameableReader);
        this.organisationReader = organisationReader;
    }

    @Override
    protected AgencySchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        AgencySchemeImpl agencyScheme = super.read(reader);
        agencyScheme.setItems(new ArrayList<>(agencies));
        return agencyScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, AgencySchemeImpl agencyScheme) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.AGENCY.equals(name)) {
            throw new IllegalArgumentException("AgencyScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
        }

        Agency agency = organisationReader.addOrganisation(reader, new AgencyImpl(), null);
        agencies.add(agency);
    }

    @Override
    protected String getName() {
        return XmlConstants.AGENCY_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.AGENCY_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<AgencySchemeImpl> artefacts) {
        artefact.getAgencySchemes().addAll(artefacts);
    }

    @Override
    protected AgencySchemeImpl createMaintainableArtefact() {
        return new AgencySchemeImpl();
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, AgencySchemeImpl artefact) throws XMLStreamException {
        setUri(reader, artefact);

        setStructureUrl(reader, artefact);

        setServiceUrl(reader, artefact);

        setId(reader, artefact);

        artefact.setOrganizationId("SDMX");

        artefact.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));

        setExternalReference(reader, artefact);

        setValidTo(reader, artefact);

        setValidFrom(reader, artefact);
    }

    @Override
    protected void clean() {
        agencies.clear();
    }
}

