package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.DateMap;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochPeriodType;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMap;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMapping;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.infomodel.sdmx30.YearStart;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class StructureMapWriter extends MaintainableWriter<StructureMap> {

    private final AnnotableWriter annotableWriter;
    private final IdentifiableWriter identifiableWriter;
    private final ReferenceAdapter referenceAdapter;

    public StructureMapWriter(VersionableWriter versionableWriter,
                              LinksWriter linksWriter,
                              AnnotableWriter annotableWriter,
                              IdentifiableWriter identifiableWriter,
                              ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.identifiableWriter = identifiableWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, StructureMap structureMap) throws IOException {
        super.writeFields(jsonGenerator, structureMap);
        writeSource(jsonGenerator, structureMap.getSource());
        writeTarget(jsonGenerator, structureMap.getTarget());

        Map<EpochMap, List<MappedComponent>> epochWithMappedComponent = StructureMapUtils.getEpochWithMappedComponent(
            structureMap.getEpochMaps(),
            structureMap.getComponentMaps()
        );
        writeEpochMaps(jsonGenerator, epochWithMappedComponent);
        Map<DatePatternMap, List<MappedComponent>> datePatternWithMappedComponent = StructureMapUtils.getDatePatternWithMappedComponent(
            structureMap.getDatePatternMaps(),
            structureMap.getComponentMaps()
        );
        writeDatePatternMaps(jsonGenerator, datePatternWithMappedComponent);
        List<FrequencyFormatMapping> frequencyFormatMappings = StructureMapUtils.getFrequencyFormatMappings(
            structureMap.getEpochMaps(),
            structureMap.getDatePatternMaps()
        );
        writeFrequencyFormatMappings(jsonGenerator, frequencyFormatMappings);
        writeComponentMaps(jsonGenerator, StructureMapUtils.getRepresentationMap(structureMap));
        writeFixedValueMaps(jsonGenerator, structureMap.getFixedComponentMaps());
    }

    @Override
    protected Set<StructureMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getStructureMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.STRUCTURE_MAP;
    }

    private void writeDateMap(JsonGenerator jsonGenerator, DateMap dateMap, List<MappedComponent> mappedComponents) throws IOException {
        identifiableWriter.write(jsonGenerator, dateMap);
        writeMappedComponents(jsonGenerator, mappedComponents);
        jsonGenerator.writeStringField(StructureUtils.RESOLVE_PERIOD, dateMap.getResolvePeriod()
            .toString());
        String frequencyDimension = dateMap.getFrequencyDimension();
        if (frequencyDimension != null) {
            jsonGenerator.writeStringField(StructureUtils.FREQUENCY_DIMENSION, frequencyDimension);
        }
        String targetFrequencyId = dateMap.getTargetFrequencyId();
        if (targetFrequencyId != null) {
            jsonGenerator.writeStringField(StructureUtils.TARGET_FREQUENCY_ID, targetFrequencyId);
        }
        List<FrequencyFormatMapping> mappedFrequencies = dateMap.getMappedFrequencies();
        writeMappedFreq(jsonGenerator, mappedFrequencies);
        YearStart yearStart = dateMap.getYearStart();
        jsonGenerator.writeFieldName(StructureUtils.YEAR_START);
        jsonGenerator.writeStartObject();
        if (yearStart != null) {
            jsonGenerator.writeNumberField(StructureUtils.DAY_OF_MONTH, yearStart.getDayOfMonth());
            jsonGenerator.writeNumberField(StructureUtils.MONTH_OF_YEAR, yearStart.getMonthOfYear());
        }
        jsonGenerator.writeEndObject();
    }

    private void writeMappedFreq(JsonGenerator jsonGenerator, List<FrequencyFormatMapping> mappedFrequencies) throws IOException {
        if (mappedFrequencies != null) {
            List<String> freqIds = mappedFrequencies.stream()
                .filter(Objects::nonNull)
                .map(IdentifiableArtefact::getId)
                .collect(Collectors.toList());
            jsonGenerator.writeFieldName(StructureUtils.MAPPED_FREQUENCIES);
            jsonGenerator.writeStartArray();
            for (String id : freqIds) {
                jsonGenerator.writeString(id);
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeMappedComponents(JsonGenerator jsonGenerator, List<MappedComponent> mappedComponents) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.MAPPED_COMPONENTS);
        jsonGenerator.writeStartArray();
        for (MappedComponent mappedComponent : mappedComponents) {
            if (mappedComponent != null) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(StructureUtils.SOURCE, mappedComponent.getSource());
                jsonGenerator.writeStringField(StructureUtils.TARGET, mappedComponent.getTarget());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }

    private void writeFixedValueMaps(JsonGenerator jsonGenerator, List<FixedValueMap> fixedComponentMaps) throws IOException {
        if (fixedComponentMaps != null) {
            jsonGenerator.writeFieldName(StructureUtils.FIXED_VALUE_MAPS);
            jsonGenerator.writeStartArray();
            for (FixedValueMap fixedValueMap : fixedComponentMaps) {
                if (fixedValueMap != null) {
                    jsonGenerator.writeStartObject();
                    annotableWriter.write(jsonGenerator, fixedValueMap);
                    jsonGenerator.writeStringField(StructureUtils.VALUE, fixedValueMap.getValue());
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeComponentMaps(JsonGenerator jsonGenerator, List<ComponentMap> componentMaps) throws IOException {
        if (CollectionUtils.isNotEmpty(componentMaps)) {
            jsonGenerator.writeFieldName(StructureUtils.COMPONENT_MAPS);
            jsonGenerator.writeStartArray();
            for (ComponentMap map : componentMaps) {
                if (map != null) {
                    jsonGenerator.writeStartObject();
                    annotableWriter.write(jsonGenerator, map);
                    List<String> sourceIds = map.getSource();
                    writeSourcesTargets(sourceIds, jsonGenerator, StructureUtils.SOURCE);

                    List<String> targetIds = map.getTarget();
                    writeSourcesTargets(targetIds, jsonGenerator, StructureUtils.TARGET);

                    ArtefactReference representationMap = map.getRepresentationMap();
                    if (representationMap != null) {
                        jsonGenerator.writeStringField(StructureUtils.REPRESENTATION_MAP, referenceAdapter.adaptUrn(representationMap.getUrn()));
                    }
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeSourcesTargets(List<String> sourceOrTargetIds, JsonGenerator jsonGenerator, String sourceOrTargetName) throws IOException {
        if (CollectionUtils.isNotEmpty(sourceOrTargetIds)) {
            jsonGenerator.writeFieldName(sourceOrTargetName);
            jsonGenerator.writeStartArray();
            for (String id : sourceOrTargetIds) {
                jsonGenerator.writeString(id);
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeFrequencyFormatMappings(JsonGenerator jsonGenerator, List<FrequencyFormatMapping> frequencyFormatMappings) throws IOException {
        if (frequencyFormatMappings != null) {
            jsonGenerator.writeFieldName(StructureUtils.FREQUENCY_FORMAT_MAPPINGS);
            jsonGenerator.writeStartArray();
            for (FrequencyFormatMapping frequencyFormatMapping : frequencyFormatMappings) {
                if (frequencyFormatMapping != null) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField(StructureUtils.FREQUENCY_ID, frequencyFormatMapping.getFrequencyCode());
                    jsonGenerator.writeStringField(StructureUtils.DATE_PATTERN, frequencyFormatMapping.getDatePattern());
                    identifiableWriter.write(jsonGenerator, frequencyFormatMapping);
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeDatePatternMaps(JsonGenerator jsonGenerator, Map<DatePatternMap, List<MappedComponent>> datePatternMaps) throws IOException {
        if (datePatternMaps != null) {
            jsonGenerator.writeFieldName(StructureUtils.DATE_PATTERN_MAPS);
            jsonGenerator.writeStartArray();
            for (Map.Entry<DatePatternMap, List<MappedComponent>> map : datePatternMaps.entrySet()) {
                jsonGenerator.writeStartObject();
                writeDateMap(jsonGenerator, map.getKey(), map.getValue());
                jsonGenerator.writeStringField(StructureUtils.LOCALE, map.getKey()
                    .getLocale());
                jsonGenerator.writeStringField(StructureUtils.SOURCE_PATTERN, map.getKey()
                    .getSourcePattern());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }
    }

    private void writeEpochMaps(JsonGenerator jsonGenerator, Map<EpochMap, List<MappedComponent>> epochMaps) throws IOException {
        if (epochMaps != null) {
            jsonGenerator.writeFieldName(StructureUtils.EPOCH_MAPS);
            jsonGenerator.writeStartArray();
            for (Map.Entry<EpochMap, List<MappedComponent>> map : epochMaps.entrySet()) {
                jsonGenerator.writeStartObject();
                writeDateMap(jsonGenerator, map.getKey(), map.getValue());
                Instant basePeriod = map.getKey()
                    .getBasePeriod();
                if (basePeriod != null) {
                    jsonGenerator.writeStringField(StructureUtils.BASE_PERIOD, StructureUtils.mapInstantToString(basePeriod));
                }
                EpochPeriodType epochPeriod = map.getKey()
                    .getEpochPeriod();
                if (epochPeriod != null) {
                    jsonGenerator.writeStringField(StructureUtils.EPOCH_PERIOD, epochPeriod.toString());
                }
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeSource(JsonGenerator jsonGenerator, ArtefactReference source) throws IOException {
        if (source != null) {
            jsonGenerator.writeStringField(StructureUtils.SOURCE, referenceAdapter.toAdaptedUrn(source));
        }
    }

    private void writeTarget(JsonGenerator jsonGenerator, ArtefactReference target) throws IOException {
        if (target != null) {
            jsonGenerator.writeStringField(StructureUtils.TARGET, referenceAdapter.toAdaptedUrn(target));
        }
    }
}
