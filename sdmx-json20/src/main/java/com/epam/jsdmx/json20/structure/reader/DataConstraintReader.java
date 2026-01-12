package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;
import static com.epam.jsdmx.json20.structure.writer.StructureUtils.STRING_CONSTRAINT_ROLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class DataConstraintReader extends MaintainableReader<DataConstraintImpl> {

    private final ReleaseCalendarReader releaseCalendarReader;
    private final DataKeySetReader dataKeySetReader;
    private final CubeRegionReader cubeRegionReader;

    public DataConstraintReader(VersionableReader versionableArtefact,
                                ReleaseCalendarReader releaseCalendarReader,
                                DataKeySetReader dataKeySetReader,
                                CubeRegionReader cubeRegionReader) {
        super(versionableArtefact);
        this.releaseCalendarReader = releaseCalendarReader;
        this.dataKeySetReader = dataKeySetReader;
        this.cubeRegionReader = cubeRegionReader;
    }

    @Override
    protected DataConstraintImpl createMaintainableArtefact() {
        return new DataConstraintImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, DataConstraintImpl dataConstraint) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.ROLE:
                dataConstraint.setConstraintRoleType(STRING_CONSTRAINT_ROLE.get(ReaderUtils.getFieldAsString(parser)));
                break;
            case StructureUtils.RELEASE_CALENDAR:
                dataConstraint.setReleaseCalendar(releaseCalendarReader.getReleaseCalendar(parser));
                break;
            case StructureUtils.CONSTRAINT_ATTACHMENT:
                dataConstraint.setConstrainedArtefacts(getConstraintAttachment(parser));
                break;
            case StructureUtils.DATA_KEY_SETS:
                List<DataKeySet> dataKeySets = ReaderUtils.getArray(parser, (dataKeySetReader::getDataKeySet));
                if (CollectionUtils.isNotEmpty(dataKeySets)) {
                    dataConstraint.setDataContentKeys(dataKeySets);
                }
                break;
            case StructureUtils.CUBE_REGIONS:
                List<CubeRegion> cubeRegions = ReaderUtils.getArray(parser, (cubeRegionReader::getCubeRegion));
                if (CollectionUtils.isNotEmpty(cubeRegions)) {
                    dataConstraint.setCubeRegions(cubeRegions);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "DataConstraint: " + fieldName);
        }
    }

    private List<ArtefactReference> getConstraintAttachment(JsonParser parser) throws IOException {
        parser.nextToken();
        List<ArtefactReference> artefactReferences = new ArrayList<>();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return Collections.emptyList();
        }
        while (parser.currentToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.DATA_PROVIDER:
                    String metadataProvider = getStringJsonField(parser);
                    if (metadataProvider != null) {
                        artefactReferences.add(new IdentifiableArtefactReferenceImpl(metadataProvider));
                    }
                    parser.nextToken();
                    break;
                case StructureUtils.DATA_STRUCTURES:
                case StructureUtils.DATAFLOWS:
                case StructureUtils.PROVISION_AGREEMENTS:
                    List<String> metadataSets = ReaderUtils.getListStrings(parser);
                    List<IdentifiableArtefactReferenceImpl> references = metadataSets.stream()
                        .filter(Objects::nonNull)
                        .map(IdentifiableArtefactReferenceImpl::new)
                        .collect(Collectors.toList());
                    artefactReferences.addAll(references);
                    parser.nextToken();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " ConstraintAttachment: " + fieldName);
            }
        }
        return artefactReferences;
    }

    @Override
    protected String getName() {
        return StructureUtils.DATA_CONSTRAINTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataConstraintImpl> artefacts) {
        artefact.getDataConstraints().addAll(artefacts);
    }
}
