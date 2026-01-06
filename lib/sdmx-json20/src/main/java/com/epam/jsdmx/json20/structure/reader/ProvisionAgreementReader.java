package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreementImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class ProvisionAgreementReader extends MaintainableReader<ProvisionAgreementImpl> {

    public ProvisionAgreementReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected ProvisionAgreementImpl createMaintainableArtefact() {
        return new ProvisionAgreementImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ProvisionAgreementImpl provisionAgreement) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.DATAFLOW:
                String dataflow = ReaderUtils.getStringJsonField(parser);
                if (dataflow != null) {
                    provisionAgreement.setControlledStructureUsage(new MaintainableArtefactReference(dataflow));
                }
                break;
            case StructureUtils.DATA_PROVIDER:
                String dataProvider = ReaderUtils.getStringJsonField(parser);
                if (dataProvider != null) {
                    provisionAgreement.setDataProvider(new IdentifiableArtefactReferenceImpl(dataProvider));
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ProvisionAgreement: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.PROVISION_AGREEMENTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ProvisionAgreementImpl> artefacts) {
        artefact.getProvisionAgreements().addAll(artefacts);
    }
}
