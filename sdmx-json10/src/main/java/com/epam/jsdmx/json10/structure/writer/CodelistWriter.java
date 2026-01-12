package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import com.fasterxml.jackson.core.JsonGenerator;

public class CodelistWriter extends MaintainableWriter<Codelist> {

    private final CodeWriterImpl codeWriter;
    private final NameableWriter nameableWriter;

    public CodelistWriter(VersionableWriter versionableWriter,
                          LinksWriter linksWriter,
                          CodeWriterImpl codeWriter,
                          NameableWriter nameableWriter) {
        super(versionableWriter, linksWriter);
        this.codeWriter = codeWriter;
        this.nameableWriter = nameableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Codelist codelist) throws IOException {
        super.writeFields(jsonGenerator, codelist);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, codelist.isPartial());
        codeWriter.writeCodes(jsonGenerator, codelist.getItems(), StructureUtils.CODES, nameableWriter);
    }

    @Override
    protected Set<Codelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCodelists();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CODE_LISTS;
    }

    @Override
    public Optional<StructureClass> getWritableArtefactStructureClass() {
        return Optional.of(StructureClassImpl.CODELIST);
    }

}