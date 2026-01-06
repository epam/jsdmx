package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

@EqualsAndHashCode(callSuper = false)
public class MeasureListWriter {

    private final IdentifiableWriter identifiableWriter;
    private final ConceptRoleWriter conceptRoleWriter;
    private final ComponentWriter componentWriter;

    public MeasureListWriter(IdentifiableWriter identifiableWriter, ConceptRoleWriter conceptRoleWriter, ComponentWriter componentWriter) {
        this.identifiableWriter = identifiableWriter;
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    public <T extends MeasureDescriptor> void write(JsonGenerator jsonGenerator, MeasureDescriptor identifiableArtefact) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.MEASURE_LIST);

        if (identifiableArtefact == null) {
            jsonGenerator.writeNull();
            return;
        }

        // set fixed id
        var measureDescriptor = (MeasureDescriptorImpl) identifiableArtefact;
        measureDescriptor.setId(StructureUtils.MEASURE_DESCRIPTOR_ID);

        jsonGenerator.writeStartObject();

        identifiableWriter.write(jsonGenerator, measureDescriptor);
        writeMeasures(jsonGenerator, measureDescriptor.getComponents());

        jsonGenerator.writeEndObject();
    }

    private void writeMeasures(JsonGenerator jsonGenerator, List<Measure> components) throws IOException {
        jsonGenerator.writeFieldName("primaryMeasure");

        if (CollectionUtils.isNotEmpty(components)) {
            jsonGenerator.writeStartObject();

            final Measure primaryMeasure = components.get(0);
            componentWriter.write(jsonGenerator, primaryMeasure);

            jsonGenerator.writeEndObject();
        }
    }
}
