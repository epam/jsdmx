package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvider;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class MetadataProviderSchemeReader extends XmlReader<MetadataProviderSchemeImpl> {

    private final OrganisationReader organisationReader;

    private final List<MetadataProvider> dataProviders = new ArrayList<>();

    public MetadataProviderSchemeReader(AnnotableReader annotableReader,
                                        NameableReader nameableReader,
                                        OrganisationReader organisationReader) {
        super(annotableReader, nameableReader);
        this.organisationReader = organisationReader;
    }

    @Override
    protected MetadataProviderSchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        MetadataProviderSchemeImpl dataProviderScheme = super.read(reader);
        dataProviderScheme.setItems(new ArrayList<>(dataProviders));
        return dataProviderScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, MetadataProviderSchemeImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.METADATA_PROVIDER.equals(name)) {
            throw new IllegalArgumentException("MetadataProviderScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
        }
        MetadataProvider dataProvider = organisationReader.addOrganisation(reader, new MetadataProviderImpl(), null);
        dataProviders.add(dataProvider);
    }

    @Override
    protected MetadataProviderSchemeImpl createMaintainableArtefact() {
        return new MetadataProviderSchemeImpl();
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, MetadataProviderSchemeImpl artefact) throws XMLStreamException {
        XmlReaderUtils.setUri(reader, artefact);

        XmlReaderUtils.setStructureUrl(reader, artefact);

        XmlReaderUtils.setServiceUrl(reader, artefact);

        artefact.setId(XmlConstants.METADATA_PROVIDERS);

        artefact.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));

        XmlReaderUtils.setAgencyId(reader, artefact);

        XmlReaderUtils.setExternalReference(reader, artefact);

        XmlReaderUtils.setValidTo(reader, artefact);

        XmlReaderUtils.setValidFrom(reader, artefact);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(artefact::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_PROVIDER_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.METADATA_PROVIDER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataProviderSchemeImpl> artefacts) {
        artefact.getMetadataProviderSchemes().addAll(artefacts);
    }

    @Override
    protected void clean() {
        dataProviders.clear();
    }
}
