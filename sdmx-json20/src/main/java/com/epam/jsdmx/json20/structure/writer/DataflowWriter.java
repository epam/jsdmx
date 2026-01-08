package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;


public class DataflowWriter extends MaintainableWriter<Dataflow> {

    private final ReferenceAdapter referenceAdapter;

    public DataflowWriter(VersionableWriter versionableWriter, LinksWriter linksWriter, ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Dataflow dataflow) throws IOException {
        super.writeFields(jsonGenerator, dataflow);
        if (dataflow.getStructure() != null) {
            jsonGenerator.writeStringField(StructureUtils.STRUCTURE, referenceAdapter.toAdaptedUrn(dataflow.getStructure()));
        }
    }

    @Override
    protected Set<Dataflow> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataflows();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATAFLOWS;
    }
}
