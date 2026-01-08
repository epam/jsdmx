package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreement;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class MetadataProvisionAgreementWriter extends MaintainableWriter<MetadataProvisionAgreement> {

    private final ReferenceAdapter referenceAdapter;

    public MetadataProvisionAgreementWriter(VersionableWriter versionableWriter,
                                            LinksWriter linksWriter,
                                            ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, MetadataProvisionAgreement metadataProvisionAgreement) throws IOException {
        super.writeFields(jsonGenerator, metadataProvisionAgreement);

        ArtefactReference dataProvider = metadataProvisionAgreement.getMetadataProvider();
        if (dataProvider != null) {
            jsonGenerator.writeStringField(StructureUtils.METADATA_PROVIDER, referenceAdapter.adaptUrn(dataProvider.getUrn()));
        }

        ArtefactReference controlledStructureUsage = metadataProvisionAgreement.getControlledStructureUsage();
        if (controlledStructureUsage != null) {
            jsonGenerator.writeStringField(StructureUtils.METADATAFLOW, referenceAdapter.adaptUrn(controlledStructureUsage.getUrn()));
        }
    }

    @Override
    protected Set<MetadataProvisionAgreement> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataProvisionAgreements();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.METADATA_PROVISION_AGREEMENTS;
    }
}
