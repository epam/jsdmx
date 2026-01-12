package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreement;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class ProvisionAgreementWriter extends MaintainableWriter<ProvisionAgreement> {

    private final ReferenceAdapter referenceAdapter;

    public ProvisionAgreementWriter(VersionableWriter versionableWriter, LinksWriter linksWriter, ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ProvisionAgreement provisionAgreement) throws IOException {
        super.writeFields(jsonGenerator, provisionAgreement);

        ArtefactReference dataProvider = provisionAgreement.getDataProvider();
        if (dataProvider != null) {
            jsonGenerator.writeStringField(StructureUtils.DATA_PROVIDER, referenceAdapter.toAdaptedUrn(dataProvider));
        }

        ArtefactReference controlledStructureUsage = provisionAgreement.getControlledStructureUsage();
        if (controlledStructureUsage != null) {
            jsonGenerator.writeStringField(StructureUtils.DATAFLOW, referenceAdapter.toAdaptedUrn(controlledStructureUsage));
        }
    }

    @Override
    protected Set<ProvisionAgreement> extractArtefacts(Artefacts artefacts) {
        return artefacts.getProvisionAgreements();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.PROVISION_AGREEMENTS;
    }
}
