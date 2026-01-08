package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DateMap;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.EpochMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.EpochPeriodType;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMapping;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ResolvePeriod;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.YearStart;
import com.epam.jsdmx.json20.structure.writer.MappedComponent;
import com.epam.jsdmx.json20.structure.writer.StructureMapUtils;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;

@Getter
@Setter
public class StructureMapReader extends MaintainableReader<StructureMapImpl> {

    private AnnotableReader annotableReader;
    private IdentifiableReader identifiableReader;

    private HashMap<DatePatternMapImpl, List<String>> datePatternFreqDimensionIds;
    private HashMap<EpochMapImpl, List<String>> epochFreqDimensionIds;
    private List<String> frequencyIds = new ArrayList<>();
    private List<FrequencyFormatMappingImpl> frequencyFormatMappings;
    private List<ComponentMapImpl> componentMaps;
    private HashMap<DateMap, MappedComponent> mappedComponentsDP;
    private HashMap<DateMap, MappedComponent> mappedComponentsEpoch;

    public StructureMapReader(VersionableReader versionableReader, IdentifiableReader identifiableReader, AnnotableReader annotableReader) {
        super(versionableReader);
        this.identifiableReader = identifiableReader;
        this.annotableReader = annotableReader;
        this.datePatternFreqDimensionIds = new HashMap<>();
        this.epochFreqDimensionIds = new HashMap<>();
        this.mappedComponentsDP = new HashMap<>();
        this.mappedComponentsEpoch = new HashMap<>();
        this.frequencyFormatMappings = new ArrayList<>();
        this.componentMaps = new ArrayList<>();
    }

    @Override
    protected void clean() {
        this.datePatternFreqDimensionIds.clear();
        this.epochFreqDimensionIds.clear();
        this.mappedComponentsDP.clear();
        this.mappedComponentsEpoch.clear();
        this.frequencyFormatMappings.clear();
        this.componentMaps.clear();
    }

    @Override
    protected StructureMapImpl createMaintainableArtefact() {
        return new StructureMapImpl();
    }

    @Override
    public StructureMapImpl read(JsonParser parser) throws IOException {
        StructureMapImpl structureMap = super.read(parser);

        List<String> freqIds = frequencyFormatMappings.stream()
            .filter(Objects::nonNull)
            .map(IdentifiableArtefact::getId)
            .collect(Collectors.toList());

        setDatePatternMapFrequencyDimensions(datePatternFreqDimensionIds, frequencyFormatMappings, freqIds);
        setEpochMapFrequencyDimensions(epochFreqDimensionIds, frequencyFormatMappings, freqIds);

        List<ComponentMapImpl> componentMapsDatePattern
            = StructureMapUtils.extractComponentMapFromDatePatternMap(mappedComponentsDP, structureMap);
        List<ComponentMapImpl> componentMapsEpoch = StructureMapUtils.extractComponentMapFromEpochMap(
            mappedComponentsEpoch,
            structureMap
        );

        List<ComponentMap> allComponentMaps = Stream.of(componentMapsDatePattern, componentMapsEpoch, componentMaps)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(allComponentMaps)) {
            structureMap.setComponentMaps(allComponentMaps);
        }

        return structureMap;
    }

    private void setDatePatternMapFrequencyDimensions(Map<DatePatternMapImpl, List<String>> datePatternFreqDimensionIds,
                                                      List<FrequencyFormatMappingImpl> frequencyFormatMappings,
                                                      List<String> freqIds) {
        for (Map.Entry<DatePatternMapImpl, List<String>> freqIdsFromDatePattern : datePatternFreqDimensionIds.entrySet()) {
            List<String> frequencyFormatMappingIds = freqIds.stream()
                .filter(id -> freqIdsFromDatePattern.getValue().contains(id))
                .collect(Collectors.toList());

            List<FrequencyFormatMapping> formatMappings = frequencyFormatMappings.stream()
                .filter(freq -> frequencyFormatMappingIds.contains(freq.getId()))
                .collect(Collectors.toList());

            freqIdsFromDatePattern.getKey()
                .setMappedFrequencies(formatMappings);
        }
    }

    private void setEpochMapFrequencyDimensions(Map<EpochMapImpl, List<String>> epochMapFreqDimensionIds,
                                                List<FrequencyFormatMappingImpl> frequencyFormatMappings,
                                                List<String> freqIds) {
        for (Map.Entry<EpochMapImpl, List<String>> freqIdsFromEpochMap : epochMapFreqDimensionIds.entrySet()) {
            List<String> frequencyFormatMappingIds = freqIds.stream()
                .filter(id -> freqIdsFromEpochMap.getValue().contains(id))
                .collect(Collectors.toList());

            List<FrequencyFormatMapping> formatMappings = frequencyFormatMappings.stream()
                .filter(freq -> frequencyFormatMappingIds.contains(freq.getId()))
                .collect(Collectors.toList());

            freqIdsFromEpochMap.getKey()
                .setMappedFrequencies(formatMappings);
        }
    }

    @Override
    public void readArtefact(JsonParser parser, StructureMapImpl structureMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.SOURCE:
                String source = ReaderUtils.getStringJsonField(parser);
                if (source != null) {
                    structureMap.setSource(new MaintainableArtefactReference(source));
                }
                break;
            case StructureUtils.TARGET:
                String target = ReaderUtils.getStringJsonField(parser);
                if (target != null) {
                    structureMap.setTarget(new MaintainableArtefactReference(target));
                }
                break;
            case StructureUtils.FREQUENCY_FORMAT_MAPPINGS:
                frequencyFormatMappings = ReaderUtils.getArray(parser, this::getFrequenciesFormatMapping);
                frequencyIds = frequencyFormatMappings.stream()
                    .filter(Objects::nonNull)
                    .map(IdentifiableArtefact::getId)
                    .collect(Collectors.toList());
                break;
            case StructureUtils.DATE_PATTERN_MAPS:
                List<DatePatternMapImpl> datePatternMaps = ReaderUtils.getArray(parser, this::getDatePatternMap);
                if (CollectionUtils.isNotEmpty(datePatternMaps)) {
                    structureMap.setDatePatternMaps(new ArrayList<>(datePatternMaps));
                }
                break;
            case StructureUtils.FIXED_VALUE_MAPS:
                List<FixedValueMapImpl> fixedValueMaps = ReaderUtils.getArray(parser, this::getFixedValueMap);
                if (CollectionUtils.isNotEmpty(fixedValueMaps)) {
                    structureMap.setFixedComponentMaps(new ArrayList<>(fixedValueMaps));
                }
                break;
            case StructureUtils.EPOCH_MAPS:
                List<EpochMapImpl> epochMaps = ReaderUtils.getArray(parser, this::getEpochMap);
                if (CollectionUtils.isNotEmpty(epochMaps)) {
                    structureMap.setEpochMaps(new ArrayList<>(epochMaps));
                }
                break;
            case StructureUtils.COMPONENT_MAPS:
                List<ComponentMapImpl> componentMapList = ReaderUtils.getArray(parser, this::getComponentMap);
                if (CollectionUtils.isNotEmpty(componentMapList)) {
                    this.componentMaps.addAll(componentMapList);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "StructureMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.STRUCTURE_MAP;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<StructureMapImpl> artefacts) {
        artefact.getStructureMaps().addAll(artefacts);
    }

    private ComponentMapImpl getComponentMap(JsonParser parser) {
        try {
            ComponentMapImpl componentMap = new ComponentMapImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.SOURCE:
                        List<String> sorces = ReaderUtils.getListStrings(parser);
                        componentMap.setSource(sorces);
                        break;
                    case StructureUtils.TARGET:
                        List<String> targets = ReaderUtils.getListStrings(parser);
                        componentMap.setTarget(targets);
                        break;
                    case StructureUtils.REPRESENTATION_MAP:
                        String representationMap = ReaderUtils.getStringJsonField(parser);
                        componentMap.setRepresentationMap(new IdentifiableArtefactReferenceImpl(representationMap));
                        break;
                    default:
                        annotableReader.read(componentMap, parser);
                        break;
                }
            }
            return componentMap;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private FrequencyFormatMappingImpl getFrequenciesFormatMapping(JsonParser parser) {
        try {
            FrequencyFormatMappingImpl frequencyFormatMapping = new FrequencyFormatMappingImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.FREQUENCY_ID:
                        frequencyFormatMapping.setFrequencyCode(ReaderUtils.getStringJsonField(parser));
                        break;
                    case StructureUtils.DATE_PATTERN:
                        frequencyFormatMapping.setDatePattern(ReaderUtils.getStringJsonField(parser));
                        break;
                    default:
                        identifiableReader.read(frequencyFormatMapping, parser);
                        break;
                }
            }
            return frequencyFormatMapping;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private DatePatternMapImpl getDatePatternMap(JsonParser parser) {
        try {
            DatePatternMapImpl datePatternMap = new DatePatternMapImpl();
            List<String> datePatternMapFreqIds = new ArrayList<>();
            List<MappedComponent> datePatternMappedComponents = new ArrayList<>();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.RESOLVE_PERIOD:
                        String resolvePeriod = ReaderUtils.getStringJsonField(parser);
                        if (resolvePeriod != null) {
                            datePatternMap.setResolvePeriod(ResolvePeriod.valueOf(resolvePeriod));
                        }
                        break;
                    case StructureUtils.YEAR_START:
                        Optional<YearStart> yearStart = getYearStart(parser);
                        yearStart.ifPresent(datePatternMap::setYearStart);
                        break;
                    case StructureUtils.LOCALE:
                        String locale = ReaderUtils.getStringJsonField(parser);
                        datePatternMap.setLocale(locale);
                        break;
                    case StructureUtils.SOURCE_PATTERN:
                        String sourcePattern = ReaderUtils.getStringJsonField(parser);
                        datePatternMap.setSourcePattern(sourcePattern);
                        break;
                    case StructureUtils.MAPPED_COMPONENTS:
                        datePatternMappedComponents = ReaderUtils.getArray(parser, this::getMappedComponent);
                        break;
                    case StructureUtils.FREQUENCY_DIMENSION:
                        datePatternMap.setFrequencyDimension(ReaderUtils.getStringJsonField(parser));
                        break;
                    case StructureUtils.TARGET_FREQUENCY_ID:
                        datePatternMap.setTargetFrequencyId(ReaderUtils.getStringJsonField(parser));
                        break;
                    case StructureUtils.MAPPED_FREQUENCIES:
                        datePatternMapFreqIds.addAll(ReaderUtils.getListStrings(parser));
                        break;
                    default:
                        identifiableReader.read(datePatternMap, parser);
                        break;
                }
            }
            if (CollectionUtils.isNotEmpty(datePatternMappedComponents)) {
                mappedComponentsDP.put(datePatternMap, datePatternMappedComponents.get(0));
            }
            datePatternFreqDimensionIds.put(datePatternMap, datePatternMapFreqIds);
            return datePatternMap;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    @SneakyThrows
    private EpochMapImpl getEpochMap(JsonParser parser) {
        EpochMapImpl epochMap = new EpochMapImpl();
        List<String> epochMapFreqIds = new ArrayList<>();
        List<MappedComponent> epochMapMappedComponents = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.BASE_PERIOD:
                    epochMap.setBasePeriod(ReaderUtils.getInstantObj(parser));
                    break;
                case StructureUtils.EPOCH_PERIOD:
                    parser.nextToken();
                    epochMap.setEpochPeriod(EnumUtils.getEnumIgnoreCase(EpochPeriodType.class, parser.getText()));
                    break;
                case StructureUtils.RESOLVE_PERIOD:
                    parser.nextToken();
                    epochMap.setResolvePeriod(EnumUtils.getEnumIgnoreCase(ResolvePeriod.class, parser.getText()));
                    break;
                case StructureUtils.YEAR_START:
                    Optional<YearStart> yearStart = getYearStart(parser);
                    yearStart.ifPresent(epochMap::setYearStart);
                    break;
                case StructureUtils.MAPPED_COMPONENTS:
                    epochMapMappedComponents = ReaderUtils.getArray(parser, this::getMappedComponent);
                    break;
                case StructureUtils.FREQUENCY_DIMENSION:
                    epochMap.setFrequencyDimension(ReaderUtils.getStringJsonField(parser));
                    break;
                case StructureUtils.MAPPED_FREQUENCIES:
                    epochMapFreqIds.addAll(ReaderUtils.getListStrings(parser));
                    break;
                default:
                    identifiableReader.read(epochMap, parser);
                    break;
            }
        }
        if (CollectionUtils.isNotEmpty(epochMapMappedComponents)) {
            mappedComponentsEpoch.put(epochMap, epochMapMappedComponents.get(0));
        }
        epochFreqDimensionIds.put(epochMap, epochMapFreqIds);
        return epochMap;
    }

    @SneakyThrows
    private MappedComponent getMappedComponent(JsonParser parser) {
        MappedComponent mappedComponent = new MappedComponent();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.SOURCE:
                    String sourceId = ReaderUtils.getStringJsonField(parser);
                    mappedComponent.setSource(sourceId);
                    break;
                case StructureUtils.TARGET:
                    String targetId = ReaderUtils.getStringJsonField(parser);
                    mappedComponent.setTarget(targetId);
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "FrequencyFormatMapping: " + fieldName);
            }
        }
        return mappedComponent;
    }


    @SneakyThrows
    private Optional<YearStart> getYearStart(JsonParser parser) {
        int dayOfMonth = -1;
        int monthOfYear = -1;
        parser.nextToken();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.DAY_OF_MONTH:
                    parser.nextToken();
                    dayOfMonth = parser.getIntValue();
                    break;
                case StructureUtils.MONTH_OF_YEAR:
                    parser.nextToken();
                    monthOfYear = parser.getIntValue();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "YearStart: " + fieldName);
            }
        }
        if (dayOfMonth > 0 && monthOfYear > 0) {
            YearStart yearStart = new YearStart(dayOfMonth, monthOfYear);
            return Optional.of(yearStart);
        }
        return Optional.empty();
    }

    @SneakyThrows
    private FixedValueMapImpl getFixedValueMap(JsonParser parser) {
        FixedValueMapImpl fixedValueMap = new FixedValueMapImpl();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            if (!ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                return null;
            }
            String fieldName = parser.getCurrentName();
            if (StructureUtils.VALUE.equals(fieldName)) {
                fixedValueMap.setValue(ReaderUtils.getStringJsonField(parser));
            } else {
                annotableReader.read(fixedValueMap, parser);
            }
        }
        return fixedValueMap;
    }

}
