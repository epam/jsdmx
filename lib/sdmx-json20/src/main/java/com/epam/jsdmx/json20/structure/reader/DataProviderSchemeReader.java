package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataProvider;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class DataProviderSchemeReader extends MaintainableReader<DataProviderSchemeImpl> {

    private final OrganisationReader organisationReader;
    private final Map<DataProviderImpl, String> dataProviderSchemeMap = new HashMap<>();

    public DataProviderSchemeReader(VersionableReader versionableArtefact,
                                    OrganisationReader organisationReader) {
        super(versionableArtefact);
        this.organisationReader = organisationReader;
    }

    @Override
    protected DataProviderSchemeImpl createMaintainableArtefact() {
        return new DataProviderSchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, DataProviderSchemeImpl dataProviderScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                dataProviderScheme.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            case StructureUtils.DATA_PROVIDERS:
                List<DataProvider> dataProviders = ReaderUtils.getArray(parser, (this::getDataProvider));
                if (CollectionUtils.isNotEmpty(dataProviders)) {
                    Map<DataProviderImpl, List<DataProviderImpl>> dataProviderListMap = organisationReader.formHierarchy(dataProviderSchemeMap);
                    for (Map.Entry<DataProviderImpl, List<DataProviderImpl>> consumersHier : dataProviderListMap.entrySet()) {
                        List<DataProviderImpl> value = consumersHier.getValue();
                        List<DataProvider> hierarchy = new ArrayList<>(value);
                        consumersHier.getKey().setHierarchy(hierarchy);
                    }
                    dataProviderScheme.setItems(dataProviders);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "DataProviderScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.DATA_PROVIDER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataProviderSchemeImpl> artefacts) {
        artefact.getDataProviderSchemes().addAll(artefacts);
    }

    private DataProvider getDataProvider(JsonParser parser) {
        return organisationReader.getOrganisation(parser, new DataProviderImpl(), dataProviderSchemeMap);
    }

    @Override
    protected void clean() {
        dataProviderSchemeMap.clear();
    }
}
