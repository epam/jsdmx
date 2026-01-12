package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class MeasureListReader {

    private final IdentifiableReader identifiableReader = new IdentifiableReader(
        new AnnotableReader());

    public MeasureDescriptorImpl getMeasureDescriptor(JsonParser parser) throws IOException {
        parser.nextToken();
        if (ReaderUtils.isNullValue(parser)) {
            return null;
        }
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        List<Measure> measures = new ArrayList<>();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            if (StructureUtils.MEASURES.equals(fieldName)) {
                List<Measure> measureList = ReaderUtils.getArray(parser, this::getMeasure);
                if (CollectionUtils.isNotEmpty(measureList)) {
                    measures.addAll(measureList);
                }
            } else {
                identifiableReader.read(measureDescriptor, parser);
            }
        }
        measureDescriptor.setComponents(measures);
        measureDescriptor.setId(StructureUtils.MEASURE_DESCRIPTOR_ID);
        return measureDescriptor;
    }

    private Measure getMeasure(JsonParser parser) {
        try {
            MeasureImpl measure = new MeasureImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.IS_MANDATORY:
                        parser.nextToken();
                        break;
                    case StructureUtils.CONCEPT_IDENTITY:
                        String conceptIdentity = ReaderUtils.getStringJsonField(parser);
                        if (conceptIdentity != null) {
                            measure.setConceptIdentity(new IdentifiableArtefactReferenceImpl(conceptIdentity));
                        }
                        break;
                    case StructureUtils.CONCEPT_ROLES:
                        List<ArtefactReference> conceptRoles = ConceptRoleUtils.getConceptRoles(parser);
                        if (CollectionUtils.isNotEmpty(conceptRoles)) {
                            measure.setConceptRoles(conceptRoles);
                        }
                        break;
                    case StructureUtils.LOCAL_REPRESENTATION:
                        measure.setLocalRepresentation(getRepresentation(parser));
                        break;
                    default:
                        identifiableReader.read(measure, parser);
                        break;
                }
            }
            return measure;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private Representation getRepresentation(JsonParser parser) throws IOException {
        RepresentationReader representationReader = new RepresentationReader();
        return representationReader.getRepresentations(parser);
    }
}
