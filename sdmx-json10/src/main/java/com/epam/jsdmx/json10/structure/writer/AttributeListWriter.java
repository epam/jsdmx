package com.epam.jsdmx.json10.structure.writer;

import static com.epam.jsdmx.json10.structure.writer.StructureUtils.writeStringField;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationship;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationship;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationship;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

@EqualsAndHashCode(callSuper = false)
public class AttributeListWriter {

    private final IdentifiableWriter identifiableWriter;
    private final ConceptRoleWriter conceptRoleWriter;
    private final ComponentWriter componentWriter;

    public AttributeListWriter(IdentifiableWriter identifiableWriter,
                               ConceptRoleWriter conceptRoleWriter,
                               ComponentWriter componentWriter) {
        this.identifiableWriter = identifiableWriter;
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    public void write(JsonGenerator jsonGenerator, AttributeDescriptor attributeDescriptor) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.ATTRIBUTE_LIST);
        if (attributeDescriptor == null || attributeDescriptor.getComponents().isEmpty()) {
            jsonGenerator.writeNull();
            return;
        }

        AttributeDescriptorImpl attributeDescriptorImpl = (AttributeDescriptorImpl) attributeDescriptor;
        attributeDescriptorImpl.setId(StructureUtils.ATTRIBUTE_DESCRIPTOR_ID);

        jsonGenerator.writeStartObject();
        identifiableWriter.write(jsonGenerator, attributeDescriptorImpl);

        writeDataAttributes(jsonGenerator, attributeDescriptorImpl.getComponents());

        jsonGenerator.writeEndObject();
    }

    private void writeDataAttributes(JsonGenerator jsonGenerator, List<DataAttribute> attributeDescriptorComponents) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.ATTRIBUTES);
        jsonGenerator.writeStartArray();
        if (attributeDescriptorComponents != null && !attributeDescriptorComponents.isEmpty()) {
            for (DataAttribute dataAttribute : attributeDescriptorComponents) {
                if (dataAttribute != null) {
                    jsonGenerator.writeStartObject();
                    componentWriter.write(jsonGenerator, dataAttribute);
                    writeAssignmentStatus(jsonGenerator, dataAttribute.isMandatory());
                    conceptRoleWriter.write(jsonGenerator, dataAttribute.getConceptRoles());
                    writeAttributeRelationship(jsonGenerator, dataAttribute.getAttributeRelationship());
                    jsonGenerator.writeEndObject();
                }
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeAssignmentStatus(JsonGenerator jg, boolean mandatory) {
        writeStringField(jg, "assignmentStatus", mandatory ? "Mandatory" : "Conditional");
    }

    private void writeAttributeRelationship(JsonGenerator jg, AttributeRelationship attributeRelationship) throws IOException {
        jg.writeFieldName(StructureUtils.ATTRIBUTE_RELATIONSHIP);
        jg.writeStartObject();
        if (attributeRelationship instanceof DimensionRelationship) {
            DimensionRelationship dimensionRelationship = (DimensionRelationship) attributeRelationship;
            jg.writeFieldName(StructureUtils.DIMENSIONS);
            List<String> dimensions = dimensionRelationship.getDimensions();
            jg.writeStartArray();
            for (String dimension : dimensions) {
                jg.writeString(dimension);
            }
            jg.writeEndArray();

            List<String> groupKeys = dimensionRelationship.getGroupKeys();
            if (CollectionUtils.isNotEmpty(groupKeys)) {
                jg.writeFieldName(StructureUtils.ATTACHMENT_GROUPS);
                jg.writeStartArray();
                for (String groupKey : groupKeys) {
                    jg.writeString(groupKey);
                }
                jg.writeEndArray();
            }

        } else if (attributeRelationship instanceof ObservationRelationship) {
            writeStringField(jg, "primaryMeasure", "OBS_VALUE");
        } else if (attributeRelationship instanceof GroupRelationship) {
            GroupRelationship groupRelationship = (GroupRelationship) attributeRelationship;
            jg.writeStringField(StructureUtils.GROUP, groupRelationship.getGroupKey());
        } else {
            jg.writeFieldName(StructureUtils.NONE);
            jg.writeStartObject();
            jg.writeEndObject();
        }
        jg.writeEndObject();
    }

}
