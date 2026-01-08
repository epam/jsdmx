package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DateMap;
import com.epam.jsdmx.infomodel.sdmx30.DateMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMap;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.EpochMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMap;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMapping;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MappingRoleType;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class StructureMapReader extends XmlReader<StructureMapImpl> {

    private final List<ComponentMap> componentMaps = new ArrayList<>();

    private final HashMap<DateMap, MappedComponent> mappedComponentsDatePattern = new HashMap<>();
    private final HashMap<DateMap, MappedComponent> mappedComponentsEpoch = new HashMap<>();
    private final Map<DateMap, List<String>> epochMapIdWithFrequencyIds = new HashMap<>();
    private final Map<DateMap, List<String>> datePatternMapIdWithFrequencyIds = new HashMap<>();
    List<EpochMap> epochMaps = new ArrayList<>();
    List<DatePatternMap> datePatternMaps = new ArrayList<>();
    List<FrequencyFormatMapping> frequencyFormatMappings = new ArrayList<>();

    public StructureMapReader(AnnotableReader annotableReader,
                              NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    private static void addSource(XMLStreamReader reader, List<String> sources) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .ifPresent(sources::add);
    }

    @Override
    protected StructureMapImpl createMaintainableArtefact() {
        return new StructureMapImpl();
    }

    @Override
    protected StructureMapImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        StructureMapImpl structureMap = super.read(reader);
        setFrequencyFormatting();
        structureMap.setEpochMaps(CollectionUtils.isNotEmpty(epochMaps) ? new ArrayList<>(epochMaps) : null);
        structureMap.setDatePatternMaps(CollectionUtils.isNotEmpty(datePatternMaps) ? new ArrayList<>(datePatternMaps) : null);

        setComponentMaps(structureMap);
        return structureMap;
    }

    @Override
    protected void read(XMLStreamReader reader, StructureMapImpl structureMap) throws URISyntaxException, XMLStreamException {
        List<FixedValueMap> fixedValueMaps = new ArrayList<>();

        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(structureMap::setSource);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(structureMap::setTarget);
                break;
            case XmlConstants.EPOCH_MAP:
                setEpochMap(reader, epochMaps);
                break;
            case XmlConstants.DATE_PATTERN_MAP:
                setDatePatternMap(reader, datePatternMaps);
                break;
            case XmlConstants.FREQUENCY_FORMAT_MAPPING:
                readFrequencyFormatMapping(reader, frequencyFormatMappings);
                break;
            case XmlConstants.COMPONENT_MAP:
                addComponentMaps(reader, componentMaps);
                break;
            case XmlConstants.FIXED_VALUE_MAP:
                setFixedValueMap(reader, fixedValueMaps);
                break;
            default:
                throw new IllegalArgumentException("StructureMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
        structureMap.setFixedComponentMaps(CollectionUtils.isNotEmpty(fixedValueMaps) ? fixedValueMaps : null);
    }

    @Override
    protected String getName() {
        return XmlConstants.STRUCTURE_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.STRUCTURE_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<StructureMapImpl> artefacts) {
        artefact.getStructureMaps().addAll(artefacts);
    }

    private void setComponentMaps(StructureMapImpl structureMap) {
        List<ComponentMapImpl> componentMapsDatePattern = StructureMapUtils.extractComponentMapFromDatePatternMap(mappedComponentsDatePattern, structureMap);
        List<ComponentMapImpl> componentMapsEpoch = StructureMapUtils.extractComponentMapFromEpochMap(mappedComponentsEpoch, structureMap);

        List<ComponentMap> allComponentMaps = Stream.of(componentMapsDatePattern, componentMapsEpoch, componentMaps)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        structureMap.setComponentMaps(allComponentMaps);
    }

    private void setFrequencyFormatting() {
        if (CollectionUtils.isNotEmpty(frequencyFormatMappings)) {

            for (Map.Entry<DateMap, List<String>> epochMap : epochMapIdWithFrequencyIds.entrySet()) {
                List<FrequencyFormatMapping> epochFrequencies = frequencyFormatMappings.stream()
                    .filter(freq -> epochMap.getValue().contains(freq.getId()))
                    .collect(Collectors.toList());
                Optional<EpochMap> optionalEpochMap = epochMaps.stream().filter(epoch -> epoch.equals(epochMap.getKey()))
                    .findFirst();
                optionalEpochMap.ifPresent(map -> ((EpochMapImpl) map).setMappedFrequencies(epochFrequencies));
            }

            for (Map.Entry<DateMap, List<String>> datePattern : datePatternMapIdWithFrequencyIds.entrySet()) {
                List<FrequencyFormatMapping> datePatternFrequencies = frequencyFormatMappings.stream()
                    .filter(freq -> datePattern.getValue().contains(freq.getId()))
                    .collect(Collectors.toList());
                Optional<DatePatternMap> optionalDatePatternMap = datePatternMaps.stream().filter(patternMap -> patternMap.equals(datePattern.getKey()))
                    .findFirst();
                optionalDatePatternMap.ifPresent(map -> ((DatePatternMapImpl) map).setMappedFrequencies(datePatternFrequencies));
            }
        }
    }

    private void setFixedValueMap(XMLStreamReader reader, List<FixedValueMap> fixedValueMaps) throws XMLStreamException, URISyntaxException {
        var fixedValueMap = new FixedValueMapImpl();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.FIXED_VALUE_MAP)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, fixedValueMap);
                    break;
                case XmlConstants.SOURCE:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(source -> {
                            fixedValueMap.setRole(MappingRoleType.SOURCE);
                            fixedValueMap.setComponent(source);
                        });
                    break;
                case XmlConstants.TARGET:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(target -> {
                            fixedValueMap.setRole(MappingRoleType.TARGET);
                            fixedValueMap.setComponent(target);
                        });
                    break;
                case XmlConstants.VALUE:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(fixedValueMap::setValue);
                    break;
                default:
                    throw new IllegalArgumentException("FixedValueMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        fixedValueMaps.add(fixedValueMap);
    }

    private void addComponentMaps(XMLStreamReader reader, List<ComponentMap> componentMaps) throws XMLStreamException, URISyntaxException {
        var componentMap = new ComponentMapImpl();
        List<String> sources = new ArrayList<>();
        List<String> targets = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COMPONENT_MAP)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, componentMap);
                    break;
                case XmlConstants.SOURCE:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(sources::add);
                    break;
                case XmlConstants.TARGET:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(targets::add);
                    break;
                case XmlConstants.REPRESENTATION_MAP:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .map(MaintainableArtefactReference::new)
                        .ifPresent(componentMap::setRepresentationMap);
                    break;
                default:
                    throw new IllegalArgumentException("ComponentMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        componentMap.setSource(CollectionUtils.isNotEmpty(sources) ? sources : null);
        componentMap.setTarget(CollectionUtils.isNotEmpty(targets) ? targets : null);
        componentMaps.add(componentMap);
    }

    private void readFrequencyFormatMapping(XMLStreamReader reader,
                                            List<FrequencyFormatMapping> frequencyFormatMappings) throws XMLStreamException, URISyntaxException {
        var frequencyFormatMapping = new FrequencyFormatMappingImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(frequencyFormatMapping::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            frequencyFormatMapping.setUri(new URI(uri));
        }

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.FREQUENCY_FORMAT_MAPPING)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, frequencyFormatMapping);
                    break;
                case XmlConstants.FREQUENCY_ID:
                    String freqId = reader.getElementText();
                    if (XmlReaderUtils.isNotEmptyOrNullElementText(freqId)) {
                        frequencyFormatMapping.setFrequencyCode(freqId);
                    }
                    break;
                case XmlConstants.DATE_PATTERN:
                    String datePattern = reader.getElementText();
                    if (XmlReaderUtils.isNotEmptyOrNullElementText(datePattern)) {
                        frequencyFormatMapping.setDatePattern(datePattern);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("FrequencyFormatMapping " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        frequencyFormatMappings.add(frequencyFormatMapping);
    }

    private void setDatePatternMap(XMLStreamReader reader, List<DatePatternMap> datePatternMaps) throws URISyntaxException, XMLStreamException {
        var datePatternMap = new DatePatternMapImpl();
        readDateMapAttributes(reader, datePatternMap);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.SOURCE_PATTERN))
            .ifPresent(datePatternMap::setSourcePattern);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.LOCALE))
            .ifPresent(datePatternMap::setLocale);

        setDateMapComponents(reader, datePatternMap, datePatternMapIdWithFrequencyIds, mappedComponentsDatePattern);
        datePatternMaps.add(datePatternMap);
    }

    private void setEpochMap(XMLStreamReader reader, List<EpochMap> epochMaps) throws URISyntaxException, XMLStreamException {
        var epochMap = new EpochMapImpl();
        readDateMapAttributes(reader, epochMap);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.BASE_PERIOD))
            .map(Instant::parse)
            .ifPresent(epochMap::setBasePeriod);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.EPOCH_PERIOD))
            .map(XmlConstants.STRING_EPOCH_PERIOD_MAP::get)
            .ifPresent(epochMap::setEpochPeriod);

        setDateMapComponents(reader, epochMap, epochMapIdWithFrequencyIds, mappedComponentsEpoch);
        epochMaps.add(epochMap);
    }

    private void setDateMapComponents(XMLStreamReader reader, DateMapImpl dateMap,
                                      Map<DateMap, List<String>> dateMapWithFreqIds,
                                      HashMap<DateMap, MappedComponent> mappedComponentsWithDateMap
    ) throws XMLStreamException, URISyntaxException {
        List<String> sources = new ArrayList<>();
        List<String> targets = new ArrayList<>();
        List<String> mappedFrequencyIds = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.EPOCH_MAP, XmlConstants.DATE_PATTERN_MAP)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, dateMap);
                    break;
                case XmlConstants.SOURCE:
                    addSource(reader, sources);
                    break;
                case XmlConstants.TARGET:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(targets::add);
                    break;
                case XmlConstants.FREQUENCY_DIMENSION:
                    setFreqDimension(reader, dateMap);
                    break;
                case XmlConstants.MAPPED_FREQUENCIES:
                    Optional.ofNullable(reader.getElementText())
                        .ifPresent(mappedFrequencyIds::add);
                    break;
                case XmlConstants.TARGET_FREQUENCY_ID:
                    setTargetFrequency(reader, dateMap);
                    break;
                default:
                    throw new IllegalArgumentException("EpochMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        dateMapWithFreqIds.put(dateMap, mappedFrequencyIds);
        List<MappedComponent> mComponents = new ArrayList<>(getMappedComponents(sources, targets));
        if (CollectionUtils.isNotEmpty(mComponents)) {
            mappedComponentsWithDateMap.put(dateMap, mComponents.get(0));
        }
    }

    private void setTargetFrequency(XMLStreamReader reader, DateMapImpl dateMap) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .ifPresent(dateMap::setTargetFrequencyId);
    }

    private void setFreqDimension(XMLStreamReader reader, DateMapImpl dateMap) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .ifPresent(dateMap::setFrequencyDimension);
    }

    private List<MappedComponent> getMappedComponents(List<String> sources, List<String> targets) {
        List<MappedComponent> mappedComp = new ArrayList<>();
        int sourcesSize = sources.size();
        int targetsSize = targets.size();
        int minimumSize = Math.min(sourcesSize, targetsSize);
        for (int i = 0; i < minimumSize; i++) {
            var mappedComponent = new MappedComponent();
            mappedComponent.setTarget(targets.get(i));
            mappedComponent.setSource(sources.get(i));
            mappedComp.add(mappedComponent);
        }
        if (sourcesSize > minimumSize) {
            for (int i = minimumSize + 1; i < sourcesSize; i++) {
                var mappedComponent = new MappedComponent();
                mappedComponent.setSource(sources.get(i));
                mappedComp.add(mappedComponent);
            }
        }

        if (targets.size() > minimumSize) {
            for (int i = minimumSize + 1; i < targetsSize; i++) {
                var mappedComponent = new MappedComponent();
                mappedComponent.setTarget(targets.get(i));
                mappedComp.add(mappedComponent);
            }
        }

        return CollectionUtils.isNotEmpty(mappedComp) ? mappedComp : Collections.emptyList();
    }

    private void readDateMapAttributes(XMLStreamReader reader, DateMapImpl epochMap) throws URISyntaxException {
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(epochMap::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            epochMap.setUri(new URI(uri));
        }

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.RESOLVE_PERIOD))
            .map(XmlConstants.STRING_RESOLVE_PERIOD_MAP::get)
            .ifPresent(epochMap::setResolvePeriod);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, StructureMapImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected void clean() {
        componentMaps.clear();
        mappedComponentsDatePattern.clear();
        mappedComponentsEpoch.clear();
        epochMaps.clear();
        datePatternMaps.clear();
        frequencyFormatMappings.clear();
        epochMapIdWithFrequencyIds.clear();
        datePatternMapIdWithFrequencyIds.clear();
    }
}
