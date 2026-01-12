package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;

import com.fasterxml.jackson.core.JsonGenerator;

public class MetadataStructureDefinitionWriter extends MaintainableWriter<MetadataStructureDefinition> {

    private final ComponentWriter componentWriter;
    private final IdentifiableWriter identifiableWriter;

    public MetadataStructureDefinitionWriter(VersionableWriter versionableWriter,
                                             LinksWriter linksWriter,
                                             ComponentWriter componentWriter,
                                             IdentifiableWriter identifiableWriter) {
        super(versionableWriter, linksWriter);
        this.componentWriter = componentWriter;
        this.identifiableWriter = identifiableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, MetadataStructureDefinition metadataStructureDefinition) throws IOException {
        super.writeFields(jsonGenerator, metadataStructureDefinition);
        jsonGenerator.writeFieldName(StructureUtils.METADATA_STRUCTURE_COMPONENTS);
        jsonGenerator.writeStartObject();
        writeMetadataAttributeList(jsonGenerator, metadataStructureDefinition.getAttributeDescriptor());
        jsonGenerator.writeEndObject();
    }

    @Override
    protected Set<MetadataStructureDefinition> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataStructureDefinitions();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.METADATA_STRUCTURES;
    }

    private void writeMetadataAttributeList(JsonGenerator jsonGenerator, MetadataAttributeDescriptor attributeDescriptor) throws IOException {
        if (attributeDescriptor != null) {
            jsonGenerator.writeFieldName(StructureUtils.METADATA_ATTRIBUTE_LIST);
            jsonGenerator.writeStartObject();
            identifiableWriter.write(jsonGenerator, attributeDescriptor);
            List<MetadataAttribute> attributeDescriptorComponents = attributeDescriptor.getComponents();
            writeMetadataAttributes(jsonGenerator, attributeDescriptorComponents);
            jsonGenerator.writeEndObject();
        }
    }

    private void writeMetadataAttributes(JsonGenerator jsonGenerator, List<MetadataAttribute> attributeDescriptorComponents) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.METADATA_ATTRIBUTES);
        jsonGenerator.writeStartArray();
        if (attributeDescriptorComponents != null && !attributeDescriptorComponents.isEmpty()) {
            for (MetadataAttribute metadataAttribute : attributeDescriptorComponents) {
                jsonGenerator.writeStartObject();
                componentWriter.write(jsonGenerator, metadataAttribute);
                jsonGenerator.writeNumberField(StructureUtils.MAX_OCCURS, metadataAttribute.getMaxOccurs());
                jsonGenerator.writeNumberField(StructureUtils.MIN_OCCURS, metadataAttribute.getMinOccurs());
                jsonGenerator.writeBooleanField(StructureUtils.IS_PRESENTATIONAL, metadataAttribute.isPresentational());
                if (!(metadataAttribute.getHierarchy() == null || metadataAttribute.getHierarchy().isEmpty())) {
                    writeMetadataAttributes(jsonGenerator, metadataAttribute.getHierarchy());
                }
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

}
