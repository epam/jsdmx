package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationship;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationship;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationship;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationship;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class AttributeListWriter extends IdentifiableWriter {

    private final ConceptRoleWriter conceptRoleWriter;
    private final ComponentWriter componentWriter;

    public AttributeListWriter(AnnotableWriter annotableWriter, ConceptRoleWriter conceptRoleWriter, ComponentWriter componentWriter) {
        super(annotableWriter);
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    @Override
    public void write(JsonGenerator jsonGenerator, IdentifiableArtefact identifiableArtefact) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.ATTRIBUTE_LIST);
        if (identifiableArtefact == null) {
            jsonGenerator.writeNull();
            return;
        }
        AttributeDescriptorImpl attributeDescriptor = (AttributeDescriptorImpl) identifiableArtefact;
        attributeDescriptor.setId(StructureUtils.ATTRIBUTE_DESCRIPTOR_ID);
        jsonGenerator.writeStartObject();
        super.write(jsonGenerator, attributeDescriptor);
        List<DataAttribute> attributeDescriptorComponents = attributeDescriptor.getComponents();
        writeDataAttributeUsage(jsonGenerator, attributeDescriptor.getMetadataAttributes());
        writeDataAttributes(jsonGenerator, attributeDescriptorComponents);
        jsonGenerator.writeEndObject();
    }

    private void writeDataAttributeUsage(JsonGenerator jsonGenerator, List<MetadataAttributeRef> metadata) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.METADATA_ATTRIBUTE_USAGES);
        jsonGenerator.writeStartArray();
        if (metadata != null && !metadata.isEmpty()) {
            for (MetadataAttributeRef attributeRef : metadata) {
                if (attributeRef != null) {
                    jsonGenerator.writeStartObject();
                    super.getAnnotableWriter()
                        .write(jsonGenerator, attributeRef);
                    jsonGenerator.writeStringField(StructureUtils.METADATA_ATTRIBUTE_REFERENCE, attributeRef.getId());
                    writeAttributeRelationship(jsonGenerator, attributeRef.getMetadataRelationship());
                    jsonGenerator.writeEndObject();
                }
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeDataAttributes(JsonGenerator jsonGenerator, List<DataAttribute> attributeDescriptorComponents) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.ATTRIBUTES);
        jsonGenerator.writeStartArray();
        if (attributeDescriptorComponents != null && !attributeDescriptorComponents.isEmpty()) {
            for (DataAttribute dataAttribute : attributeDescriptorComponents) {
                if (dataAttribute != null) {
                    jsonGenerator.writeStartObject();
                    componentWriter.write(jsonGenerator, dataAttribute);
                    jsonGenerator.writeBooleanField(StructureUtils.IS_MANDATORY, dataAttribute.isMandatory());
                    conceptRoleWriter.write(jsonGenerator, dataAttribute.getConceptRoles());
                    writeAttributeRelationship(jsonGenerator, dataAttribute.getAttributeRelationship());
                    writeMeasureRelationship(jsonGenerator, dataAttribute.getMeasureRelationship());
                    jsonGenerator.writeEndObject();
                }
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeAttributeRelationship(JsonGenerator jsonGenerator, AttributeRelationship attributeRelationship) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.ATTRIBUTE_RELATIONSHIP);
        jsonGenerator.writeStartObject();
        if (attributeRelationship instanceof DimensionRelationship) {
            DimensionRelationship dimensionRelationship = (DimensionRelationship) attributeRelationship;
            jsonGenerator.writeFieldName(StructureUtils.DIMENSIONS);
            List<String> dimensions = dimensionRelationship.getDimensions();
            jsonGenerator.writeStartArray();
            for (String dimension : dimensions) {
                jsonGenerator.writeString(dimension);
            }
            jsonGenerator.writeEndArray();

        } else if (attributeRelationship instanceof ObservationRelationship) {
            jsonGenerator.writeFieldName(StructureUtils.OBSERVATION);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeEndObject();
        } else if (attributeRelationship instanceof GroupRelationship) {
            GroupRelationship groupRelationship = (GroupRelationship) attributeRelationship;
            jsonGenerator.writeStringField(StructureUtils.GROUP, groupRelationship.getGroupKey());
        } else {
            jsonGenerator.writeFieldName(StructureUtils.NONE);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }

    private void writeMeasureRelationship(JsonGenerator jsonGenerator, MeasureRelationship measureRelationship) throws IOException {
        if (measureRelationship != null) {
            jsonGenerator.writeFieldName(StructureUtils.MEASURE_RELATIONSHIP);
            jsonGenerator.writeStartArray();
            for (String measure : measureRelationship.getMeasures()) {
                jsonGenerator.writeString(measure);
            }
            jsonGenerator.writeEndArray();
        }
    }
}
