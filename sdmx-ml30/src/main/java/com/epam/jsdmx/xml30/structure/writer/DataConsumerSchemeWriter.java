package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumer;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;


public class DataConsumerSchemeWriter extends XmlWriter<DataConsumerScheme> {

    private final OrganisationWriter organisationWriter;

    public DataConsumerSchemeWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    LinksWriter linksWriter,
                                    OrganisationWriter organisationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeAttributes(DataConsumerScheme dataConsumerScheme, XMLStreamWriter writer) throws XMLStreamException {
        DataConsumerSchemeImpl dataConsumerSchemeImpl = (DataConsumerSchemeImpl) dataConsumerScheme;
        dataConsumerSchemeImpl.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));
        commonAttributesWriter.writeAttributes(
            XmlConstants.DATA_CONSUMERS,
            dataConsumerScheme.getOrganizationId(),
            dataConsumerScheme.getUri(),
            dataConsumerSchemeImpl.getUrn(),
            null,
            dataConsumerScheme.isExternalReference(),
            dataConsumerScheme.getServiceUrl(),
            dataConsumerScheme.getStructureUrl(),
            dataConsumerScheme.getValidToString(),
            dataConsumerScheme.getValidFromString(),
            writer
        );
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(dataConsumerScheme.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(DataConsumerScheme dataConsumerScheme, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends DataConsumer> dataConsumerSchemeItems = dataConsumerScheme.getItems();
        organisationWriter.writeOrganisation(dataConsumerSchemeItems, writer, XmlConstants.DATA_CONSUMER);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATA_CONSUMER_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.DATA_CONSUMER_SCHEMES;
    }

    @Override
    protected Set<DataConsumerScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataConsumerSchemes();
    }
}
