package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.Level;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class HierarchyWriter extends MaintainableWriter<Hierarchy> {

    private final IdentifiableWriter identifiableWriter;
    private final NameableWriter nameableWriter;
    private final ReferenceAdapter referenceAdapter;

    public HierarchyWriter(VersionableWriter versionableWriter,
                           LinksWriter linksWriter,
                           IdentifiableWriter identifiableWriter,
                           NameableWriter nameableWriter,
                           ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.identifiableWriter = identifiableWriter;
        this.nameableWriter = nameableWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Hierarchy hierarchy) throws IOException {
        super.writeFields(jsonGenerator, hierarchy);
        jsonGenerator.writeBooleanField(StructureUtils.HAS_FORMAT_LEVELS, hierarchy.isHasFormalLevels());
        writeHierarchyCodes(jsonGenerator, hierarchy.getCodes());
        writeHierarchyLevel(jsonGenerator, hierarchy.getLevel());
    }

    @Override
    protected Set<Hierarchy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchies();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.HIERARCHIES;
    }

    private void writeHierarchyLevel(JsonGenerator jsonGenerator, Level level) throws IOException {
        if (level != null) {
            jsonGenerator.writeFieldName(StructureUtils.LEVEL);
            jsonGenerator.writeStartObject();
            nameableWriter.write(jsonGenerator, level);
            writeCodingFormat(jsonGenerator, level);
            writeHierarchyLevel(jsonGenerator, level.getChild());
            jsonGenerator.writeEndObject();
        }

    }

    private void writeCodingFormat(JsonGenerator jsonGenerator, Level level) throws IOException {
        List<CodingFormat> levelCodeFormat = level.getCodeFormat();
        if (levelCodeFormat != null) {
            jsonGenerator.writeFieldName(StructureUtils.CODING_FORMAT);
            jsonGenerator.writeStartObject();
            for (CodingFormat codingFormat : levelCodeFormat) {
                if (codingFormat != null) {
                    Facet format = codingFormat.getCodingFormat();
                    TextFormatImpl textFormat = new TextFormatImpl(Set.of(format));
                    StructureUtils.writeCommonFormatAttributes(jsonGenerator, textFormat);
                }
            }
            jsonGenerator.writeEndObject();
        }
    }

    private void writeHierarchyCodes(JsonGenerator jsonGenerator, List<HierarchicalCode> codes) throws IOException {
        if (codes != null) {
            jsonGenerator.writeFieldName(StructureUtils.HIERARCHICAL_CODES);
            jsonGenerator.writeStartArray();
            if (!codes.isEmpty()) {
                for (HierarchicalCode code : codes) {
                    jsonGenerator.writeStartObject();
                    identifiableWriter.write(jsonGenerator, code);
                    if (code.getValidFrom() != null) {
                        jsonGenerator.writeStringField(StructureUtils.VALID_FROM, StructureUtils.mapInstantToString(code.getValidFrom()));
                    }
                    if (code.getValidTo() != null) {
                        jsonGenerator.writeStringField(StructureUtils.VALID_TO, StructureUtils.mapInstantToString(code.getValidTo()));
                    }
                    if (code.getCode() != null) {
                        jsonGenerator.writeStringField(StructureUtils.CODE, referenceAdapter.toAdaptedUrn(code.getCode()));
                    }
                    if (code.getLevelId() != null) {
                        jsonGenerator.writeStringField(StructureUtils.LEVEL, code.getLevelId());
                    }
                    if (code.getHierarchicalCodes() != null && !code.getHierarchicalCodes().isEmpty()) {
                        writeHierarchyCodes(jsonGenerator, code.getHierarchicalCodes());
                    }
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
