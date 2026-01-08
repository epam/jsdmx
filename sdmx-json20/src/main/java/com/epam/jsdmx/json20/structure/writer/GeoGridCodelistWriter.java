package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelist;

import com.fasterxml.jackson.core.JsonGenerator;

public class GeoGridCodelistWriter extends MaintainableWriter<GeoGridCodelist> {

    private final CodelistExtensionWriter codelistExtensionWriter;
    private final GridCodeWriter gridCodeWriter;
    private final NameableWriter nameableWriter;

    public GeoGridCodelistWriter(VersionableWriter versionableWriter,
                                 LinksWriter linksWriter,
                                 CodelistExtensionWriter codelistExtensionWriter,
                                 GridCodeWriter gridCodeWriter,
                                 NameableWriter nameableWriter) {
        super(versionableWriter, linksWriter);
        this.codelistExtensionWriter = codelistExtensionWriter;
        this.gridCodeWriter = gridCodeWriter;
        this.nameableWriter = nameableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, GeoGridCodelist geoGridCodelist) throws IOException {
        super.writeFields(jsonGenerator, geoGridCodelist);

        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, geoGridCodelist.isPartial());
        jsonGenerator.writeStringField(StructureUtils.GEO_TYPE, StructureUtils.GEO_GRID_CODELIST);
        if (geoGridCodelist.getGridDefinition() != null) {
            jsonGenerator.writeStringField(StructureUtils.GRID_DEFINITION, geoGridCodelist.getGridDefinition());
        }

        gridCodeWriter.writeCodes(jsonGenerator, geoGridCodelist.getItems(), StructureUtils.GEO_GRID_CODES, nameableWriter);

        codelistExtensionWriter.writeCodeListExtentions(jsonGenerator, geoGridCodelist.getExtensions());
    }

    @Override
    protected Set<GeoGridCodelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getGeoGridCodelists();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.GEO_GRID_CODELISTS;
    }
}
