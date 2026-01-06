package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class MetadataConstraintWriter extends MaintainableWriter<MetadataConstraint> {

    private final MemberSelectionWriter memberSelectionWriter;
    private final ReferenceAdapter referenceAdapter;

    public MetadataConstraintWriter(VersionableWriter versionableWriter,
                                    LinksWriter linksWriter,
                                    MemberSelectionWriter memberSelectionWriter,
                                    ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.memberSelectionWriter = memberSelectionWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, MetadataConstraint metadataConstraint) throws IOException {
        super.writeFields(jsonGenerator, metadataConstraint);
        jsonGenerator.writeStringField(StructureUtils.ROLE, "Allowed");

        List<MetadataTargetRegion> metadataTargetRegions = metadataConstraint.getMetadataTargetRegions();
        writeMetadataTargetRegions(jsonGenerator, metadataTargetRegions);

        ReleaseCalendar releaseCalendar = metadataConstraint.getReleaseCalendar();
        writeReleaseCalendar(jsonGenerator, releaseCalendar);

        List<ArtefactReference> constrainedArtefacts = metadataConstraint.getConstrainedArtefacts();
        writeConstrainedArtefacts(jsonGenerator, constrainedArtefacts);

    }

    @Override
    protected Set<MetadataConstraint> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataConstraints();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.METADATA_CONSTRAINTS;
    }

    private void writeConstrainedArtefacts(JsonGenerator jsonGenerator, List<ArtefactReference> constrainedArtefacts) throws IOException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            jsonGenerator.writeFieldName(StructureUtils.CONSTRAINT_ATTACHMENT);
            jsonGenerator.writeStartObject();
            Optional<ArtefactReference> providerRef = StructureUtils.getArtefactReferenceByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.METADATA_PROVIDER
            );
            if (providerRef.isPresent()) {
                jsonGenerator.writeStringField(StructureUtils.METADATA_PROVIDER, referenceAdapter.toAdaptedUrn(providerRef.get()));
            }

            List<ArtefactReference> metadataSets = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.METADATA_SET);
            StructureUtils.writeArtefactReferences(jsonGenerator, metadataSets, StructureUtils.METADATA_SETS, referenceAdapter);

            List<ArtefactReference> metadataStructures = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.METADATA_STRUCTURE
            );
            StructureUtils.writeArtefactReferences(jsonGenerator, metadataStructures, StructureUtils.METADATA_STRUCTURES, referenceAdapter);

            List<ArtefactReference> metadataflows = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.METADATAFLOW);
            StructureUtils.writeArtefactReferences(jsonGenerator, metadataflows, StructureUtils.METADATAFLOWS, referenceAdapter);

            List<ArtefactReference> metadataProvisionAgreements = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.METADATA_PROVISION_AGREEMENT
            );
            StructureUtils.writeArtefactReferences(jsonGenerator, metadataProvisionAgreements, StructureUtils.METADATA_PROVISION_AGREEMENTS, referenceAdapter);

            jsonGenerator.writeEndObject();
        }
    }

    private void writeMetadataTargetRegions(JsonGenerator jsonGenerator, List<MetadataTargetRegion> metadataTargetRegions) throws IOException {
        if (CollectionUtils.isNotEmpty(metadataTargetRegions)) {
            jsonGenerator.writeFieldName(StructureUtils.METADATA_TARGET_REGIONS);
            jsonGenerator.writeStartArray();
            for (MetadataTargetRegion metadataTargetRegion : metadataTargetRegions) {
                if (metadataTargetRegion != null) {
                    jsonGenerator.writeStartObject();

                    jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, metadataTargetRegion.isIncluded());

                    Instant validFrom = metadataTargetRegion.getValidFrom();
                    if (validFrom != null) {
                        jsonGenerator.writeStringField(StructureUtils.VALID_FROM, StructureUtils.mapInstantToString(validFrom));
                    }

                    Instant validTo = metadataTargetRegion.getValidTo();
                    if (validTo != null) {
                        jsonGenerator.writeStringField(StructureUtils.VALID_TO, StructureUtils.mapInstantToString(validTo));
                    }

                    List<MemberSelection> memberSelections = metadataTargetRegion.getMemberSelections();
                    memberSelectionWriter.writeMemberSelections(jsonGenerator, memberSelections);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeReleaseCalendar(JsonGenerator jsonGenerator, ReleaseCalendar releaseCalendar) throws IOException {
        if (releaseCalendar != null) {
            jsonGenerator.writeFieldName(StructureUtils.RELEASE_CALENDAR);
            jsonGenerator.writeStartObject();

            String tolerance = releaseCalendar.getTolerance();
            if (tolerance != null) {
                jsonGenerator.writeStringField(StructureUtils.TOLERANCE, tolerance);
            }

            String offset = releaseCalendar.getOffset();
            if (offset != null) {
                jsonGenerator.writeStringField(StructureUtils.OFFSET, offset);
            }

            String periodicity = releaseCalendar.getPeriodicity();
            if (periodicity != null) {
                jsonGenerator.writeStringField(StructureUtils.PERIODICITY, periodicity);
            }
            jsonGenerator.writeEndObject();
        }
    }
}
