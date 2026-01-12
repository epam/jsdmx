package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setAgencyId;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setExternalReference;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setServiceUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setStructureUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setUri;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidFrom;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidTo;

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
import com.epam.jsdmx.infomodel.sdmx30.DataConsumer;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class DataConsumerSchemeReader extends XmlReader<DataConsumerSchemeImpl> {

    private final OrganisationReader organisationReader;
    private final List<DataConsumer> dataConsumers = new ArrayList<>();
    private final Map<DataConsumerImpl, String> dataConsumerWithParent = new HashMap<>();

    public DataConsumerSchemeReader(AnnotableReader annotableReader,
                                    NameableReader nameableReader,
                                    OrganisationReader organisationReader) {
        super(annotableReader, nameableReader);
        this.organisationReader = organisationReader;
    }

    @Override
    protected DataConsumerSchemeImpl createMaintainableArtefact() {
        return new DataConsumerSchemeImpl();
    }

    @Override
    protected DataConsumerSchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        DataConsumerSchemeImpl dataConsumerScheme = super.read(reader);
        Map<DataConsumerImpl, List<DataConsumerImpl>> dataConsumerListMap = organisationReader.formHierarchy(dataConsumerWithParent);
        for (Map.Entry<DataConsumerImpl, List<DataConsumerImpl>> consumersHier : dataConsumerListMap.entrySet()) {
            List<DataConsumerImpl> value = consumersHier.getValue();
            List<DataConsumer> hierarchy = new ArrayList<>(value);
            consumersHier.getKey().setHierarchy(hierarchy);
        }
        dataConsumerScheme.setItems(new ArrayList<>(dataConsumers));
        return dataConsumerScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, DataConsumerSchemeImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.DATA_CONSUMER.equals(name)) {
            throw new IllegalArgumentException("DataConsumerScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);

        }
        DataConsumer dataConsumer = organisationReader.addOrganisation(reader, new DataConsumerImpl(), dataConsumerWithParent);
        dataConsumers.add(dataConsumer);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, DataConsumerSchemeImpl artefact) throws XMLStreamException {
        setUri(reader, artefact);

        setStructureUrl(reader, artefact);

        setServiceUrl(reader, artefact);

        artefact.setId(XmlConstants.DATA_CONSUMERS);

        artefact.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));

        setAgencyId(reader, artefact);

        setExternalReference(reader, artefact);

        setValidTo(reader, artefact);

        setValidFrom(reader, artefact);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(artefact::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATA_CONSUMER_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.DATA_CONSUMER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataConsumerSchemeImpl> artefacts) {
        artefact.getDataConsumerSchemes().addAll(artefacts);
    }

    @Override
    protected void clean() {
        dataConsumers.clear();
    }
}
