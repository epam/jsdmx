package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataProvider;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;


public class DataProviderSchemeWriter extends XmlWriter<DataProviderScheme> {

    private final OrganisationWriter organisationWriter;

    public DataProviderSchemeWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    LinksWriter linksWriter,
                                    OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(DataProviderScheme dataProviderScheme, XMLStreamWriter writer) throws XMLStreamException {
        DataProviderSchemeImpl dataProviderSchemeImpl = (DataProviderSchemeImpl) dataProviderScheme;
        dataProviderSchemeImpl.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));
        commonAttributesWriter.writeAttributes(
            XmlConstants.DATA_PROVIDERS,
            dataProviderScheme.getOrganizationId(),
            dataProviderScheme.getUri(),
            dataProviderSchemeImpl.getUrn(),
            null,
            dataProviderScheme.isExternalReference(),
            dataProviderScheme.getServiceUrl(),
            dataProviderScheme.getStructureUrl(),
            dataProviderScheme.getValidToString(),
            dataProviderScheme.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(dataProviderScheme.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(DataProviderScheme dataProviderScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends DataProvider> dataProviderSchemeItems = dataProviderScheme.getItems();
        organisationWriter.writeOrganisation(dataProviderSchemeItems, writer, XmlConstants.DATA_PROVIDER);
    }


    @Override
    protected String getName() {
        return XmlConstants.DATA_PROVIDER_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.DATA_PROVIDER_SCHEMES;
    }

    @Override
    protected Set<DataProviderScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataProviderSchemes();
    }
}
