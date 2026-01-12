package com.epam.jsdmx.json10.structure.writer;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.Dimension;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimension;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DimensionListWriter {

    private final IdentifiableWriter identifiableWriter;
    private final ConceptRoleWriter conceptRoleWriter;
    private final ComponentWriter componentWriter;

    public DimensionListWriter(IdentifiableWriter identifiableWriter, ConceptRoleWriter conceptRoleWriter, ComponentWriter componentWriter) {
        this.identifiableWriter = identifiableWriter;
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    public void write(JsonGenerator jsonGenerator, DimensionDescriptor descriptor) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.DIMENSION_LIST);

        if (descriptor == null) {
            jsonGenerator.writeNull();
            return;
        }

        var descriptorImpl = (DimensionDescriptorImpl) descriptor;
        descriptorImpl.setId(StructureUtils.DIMENSION_DESCRIPTOR_ID);

        jsonGenerator.writeStartObject();

        identifiableWriter.write(jsonGenerator, descriptorImpl);
        List<DimensionComponent> components = descriptorImpl.getComponents();
        writeDimensions(jsonGenerator, components);
        writeTimeDimensions(jsonGenerator, components);

        jsonGenerator.writeEndObject();
    }

    private void writeDimensions(JsonGenerator jsonGenerator, List<DimensionComponent> components) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.DIMENSIONS);
        jsonGenerator.writeStartArray();
        List<Dimension> dimensions = Optional.ofNullable(components)
            .stream()
            .flatMap(List::stream)
            .filter(d -> Dimension.class.isAssignableFrom(d.getClass()))
            .map(Dimension.class::cast)
            .collect(Collectors.toList());
        if (!dimensions.isEmpty()) {
            for (Dimension dimension : dimensions) {
                jsonGenerator.writeStartObject();
                componentWriter.write(jsonGenerator, dimension);
                jsonGenerator.writeNumberField(StructureUtils.POSITION, dimension.getOrder());
                jsonGenerator.writeStringField(StructureUtils.TYPE, "Dimension");
                conceptRoleWriter.write(jsonGenerator, dimension.getConceptRoles());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeTimeDimensions(JsonGenerator jsonGenerator, List<DimensionComponent> components) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.TIME_DIMENSIONS);
        jsonGenerator.writeStartArray();
        List<TimeDimension> timeDimensions = Optional.ofNullable(components)
            .stream()
            .flatMap(List::stream)
            .filter(d -> d instanceof TimeDimension)
            .map(TimeDimension.class::cast)
            .collect(Collectors.toList());
        if (!timeDimensions.isEmpty()) {
            for (TimeDimension dimension : timeDimensions) {
                jsonGenerator.writeStartObject();
                componentWriter.write(jsonGenerator, dimension);
                jsonGenerator.writeNumberField(StructureUtils.POSITION, dimension.getOrder());
                jsonGenerator.writeStringField(StructureUtils.TYPE, "TimeDimension");
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

}
