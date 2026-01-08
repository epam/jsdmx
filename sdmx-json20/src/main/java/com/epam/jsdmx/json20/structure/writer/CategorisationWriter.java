package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;


public class CategorisationWriter extends MaintainableWriter<Categorisation> {

    private final ReferenceAdapter referenceAdapter;

    public CategorisationWriter(VersionableWriter versionableWriter,
                                LinksWriter linksWriter,
                                ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Categorisation categorisation) throws IOException {
        super.writeFields(jsonGenerator, categorisation);
        writeArtefactReference(jsonGenerator, categorisation.getCategorizedArtefact(), StructureUtils.SOURCE);
        writeArtefactReference(jsonGenerator, categorisation.getCategorizedBy(), StructureUtils.TARGET);
    }

    @Override
    protected Set<Categorisation> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorisations();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CATEGORISATIONS;
    }

    private void writeArtefactReference(JsonGenerator jsonGenerator, ArtefactReference structure, String fieldName) throws IOException {
        if (structure != null) {
            jsonGenerator.writeStringField(fieldName, referenceAdapter.toAdaptedUrn(structure));
        }
    }
}
