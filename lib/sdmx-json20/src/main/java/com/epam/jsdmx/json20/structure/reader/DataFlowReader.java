package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class DataFlowReader extends MaintainableReader<DataflowImpl> {

    public DataFlowReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected DataflowImpl createMaintainableArtefact() {
        return new DataflowImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, DataflowImpl dataflow) throws IOException {
        String fieldName = parser.getCurrentName();
        if (StructureUtils.STRUCTURE.equals(fieldName)) {
            String urn = ReaderUtils.getStringJsonField(parser);
            if (urn != null) {
                dataflow.setStructure(new MaintainableArtefactReference(urn));
            }
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.DATAFLOWS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataflowImpl> artefacts) {
        artefact.getDataflows().addAll(artefacts);
    }
}
