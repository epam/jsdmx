package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCode;
import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelistImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class GeographicCodelistReader extends MaintainableReader<GeographicCodelistImpl> {

    private final CodelistExtensionReader codelistExtensionReader;
    private final GeoFeatureSetCodeReader geoFeatureSetCodeReader;
    private final NameableReader nameableReader;

    Map<GeoFeatureSetCodeImpl, String> codeWithParentId = new HashMap<>();

    protected GeographicCodelistReader(VersionableReader versionableArtefact,
                                       NameableReader nameableReader,
                                       CodelistExtensionReader codelistExtensionReader,
                                       GeoFeatureSetCodeReader geoFeatureSetCodeReader) {
        super(versionableArtefact);
        this.codelistExtensionReader = codelistExtensionReader;
        this.nameableReader = nameableReader;
        this.geoFeatureSetCodeReader = geoFeatureSetCodeReader;
    }

    @Override
    protected GeographicCodelistImpl createMaintainableArtefact() {
        return new GeographicCodelistImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, GeographicCodelistImpl geographicCodelist) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.GEO_FEATURE_SET_CODES:
                List<GeoFeatureSetCode> geoFeatureSetCodes = ReaderUtils.getArray(parser, (this::getGeographicalCode));
                if (CollectionUtils.isNotEmpty(geoFeatureSetCodes)) {
                    geographicCodelist.setItems(List.copyOf(geoFeatureSetCodes));
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
                    geographicCodelist.setExtensions(codeListExtensions);
                }
                break;
            case StructureUtils.IS_PARTIAL:
                geographicCodelist.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "GeographicalCodelist: " + fieldName);
        }
    }

    private GeoFeatureSetCodeImpl getGeographicalCode(JsonParser parser) {
        try {
            return geoFeatureSetCodeReader.getCode(parser, new GeoFeatureSetCodeImpl(), nameableReader, codeWithParentId);
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.GEOGRAPHIC_CODELISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<GeographicCodelistImpl> artefacts) {
        artefact.getGeographicCodelists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        codeWithParentId.clear();
    }
}
