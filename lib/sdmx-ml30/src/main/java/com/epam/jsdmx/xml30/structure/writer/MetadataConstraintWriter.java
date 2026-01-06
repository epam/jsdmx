package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import org.apache.commons.collections.CollectionUtils;

public class MetadataConstraintWriter extends XmlWriter<MetadataConstraint> {

    private final ReleaseCalenderWriter releaseCalenderWriter;
    private final MemberSelectionWriter memberSelectionWriter;

    public MetadataConstraintWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    LinksWriter linksWriter,
                                    ReleaseCalenderWriter releaseCalenderWriter,
                                    MemberSelectionWriter memberSelectionWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.releaseCalenderWriter = releaseCalenderWriter;
        this.memberSelectionWriter = memberSelectionWriter;
    }

    @Override
    protected void writeAttributes(MetadataConstraint metadataConstraint, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(metadataConstraint, writer);
        writer.writeAttribute(XmlConstants.ROLE_ATTR, XmlConstants.ALLOWED);
    }

    @Override
    protected void writeCustomAttributeElements(MetadataConstraint metadataConstraint, XMLStreamWriter writer) throws XMLStreamException {

        List<ArtefactReference> constrainedArtefacts = metadataConstraint.getConstrainedArtefacts();
        writeConstrainedArtefacts(writer, constrainedArtefacts);

        ReleaseCalendar releaseCalendar = metadataConstraint.getReleaseCalendar();
        releaseCalenderWriter.writeReleaseCalendar(writer, releaseCalendar);

        List<MetadataTargetRegion> metadataTargetRegions = metadataConstraint.getMetadataTargetRegions();
        writeMetadataTargetRegions(writer, metadataTargetRegions);
    }

    private void writeConstrainedArtefacts(XMLStreamWriter writer, List<ArtefactReference> constrainedArtefacts) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CONSTRAINT_ATTACHMENT);
            for (ArtefactReference constrained : constrainedArtefacts) {
                writeConstrainedArtefact(writer, constrained);
            }
            writer.writeEndElement();
        }
    }

    private void writeConstrainedArtefact(XMLStreamWriter writer, ArtefactReference constrained) throws XMLStreamException {
        if (constrained != null) {
            StructureClass maintainableStructureClass = constrained.getMaintainableStructureClass();
            String simpleName = maintainableStructureClass.getSimpleName();
            if (simpleName.equals(StructureClassImpl.METADATA_PROVIDER.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.METADATA_PROVIDER, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.METADATA_SET.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.METADATA_SET, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.METADATA_STRUCTURE.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.METADATA_STRUCTURE, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.METADATAFLOW.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.METADATAFLOW, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.METADATA_PROVISION_AGREEMENT.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.METADATA_PROVISION_AGREEMENT, constrained.getUrn());
            }
        }
    }

    private void writeMetadataTargetRegions(XMLStreamWriter writer, List<MetadataTargetRegion> metadataTargetRegions) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(metadataTargetRegions)) {
            for (MetadataTargetRegion metadataTargetRegion : metadataTargetRegions) {
                if (metadataTargetRegion != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_TARGET_REGION);

                    boolean included = metadataTargetRegion.isIncluded();
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(included));
                    XmlWriterUtils.writeInstant(metadataTargetRegion.getValidTo(), writer, XmlConstants.VALID_TO);
                    XmlWriterUtils.writeInstant(metadataTargetRegion.getValidFrom(), writer, XmlConstants.VALID_FROM);
                    List<MemberSelection> memberSelections = metadataTargetRegion.getMemberSelections();
                    memberSelectionWriter.writeMemberSelections(writer, memberSelections);

                    writer.writeEndElement();
                }
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_CONSTRAINT;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.METADATA_CONSTRAINTS;
    }

    @Override
    protected Set<MetadataConstraint> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataConstraints();
    }
}
