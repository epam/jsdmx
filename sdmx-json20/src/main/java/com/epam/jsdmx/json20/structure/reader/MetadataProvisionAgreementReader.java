package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreementImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class MetadataProvisionAgreementReader extends MaintainableReader<MetadataProvisionAgreementImpl> {

    public MetadataProvisionAgreementReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected MetadataProvisionAgreementImpl createMaintainableArtefact() {
        return new MetadataProvisionAgreementImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, MetadataProvisionAgreementImpl provisionAgreement) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.METADATA_FLOW:
                String dataflow = ReaderUtils.getStringJsonField(parser);
                if (dataflow != null) {
                    provisionAgreement.setControlledStructureUsage(new MaintainableArtefactReference(dataflow));
                }
                break;
            case StructureUtils.METADATA_PROVIDER:
                String dataProvider = ReaderUtils.getStringJsonField(parser);
                if (dataProvider != null) {
                    provisionAgreement.setMetadataProvider(new IdentifiableArtefactReferenceImpl(dataProvider));
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MetadataProvisionAgreement: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.METADATA_PROVISION_AGREEMENTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataProvisionAgreementImpl> artefacts) {
        artefact.getMetadataProvisionAgreements().addAll(artefacts);
    }
}
