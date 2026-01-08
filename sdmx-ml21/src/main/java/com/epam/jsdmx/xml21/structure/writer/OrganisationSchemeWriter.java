package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.AGENCY_SCHEME;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.DATA_CONSUMER_SCHEME;
import static com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl.DATA_PROVIDER_SCHEME;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.AgencyScheme;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderScheme;
import com.epam.jsdmx.infomodel.sdmx30.Organisation;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationScheme;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.Version;

public class OrganisationSchemeWriter extends XmlWriter<OrganisationScheme<? extends Organisation<?>>> {

    private final OrganisationWriter organisationWriter;

    public OrganisationSchemeWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(OrganisationScheme<?> scheme, XMLStreamWriter writer) throws XMLStreamException {
        if (scheme instanceof AgencyScheme) {
            var agencyScheme = (AgencyScheme) scheme;
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
        } else if (scheme instanceof DataProviderScheme) {
            var dataProviderScheme = (DataProviderScheme) scheme;
            commonAttributesWriter.writeAttributes(
                "DATA_PROVIDERS",
                "SDMX",
                dataProviderScheme.getUri(),
                dataProviderScheme.getUrn(),
                null,
                dataProviderScheme.isExternalReference(),
                dataProviderScheme.getServiceUrl(),
                dataProviderScheme.getStructureUrl(),
                dataProviderScheme.getValidToString(),
                dataProviderScheme.getValidFromString(),
                writer
            );
        } else if (scheme instanceof DataConsumerScheme) {
            var dataConsumerScheme = (DataConsumerScheme) scheme;
            commonAttributesWriter.writeAttributes(
                "DATA_CONSUMERS",
                "SDMX",
                dataConsumerScheme.getUri(),
                dataConsumerScheme.getUrn(),
                null,
                dataConsumerScheme.isExternalReference(),
                dataConsumerScheme.getServiceUrl(),
                dataConsumerScheme.getStructureUrl(),
                dataConsumerScheme.getValidToString(),
                dataConsumerScheme.getValidFromString(),
                writer
            );
        }
    }

    @Override
    protected void writeCustomAttributeElements(OrganisationScheme<?> orgScheme, XMLStreamWriter writer) throws XMLStreamException {
        String orgName = getOrgName(orgScheme);
        organisationWriter.writeOrganisation(orgScheme.getItems(), writer, orgName);
    }

    private String getOrgName(OrganisationScheme<?> orgScheme) {
        StructureClass structureClass = orgScheme.getStructureClass();
        if (structureClass == AGENCY_SCHEME) {
            return XmlConstants.AGENCY;
        } else if (structureClass == DATA_PROVIDER_SCHEME) {
            return XmlConstants.DATA_PROVIDER;
        } else if (structureClass == DATA_CONSUMER_SCHEME) {
            return XmlConstants.DATA_CONSUMER;
        }
        throw new IllegalArgumentException("Unknown structure class: " + structureClass);
    }

    @Override
    protected String getName(OrganisationScheme<?> scheme) {
        return scheme.getStructureClass().getSimpleName();
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.ORGANISATION_SCHEMES;
    }

    @Override
    protected Set<OrganisationScheme<?>> extractArtefacts(Artefacts artefacts) {
        var allOrgSchemes = new HashSet<OrganisationScheme<?>>();
        allOrgSchemes.addAll(artefacts.getAgencySchemes());
        allOrgSchemes.addAll(artefacts.getDataProviderSchemes());
        allOrgSchemes.addAll(artefacts.getDataConsumerSchemes());
        return allOrgSchemes;
    }

}
