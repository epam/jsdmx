package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;

import org.apache.commons.collections4.CollectionUtils;

public class CubeRegionWriter {
    private final AnnotableWriter annotableWriter;
    private final MemberSelectionWriter memberSelectionWriter;

    public CubeRegionWriter(AnnotableWriter annotableWriter, MemberSelectionWriter memberSelectionWriter) {
        this.annotableWriter = annotableWriter;
        this.memberSelectionWriter = memberSelectionWriter;
    }

    public void writeCubeRegions(XMLStreamWriter writer, List<CubeRegion> cubeRegions) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(cubeRegions)) {
            for (CubeRegion cubeRegion : cubeRegions) {
                if (cubeRegion != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.CUBE_REGION);
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
                    writer.writeStartElement(XmlConstants.COMMON + XmlConstants.KEY_VALUE);
                    writer.writeAttribute(XmlConstants.ID, cubeRegionKey.getComponentId());
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(cubeRegionKey.isIncluded()));
                    memberSelectionWriter.writeSelectionValues(writer, cubeRegionKey.getSelectionValues());
                    writer.writeEndElement();
                }
            }
        }
    }


}
