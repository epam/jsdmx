package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.ConstraintRoleType;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

import org.apache.commons.collections.CollectionUtils;

public class DataConstraintWriter extends XmlWriter<DataConstraint> {

    private final ReleaseCalenderWriter releaseCalenderWriter;
    private final MemberSelectionWriter memberSelectionWriter;

    public DataConstraintWriter(NameableWriter nameableWriter,
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
    protected void writeAttributes(DataConstraint artefact, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(artefact, writer);
        ConstraintRoleType constraintRoleType = artefact.getConstraintRoleType();
        if (constraintRoleType != null) {
            writer.writeAttribute(XmlConstants.ROLE_ATTR, XmlConstants.CONSTRAINT_ROLE_STRING.get(constraintRoleType));
        }
    }

    private void writeDataConstraints(XMLStreamWriter writer, DataConstraint artefact) throws XMLStreamException {

        List<ArtefactReference> constrainedArtefacts = artefact.getConstrainedArtefacts();
        writeConstrainedArtefacts(writer, constrainedArtefacts);

        ReleaseCalendar releaseCalendar = artefact.getReleaseCalendar();
        releaseCalenderWriter.writeReleaseCalendar(writer, releaseCalendar);

        List<DataKeySet> dataKeySets = artefact.getDataContentKeys();
        writeDataKeySets(dataKeySets, writer);

        List<CubeRegion> cubeRegions = artefact.getCubeRegions();
        if (CollectionUtils.isNotEmpty(cubeRegions)) {
            for (CubeRegion cubeRegion : cubeRegions) {
                if (cubeRegion != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CUBE_REGION);
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(cubeRegion.isIncluded()));
                    annotableWriter.write(cubeRegion, writer);
                    List<MemberSelection> memberSelections = cubeRegion.getMemberSelections();
                    List<CubeRegionKey> cubeRegionKeys = cubeRegion.getCubeRegionKeys();
                    writeCubeRegionKeys(cubeRegionKeys, writer);
                    memberSelectionWriter.writeMemberSelections(writer, memberSelections);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeCubeRegionKeys(List<CubeRegionKey> cubeRegionKeys, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(cubeRegionKeys)) {
            for (CubeRegionKey cubeRegionKey : cubeRegionKeys) {
                if (cubeRegionKey != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.KEY_VALUE);
                    writer.writeAttribute(XmlConstants.ID, cubeRegionKey.getComponentId());
                    XmlWriterUtils.writeInstant(cubeRegionKey.getValidTo(), writer, XmlConstants.VALID_TO);
                    XmlWriterUtils.writeInstant(cubeRegionKey.getValidFrom(), writer, XmlConstants.VALID_FROM);
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(cubeRegionKey.isIncluded()));
                    writer.writeAttribute(XmlConstants.REMOVE_PREFIX, String.valueOf(cubeRegionKey.isRemovePrefix()));

                    memberSelectionWriter.writeSelectionValues(writer, cubeRegionKey.getSelectionValues());
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeConstrainedArtefacts(XMLStreamWriter writer, List<ArtefactReference> constrainedArtefacts) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(constrainedArtefacts)) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CONSTRAINT_ATTACHMENT);
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
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.DATA_PROVIDER, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.DATA_STRUCTURE.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.DATA_STRUCTURE, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.DATAFLOW.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.DATAFLOW, constrained.getUrn());
            }
            if (simpleName.equals(StructureClassImpl.PROVISION_AGREEMENT.getSimpleName())) {
                XmlWriterUtils.writeArtefactReference(writer, XmlConstants.PROVISION_AGREEMENT, constrained.getUrn());
            }
        }
    }

    private void writeDataKeySets(List<DataKeySet> dataKeySets, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(dataKeySets)) {
            for (DataKeySet dataKeySet : dataKeySets) {
                if (dataKeySet != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATA_KEY_SET);
                    writer.writeAttribute(XmlConstants.IS_INCLUDED, String.valueOf(dataKeySet.isIncluded()));
                    List<DataKey> keys = dataKeySet.getKeys();
                    writeDataKeys(keys, writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeDataKeys(List<DataKey> keys, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(keys)) {
            for (DataKey dataKey : keys) {
                if (dataKey != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.KEY);
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(dataKey.isIncluded()));
                    XmlWriterUtils.writeInstant(dataKey.getValidTo(), writer, XmlConstants.VALID_TO);
                    XmlWriterUtils.writeInstant(dataKey.getValidFrom(), writer, XmlConstants.VALID_FROM);

                    annotableWriter.write(dataKey, writer);
                    List<ComponentValue> keyValues = dataKey.getKeyValues();
                    writeKeyValues(keyValues, writer);
                    List<MemberSelection> memberSelections = dataKey.getMemberSelections();
                    memberSelectionWriter.writeMemberSelections(writer, memberSelections);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeKeyValues(List<ComponentValue> keyValues, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(keyValues)) {
            for (ComponentValue componentValue : keyValues) {
                if (componentValue != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.KEY_VALUE);

                    writer.writeAttribute(XmlConstants.ID, componentValue.getComponentId());
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(componentValue.isIncluded()));
                    writer.writeAttribute(XmlConstants.REMOVE_PREFIX, String.valueOf(componentValue.isRemovePrefix()));

                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.VALUE);
                    XmlWriterUtils.writeCharacters(componentValue.getValue(), writer);
                    writer.writeEndElement();

                    writer.writeEndElement();
                }
            }
        }
    }

    @Override
    protected void writeCustomAttributeElements(DataConstraint artefact, XMLStreamWriter writer) throws XMLStreamException {
        writeDataConstraints(writer, artefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATA_CONSTRAINT;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.DATA_CONSTRAINTS;
    }

    @Override
    protected Set<DataConstraint> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataConstraints();
    }
}
