package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegionImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class MetadataConstraintReader extends MaintainableReader<MetadataConstraintImpl> {

    private final MemberSelectionReader memberSelectionReader;
    private final ReleaseCalendarReader releaseCalendarReader;

    public MetadataConstraintReader(VersionableReader versionableArtefact,
                                    MemberSelectionReader memberSelectionReader,
                                    ReleaseCalendarReader releaseCalendarReader) {
        super(versionableArtefact);
        this.memberSelectionReader = memberSelectionReader;
        this.releaseCalendarReader = releaseCalendarReader;
    }

    @Override
    protected MetadataConstraintImpl createMaintainableArtefact() {
        return new MetadataConstraintImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, MetadataConstraintImpl metadataConstraint) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.ROLE:
                metadataConstraint.setConstraintRoleType(ConstraintRoleType.ALLOWABLE_CONTENT);
                break;
            case StructureUtils.METADATA_TARGET_REGIONS:
                List<MetadataTargetRegion> metadataTargetRegions = ReaderUtils.getArray(parser, (this::getMetadataTargetRegion));
                if (CollectionUtils.isNotEmpty(metadataTargetRegions)) {
                    metadataConstraint.setMetadataTargetRegions(metadataTargetRegions);
                }
                break;
            case StructureUtils.RELEASE_CALENDAR:
                metadataConstraint.setReleaseCalendar(releaseCalendarReader.getReleaseCalendar(parser));
                break;
            case StructureUtils.CONSTRAINT_ATTACHMENT:
                metadataConstraint.setConstrainedArtefacts(getConstraintAttachment(parser));
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MetadataConstraint: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.METADATA_CONSTRAINTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataConstraintImpl> artefacts) {
        artefact.getMetadataConstraints().addAll(artefacts);
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
                case StructureUtils.METADATA_PROVIDER:
                    String metadataProvider = getStringJsonField(parser);
                    if (metadataProvider != null) {
                        artefactReferences.add(new IdentifiableArtefactReferenceImpl(metadataProvider));
                    }
                    parser.nextToken();
                    break;
                case StructureUtils.METADATA_SETS:
                case StructureUtils.METADATA_STRUCTURES:
                case StructureUtils.METADATAFLOWS:
                case StructureUtils.METADATA_PROVISION_AGREEMENTS:
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

    private MetadataTargetRegion getMetadataTargetRegion(JsonParser parser) {
        try {
            MetadataTargetRegionImpl metadataTargetRegion = new MetadataTargetRegionImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.INCLUDE:
                        metadataTargetRegion.setIncluded(ReaderUtils.getBooleanJsonField(parser));
                        break;
                    case StructureUtils.VALID_FROM:
                        metadataTargetRegion.setValidFrom(ReaderUtils.getInstantObj(parser));
                        break;
                    case StructureUtils.VALID_TO:
                        metadataTargetRegion.setValidTo(ReaderUtils.getInstantObj(parser));
                        break;
                    case StructureUtils.COMPONENTS:
                        List<MemberSelection> memberSelections = ReaderUtils.getArray(parser, (this::getMemberSelection));
                        if (CollectionUtils.isNotEmpty(memberSelections)) {
                            metadataTargetRegion.setMemberSelections(memberSelections);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MetadataTargetRegion: " + fieldName);
                }
            }
            return metadataTargetRegion;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private MemberSelection getMemberSelection(JsonParser parser) {
        return memberSelectionReader.getMemberSelection(parser);
    }
}
