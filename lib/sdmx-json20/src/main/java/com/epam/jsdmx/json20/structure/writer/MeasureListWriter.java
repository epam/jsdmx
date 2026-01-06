package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;

@EqualsAndHashCode(callSuper = false)
public class MeasureListWriter extends IdentifiableWriter {

    private final ConceptRoleWriter conceptRoleWriter;

    private final ComponentWriter componentWriter;

    public MeasureListWriter(AnnotableWriter annotableWriter, ConceptRoleWriter conceptRoleWriter, ComponentWriter componentWriter) {
        super(annotableWriter);
        this.conceptRoleWriter = conceptRoleWriter;
        this.componentWriter = componentWriter;
    }

    @Override
    public void write(JsonGenerator jsonGenerator, IdentifiableArtefact identifiableArtefact) throws IOException {
        if (identifiableArtefact == null) {
            return;
        }

        jsonGenerator.writeFieldName(StructureUtils.MEASURE_LIST);
        MeasureDescriptorImpl measureDescriptor = (MeasureDescriptorImpl) identifiableArtefact;
        measureDescriptor.setId(StructureUtils.MEASURE_DESCRIPTOR_ID);
        jsonGenerator.writeStartObject();
        super.write(jsonGenerator, measureDescriptor);
        List<Measure> components = measureDescriptor.getComponents();
        writeMeasures(jsonGenerator, components);
        jsonGenerator.writeEndObject();
    }

    private void writeMeasures(JsonGenerator jsonGenerator, List<Measure> components) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.MEASURES);
        jsonGenerator.writeStartArray();
        if (CollectionUtils.isNotEmpty(components)) {
            for (Measure measure : components) {
                if (measure != null) {
                    jsonGenerator.writeStartObject();
                    componentWriter.write(jsonGenerator, measure);
                    jsonGenerator.writeBooleanField(StructureUtils.IS_MANDATORY, measure.getMinOccurs() > 0);
                    conceptRoleWriter.write(jsonGenerator, measure.getConceptRoles());
                    jsonGenerator.writeEndObject();
                }
            }
        }
        jsonGenerator.writeEndArray();
    }
}
