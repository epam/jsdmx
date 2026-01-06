package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonGenerator;


public class DataflowWriter extends MaintainableWriter<Dataflow> {

    private final ReferenceResolver referenceResolver;

    public DataflowWriter(VersionableWriter versionableWriter,
                          LinksWriter linksWriter,
                          ReferenceResolver referenceResolver) {
        super(versionableWriter, linksWriter);
        this.referenceResolver = referenceResolver;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Dataflow dataflow) throws IOException {
        super.writeFields(jsonGenerator, dataflow);
        if (dataflow.getStructure() != null) {
            jsonGenerator.writeStringField(StructureUtils.STRUCTURE, referenceResolver.resolve(dataflow.getStructure()).getUrn());
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
