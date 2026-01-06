package com.epam.jsdmx.json20.structure.writer;


import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import com.fasterxml.jackson.core.JsonGenerator;

public class DataConsumerSchemeWriter extends MaintainableWriter<DataConsumerScheme> {

    private final OrganisationWriter organisationWriter;

    public DataConsumerSchemeWriter(VersionableWriter versionableWriter,
                                    LinksWriter linksWriter,
                                    OrganisationWriter organisationWriter) {
        super(versionableWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, DataConsumerScheme dataConsumerScheme) throws IOException {
        DataConsumerSchemeImpl dataConsumerSchemeImpl = (DataConsumerSchemeImpl) dataConsumerScheme;
        dataConsumerSchemeImpl.setId(StructureUtils.DATA_CONSUMERS_ID);
        dataConsumerSchemeImpl.setVersion(Version.createFromString(StructureUtils.DEFAULT_VERSION));
        super.writeFields(jsonGenerator, dataConsumerSchemeImpl);
        Boolean isPartial = dataConsumerScheme.isPartial();
        if (isPartial != null) {
            jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, isPartial);
        }
        organisationWriter.writeOrganisation(jsonGenerator, dataConsumerScheme.getItems(), StructureUtils.DATA_CONSUMERS);
    }

    @Override
    protected Set<DataConsumerScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataConsumerSchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATA_CONSUMER_SCHEMES;
    }
}
