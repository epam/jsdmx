package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvider;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderScheme;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;


public class MetadataProviderSchemeWriter extends XmlWriter<MetadataProviderScheme> {

    private final OrganisationWriter organisationWriter;

    public MetadataProviderSchemeWriter(NameableWriter nameableWriter,
                                        AnnotableWriter annotableWriter,
                                        CommonAttributesWriter commonAttributesWriter,
                                        LinksWriter linksWriter,
                                        OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(MetadataProviderScheme metadataProviderScheme, XMLStreamWriter writer) throws XMLStreamException {
        MetadataProviderSchemeImpl metadataProviderSchemeImpl = (MetadataProviderSchemeImpl) metadataProviderScheme;
        metadataProviderSchemeImpl.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));
        commonAttributesWriter.writeAttributes(
            XmlConstants.METADATA_PROVIDERS,
            metadataProviderScheme.getOrganizationId(),
            metadataProviderScheme.getUri(),
            metadataProviderSchemeImpl.getUrn(),
            null,
            metadataProviderScheme.isExternalReference(),
            metadataProviderScheme.getServiceUrl(),
            metadataProviderScheme.getStructureUrl(),
            metadataProviderScheme.getValidToString(),
            metadataProviderScheme.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(metadataProviderScheme.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(MetadataProviderScheme dataProviderScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends MetadataProvider> dataProviderSchemeItems = dataProviderScheme.getItems();
        organisationWriter.writeOrganisation(dataProviderSchemeItems, writer, XmlConstants.METADATA_PROVIDER);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_PROVIDER_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.METADATA_PROVIDER_SCHEMES;
    }

    @Override
    protected Set<MetadataProviderScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataProviderSchemes();
    }
}
