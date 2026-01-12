package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormatImpl;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.LevelImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class HierarchyReader extends MaintainableReader<HierarchyImpl> {

    public final IdentifiableReader identifiableReader;

    private final NameableReader nameableReader;

    public HierarchyReader(VersionableReader versionableArtefact,
                           IdentifiableReader identifiableReader,
                           NameableReader nameableReader) {
        super(versionableArtefact);
        this.identifiableReader = identifiableReader;
        this.nameableReader = nameableReader;
    }

    @Override
    protected HierarchyImpl createMaintainableArtefact() {
        return new HierarchyImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, HierarchyImpl hierarchy) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.HIERARCHICAL_CODES:
                List<HierarchicalCode> hierarchicalCodes = ReaderUtils.getArray(parser, this::getHierarchicalCode);
                if (CollectionUtils.isNotEmpty(hierarchicalCodes)) {
                    hierarchy.setCodes(hierarchicalCodes);
                }
                break;
            case StructureUtils.HAS_FORMAT_LEVELS:
                boolean hasFormatLevels = ReaderUtils.getBooleanJsonField(parser);
                hierarchy.setHasFormalLevels(hasFormatLevels);
                break;
            case StructureUtils.LEVEL:
                hierarchy.setLevel(getLevel(parser));
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Hierarchy: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.HIERARCHIES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<HierarchyImpl> artefacts) {
        artefact.getHierarchies().addAll(artefacts);
    }

    private LevelImpl getLevel(JsonParser parser) throws IOException {
        parser.nextToken();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return null;
        }
        LevelImpl level = new LevelImpl();
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.LEVEL:
                    level.setChild(getLevel(parser));
                    parser.nextToken();
                    break;
                case StructureUtils.CODING_FORMAT:
                    level.setCodeFormat(getCodingFormats(parser));
                    parser.nextToken();
                    break;
                default:
                    nameableReader.read(level, parser);
                    parser.nextToken();
                    break;
            }
        }
        return level;
    }

    private List<CodingFormat> getCodingFormats(JsonParser parser) throws IOException {
        List<CodingFormat> codingFormats = new ArrayList<>();
        parser.nextToken();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return List.of();
        }
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.DATA_TYPE:
                    String dataType = ReaderUtils.getStringJsonField(parser);
                    if (dataType != null) {
                        CodingFormatImpl codingFormat = new CodingFormatImpl();
                        codingFormat.setCodingFormat(new BaseFacetImpl(FacetValueType.fromValue(dataType)));
                        codingFormats.add(codingFormat);
                    }
                    parser.nextToken();
                    break;
                case StructureUtils.IS_SEQUENCE:
                    codingFormats.add(setFacet(FacetType.IS_SEQUENCE, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.INTERVAL:
                    codingFormats.add(setFacet(FacetType.INTERVAL, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.START_VALUE:
                    codingFormats.add(setFacet(FacetType.START_VALUE, parser));
                    break;
                case StructureUtils.END_VALUE:
                    codingFormats.add(setFacet(FacetType.END_VALUE, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.MIN_LENGTH:
                    codingFormats.add(setFacet(FacetType.MIN_LENGTH, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.MAX_LENGTH:
                    codingFormats.add(setFacet(FacetType.MAX_LENGTH, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.MIN_VALUE:
                    codingFormats.add(setFacet(FacetType.MIN_VALUE, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.MAX_VALUE:
                    codingFormats.add(setFacet(FacetType.MAX_VALUE, parser));
                    parser.nextToken();
                    break;
                case StructureUtils.PATTERN:
                    codingFormats.add(setFacet(FacetType.PATTERN, parser));
                    parser.nextToken();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "CodingFormat:" + fieldName);
            }
        }
        return codingFormats;
    }

    private CodingFormatImpl setFacet(FacetType startValue, JsonParser parser) throws IOException {
        BaseFacetImpl baseFacetStartValue = new BaseFacetImpl();
        baseFacetStartValue.setType(startValue);
        baseFacetStartValue.setValue(ReaderUtils.getFieldAsString(parser));
        CodingFormatImpl codingFormat = new CodingFormatImpl();
        codingFormat.setCodingFormat(baseFacetStartValue);
        return codingFormat;
    }

    private HierarchicalCode getHierarchicalCode(JsonParser parser) {
        try {
            HierarchicalCodeImpl hierarchicalCode = new HierarchicalCodeImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.HIERARCHICAL_CODES:
                        parser.nextToken();
                        setHierarchicalCodes(parser, hierarchicalCode);
                        break;
                    case StructureUtils.CODE:
                        String code = ReaderUtils.getStringJsonField(parser);
                        if (code != null) {
                            hierarchicalCode.setCode(new IdentifiableArtefactReferenceImpl(code));
                        }
                        break;
                    case StructureUtils.VALID_TO:
                        Instant validTo = ReaderUtils.getInstantObj(parser);
                        if (validTo != null) {
                            hierarchicalCode.setValidTo(validTo);
                        }
                        break;
                    case StructureUtils.VALID_FROM:
                        Instant validFrom = ReaderUtils.getInstantObj(parser);
                        if (validFrom != null) {
                            hierarchicalCode.setValidFrom(validFrom);
                        }
                        break;
                    case StructureUtils.LEVEL:
                        hierarchicalCode.setLevelId(ReaderUtils.getStringJsonField(parser));
                        break;
                    default:
                        identifiableReader.read(hierarchicalCode, parser);
                        break;
                }
            }
            return hierarchicalCode;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private void setHierarchicalCodes(JsonParser parser, HierarchicalCodeImpl hierarchicalCode) throws IOException {
        List<HierarchicalCode> hierarchicalCodes = ReaderUtils.getArray(parser, this::getHierarchicalCode);
        if (CollectionUtils.isNotEmpty(hierarchicalCodes)) {
            hierarchicalCode.setHierarchicalCodes(hierarchicalCodes);
        }
    }
}
