package com.epam.jsdmx.json10.structure.writer;

import static com.epam.jsdmx.json10.structure.writer.StructureUtils.CONSTRAINT_ROLE_STRING;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Constraint;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class ContentConstraintWriter extends MaintainableWriter<Constraint> {

    private final ReferenceAdapter referenceAdapter;
    private final DataKeySetsWriter dataKeySetsWriter;
    private final CubeRegionWriter cubeRegionWriter;
    private final MetadataConstraintWriter metadataConstraintWriter;

    public ContentConstraintWriter(VersionableWriter versionableWriter,
                                   LinksWriter linksWriter,
                                   ReferenceAdapter referenceAdapter,
                                   DataKeySetsWriter dataKeySetsWriter,
                                   CubeRegionWriter cubeRegionWriter,
                                   MetadataConstraintWriter metadataConstraintWriter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
        this.dataKeySetsWriter = dataKeySetsWriter;
        this.cubeRegionWriter = cubeRegionWriter;
        this.metadataConstraintWriter = metadataConstraintWriter;
    }

    @Override
    protected void writeFields(JsonGenerator gen, Constraint constraint) throws IOException {
        super.writeFields(gen, constraint);
        writeConstrainedArtefacts(gen, constraint.getConstrainedArtefacts());
        if (constraint.getConstraintRoleType() != null) {
            String constraintRoleType = CONSTRAINT_ROLE_STRING.get(constraint.getConstraintRoleType());
            gen.writeStringField(StructureUtils.ROLE, constraintRoleType);
        }

        if (constraint instanceof DataConstraint) {
            DataConstraint dataConstraint = (DataConstraint) constraint;
            writeReleaseCalendar(gen, dataConstraint.getReleaseCalendar());
            if (CollectionUtils.isNotEmpty(dataConstraint.getCubeRegions())) {
                cubeRegionWriter.write(gen, dataConstraint.getCubeRegions());
            }
            if (CollectionUtils.isNotEmpty(dataConstraint.getDataContentKeys())) {
                dataKeySetsWriter.write(gen, dataConstraint.getDataContentKeys());
            }
        } else {
            MetadataConstraint metadataConstraint = (MetadataConstraint) constraint;
            writeReleaseCalendar(gen, metadataConstraint.getReleaseCalendar());
            metadataConstraintWriter.writeMetadataTargetRegions(gen, metadataConstraint.getMetadataTargetRegions());
        }
    }

    private void writeConstrainedArtefacts(JsonGenerator gen, List<ArtefactReference> constrainedArtefacts) throws IOException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            gen.writeFieldName(StructureUtils.CONSTRAINT_ATTACHMENT);
            gen.writeStartObject();
            List<ArtefactReference> providerRef = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.DATA_PROVIDER);
            if (providerRef.size() != 0) {
                gen.writeStringField(StructureUtils.DATA_PROVIDER, referenceAdapter.toAdaptedUrn(providerRef.get(0)));
            }

            List<ArtefactReference> dataStructures = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.DATA_STRUCTURE
            );
            StructureUtils.writeArtefactReferences(gen, dataStructures, StructureUtils.DATA_STRUCTURES, referenceAdapter);
            List<ArtefactReference> dataFlows = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.DATAFLOW);
            StructureUtils.writeArtefactReferences(gen, dataFlows, StructureUtils.DATAFLOWS, referenceAdapter);

            List<ArtefactReference> metadataStructures = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.METADATA_STRUCTURE
            );
            StructureUtils.writeArtefactReferences(gen, metadataStructures, StructureUtils.METADATA_STRUCTURES, referenceAdapter);
            List<ArtefactReference> metadataFlows = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.METADATAFLOW);
            StructureUtils.writeArtefactReferences(gen, metadataFlows, StructureUtils.METADATAFLOWS, referenceAdapter);
            List<ArtefactReference> metadataSets = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.METADATA_SET);
            StructureUtils.writeArtefactReferences(gen, metadataSets, StructureUtils.METADATA_SETS, referenceAdapter);

            List<ArtefactReference> provisionAgreements = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.PROVISION_AGREEMENT
            );
            StructureUtils.writeArtefactReferences(gen, provisionAgreements, StructureUtils.PROVISION_AGREEMENTS, referenceAdapter);
            gen.writeEndObject();
        }
    }

    private void writeReleaseCalendar(JsonGenerator gen, ReleaseCalendar releaseCalendar) throws IOException {
        if (releaseCalendar != null) {
            gen.writeFieldName(StructureUtils.RELEASE_CALENDAR);
            gen.writeStartObject();
            String tolerance = releaseCalendar.getTolerance();
            if (tolerance != null) {
                gen.writeStringField(StructureUtils.TOLERANCE, tolerance);
            }
            String offset = releaseCalendar.getOffset();
            if (offset != null) {
                gen.writeStringField(StructureUtils.OFFSET, offset);
            }
            String periodicity = releaseCalendar.getPeriodicity();
            if (periodicity != null) {
                gen.writeStringField(StructureUtils.PERIODICITY, periodicity);
            }
            gen.writeEndObject();
        }
    }

    @Override
    protected Set<Constraint> extractArtefacts(Artefacts artefacts) {
        var constraints = new HashSet<Constraint>();
        constraints.addAll(artefacts.getDataConstraints());
        constraints.addAll(artefacts.getMetadataConstraints());
        return constraints;
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CONTENT_CONSTRAINT;
    }

}
