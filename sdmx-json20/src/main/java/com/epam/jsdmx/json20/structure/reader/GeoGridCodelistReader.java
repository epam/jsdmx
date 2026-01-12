package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.GridCodeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class GeoGridCodelistReader extends MaintainableReader<GeoGridCodelistImpl> {

    private final CodelistExtensionReader codelistExtensionReader;
    private final GridCodeReader gridCodeReader;
    private final NameableReader nameableReader;

    Map<GridCodeImpl, String> codeWithParentId = new HashMap<>();

    protected GeoGridCodelistReader(VersionableReader versionableArtefact,
                                    CodelistExtensionReader codelistExtensionReader,
                                    GridCodeReader gridCodeReader,
                                    NameableReader nameableReader) {
        super(versionableArtefact);
        this.codelistExtensionReader = codelistExtensionReader;
        this.gridCodeReader = gridCodeReader;
        this.nameableReader = nameableReader;
    }

    @Override
    protected GeoGridCodelistImpl createMaintainableArtefact() {
        return new GeoGridCodelistImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, GeoGridCodelistImpl geoGridCodelist) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.GEO_GRID_CODES:
                List<GridCodeImpl> geoFeatureSetCodes = ReaderUtils.getArray(parser, (this::getGeoGridCodes));
                if (CollectionUtils.isNotEmpty(geoFeatureSetCodes)) {
                    geoGridCodelist.setItems(List.copyOf(geoFeatureSetCodes));
                }
                break;
            case StructureUtils.GEO_TYPE:
                parser.nextToken();
                parser.nextToken();
                break;
            case StructureUtils.CODE_LIST_EXTENSIONS:
                parser.nextToken();
                List<CodelistExtension> codeListExtensions = codelistExtensionReader.getCodeListExtensions(parser);
                if (CollectionUtils.isNotEmpty(codeListExtensions)) {
                    geoGridCodelist.setExtensions(codeListExtensions);
                }
                break;
            case StructureUtils.GRID_DEFINITION:
                geoGridCodelist.setGridDefinition(ReaderUtils.getStringJsonField(parser));
                break;
            case StructureUtils.IS_PARTIAL:
                geoGridCodelist.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "GeographicalCodelist: " + fieldName);
        }
    }

    private GridCodeImpl getGeoGridCodes(JsonParser parser) {
        try {
            return gridCodeReader.getCode(parser, new GridCodeImpl(), nameableReader, codeWithParentId);
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.GEO_GRID_CODELISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<GeoGridCodelistImpl> artefacts) {
        artefact.getGeoGridCodelists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        codeWithParentId.clear();
    }
}
