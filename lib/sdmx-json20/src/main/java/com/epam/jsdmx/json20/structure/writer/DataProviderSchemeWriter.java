package com.epam.jsdmx.json20.structure.writer;


import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import com.fasterxml.jackson.core.JsonGenerator;

public class DataProviderSchemeWriter extends MaintainableWriter<DataProviderScheme> {

    private final OrganisationWriter organisationWriter;

    public DataProviderSchemeWriter(VersionableWriter versionableWriter,
                                    LinksWriter linksWriter,
                                    OrganisationWriter organisationWriter) {
        super(versionableWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, DataProviderScheme dataProviderScheme) throws IOException {
        DataProviderSchemeImpl providerScheme = (DataProviderSchemeImpl) dataProviderScheme;
        providerScheme.setId(StructureUtils.DATA_PROVIDERS_ID);
        providerScheme.setVersion(Version.createFromString(StructureUtils.DEFAULT_VERSION));
        super.writeFields(jsonGenerator, providerScheme);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, dataProviderScheme.isPartial());
        organisationWriter.writeOrganisation(jsonGenerator, dataProviderScheme.getItems(), StructureUtils.DATA_PROVIDERS);
    }

    @Override
    protected Set<DataProviderScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataProviderSchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATA_PROVIDER_SCHEMES;
    }
}
