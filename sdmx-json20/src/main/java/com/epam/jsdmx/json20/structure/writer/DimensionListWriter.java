package com.epam.jsdmx.json20.structure.writer;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.Dimension;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimension;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DimensionListWriter extends IdentifiableWriter {

    private final ConceptRoleWriter conceptRoleWriter;
    private final ComponentWriter componentWriter;

    public DimensionListWriter(AnnotableWriter annotableWriter, ConceptRoleWriter conceptRoleWriter, ComponentWriter componentWriter) {
        super(annotableWriter);
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    @Override
    public void write(JsonGenerator jsonGenerator, IdentifiableArtefact identifiableArtefact) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.DIMENSION_LIST);
        if (identifiableArtefact == null) {
            jsonGenerator.writeNull();
            return;
        }
        DimensionDescriptorImpl dimensionDescriptor = (DimensionDescriptorImpl) identifiableArtefact;
        dimensionDescriptor.setId(StructureUtils.DIMENSION_DESCRIPTOR_ID);
        jsonGenerator.writeStartObject();
        super.write(jsonGenerator, dimensionDescriptor);
        List<DimensionComponent> components = dimensionDescriptor.getComponents();
        writeDimensions(jsonGenerator, components);
        writeTimeDimensions(jsonGenerator, components);

        jsonGenerator.writeEndObject();
    }

    private void writeTimeDimensions(JsonGenerator jsonGenerator, List<DimensionComponent> components) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.TIME_DIMENSIONS);
        jsonGenerator.writeStartArray();
        List<TimeDimension> timeDimensions = Optional.ofNullable(components)
            .stream()
            .flatMap(List::stream)
            .filter(d -> TimeDimension.class.isAssignableFrom(d.getClass()))
            .map(TimeDimension.class::cast)
            .collect(Collectors.toList());
        if (!timeDimensions.isEmpty()) {
            for (TimeDimension dimension : timeDimensions) {
                jsonGenerator.writeStartObject();
                componentWriter.write(jsonGenerator, dimension);
                jsonGenerator.writeNumberField(StructureUtils.POSITION, dimension.getOrder());
                jsonGenerator.writeStringField(StructureUtils.TYPE, dimension.getStructureClass()
                    .toString());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
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
                jsonGenerator.writeStringField(StructureUtils.TYPE, dimension.getStructureClass().toString());
                conceptRoleWriter.write(jsonGenerator, dimension.getConceptRoles());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

}
