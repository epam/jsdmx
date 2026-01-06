package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnit;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitScheme;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import org.apache.commons.collections.CollectionUtils;

public class OrganisationUnitSchemeWriter extends XmlWriter<OrganisationUnitScheme> {

    private final OrganisationWriter organisationWriter;

    public OrganisationUnitSchemeWriter(NameableWriter nameableWriter,
                                        AnnotableWriter annotableWriter,
                                        CommonAttributesWriter commonAttributesWriter,
                                        LinksWriter linksWriter,
                                        OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(OrganisationUnitScheme organisationUnitScheme, XMLStreamWriter writer) throws XMLStreamException {
        OrganisationUnitSchemeImpl organisationUnitSchemeImpl = (OrganisationUnitSchemeImpl) organisationUnitScheme;
        organisationUnitSchemeImpl.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));
        commonAttributesWriter.writeAttributes(
            organisationUnitSchemeImpl.getId(),
            organisationUnitSchemeImpl.getOrganizationId(),
            organisationUnitSchemeImpl.getUri(),
            organisationUnitSchemeImpl.getUrn(),
            null,
            organisationUnitSchemeImpl.isExternalReference(),
            organisationUnitSchemeImpl.getServiceUrl(),
            organisationUnitSchemeImpl.getStructureUrl(),
            organisationUnitSchemeImpl.getValidToString(),
            organisationUnitSchemeImpl.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(organisationUnitSchemeImpl.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(OrganisationUnitScheme organisationUnitScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends OrganisationUnit> organisationUnitSchemeItems = organisationUnitScheme.getItems();
        if (CollectionUtils.isNotEmpty(organisationUnitSchemeItems)) {
            organisationWriter.writeOrganisation(organisationUnitSchemeItems, writer, XmlConstants.ORGANISATION_UNIT);
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.ORGANISATION_UNIT_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.ORGANISATION_UNIT_SCHEMES;
    }

    @Override
    protected Set<OrganisationUnitScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getOrganisationUnitSchemes();
    }
}
