package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;

import org.apache.commons.collections4.CollectionUtils;

public class MetadataConstraintWriter {

    private final MemberSelectionWriter memberSelectionWriter;

    public MetadataConstraintWriter(MemberSelectionWriter memberSelectionWriter) {
        this.memberSelectionWriter = memberSelectionWriter;
    }

    public void writeMetadataTargetRegions(XMLStreamWriter writer, List<MetadataTargetRegion> metadataTargetRegions) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(metadataTargetRegions)) {
            for (MetadataTargetRegion metadataTargetRegion : metadataTargetRegions) {
                if (metadataTargetRegion != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.METADATA_TARGET_REGION);
                    boolean included = metadataTargetRegion.isIncluded();
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(included));
                    writer.writeAttribute(XmlConstants.REPORT, "skipped");
                    writer.writeAttribute(XmlConstants.METADATA_TARGET, "skipped");
                    List<MemberSelection> memberSelections = metadataTargetRegion.getMemberSelections();
                    memberSelectionWriter.writeMemberSelections(writer, memberSelections);

                    writer.writeEndElement();
                }
            }
        }
    }
}
