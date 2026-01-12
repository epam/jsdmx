package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderScheme;

import com.fasterxml.jackson.core.JsonGenerator;

public class MetadataProviderSchemeWriter extends MaintainableWriter<MetadataProviderScheme> {

    private final OrganisationWriter organisationWriter;

    public MetadataProviderSchemeWriter(VersionableWriter versionableWriter,
                                        LinksWriter linksWriter,
                                        OrganisationWriter organisationWriter) {
        super(versionableWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, MetadataProviderScheme metadataProviderScheme) throws IOException {
        super.writeFields(jsonGenerator, metadataProviderScheme);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, metadataProviderScheme.isPartial());
        organisationWriter.writeOrganisation(jsonGenerator, metadataProviderScheme.getItems(), StructureUtils.METADATA_PROVIDERS);
    }

    @Override
    protected Set<MetadataProviderScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataProviderSchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.METADATA_PROVIDER_SCHEMES;
    }
}
