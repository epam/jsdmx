package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Code;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import com.fasterxml.jackson.core.JsonGenerator;

public class CodelistWriter extends MaintainableWriter<Codelist> {

    private final CodeWriterImpl codeWriter;
    private final CodelistExtensionWriter extensionWriter;
    private final NameableWriter nameableWriter;

    public CodelistWriter(VersionableWriter versionableWriter,
                          LinksWriter linksWriter,
                          CodeWriterImpl codeWriter,
                          CodelistExtensionWriter extensionWriter,
                          NameableWriter nameableWriter) {
        super(versionableWriter, linksWriter);
        this.codeWriter = codeWriter;
        this.extensionWriter = extensionWriter;
        this.nameableWriter = nameableWriter;
    }

    @Override
    public Optional<StructureClass> getWritableArtefactStructureClass() {
        return Optional.of(StructureClassImpl.CODELIST);
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Codelist codelist) throws IOException {
        super.writeFields(jsonGenerator, codelist);

        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, codelist.isPartial());
        codeWriter.writeCodes(jsonGenerator, (List<Code>) codelist.getItems(), StructureUtils.CODES, nameableWriter);
        extensionWriter.writeCodeListExtentions(jsonGenerator, codelist.getExtensions());
    }

    @Override
    protected Set<Codelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCodelists();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CODE_LISTS;
    }

}