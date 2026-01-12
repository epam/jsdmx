package com.epam.jsdmx.xml30.structure.writer;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.DateMap;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochMap;
import com.epam.jsdmx.infomodel.sdmx30.EpochPeriodType;
import com.epam.jsdmx.infomodel.sdmx30.FixedValueMap;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMapping;
import com.epam.jsdmx.infomodel.sdmx30.MappingRoleType;
import com.epam.jsdmx.infomodel.sdmx30.ResolvePeriod;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.xml30.structure.reader.MappedComponent;
import com.epam.jsdmx.xml30.structure.reader.StructureMapUtils;

import org.apache.commons.collections.CollectionUtils;

public class StructureMapWriter extends XmlWriter<StructureMap> {

    public StructureMapWriter(NameableWriter nameableWriter,
                              AnnotableWriter annotableWriter,
                              CommonAttributesWriter commonAttributesWriter,
                              LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(StructureMap structureMap, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(structureMap, writer);
    }

    @Override
    protected void writeCustomAttributeElements(StructureMap structureMap, XMLStreamWriter writer) throws XMLStreamException {

        ArtefactReference source = structureMap.getSource();
        writeArtefactReference(writer, source, XmlConstants.SOURCE);

        ArtefactReference target = structureMap.getTarget();
        writeArtefactReference(writer, target, XmlConstants.TARGET);

        List<ComponentMap> componentMaps = structureMap.getComponentMaps();

        List<EpochMap> epochMaps = structureMap.getEpochMaps();
        if (CollectionUtils.isNotEmpty(epochMaps)) {
            Map<EpochMap, List<MappedComponent>> epochWithMappedComponent = StructureMapUtils.getEpochWithMappedComponent(
                epochMaps,
                componentMaps
            );
            writeEpochMap(writer, epochWithMappedComponent);
        }

        List<DatePatternMap> datePatternMaps = structureMap.getDatePatternMaps();
        if (CollectionUtils.isNotEmpty(datePatternMaps)) {
            Map<DatePatternMap, List<MappedComponent>> datePatternWithMappedComponent = StructureMapUtils.getDatePatternWithMappedComponent(
                datePatternMaps,
                componentMaps
            );
            writeDatePatternMap(writer, datePatternWithMappedComponent);
        }

        List<FrequencyFormatMapping> frequencyFormatMappings = StructureMapUtils.getFrequencyFormatMappings(epochMaps, datePatternMaps);
        if (CollectionUtils.isNotEmpty(frequencyFormatMappings)) {
            writeFrequencyFormatMapping(writer, frequencyFormatMappings);
        }

        List<ComponentMap> representationMap = StructureMapUtils.getRepresentationMap(structureMap);
        if (CollectionUtils.isNotEmpty(representationMap)) {
            writeComponentMap(writer, representationMap);
        }

        List<FixedValueMap> fixedComponentMaps = structureMap.getFixedComponentMaps();
        if (CollectionUtils.isNotEmpty(fixedComponentMaps)) {
            writeFixedValueMap(writer, fixedComponentMaps);
        }

    }

    @Override
    protected String getName() {
        return XmlConstants.STRUCTURE_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.STRUCTURE_MAPS;
    }

    @Override
    protected Set<StructureMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getStructureMaps();
    }

    private void writeComponentMap(XMLStreamWriter writer, List<ComponentMap> representationMap) throws XMLStreamException {
        for (ComponentMap componentMap : representationMap) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.COMPONENT_MAP);
            this.annotableWriter.write(componentMap, writer);

            for (String source : componentMap.getSource()) {
                writeSource(writer, source);
            }

            for (String target : componentMap.getTarget()) {
                writeTarget(writer, target);
            }

            ArtefactReference representationMapRef = componentMap.getRepresentationMap();
            if (representationMapRef != null) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.REPRESENTATION_MAP);
                XmlWriterUtils.writeCharacters(representationMapRef.getUrn(), writer);
                writer.writeEndElement();
            }

            writer.writeEndElement();
        }
    }

    private void writeFixedValueMap(XMLStreamWriter writer, List<FixedValueMap> fixedComponentMaps) throws XMLStreamException {
        for (FixedValueMap fixedValueMap : fixedComponentMaps) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.FIXED_VALUE_MAP);
            this.annotableWriter.write(fixedValueMap, writer);
            writeComponent(writer, fixedValueMap);
            String value = fixedValueMap.getValue();
            if (value != null) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.VALUE);
                writer.writeCharacters(value);
                writer.writeEndElement();
            }

            writer.writeEndElement();
        }
    }

    private void writeComponent(XMLStreamWriter writer, FixedValueMap fixedValueMap) throws XMLStreamException {
        String component = fixedValueMap.getComponent();
        if (component != null) {
            MappingRoleType role = fixedValueMap.getRole();
            if (MappingRoleType.SOURCE.equals(role)) {
                writeSource(writer, component);
            } else {
                writeTarget(writer, component);
            }
        }
    }

    private void writeFrequencyFormatMapping(XMLStreamWriter writer, List<FrequencyFormatMapping> frequencyFormatMappings) throws XMLStreamException {
        for (FrequencyFormatMapping frequencyFormatMapping : frequencyFormatMappings) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.FREQUENCY_FORMAT_MAPPING);
            XmlWriterUtils.writeIdUriAttributes(writer, frequencyFormatMapping.getId(), frequencyFormatMapping.getUri());
            if (frequencyFormatMapping.getContainer() != null) {
                writer.writeAttribute(XmlConstants.URN, frequencyFormatMapping.getUrn());
            }
            this.annotableWriter.write(frequencyFormatMapping, writer);
            String frequencyCode = frequencyFormatMapping.getFrequencyCode();
            if (frequencyCode != null) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.FREQUENCY_ID);
                writer.writeCharacters(frequencyCode);
                writer.writeEndElement();
            }

            String datePattern = frequencyFormatMapping.getDatePattern();
            if (datePattern != null) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATE_PATTERN);
                writer.writeCharacters(datePattern);
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    private void writeDatePatternMap(XMLStreamWriter writer, Map<DatePatternMap, List<MappedComponent>> datePatternMaps) throws XMLStreamException {
        for (Map.Entry<DatePatternMap, List<MappedComponent>> datePatternMap : datePatternMaps.entrySet()) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATE_PATTERN_MAP);

            writeDatePatternMapAttributes(writer, datePatternMap.getKey());
            writeDateMapElements(writer, datePatternMap.getKey(), datePatternMap.getValue());

            writer.writeEndElement();
        }
    }

    private void writeEpochMap(XMLStreamWriter writer, Map<EpochMap, List<MappedComponent>> epochMaps) throws XMLStreamException {
        for (Map.Entry<EpochMap, List<MappedComponent>> epochMap : epochMaps.entrySet()) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.EPOCH_MAP);

            writeEpochMapAttributes(writer, epochMap.getKey());
            writeDateMapElements(writer, epochMap.getKey(), epochMap.getValue());

            writer.writeEndElement();
        }
    }

    private void writeDateMapElements(XMLStreamWriter writer, DateMap dateMap, List<MappedComponent> mappedComponents) throws XMLStreamException {
        this.annotableWriter.write(dateMap, writer);
        writeSourceTarget(writer, mappedComponents);

        String frequencyDimension = dateMap.getFrequencyDimension();
        writeFrequency(writer, frequencyDimension, XmlConstants.FREQUENCY_DIMENSION);

        List<FrequencyFormatMapping> mappedFrequencies = dateMap.getMappedFrequencies();
        writeMappedFrequencies(writer, mappedFrequencies);

        String targetFrequencyId = dateMap.getTargetFrequencyId();
        writeFrequency(writer, targetFrequencyId, XmlConstants.TARGET_FREQUENCY_ID);
    }

    private void writeSourceTarget(XMLStreamWriter writer, List<MappedComponent> mappedComponents) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(mappedComponents)) {
            for (MappedComponent mappedComponent : mappedComponents) {
                String source = mappedComponent.getSource();
                writeSource(writer, source);

                String target = mappedComponent.getTarget();
                writeTarget(writer, target);
            }
        }
    }

    private void writeTarget(XMLStreamWriter writer, String target) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TARGET);
        XmlWriterUtils.writeCharacters(target, writer);
        writer.writeEndElement();
    }

    private void writeSource(XMLStreamWriter writer, String source) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.SOURCE);
        XmlWriterUtils.writeCharacters(source, writer);
        writer.writeEndElement();
    }

    private void writeMappedFrequencies(XMLStreamWriter writer, List<FrequencyFormatMapping> mappedFrequencies) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(mappedFrequencies)) {
            for (FrequencyFormatMapping mapping : mappedFrequencies) {
                if (mapping != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MAPPED_FREQUENCIES);
                    XmlWriterUtils.writeCharacters(mapping.getId(), writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeFrequency(XMLStreamWriter writer, String frequency, String frequencyName) throws XMLStreamException {
        if (frequency != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + frequencyName);
            writer.writeCharacters(frequency);
            writer.writeEndElement();
        }
    }

    private void writeEpochMapAttributes(XMLStreamWriter writer, EpochMap epochMap) throws XMLStreamException {
        writeCommonDateMapAttributes(
            writer,
            epochMap
        );

        Instant basePeriod = epochMap.getBasePeriod();
        if (basePeriod != null) {
            writer.writeAttribute(XmlConstants.BASE_PERIOD, basePeriod.toString());
        }

        EpochPeriodType epochPeriod = epochMap.getEpochPeriod();
        if (epochPeriod != null) {
            writer.writeAttribute(XmlConstants.EPOCH_PERIOD, XmlConstants.EPOCH_PERIOD_STRING_MAP.get(epochPeriod));
        }
    }

    private void writeDatePatternMapAttributes(XMLStreamWriter writer, DatePatternMap datePatternMap) throws XMLStreamException {
        writeCommonDateMapAttributes(writer, datePatternMap);

        String sourcePattern = datePatternMap.getSourcePattern();
        if (sourcePattern != null) {
            writer.writeAttribute(XmlConstants.SOURCE_PATTERN, sourcePattern);
        }

        String locale = datePatternMap.getLocale();
        if (locale != null) {
            writer.writeAttribute(XmlConstants.LOCALE, locale);
        }
    }

    private void writeCommonDateMapAttributes(XMLStreamWriter writer,
                                              DateMap dateMap) throws XMLStreamException {
        XmlWriterUtils.writeIdUriAttributes(writer, dateMap.getId(), dateMap.getUri());

        if (dateMap.getContainer() != null) {
            writer.writeAttribute(XmlConstants.URN, dateMap.getUrn());
        }

        ResolvePeriod resolvePeriod = dateMap.getResolvePeriod();
        if (resolvePeriod != null) {
            writer.writeAttribute(XmlConstants.RESOLVE_PERIOD, XmlConstants.RESOLVE_PERIOD_STRING_MAP.get(resolvePeriod));
        }
    }

    private void writeArtefactReference(XMLStreamWriter writer,
                                        ArtefactReference source,
                                        String artefactReferenceName) throws XMLStreamException {
        if (source != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + artefactReferenceName);
            XmlWriterUtils.writeCharacters(source.getUrn(), writer);
            writer.writeEndElement();
        }
    }
}