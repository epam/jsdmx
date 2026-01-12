package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelist;

import com.fasterxml.jackson.core.JsonGenerator;

public class GeographicCodelistWriter extends MaintainableWriter<GeographicCodelist> {

    private final GeoFeatureSetCodeWriter codeWriter;
    private final CodelistExtensionWriter codelistExtensionWriter;
    private final NameableWriter nameableWriter;

    public GeographicCodelistWriter(VersionableWriter versionableWriter,
                                    LinksWriter linksWriter,
                                    GeoFeatureSetCodeWriter codeWriter,
                                    CodelistExtensionWriter codelistExtensionWriter,
                                    NameableWriter nameableWriter) {
        super(versionableWriter, linksWriter);
        this.codeWriter = codeWriter;
        this.codelistExtensionWriter = codelistExtensionWriter;
        this.nameableWriter = nameableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, GeographicCodelist geographicCodelist) throws IOException {
        super.writeFields(jsonGenerator, geographicCodelist);

        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, geographicCodelist.isPartial());
        jsonGenerator.writeStringField(StructureUtils.GEO_TYPE, StructureUtils.GEOGRAPHIC_CODELIST);

        writeGeoFeatureCodelists(jsonGenerator, geographicCodelist);

        codelistExtensionWriter.writeCodeListExtentions(jsonGenerator, geographicCodelist.getExtensions());
    }

    private void writeGeoFeatureCodelists(JsonGenerator jsonGenerator, GeographicCodelist geographicCodelist) throws IOException {
        codeWriter.writeCodes(
            jsonGenerator,
            geographicCodelist.getItems(),
            StructureUtils.GEO_FEATURE_SET_CODES,
            nameableWriter
        );
    }

    @Override
    protected Set<GeographicCodelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getGeographicCodelists();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.GEOGRAPHIC_CODELISTS;
    }
}
