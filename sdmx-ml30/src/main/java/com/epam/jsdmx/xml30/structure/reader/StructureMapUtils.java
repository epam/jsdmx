package com.epam.jsdmx.xml30.structure.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DateMap;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochMap;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMapping;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;

import org.apache.commons.collections.CollectionUtils;

public final class StructureMapUtils {

    private StructureMapUtils() {
    }

    public static Map<EpochMap, List<MappedComponent>> getEpochWithMappedComponent(List<EpochMap> epochMaps, List<ComponentMap> componentMaps) {
        Map<String, EpochMap> epochs = epochMaps.stream()
            .collect(Collectors.toMap(EpochMap::getId, Function.identity()));
        if (CollectionUtils.isNotEmpty(componentMaps)) {
            return componentMaps.stream()
                .filter(Objects::nonNull)
                .filter(componentMap -> componentMap.getRepresentationMap() != null)
                .filter(componentMap -> epochs.containsKey(componentMap.getRepresentationMap()
                    .getItemId()))
                .collect(Collectors.groupingBy(componentMap -> epochs.get(componentMap.getRepresentationMap()
                    .getItemId()), Collectors.mapping(map -> new MappedComponent(map.getSource()
                    .get(0), map.getTarget()
                    .get(0)), Collectors.toList())));
        }
        return Collections.emptyMap();
    }

    public static Map<DatePatternMap, List<MappedComponent>> getDatePatternWithMappedComponent(List<DatePatternMap> dateMap, List<ComponentMap> componentMaps) {
        Map<String, DatePatternMap> datePatterns = dateMap.stream()
            .filter(datePatternMap -> datePatternMap.getId() != null)
            .collect(Collectors.toMap(DatePatternMap::getId, Function.identity()));
        if (CollectionUtils.isNotEmpty(componentMaps)) {
            return componentMaps.stream()
                .filter(Objects::nonNull)
                .filter(componentMap -> componentMap.getRepresentationMap() != null)
                .filter(componentMap -> datePatterns.containsKey(componentMap.getRepresentationMap()
                    .getItemId()))
                .collect(Collectors.groupingBy(componentMap -> datePatterns.get(componentMap.getRepresentationMap()
                    .getItemId()), Collectors.mapping(map -> new MappedComponent(map.getSource()
                    .get(0), map.getTarget()
                    .get(0)), Collectors.toList())));
        }
        return Collections.emptyMap();
    }

    public static List<FrequencyFormatMapping> getFrequencyFormatMappings(List<EpochMap> epochMaps, List<DatePatternMap> datePatternMaps) {
        List<FrequencyFormatMapping> frequencyFormatMappings = new ArrayList<>();
        for (EpochMap epochMap : epochMaps) {
            if (epochMap != null && epochMap.getMappedFrequencies() != null) {
                frequencyFormatMappings.addAll(epochMap.getMappedFrequencies());
            }
        }
        for (DatePatternMap datePatternMap : datePatternMaps) {
            if (datePatternMap != null && datePatternMap.getMappedFrequencies() != null) {
                frequencyFormatMappings.addAll(datePatternMap.getMappedFrequencies());
            }
        }
        return frequencyFormatMappings;
    }

    public static List<ComponentMap> getRepresentationMap(StructureMap structureMap) {
        List<String> epochIds = structureMap.getEpochMaps()
            .stream()
            .map(EpochMap::getId)
            .collect(Collectors.toList());
        List<String> datePatternIds = structureMap.getDatePatternMaps()
            .stream()
            .map(DatePatternMap::getId)
            .collect(Collectors.toList());
        List<String> nonRepresentationMapIds = Stream.concat(epochIds.stream(), datePatternIds.stream())
            .collect(Collectors.toList());
        List<ComponentMap> componentMaps = structureMap.getComponentMaps();
        return componentMaps == null ? null
            : componentMaps.stream()
                .filter(Objects::nonNull)
                .filter(componentMap ->
                    componentMap.getRepresentationMap() == null
                        || !nonRepresentationMapIds.contains(componentMap.getRepresentationMap().getItemId()))
                .collect(Collectors.toList());
    }

    public static List<ComponentMapImpl> extractComponentMapFromDatePatternMap(Map<DateMap, MappedComponent> mappedComponents,
                                                                               StructureMapImpl structureMap) {
        List<ComponentMapImpl> componentMaps = new ArrayList<>();
        for (Map.Entry<DateMap, MappedComponent> entrySet : mappedComponents
            .entrySet()) {
            ComponentMapImpl componentMapDP = new ComponentMapImpl();
            componentMapDP.setSource(List.of(entrySet.getValue()
                .getSource()));
            componentMapDP.setTarget(List.of(entrySet.getValue()
                .getTarget()));
            componentMapDP.setRepresentationMap(
                new IdentifiableArtefactReferenceImpl(structureMap.getId(), structureMap.getOrganizationId(), structureMap.getVersion().toString(),
                    StructureClassImpl.DATE_PATTERN_MAP, entrySet.getKey()
                    .getId()
                ));
            componentMaps.add(componentMapDP);
        }
        return componentMaps;
    }

    public static List<ComponentMapImpl> extractComponentMapFromEpochMap(Map<DateMap, MappedComponent> mappedComponents, StructureMapImpl structureMap) {
        List<ComponentMapImpl> componentMaps = new ArrayList<>();
        for (Map.Entry<DateMap, MappedComponent> entrySet : mappedComponents
            .entrySet()) {
            ComponentMapImpl componentMapEpoch = new ComponentMapImpl();
            componentMapEpoch.setSource(List.of(entrySet.getValue()
                .getSource()));
            componentMapEpoch.setTarget(List.of(entrySet.getValue()
                .getTarget()));
            componentMapEpoch.setRepresentationMap(
                new IdentifiableArtefactReferenceImpl(
                    structureMap.getId(),
                    structureMap.getOrganizationId(),
                    structureMap.getVersion().toString(),
                    StructureClassImpl.EPOCH_MAP,
                    entrySet.getKey()
                        .getId()
                ));
            componentMaps.add(componentMapEpoch);
        }
        return componentMaps;
    }
}