package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.Level;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class HierarchicalCodelistWriter extends MaintainableWriter<Hierarchy> {

    private final IdentifiableWriter identifiableWriter;
    private final NameableWriter nameableWriter;
    private final ReferenceResolver referenceResolver;
    private final ReferenceAdapter referenceAdapter;

    public HierarchicalCodelistWriter(VersionableWriter versionableWriter,
                                      LinksWriter linksWriter,
                                      IdentifiableWriter identifiableWriter,
                                      NameableWriter nameableWriter,
                                      ReferenceResolver referenceResolver,
                                      ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.identifiableWriter = identifiableWriter;
        this.nameableWriter = nameableWriter;
        this.referenceResolver = referenceResolver;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator gen, Hierarchy hierarchy) throws IOException {
        super.writeFields(gen, hierarchy);
        if (CollectionUtils.isNotEmpty(hierarchy.getCodes())) {
            gen.writeFieldName(StructureUtils.HIERARCHIES);
            gen.writeStartArray();
            writeHierarchies(gen, hierarchy.getCodes());
            gen.writeEndArray();
        }
        writeHierarchyLevel(gen, hierarchy.getLevel());
    }

    private void writeHierarchies(JsonGenerator gen, List<HierarchicalCode> codes) throws IOException {
        // write single hierarchy
        gen.writeStartObject();
        gen.writeStringField(StructureUtils.ID, "Hierarchy");
        gen.writeStringField(StructureUtils.NAME, "Hierarchy");
        gen.writeFieldName(StructureUtils.HIERARCHICAL_CODES);
        gen.writeStartArray();
        writeHierarchicalCodes(gen, codes);
        gen.writeEndArray();
        gen.writeEndObject();
    }

    @Override
    protected Set<Hierarchy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchies();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.HIERARCHICAL_CODELISTS;
    }

    private void writeHierarchyLevel(JsonGenerator gen, Level level) throws IOException {
        if (level != null) {
            gen.writeFieldName(StructureUtils.LEVEL);
            gen.writeStartObject();
            nameableWriter.write(gen, level);
            writeCodingFormat(gen, level);
            writeHierarchyLevel(gen, level.getChild());
            gen.writeEndObject();
        }

    }

    private void writeCodingFormat(JsonGenerator gen, Level level) throws IOException {
        List<CodingFormat> levelCodeFormat = level.getCodeFormat();
        if (levelCodeFormat != null) {
            gen.writeFieldName(StructureUtils.CODING_FORMAT);
            gen.writeStartObject();
            for (CodingFormat codingFormat : levelCodeFormat) {
                if (codingFormat != null) {
                    Facet format = codingFormat.getCodingFormat();
                    TextFormatImpl textFormat = new TextFormatImpl(Set.of(format));
                    StructureUtils.writeCommonFormatAttributes(gen, textFormat);
                }
            }
            gen.writeEndObject();
        }
    }

    private void writeHierarchicalCodes(JsonGenerator gen, List<HierarchicalCode> codes) throws IOException {
        if (codes != null) {
            if (!codes.isEmpty()) {
                for (HierarchicalCode code : codes) {
                    gen.writeStartObject();
                    identifiableWriter.write(gen, code);
                    gen.writeStringField(StructureUtils.VALID_FROM, StructureUtils.mapInstantToString(code.getValidFrom()));
                    gen.writeStringField(StructureUtils.VALID_TO, StructureUtils.mapInstantToString(code.getValidTo()));
                    if (code.getCode() != null) {
                        final ArtefactReference resolvedCodeRef = referenceResolver.resolve(code.getCode());
                        gen.writeStringField(StructureUtils.CODE, referenceAdapter.toAdaptedUrn(resolvedCodeRef));
                        gen.writeStringField(StructureUtils.CODE_ID, code.getCode().getItemId());
                    }
                    gen.writeStringField(StructureUtils.LEVEL, code.getLevelId());
                    if (code.getHierarchicalCodes() != null && !code.getHierarchicalCodes().isEmpty()) {
                        gen.writeFieldName(StructureUtils.HIERARCHICAL_CODES);
                        gen.writeStartArray();
                        writeHierarchicalCodes(gen, code.getHierarchicalCodes());
                        gen.writeEndArray();
                    }
                    gen.writeEndObject();
                }
            }
        }
    }
}
