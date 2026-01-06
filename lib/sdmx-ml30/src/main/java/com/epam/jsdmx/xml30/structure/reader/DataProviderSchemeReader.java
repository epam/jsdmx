package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataProvider;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class DataProviderSchemeReader extends XmlReader<DataProviderSchemeImpl> {

    private final OrganisationReader organisationReader;

    private final List<DataProvider> dataProviders = new ArrayList<>();
    private final Map<DataProviderImpl, String> dataProviderStringMap = new HashMap<>();

    public DataProviderSchemeReader(AnnotableReader annotableReader,
                                    NameableReader nameableReader,
                                    OrganisationReader organisationReader) {
        super(annotableReader, nameableReader);
        this.organisationReader = organisationReader;
    }

    @Override
    protected DataProviderSchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        DataProviderSchemeImpl dataProviderScheme = super.read(reader);
        Map<DataProviderImpl, List<DataProviderImpl>> dataProviderListMap = organisationReader.formHierarchy(dataProviderStringMap);
        for (Map.Entry<DataProviderImpl, List<DataProviderImpl>> consumersHier : dataProviderListMap.entrySet()) {
            List<DataProviderImpl> value = consumersHier.getValue();
            List<DataProvider> hierarchy = new ArrayList<>(value);
            consumersHier.getKey().setHierarchy(hierarchy);
        }
        dataProviderScheme.setItems(new ArrayList<>(dataProviders));
        return dataProviderScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, DataProviderSchemeImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.DATA_PROVIDER.equals(name)) {
            throw new IllegalArgumentException("DataProviderScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);

        }
        DataProvider dataProvider = organisationReader.addOrganisation(reader, new DataProviderImpl(), dataProviderStringMap);
        dataProviders.add(dataProvider);
    }

    @Override
    protected DataProviderSchemeImpl createMaintainableArtefact() {
        return new DataProviderSchemeImpl();
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, DataProviderSchemeImpl artefact) throws XMLStreamException {
        XmlReaderUtils.setUri(reader, artefact);

        XmlReaderUtils.setStructureUrl(reader, artefact);

        XmlReaderUtils.setServiceUrl(reader, artefact);

        artefact.setId(XmlConstants.DATA_PROVIDERS);

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
        return XmlConstants.DATA_PROVIDER_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.DATA_PROVIDER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataProviderSchemeImpl> artefacts) {
        artefact.getDataProviderSchemes().addAll(artefacts);
    }

    @Override
    protected void clean() {
        dataProviders.clear();
    }
}
