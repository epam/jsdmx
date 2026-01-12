package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.writer.StructureUtils.CONSTRAINT_ROLE_STRING;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;


public class DataConstraintWriter extends MaintainableWriter<DataConstraint> {

    private final CubeRegionWriter cubeRegionWriter;
    private final DataKeySetsWriter dataKeySetsWriter;
    private final ReferenceAdapter referenceAdapter;

    public DataConstraintWriter(VersionableWriter versionableWriter,
                                LinksWriter linksWriter,
                                CubeRegionWriter cubeRegionWriter,
                                DataKeySetsWriter dataKeySetsWriter,
                                ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.cubeRegionWriter = cubeRegionWriter;
        this.dataKeySetsWriter = dataKeySetsWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, DataConstraint dataConstraint) throws IOException {
        super.writeFields(jsonGenerator, dataConstraint);
        if (dataConstraint.getConstraintRoleType() != null) {
            jsonGenerator.writeStringField(StructureUtils.ROLE, CONSTRAINT_ROLE_STRING.get(dataConstraint.getConstraintRoleType()));
        }

        List<ArtefactReference> constrainedArtefacts = dataConstraint.getConstrainedArtefacts();
        writeConstrainedArtefacts(jsonGenerator, constrainedArtefacts);

        writeReleaseCalendar(jsonGenerator, dataConstraint.getReleaseCalendar());

        dataKeySetsWriter.write(jsonGenerator, dataConstraint.getDataContentKeys());

        cubeRegionWriter.write(jsonGenerator, dataConstraint.getCubeRegions());
    }

    private void writeConstrainedArtefacts(JsonGenerator jsonGenerator, List<ArtefactReference> constrainedArtefacts) throws IOException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            jsonGenerator.writeFieldName(StructureUtils.CONSTRAINT_ATTACHMENT);
            jsonGenerator.writeStartObject();
            Optional<ArtefactReference> providerRef = StructureUtils.getArtefactReferenceByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.DATA_PROVIDER
            );
            if (providerRef.isPresent()) {
                jsonGenerator.writeStringField(StructureUtils.DATA_PROVIDER, referenceAdapter.toAdaptedUrn(providerRef.get()));
            }

            List<ArtefactReference> dataStructures = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.DATA_STRUCTURE
            );
            StructureUtils.writeArtefactReferences(jsonGenerator, dataStructures, StructureUtils.DATA_STRUCTURES, referenceAdapter);

            List<ArtefactReference> dataFlows = StructureUtils.getArtefactReferencesByStructureClass(constrainedArtefacts, StructureClassImpl.DATAFLOW);
            StructureUtils.writeArtefactReferences(jsonGenerator, dataFlows, StructureUtils.DATAFLOWS, referenceAdapter);

            List<ArtefactReference> provisionAgreements = StructureUtils.getArtefactReferencesByStructureClass(
                constrainedArtefacts,
                StructureClassImpl.PROVISION_AGREEMENT
            );
            StructureUtils.writeArtefactReferences(jsonGenerator, provisionAgreements, StructureUtils.PROVISION_AGREEMENTS, referenceAdapter);

            jsonGenerator.writeEndObject();
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

    @Override
    protected Set<DataConstraint> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataConstraints();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATA_CONSTRAINTS;
    }
}
