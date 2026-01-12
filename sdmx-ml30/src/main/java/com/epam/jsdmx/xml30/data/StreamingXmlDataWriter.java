package com.epam.jsdmx.xml30.data;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.serializer.sdmx30.common.ActionType;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetHeader;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetStructureReference;
import com.epam.jsdmx.serializer.sdmx30.common.MetadataAttributeValue;
import com.epam.jsdmx.serializer.sdmx30.common.ProvisionAgreement;
import com.epam.jsdmx.xml30.structure.writer.HeaderWriter;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class StreamingXmlDataWriter implements XmlDataWriter {
    public static final String STRUCTURE_SPECIFIC_DATA = "StructureSpecificData";
    public static final String DATA_SET = "DataSet";
    public static final String STRUCTURE_REF = "structureRef";
    public static final String OBS = "Obs";
    public static final String SERIES = "Series";
    public static final String GROUP = "Group";
    public static final String ATTS = "Atts";
    public static final String ATTRIBUTE = "Attribute";
    public static final String ACTION = "action";
    public static final String VALID_FROM_DATE = "validFromDate";
    public static final String VALID_TO_DATE = "validToDate";
    public static final String METADATA = "Metadata";
    public static final String SCHEMAS_V_3_0_COMMON = "http://www.sdmx.org/resources/sdmxml/schemas/v3_0/common";
    public static final String XML_V_3_0_METADATA = "http://www.sdmx.org/resources/sdmxml/schemas/v3_0/metadata/generic";
    public static final String XML_XS = "http://www.w3.org/2001/XMLSchema";
    public static final String ALL_DIMENSIONS = "AllDimensions";
    public static final String SS = "ss:";
    public static final String COMP = "Comp";

    private boolean startSeries = false;
    private boolean startGroup = false;
    private boolean seriesAttribute = false;
    private boolean currentDataSetFlat;
    private boolean isFirstDataset = true;

    private Boolean isFirstSerie = true;
    private Boolean isFirstGroup = true;

    private Map<String, Boolean> multivaluedMap = new HashMap<>();
    private Map<String, Boolean> metaAttributeIds = new HashMap<>();
    private Map<String, String> attributesOfDataset = new HashMap<>();
    private Map<String, List<?>> attributesOfDatasetComp = new HashMap<>();
    private Map<String, String> attributesOfSeries = new HashMap<>();
    private Map<String, List<?>> attributesOfSeriesComp = new HashMap<>();
    private Map<String, String> attributesOfGroup = new HashMap<>();
    private Map<String, List<?>> attributesOfGroupComp = new HashMap<>();
    private Map<String, String> attributesOfObservation = new HashMap<>();
    private Map<String, List<?>> attributesOfObservationComp = new HashMap<>();
    private Map<String, List<?>> metaAttributesOfDatasetComp = new HashMap<>();
    private Map<String, List<?>> metaAttributesOfSeriesComp = new HashMap<>();
    private Map<String, List<?>> metaAttributesOfGroupComp = new HashMap<>();
    private Map<String, List<?>> metaAttributesOfObservationComp = new HashMap<>();
    private Map<String, String> observations = new HashMap<>();
    private Map<Integer, Map<String, String>> oneObservationMap = new HashMap<>();
    private Map<Integer, Map<String, List<?>>> oneObservationMapComp = new HashMap<>();
    private Map<String, Integer> dimensionOrder = new HashMap<>();
    private DataStructureDefinition dataStructureDefinition;

    private int observationNumber;
    private final XMLStreamWriter writer;
    private final HeaderWriter headerWriter;
    private boolean dataPresent;
    private String dimensionAtObservation;
    private String[] currentGroupId;

    public StreamingXmlDataWriter(XMLStreamWriter writer, HeaderWriter headerWriter) {
        this.writer = writer;
        this.headerWriter = headerWriter;
        dataPresent = false;
    }

    @Override
    @SneakyThrows
    public void startDataset(ProvisionAgreement agreement, Artefacts artefacts, DatasetHeader header) {
        dataStructureDefinition = CollectionUtils.extractSingleton(artefacts.getDataStructures());
        multivaluedMap.clear();
        dataStructureDefinition.getDataAttributes()
            .stream()
            .filter(Objects::nonNull)
            .forEach(attr -> multivaluedMap.put(attr.getId(), attr.getMaxOccurs() > 1));
        dataStructureDefinition.getMetaAttributeReferences()
            .stream()
            .filter(Objects::nonNull)
            .forEach(attr -> {
                multivaluedMap.put(attr.getId(), true);
                metaAttributeIds.put(attr.getId(), true);
            });

        final var dimensionPosition = new AtomicInteger();
        final List<DimensionComponent> dimensions = dataStructureDefinition.getDimensions();
        dimensions
            .forEach(dim -> dimensionOrder.put(dim.getId(), dimensionPosition.getAndIncrement()));

        DatasetStructureReference datasetStructureReference = header.getDatasetStructureReference();
        ArtefactReference dataStructureReference = datasetStructureReference.getDataStructureReference();
        dimensionAtObservation = datasetStructureReference.getDimensionAtObservation();

        closePreviousDataset(header);
        dataPresent = true;
        writeDataSet(dataStructureReference, header);
        currentDataSetFlat = ALL_DIMENSIONS.equals(dimensionAtObservation);
        if (dataStructureDefinition.getTimeDimension() != null) {
            currentGroupId = new String[dimensions.size() - 1];
        } else {
            currentGroupId = new String[dimensions.size()];
        }
    }

    private void writeHeader(DatasetHeader header) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.HEADER);
        headerWriter.writeHeaderElements(writer, headerWriter.getDefaultHeaderProvider().provide());
        writeStructure(writer, header);
        writer.writeEndElement();
    }

    @SneakyThrows
    private void closePreviousDataset(DatasetHeader header) {
        if (isFirstDataset) {
            writer.writeStartDocument();
            writer.writeStartElement(XmlConstants.MESSAGE + STRUCTURE_SPECIFIC_DATA);
            writeNameSpaces();
            writeHeader(header);
            isFirstDataset = false;
        } else {
            if (dataPresent) {
                if (!startSeries && !startGroup) {
                    writeDatasetAttributes();
                }
                if (startSeries) {
                    closeSeries();
                }
                if (startGroup) {
                    closeGroup();
                }
            }
            writer.writeEndElement();
        }
    }

    private void writeStructure(XMLStreamWriter writer, DatasetHeader datasetHeader) throws XMLStreamException {
        if (datasetHeader != null && datasetHeader.getDatasetStructureReference() != null) {
            DatasetStructureReference datasetStructureReference = datasetHeader.getDatasetStructureReference();
            ArtefactReference dataStructureReference = datasetStructureReference.getDataStructureReference();
            if (dataStructureReference != null) {
                writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.STRUCTURE_ELEMENT);
                String structureRef = getStructureRef(dataStructureReference);
                writer.writeAttribute(XmlConstants.STRUCTURE_ID, structureRef);
                writer.writeAttribute(XmlConstants.NAMESPACE, dataStructureReference.getMaintainableArtefactReference().getUrn());
                writer.writeAttribute(XmlConstants.DIMENSION_AT_OBSERVATION, datasetStructureReference.getDimensionAtObservation());
                writer.writeStartElement(XmlConstants.COMMON + XmlConstants.STRUCTURE_ELEMENT);
                writer.writeCharacters(dataStructureReference.getMaintainableArtefactReference().getUrn());
                writer.writeEndElement();
                writer.writeEndElement();
            }
        }
    }

    private void writeDataSet(ArtefactReference dataStructureReference, DatasetHeader header) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.MESSAGE + DATA_SET);
        String structureRef = getStructureRef(dataStructureReference);
        ActionType action = header.getAction();
        Instant validFrom = header.getValidFrom();
        Instant validTo = header.getValidTo();
        if (structureRef != null) {
            writer.writeAttribute(SS + STRUCTURE_REF, structureRef);
        }
        if (action != null) {
            writer.writeAttribute(ACTION, action.getValue());
        }
        if (validFrom != null) {
            writer.writeAttribute(VALID_FROM_DATE, validFrom.toString());
        }
        if (validTo != null) {
            writer.writeAttribute(VALID_TO_DATE, validTo.toString());
        }
    }

    private static String getStructureRef(ArtefactReference r) {
        if (r == null) {
            return null;
        }
        return r.getOrganisationId() + "." + r.getId() + "." + r.getVersion();
    }

    private void writeNameSpaces() throws XMLStreamException {
        writer.writeNamespace("metadata", XML_V_3_0_METADATA);
        writer.writeNamespace("common", SCHEMAS_V_3_0_COMMON);
        writer.writeNamespace("xs", XML_XS);
        writer.writeNamespace("message", XmlConstants.SCHEMAS_V_3_0_MESSAGE);
        writer.writeNamespace("xsi", XmlConstants.XMLSCHEMA_INSTANCE);
        writer.writeNamespace("xml", XmlConstants.XML_1998_NAMESPACE);
        writer.writeNamespace("ss", XmlConstants.SS_NAMESPACE);
        writer.writeNamespace("ns1", getCustomNamespace(dataStructureDefinition, dimensionAtObservation));

        writer.writeAttribute("xsi:schemaLocation", "http://www.sdmx.org/resources/sdmxml/schemas/v3_0/message ../../schemas/SDMXMessage.xsd");
    }

    private static String getCustomNamespace(DataStructureDefinition dsd, String dimAtObs) {
        return dsd.getUrn() + ":ObsLevelDim:" + dimAtObs;
    }

    @Override
    @SneakyThrows
    public void startSeries(Annotation... annotations) {
        writeDatasetAttributes();
        if (startGroup) {
            closeGroup();
            startGroup = false;
        }
        if (currentDataSetFlat) {
            if (Boolean.FALSE.equals(isFirstSerie)) {
                closeSeries();
            }
            writer.writeStartElement(OBS);
            startSeries = true;
            isFirstSerie = false;
            seriesAttribute = true;
        } else {
            if (Boolean.FALSE.equals(isFirstSerie)) {
                closeSeries();
            }
            writer.writeStartElement(SERIES);
            startSeries = true;
            isFirstSerie = false;
            seriesAttribute = true;
        }
    }

    @SneakyThrows
    protected void closeSeries() {
        if (currentDataSetFlat) {
            closeFlatSeries();
        } else {
            writeSeriesAttributes();
            if (!attributesOfSeriesComp.isEmpty()) {
                writeAttributeComponents(attributesOfSeriesComp);
            }

            if (!metaAttributesOfSeriesComp.isEmpty()) {
                writer.writeStartElement(METADATA);
                writeMetaAttributeComponents(metaAttributesOfSeriesComp);
                writer.writeEndElement();
            }

            attributesOfSeriesComp.clear();
            metaAttributesOfSeriesComp.clear();
            writePreviousObservation();
            writer.writeEndElement();
        }
        startSeries = false;
    }

    private void closeFlatSeries() throws XMLStreamException {
        writeSeriesAttributes();

        for (Map.Entry<Integer, Map<String, String>> obs : oneObservationMap.entrySet()) {
            writeAttributes(obs.getValue());
            writeCompComponents(obs);
        }

        if (!attributesOfObservation.isEmpty()) {
            writeAttributes(attributesOfObservation);
        }

        if (!attributesOfObservationComp.isEmpty()) {
            writeAttributeComponents(attributesOfObservationComp);
        }

        if (!attributesOfSeriesComp.isEmpty()) {
            writeAttributeComponents(attributesOfSeriesComp);
        }

        if (!metaAttributesOfObservationComp.isEmpty() || !metaAttributesOfSeriesComp.isEmpty()) {
            writer.writeStartElement(METADATA);
            if (!metaAttributesOfObservationComp.isEmpty()) {
                writeMetaAttributeComponents(metaAttributesOfObservationComp);
            }

            if (!metaAttributesOfSeriesComp.isEmpty()) {
                writeMetaAttributeComponents(metaAttributesOfSeriesComp);
            }
            writer.writeEndElement();
        }

        attributesOfObservationComp.clear();
        attributesOfSeriesComp.clear();
        attributesOfObservation.clear();
        observationNumber = 0;
        oneObservationMap.clear();
        oneObservationMapComp.clear();
        metaAttributesOfObservationComp.clear();
        metaAttributesOfSeriesComp.clear();
        observations.clear();
        writer.writeEndElement();
    }

    private void writeCompComponents(Map.Entry<Integer, Map<String, String>> obs) throws XMLStreamException {
        Map<String, List<?>> obsComp = new HashMap<>();
        Map<String, List<?>> metaObsComp = new HashMap<>();
        if (oneObservationMapComp.containsKey(obs.getKey())) {
            for (var ob : oneObservationMapComp.get(obs.getKey()).entrySet()) {
                if (!metaAttributeIds.getOrDefault(ob.getKey(), false)) {
                    obsComp.put(ob.getKey(), ob.getValue());
                } else {
                    metaObsComp.put(ob.getKey(), ob.getValue());
                }
            }
        }

        writeAttributeComponents(obsComp);
        if (!attributesOfObservationComp.isEmpty()) {
            writeAttributeComponents(attributesOfObservationComp);
        }

        if (!metaObsComp.isEmpty() || !metaAttributesOfObservationComp.isEmpty()) {
            writer.writeStartElement(METADATA);
            writeMetaAttributeComponents(metaObsComp);
            writeMetaAttributeComponents(metaAttributesOfObservationComp);
            writer.writeEndElement();
        }
    }

    private void writeAttributes(Map<String, String> attributes) throws XMLStreamException {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            writer.writeAttribute(entry.getKey(), entry.getValue());
        }
    }

    @SneakyThrows
    protected void closeGroup() {
        writeGroupAttributes();
        writer.writeEndElement();
    }

    private String getGroupIdString() {
        StringJoiner joiner = new StringJoiner("__");
        for (String groupDimension : currentGroupId) {
            if (groupDimension != null) {
                joiner.add(groupDimension);
            }
        }
        return joiner.toString();
    }

    @SneakyThrows
    private void writeGroupAttributes() {
        writeAttributes(attributesOfGroup);
        writer.writeAttribute("ns1:type", "GROUP_" + getGroupIdString());

        if (!attributesOfGroupComp.isEmpty()) {
            writeAttributeComponents(attributesOfGroupComp);
        }
        if (!metaAttributesOfGroupComp.isEmpty()) {
            writer.writeStartElement(METADATA);
            writeMetaAttributeComponents(metaAttributesOfGroupComp);
            writer.writeEndElement();
        }
        attributesOfGroupComp.clear();
        metaAttributesOfGroupComp.clear();
    }

    @SneakyThrows
    private void writePreviousObservation() {
        for (Map.Entry<Integer, Map<String, String>> obs : oneObservationMap.entrySet()) {
            writer.writeStartElement(OBS);

            writeAttributes(obs.getValue());

            if (attributesOfObservation.isEmpty()) {
                writeAttributes(attributesOfObservation);
            }

            writeCompComponents(obs);
            writer.writeEndElement();
        }
        attributesOfObservation.clear();
        attributesOfObservationComp.clear();
        observationNumber = 0;
        oneObservationMap.clear();
        oneObservationMapComp.clear();
        observations.clear();
    }

    @Override
    @SneakyThrows
    public void writeSeriesComponent(String concept, String code) {
        writer.writeAttribute(concept, code);
    }

    @Override
    @SneakyThrows
    public void startGroup(Annotation... annotations) {
        writeDatasetAttributes();
        if (startSeries) {
            closeSeries();
            startSeries = false;
        }
        if (Boolean.FALSE.equals(isFirstGroup)) {
            closeGroup();
        }
        isFirstGroup = false;
        writer.writeStartElement(GROUP);
        startGroup = true;
        clearCurrentGroupId();
    }

    private void clearCurrentGroupId() {
        Arrays.fill(currentGroupId, null);
    }

    @Override
    @SneakyThrows
    public void writeGroupComponent(String concept, String code) {
        Integer dimIndex = requireNonNull(dimensionOrder.get(concept), concept + " index was not found");
        currentGroupId[dimIndex] = concept;
        writer.writeAttribute(concept, code);
    }

    @Override
    public void writeObservation(String obsConceptValue, Map<String, String> measureValues, Annotation[] annotations30) {
        writeObservation(null, obsConceptValue, measureValues, annotations30);
    }

    @Override
    @SneakyThrows
    public void writeObservation(String dimAtObsForWrite, String obsConceptValue, Map<String, String> measureValues, Annotation[] annotations30) {
        observationNumber++;
        if (currentDataSetFlat) {
            if (dimAtObsForWrite != null && !StringUtils.EMPTY.equals(dimAtObsForWrite)) {
                writer.writeAttribute(dimAtObsForWrite, obsConceptValue);
            }

            if (!measureValues.isEmpty()) {
                writeAttributes(measureValues);
            }
        } else if (startSeries) {
            seriesAttribute = false;

            if (dimAtObsForWrite != null && !StringUtils.EMPTY.equals(dimAtObsForWrite)) {
                observations.put(dimAtObsForWrite, obsConceptValue);
            }

            if (!measureValues.isEmpty()) {
                observations.putAll(measureValues);
            }

            oneObservationMap.put(observationNumber, new HashMap<>(observations));
        }
    }

    @SneakyThrows
    private void writeSeriesAttributes() {
        if (!attributesOfSeries.isEmpty()) {
            writeAttributes(attributesOfSeries);
        }

        attributesOfSeries.clear();
    }

    @Override
    @SneakyThrows
    public void writeAttribute(String concept, String code) {
        boolean isMultiValued = multivaluedMap.getOrDefault(concept, false);
        boolean isMetaConcept = metaAttributeIds.getOrDefault(concept, false);
        if (startSeries && currentDataSetFlat) {
            fillStartSeriesDatasetFlat(concept, code, isMultiValued, isMetaConcept);
        } else if (startSeries) {
            fillStartSeriesAttribute(concept, code, isMultiValued, isMetaConcept);
        } else if (startGroup) {
            if (isMultiValued) {
                if (isMetaConcept) {
                    metaAttributesOfGroupComp.put(concept, List.of(code));
                } else {
                    attributesOfGroupComp.put(concept, List.of(code));
                }
            } else {
                attributesOfGroup.put(concept, code);
            }
        } else {
            if (isMultiValued) {
                if (isMetaConcept) {
                    metaAttributesOfDatasetComp.put(concept, List.of(code));
                } else {
                    attributesOfDatasetComp.put(concept, List.of(code));
                }
            } else {
                attributesOfDataset.put(concept, code);
            }
        }
    }

    @Override
    @SneakyThrows
    public void writeAttribute(String concept, List<String> code) {
        if (CollectionUtils.isEmpty(code)) {
            return;
        }

        boolean isMetaConcept = metaAttributeIds.getOrDefault(concept, false);
        if (startSeries) {
            fillStartSeriesAttribute(concept, code, isMetaConcept);
        } else if (startGroup) {
            if (isMetaConcept) {
                metaAttributesOfGroupComp.put(concept, code);
            } else {
                attributesOfGroupComp.put(concept, code);
            }
        } else {
            if (isMetaConcept) {
                metaAttributesOfDatasetComp.put(concept, code);
            } else {
                attributesOfDatasetComp.put(concept, code);
            }
        }
    }

    @Override
    public void writeAttribute(String concept, InternationalString value) {
        writeInternationalAttributes(concept, List.of(value));
    }

    @Override
    @SneakyThrows
    public void writeInternationalAttributes(String concept, List<InternationalString> values) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        boolean isMetaConcept = metaAttributeIds.getOrDefault(concept, false);
        if (startSeries) {
            fillStartSeriesAttribute(concept, values, true, isMetaConcept);
        } else if (startGroup) {
            if (isMetaConcept) {
                metaAttributesOfGroupComp.put(concept, values);
            } else {
                attributesOfGroupComp.put(concept, values);
            }
        } else {
            if (isMetaConcept) {
                metaAttributesOfDatasetComp.put(concept, values);
            } else {
                attributesOfDatasetComp.put(concept, values);
            }
        }
    }

    @Override
    @SneakyThrows
    public void writeMetaData(MetadataAttributeValue metadataAttributeValue) {
        boolean isMultivalued = multivaluedMap.getOrDefault(metadataAttributeValue.getId(), false);
        boolean isMetaConcept = metaAttributeIds.getOrDefault(metadataAttributeValue.getId(), false);
        final List<String> stringValues = metadataAttributeValue.getStringValues();
        if (startSeries) {
            if (currentDataSetFlat) {
                if (isMultivalued) {
                    if (isMetaConcept) {
                        metaAttributesOfObservationComp.put(metadataAttributeValue.getId(), stringValues);
                    } else {
                        attributesOfObservationComp.put(metadataAttributeValue.getId(), stringValues);
                    }
                } else if (!stringValues.isEmpty()) {
                    attributesOfObservation.put(metadataAttributeValue.getId(), stringValues.get(0));
                }
            } else {
                fillStartSeriesAttribute(metadataAttributeValue.getId(), metadataAttributeValue.getStringValues(), isMetaConcept);
            }
        } else {
            if (startGroup) {
                if (isMultivalued) {
                    if (isMetaConcept) {
                        metaAttributesOfGroupComp.put(metadataAttributeValue.getId(), stringValues);
                    } else {
                        attributesOfGroupComp.put(metadataAttributeValue.getId(), stringValues);
                    }
                } else if (!stringValues.isEmpty()) {
                    attributesOfGroup.put(metadataAttributeValue.getId(), stringValues.get(0));
                }
            } else {
                if (isMultivalued) {
                    if (isMetaConcept) {
                        metaAttributesOfDatasetComp.put(metadataAttributeValue.getId(), stringValues);
                    } else {
                        attributesOfDatasetComp.put(metadataAttributeValue.getId(), stringValues);
                    }
                } else if (!stringValues.isEmpty()) {
                    attributesOfDataset.put(metadataAttributeValue.getId(), stringValues.get(0));
                }
            }
        }
    }

    private void fillStartSeriesAttribute(String concept, Object code, boolean isMultiValued, boolean isMeta) {
        if (seriesAttribute) {
            if (isMultiValued) {
                if (isMeta) {
                    metaAttributesOfSeriesComp.put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
                } else {
                    attributesOfSeriesComp.put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
                }
            } else {
                attributesOfSeries.put(concept, Objects.toString(code));
            }
        } else {
            fillObservationAttribute(concept, code, isMultiValued);
        }
    }

    private void fillStartSeriesAttribute(String concept, List<String> code, boolean isMeta) {
        if (seriesAttribute) {
            if (isMeta) {
                metaAttributesOfSeriesComp.put(concept, code);
            } else {
                attributesOfSeriesComp.put(concept, code);
            }
        } else {
            fillObservationAttribute(concept, code);
        }
    }

    private void fillObservationAttribute(String concept, Object code, boolean isMultiValued) {
        if (isMultiValued) {
            if (oneObservationMapComp.containsKey(observationNumber)) {
                oneObservationMapComp.get(observationNumber).put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
            } else {
                HashMap<String, List<?>> map = new HashMap<>();
                map.put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
                oneObservationMapComp.put(observationNumber, map);
            }
        } else {
            if (oneObservationMap.containsKey(observationNumber)) {
                oneObservationMap.get(observationNumber).put(concept, Objects.toString(code));
            } else {
                HashMap<String, String> map = new HashMap<>();
                map.put(concept, Objects.toString(code));
                oneObservationMap.put(observationNumber, map);
            }
        }
    }

    private void fillObservationAttribute(String concept, List<String> code) {
        if (oneObservationMapComp.containsKey(observationNumber)) {
            oneObservationMapComp.get(observationNumber).put(concept, code);
        } else {
            HashMap<String, List<?>> map = new HashMap<>();
            map.put(concept, code);
            oneObservationMapComp.put(observationNumber, map);
        }
    }

    private void fillStartSeriesDatasetFlat(String concept, Object code, boolean isMultiValued, boolean isMeta) {
        if (observationNumber != 0) {
            fillObservationAttribute(concept, code, isMultiValued);
        } else {
            if (isMultiValued) {
                if (isMeta) {
                    metaAttributesOfObservationComp.put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
                } else {
                    attributesOfObservationComp.put(concept, code instanceof String ? List.of(code) : getAttributeValue(code));
                }
            } else {
                attributesOfObservation.put(concept, code.toString());
            }
        }
    }

    private <T> List<?> getAttributeValue(T code) {
        if (code instanceof List<?>) {
            List<?> list = (List<?>) code;
            if (!list.isEmpty()) {
                if (list.get(0) instanceof String) {
                    return getListOfStrings(list);
                } else if (list.get(0) instanceof InternationalString) {
                    return getListOfInternationalStrings(list);
                }
            } else {
                return list;
            }
        }

        return Collections.emptyList();
    }

    private List<String> getListOfStrings(List<?> list) {
        return list.stream().map(Objects::toString).collect(Collectors.toList());
    }

    private List<InternationalString> getListOfInternationalStrings(List<?> list) {
        return list.stream().map(value -> (InternationalString) value).collect(Collectors.toList());
    }


    @SneakyThrows
    private void writeDatasetAttributes() {
        if (!attributesOfDataset.isEmpty() || !attributesOfDatasetComp.isEmpty()) {
            writer.writeStartElement(ATTS);
            for (var entry : attributesOfDataset.entrySet()) {
                writer.writeAttribute(entry.getKey(), entry.getValue());
            }
            if (!attributesOfDatasetComp.isEmpty()) {
                writeAttributeComponents(attributesOfDatasetComp);
            }
            writer.writeEndElement();
        }

        if (!metaAttributesOfDatasetComp.isEmpty()) {
            writer.writeStartElement(METADATA);
            writeMetaAttributeComponents(metaAttributesOfDatasetComp);
            writer.writeEndElement();
        }

        attributesOfDataset.clear();
        attributesOfDatasetComp.clear();
        metaAttributesOfDatasetComp.clear();
    }

    private void writeAttributeComponents(Map<String, List<?>> map) throws XMLStreamException {
        writeComponents(map, false);
    }

    private void writeMetaAttributeComponents(Map<String, List<?>> map) throws XMLStreamException {
        writeComponents(map, true);
    }

    private void writeComponents(Map<String, List<?>> map, boolean isMetaConcept) throws XMLStreamException {
        for (var attrComp : map.entrySet()) {
            if (attrComp.getValue().get(0) instanceof String) {
                var valuesList = getListOfStrings(attrComp.getValue());
                if (isMetaConcept) {
                    writeMetaComp(attrComp.getKey(), valuesList);
                } else {
                    writeComp(attrComp.getKey(), valuesList);
                }
            } else {
                var valuesList = getListOfInternationalStrings(attrComp.getValue());
                writeInternationalStringComp(attrComp.getKey(), valuesList, isMetaConcept);
            }
        }
    }

    private void writeComp(String id, List<String> values) throws XMLStreamException {
        if (!isStringCollectionEmpty(values)) {
            writer.writeStartElement(COMP);
            writer.writeAttribute(XmlConstants.ID, id);
            for (String value : values) {
                if (StringUtils.isNotEmpty(value)) {
                    writer.writeStartElement(XmlConstants.VALUE);
                    writer.writeCharacters(value);
                    writer.writeEndElement();
                }
            }
            writer.writeEndElement();
        }
    }

    private void writeMetaComp(String id, List<String> values) throws XMLStreamException {
        if (!isStringCollectionEmpty(values)) {
            if (metaAttributeIds.getOrDefault(id, false)) {
                writer.writeStartElement(ATTRIBUTE);
                writer.writeAttribute(XmlConstants.ID, id);
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        writer.writeStartElement(XmlConstants.VALUE);
                        writer.writeCharacters(value);
                        writer.writeEndElement();
                    }
                }
                writer.writeEndElement();
            }
        }
    }

    @SneakyThrows
    private void writeInternationalStringComp(String concept, List<InternationalString> valuesList, boolean isMetaConcept) {
        if (isMetaConcept) {
            writer.writeStartElement(ATTRIBUTE);
        } else {
            writer.writeStartElement(COMP);
        }
        writer.writeAttribute(XmlConstants.ID, concept);
        for (var values : valuesList) {
            writer.writeStartElement(XmlConstants.VALUE);
            values.getAll().forEach((key, value) -> {
                try {
                    writer.writeStartElement(XmlConstants.COMMON + XmlConstants.TEXT);
                    writer.writeAttribute(XmlConstants.XML_LANG, key);
                    writer.writeCharacters(value);
                    writer.writeEndElement();
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    private boolean isStringCollectionEmpty(List<String> values) {
        return CollectionUtils.isEmpty(values)
               || values.stream().noneMatch(StringUtils::isNotEmpty);
    }

    @Override
    @SneakyThrows
    public void close() {
        if (dataPresent) {
            writeDatasetAttributes();
            if (startSeries) {
                closeSeries();
            }
            if (startGroup) {
                closeGroup();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            dataPresent = false;
        }
        writer.close();
    }
}
