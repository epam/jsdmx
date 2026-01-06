package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueTypeRepresentation;
import com.epam.jsdmx.infomodel.sdmx30.ListReferenceValueRepresentation;
import com.epam.jsdmx.infomodel.sdmx30.MappedValue;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapping;
import com.epam.jsdmx.infomodel.sdmx30.TargetValue;
import com.epam.jsdmx.infomodel.sdmx30.ValueRepresentation;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class RepresentationMapWriter extends MaintainableWriter<RepresentationMap> {

    private final AnnotableWriter annotableWriter;
    private final ReferenceAdapter referenceAdapter;

    public RepresentationMapWriter(VersionableWriter versionableWriter,
                                   LinksWriter linksWriter,
                                   AnnotableWriter annotableWriter,
                                   ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, RepresentationMap representationMap) throws IOException {
        super.writeFields(jsonGenerator, representationMap);
        writeValueRepresentationList(jsonGenerator, representationMap.getSource(), StructureUtils.SOURCE);
        writeValueRepresentationList(jsonGenerator, representationMap.getTarget(), StructureUtils.TARGET);
        writeRepresentationMappings(jsonGenerator, representationMap.getRepresentationMappings());
    }

    @Override
    protected Set<RepresentationMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getRepresentationMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.REPRESENTATION_MAPS;
    }

    private void writeRepresentationMappings(JsonGenerator jsonGenerator, List<RepresentationMapping> representationMappings) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.REPRESENTATION_MAPPINGS);
        jsonGenerator.writeStartArray();
        if (representationMappings != null && !representationMappings.isEmpty()) {
            for (RepresentationMapping representationMapping : representationMappings) {
                jsonGenerator.writeStartObject();
                annotableWriter.write(jsonGenerator, representationMapping);
                Instant validTo = representationMapping.getValidTo();
                if (validTo != null) {
                    jsonGenerator.writeStringField(
                        StructureUtils.VALID_TO,
                        StructureUtils.mapInstantToString(validTo)
                    );
                }
                Instant validFrom = representationMapping.getValidFrom();
                if (validFrom != null) {
                    jsonGenerator.writeStringField(
                        StructureUtils.VALID_FROM,
                        StructureUtils.mapInstantToString(validFrom)
                    );
                }
                writeSourceValues(jsonGenerator, representationMapping.getSourceValues());
                writeTargetValues(jsonGenerator, representationMapping.getTargetValues());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeTargetValues(JsonGenerator jsonGenerator, List<TargetValue> targetValues) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.TARGET_VALUES);
        jsonGenerator.writeStartArray();
        if (targetValues != null && !targetValues.isEmpty()) {
            for (TargetValue targetValue : targetValues) {
                jsonGenerator.writeString(targetValue.getValue());
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeSourceValues(JsonGenerator jsonGenerator, List<MappedValue> sourceValues) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.SOURCE_VALUES);
        jsonGenerator.writeStartArray();
        if (sourceValues != null && !sourceValues.isEmpty()) {
            for (MappedValue mappedValue : sourceValues) {
                jsonGenerator.writeStartObject();
                if (mappedValue.getValue() != null) {
                    jsonGenerator.writeStringField(StructureUtils.VALUE, mappedValue.getValue());
                }
                jsonGenerator.writeBooleanField(StructureUtils.IS_REG_EX, mappedValue.isRegEx());
                jsonGenerator.writeNumberField(StructureUtils.START_INDEX, mappedValue.getStartIndex());
                jsonGenerator.writeNumberField(StructureUtils.END_INDEX, mappedValue.getEndIndex());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeValueRepresentationList(JsonGenerator jsonGenerator,
                                              List<ValueRepresentation> valueRepresentations,
                                              String valueRepresentationName) throws IOException {
        if (valueRepresentations != null && !valueRepresentations.isEmpty()) {
            jsonGenerator.writeFieldName(valueRepresentationName);
            jsonGenerator.writeStartArray();
            for (ValueRepresentation valueRepresentation : valueRepresentations) {
                jsonGenerator.writeStartObject();
                if (valueRepresentation instanceof ListReferenceValueRepresentation) {
                    ListReferenceValueRepresentation listReferenceValueRepresentation = (ListReferenceValueRepresentation) valueRepresentation;
                    String urn = Optional.ofNullable(listReferenceValueRepresentation.getListReference())
                        .map(referenceAdapter::toAdaptedUrn)
                        .orElse(null);
                    if (urn != null) {
                        jsonGenerator.writeStringField(StructureUtils.CODE_LIST, urn);
                    }
                } else if (valueRepresentation instanceof FacetValueTypeRepresentation) {
                    FacetValueTypeRepresentation facetValueTypeRepresentation = (FacetValueTypeRepresentation) valueRepresentation;
                    FacetValueType facetType = facetValueTypeRepresentation.getType();
                    if (facetType != null) {
                        jsonGenerator.writeStringField(StructureUtils.DATA_TYPE, facetType.value());
                    }
                }
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }
}
