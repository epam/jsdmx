package com.epam.jsdmx.xml21.structure.writer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Constraint;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import org.apache.commons.collections4.CollectionUtils;

public class ContentConstraintWriter extends XmlWriter<Constraint> {

    private final ReferenceWriter referenceWriter;
    private final ReleaseCalenderWriter releaseCalenderWriter;
    private final DataKeySetsWriter dataKeySetsWriter;
    private final CubeRegionWriter cubeRegionWriter;
    private final MetadataConstraintWriter metadataConstraintWriter;

    protected ContentConstraintWriter(NameableWriter nameableWriter,
                                      AnnotableWriter annotableWriter,
                                      CommonAttributesWriter commonAttributesWriter,
                                      ReferenceWriter referenceWriter,
                                      ReleaseCalenderWriter releaseCalenderWriter,
                                      DataKeySetsWriter dataKeySetsWriter,
                                      CubeRegionWriter cubeRegionWriter,
                                      MetadataConstraintWriter metadataConstraintWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.referenceWriter = referenceWriter;
        this.releaseCalenderWriter = releaseCalenderWriter;
        this.dataKeySetsWriter = dataKeySetsWriter;
        this.cubeRegionWriter = cubeRegionWriter;
        this.metadataConstraintWriter = metadataConstraintWriter;
    }

    @Override
    protected void writeAttributes(Constraint artefact, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(artefact, writer);
        ConstraintRoleType constraintRoleType = artefact.getConstraintRoleType();
        if (constraintRoleType != null) {
            writer.writeAttribute(XmlConstants.ROLE_ATTR, XmlConstants.CONSTRAINT_ROLE_STRING.get(constraintRoleType));
        }
    }

    @Override
    protected void writeCustomAttributeElements(Constraint artefact, XMLStreamWriter writer) throws XMLStreamException {
        List<ArtefactReference> constrainedArtefacts = artefact.getConstrainedArtefacts();
        writeConstrainedArtefacts(writer, constrainedArtefacts);

        if (artefact instanceof DataConstraint) {
            DataConstraint constraint = (DataConstraint) artefact;
            releaseCalenderWriter.writeReleaseCalendar(writer, constraint.getReleaseCalendar());
            dataKeySetsWriter.writeDataKeySets(writer, constraint.getDataContentKeys());
            cubeRegionWriter.writeCubeRegions(writer, constraint.getCubeRegions());
        } else {
            MetadataConstraint constraint = (MetadataConstraint) artefact;
            releaseCalenderWriter.writeReleaseCalendar(writer, constraint.getReleaseCalendar());
            metadataConstraintWriter.writeMetadataTargetRegions(writer, constraint.getMetadataTargetRegions());
        }
    }

    private void writeConstrainedArtefacts(XMLStreamWriter writer, List<ArtefactReference> constrainedArtefacts) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.CONSTRAINT_ATTACHMENT);
            for (ArtefactReference constrained : constrainedArtefacts) {
                writeArtefactReference(writer, constrained);
            }
            writer.writeEndElement();
        }
    }

    private void writeArtefactReference(XMLStreamWriter writer, ArtefactReference constrained) throws XMLStreamException {
        if (constrained != null) {
            StructureClass maintainableStructureClass = constrained.getMaintainableStructureClass();
            String simpleName = maintainableStructureClass.getSimpleName();
            if (simpleName.equals(StructureClassImpl.DATA_PROVIDER.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.DATA_PROVIDER);
                referenceWriter.writeObjectReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.DATA_STRUCTURE.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.DATA_STRUCTURE);
                referenceWriter.writeDataStructureReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.DATAFLOW.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.DATAFLOW);
                referenceWriter.writeDataflowReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.METADATA_STRUCTURE.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATA_STRUCTURE);
                referenceWriter.writeMetaDataStructureReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.METADATAFLOW.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATAFLOW);
                referenceWriter.writeMetaDataflowReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.PROVISION_AGREEMENT.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.PROVISION_AGREEMENT);
                referenceWriter.writeProvisionAgreementReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.METADATA_PROVIDER.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATA_PROVIDER);
                referenceWriter.writeObjectReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.METADATA_SET.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATA_SET);
                referenceWriter.writeMetadataSetReference(writer, constrained);
                writer.writeEndElement();
            }
            if (simpleName.equals(StructureClassImpl.METADATA_PROVISION_AGREEMENT.getSimpleName())) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATA_PROVISION_AGREEMENT);
                referenceWriter.writeMetaProvisionAgreementReference(writer, constrained);
                writer.writeEndElement();
            }
        }
    }

    @Override
    protected String getName(Constraint artefact) {
        return XmlConstants.CONTENT_CONSTRAINT;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CONSTRAINTS;
    }

    @Override
    protected Set<Constraint> extractArtefacts(Artefacts artefacts) {
        var constraints = new HashSet<Constraint>();
        constraints.addAll(artefacts.getDataConstraints());
        constraints.addAll(artefacts.getMetadataConstraints());
        return constraints;
    }
}
