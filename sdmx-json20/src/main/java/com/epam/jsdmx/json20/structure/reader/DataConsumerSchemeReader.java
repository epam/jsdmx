package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumer;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class DataConsumerSchemeReader extends MaintainableReader<DataConsumerSchemeImpl> {

    private final OrganisationReader organisationReader;
    private final Map<DataConsumerImpl, String> dataConsumerWithParent = new HashMap<>();

    public DataConsumerSchemeReader(VersionableReader versionableArtefact,
                                    OrganisationReader organisationReader) {
        super(versionableArtefact);
        this.organisationReader = organisationReader;
    }

    @Override
    protected DataConsumerSchemeImpl createMaintainableArtefact() {
        return new DataConsumerSchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, DataConsumerSchemeImpl dataConsumerScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                dataConsumerScheme.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            case StructureUtils.DATA_CONSUMERS:
                List<DataConsumer> dataConsumers = ReaderUtils.getArray(parser, (this::getDataConsumer));
                if (CollectionUtils.isNotEmpty(dataConsumers)) {
                    Map<DataConsumerImpl, List<DataConsumerImpl>> dataConsumerListMap = organisationReader.formHierarchy(dataConsumerWithParent);
                    for (Map.Entry<DataConsumerImpl, List<DataConsumerImpl>> consumersHier : dataConsumerListMap.entrySet()) {
                        List<DataConsumerImpl> value = consumersHier.getValue();
                        List<DataConsumer> hierarchy = new ArrayList<>(value);
                        consumersHier.getKey().setHierarchy(hierarchy);
                    }
                    dataConsumerScheme.setItems(dataConsumers);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "DataConsumerScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.DATA_CONSUMER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataConsumerSchemeImpl> artefacts) {
        artefact.getDataConsumerSchemes().addAll(artefacts);
    }

    private DataConsumer getDataConsumer(JsonParser parser) {
        return organisationReader.getOrganisation(parser, new DataConsumerImpl(), dataConsumerWithParent);
    }

    @Override
    protected void clean() {
        dataConsumerWithParent.clear();
    }
}
