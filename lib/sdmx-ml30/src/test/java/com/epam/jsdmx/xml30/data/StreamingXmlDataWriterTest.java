package com.epam.jsdmx.xml30.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponentImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRefImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.serializer.sdmx30.common.ActionType;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetHeader;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetHeaderImpl;
import com.epam.jsdmx.serializer.sdmx30.common.DatasetStructureReferenceImpl;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.Header;
import com.epam.jsdmx.serializer.sdmx30.common.SimpleMetadataAttributeValue;
import com.epam.jsdmx.xml30.structure.writer.HeaderWriter;

import lombok.SneakyThrows;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class StreamingXmlDataWriterTest {

    public static final String DATASET_XML = "/xml/dataquery/xml_3.0_dataset.xml";
    public static final String DATASET_FLAT_XML = "/xml/dataquery/xml_3.0_dataset_flat.xml";
    public static final String DATASET_ATTR_XML = "/xml/dataquery/xml_3.0_data_query_dataset_atts.xml";
    public static final String SERIES_XML = "/xml/dataquery/xml_3.0_data_query_series.xml";
    public static final String SERIES_MULTI_XML = "/xml/dataquery/xml_3.0_data_query_series_multi.xml";
    public static final String SERIES_OBS_XML = "/xml/dataquery/xml_3.0_data_query_series_and_obs.xml";
    public static final String GROUPS_XML = "/xml/dataquery/xml_3.0_data_query_groups.xml";
    public static final String GROUPS_SERIES_XML = "/xml/dataquery/xml_3.0_data_query_groups_series.xml";
    public static final String GROUPS_OBS_XML = "/xml/dataquery/xml_3.0_data_query_groups_obs.xml";
    public static final String GROUPS_META_XML = "/xml/dataquery/xml_3.0_data_query_groups_meta.xml";
    public static final String OBS_XML = "/xml/dataquery/xml_3.0_data_query_obs.xml";
    public static final String MULTILINGUAL_ATTS_XML = "/xml/dataquery/xml_3.0_data_query_multiling_atts.xml";
    public static final String ATTS_SERIES_XML = "/xml/dataquery/xml_3.0_data_query_atts_series.xml";
    public static final String ATTS_OBS_XML = "/xml/dataquery/xml_3.0_data_query_atts_obs.xml";
    public static final String MULTILINGUAL_ATTS_OBS_XML = "/xml/dataquery/xml_3.0_data_query_multiling_atts_obs.xml";
    public static final String META_XML = "/xml/dataquery/xml_3.0_data_query_meta.xml";
    public static final String META2_XML = "/xml/dataquery/xml_3.0_data_query_meta2.xml";
    public static final String META_FLAT_XML = "/xml/dataquery/xml_3.0_data_query_meta_flat.xml";
    public static final String META_MULTI_XML = "/xml/dataquery/xml_3.0_data_query_meta_multi.xml";

    private StreamingXmlDataWriter streamingXmlDataWriter;
    private final DefaultHeaderProvider defaultHeaderProvider = mock(DefaultHeaderProvider.class);
    HeaderWriter headerWriter = new HeaderWriter(defaultHeaderProvider);

    @BeforeEach
    @SneakyThrows
    void setup() {
        Header header = new Header();
        header.setId("HED1");
        header.setName(new InternationalString("HeaderTest"));
        header.setTest(true);
        header.setReceivers(List.of(new Party("id", new InternationalString("str"))));
        header.setSender(new Party("id", new InternationalString()));
        header.setPrepared(Instant.parse("2023-04-17T10:11:16Z"));
        when(defaultHeaderProvider.provide()).thenReturn(header);
    }

    @Test
    void writeDatasets() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute1");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute3");
        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(DATASET_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeFlatDatasets() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute1");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute3");
        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(DATASET_FLAT_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeDatasetAtts() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeAttribute("ATTR2", List.of("attribute2", "attribute3"));
        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(DATASET_ATTR_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeSeries() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.writeObservation(null, Map.of("MM2", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(SERIES_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeSeriesMulti() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeAttribute("MULT", List.of("mult_value_1", "mult_value_2"));
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.writeAttribute("MULT", List.of("mult_value_3", "mult_value_4"));
        streamingXmlDataWriter.writeObservation(null, Map.of("MM2", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(SERIES_MULTI_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeSeriesAndObs() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("MULT", "mult1");
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension2");
        streamingXmlDataWriter.writeObservation(null, Map.of("MM2", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute22");
        streamingXmlDataWriter.writeAttribute("MULT", List.of("mult_value_1", "mult_value_2"));
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(SERIES_OBS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeGroups() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM2", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(GROUPS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeObs() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(OBS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeAtts() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute2");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(ATTS_SERIES_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMultilingualAttribute() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        InternationalString internationalString = new InternationalString(Map.of("en", "english", "fr", "french"));
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeAttribute("METAATTR", internationalString);
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR2", internationalString);
        streamingXmlDataWriter.writeAttribute("METAATTR_SERIES", internationalString);
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("METAATTR_OBS", internationalString);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(MULTILINGUAL_ATTS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeAttsFlat() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute2");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(ATTS_OBS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMultilingualAttributeFlat() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        InternationalString internationalString = new InternationalString(Map.of("en", "english", "fr", "french"));
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeInternationalAttributes("ATTR2", List.of(internationalString, internationalString));
        streamingXmlDataWriter.writeAttribute("ATT23R", List.of("attribute"));
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(MULTILINGUAL_ATTS_OBS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeGroupSeriesAtts() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM2", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute2");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(GROUPS_SERIES_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeGroupObsAtts() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM2", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension_");
        streamingXmlDataWriter.writeSeriesComponent("DIM2", "dimension__");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR1", "P");
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(GROUPS_OBS_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeGroupMetaAtts() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM2", "dimension2");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute2");
        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR2", "attribute2");
        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(GROUPS_META_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMetaByWriteMetaDataMethod() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);

        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("Multi");
        metadataAttributeValue.setValues(List.of("multi"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("gr_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        metadataAttributeValue.setId("METAATTR_SERIES");
        metadataAttributeValue.setValues(List.of("att_series"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");

        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("ATTR2", "obs_attribute");
        metadataAttributeValue.setId("METAATTR_OBS");
        metadataAttributeValue.setValues(List.of("att_obs", "att_obs2"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(META_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMetaByWriteAttributeMethod() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeader();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);
        streamingXmlDataWriter.writeAttribute("Multi", "multi");
        streamingXmlDataWriter.writeAttribute("METAATTR", "dataset_attr");

        streamingXmlDataWriter.startGroup();
        streamingXmlDataWriter.writeGroupComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("METAATTR", "gr_attr");

        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("METAATTR_SERIES", List.of("att_series"));
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");

        streamingXmlDataWriter.writeObservation("TIME_PERIOD", "2020", Map.of("MM", "measure"), null);
        streamingXmlDataWriter.writeAttribute("METAATTR_OBS", List.of("att_obs", "att_obs2"));
        streamingXmlDataWriter.writeAttribute("ATTR2", "obs_attribute");
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(META2_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMetaFlat() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);

        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("Multi");
        metadataAttributeValue.setValues(List.of("multi"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        metadataAttributeValue.setId("METAATTR_SERIES");
        metadataAttributeValue.setValues(List.of("att_series"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");

        streamingXmlDataWriter.writeObservation(null, Map.of("MM", "measure"), null);
        metadataAttributeValue.setId("METAATTR_OBS");
        metadataAttributeValue.setValues(List.of("att_obs", "att_obs2"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(META_FLAT_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeMultiMeta() throws XMLStreamException, IOException, SAXException {
        Artefacts artefacts = getArtefacts();
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        DatasetHeader datasetHeader = getDatasetHeaderAllDimensions();
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.startDataset(null, artefacts, datasetHeader);

        SimpleMetadataAttributeValue metadataAttributeValue = new SimpleMetadataAttributeValue();
        metadataAttributeValue.setId("METAATTR");
        metadataAttributeValue.setValues(List.of("dataset_attr", "dataset_attr2"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);

        streamingXmlDataWriter.startSeries();
        streamingXmlDataWriter.writeSeriesComponent("DIM", "dimension");
        streamingXmlDataWriter.writeAttribute("ATTR", "attribute");
        streamingXmlDataWriter.writeObservation(null, Map.of("MM", "measure"), null);
        metadataAttributeValue.setValues(List.of("meta1", "meta2"));
        streamingXmlDataWriter.writeMetaData(metadataAttributeValue);
        streamingXmlDataWriter.writeAttribute("MULT", List.of("mult_value_1", "mult_value_2"));
        streamingXmlDataWriter.close();
        InputStream resourceAsStream = StreamingXmlDataWriterTest.class.getResourceAsStream(META_MULTI_XML);
        assert resourceAsStream != null;
        String expected = new String(resourceAsStream.readAllBytes());
        String actual = output.toString();
        System.out.println(actual);
        assertXMLEqual(expected, actual);
    }

    @Test
    void writeNothingAndClose() throws XMLStreamException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        XMLUnit.setIgnoreWhitespace(true);
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(output, StandardCharsets.UTF_8));
        streamingXmlDataWriter = new StreamingXmlDataWriter(xmlStreamWriter, headerWriter);
        streamingXmlDataWriter.close();
        String actual = output.toString();
        assertThat(actual).isBlank();
    }

    public static DatasetHeader getDatasetHeader() {
        return DatasetHeaderImpl.builder()
            .datasetId("1")
            .action(ActionType.INFORMATION)
            .datasetStructureReference(new DatasetStructureReferenceImpl(
                new IdentifiableArtefactReferenceImpl(
                    "id1",
                    "agnc1",
                    "1.0",
                    StructureClassImpl.DATA_STRUCTURE,
                    "SID"
                ),
                ActionType.INFORMATION,
                "dd"
            ))
            .validFrom(Instant.parse("2022-05-17T10:11:16Z"))
            .validTo(Instant.parse("2023-06-17T11:10:16Z"))
            .build();
    }

    public static DatasetHeader getDatasetHeaderAllDimensions() {
        return DatasetHeaderImpl.builder()
            .datasetId("1")
            .action(ActionType.INFORMATION)
            .datasetStructureReference(new DatasetStructureReferenceImpl(
                new IdentifiableArtefactReferenceImpl(
                    "id1",
                    "agnc1",
                    "1.0",
                    StructureClassImpl.DATA_STRUCTURE,
                    "SID"
                ),
                ActionType.INFORMATION,
                "AllDimensions"
            ))
            .validFrom(Instant.parse("2022-05-17T10:11:16Z"))
            .validTo(Instant.parse("2023-06-17T11:10:16Z"))
            .build();
    }

    private static DataStructureDefinitionImpl getDataStructureDefinition() {
        DataStructureDefinitionImpl dsd = new DataStructureDefinitionImpl();
        dsd.setId("DSD1");
        dsd.setOrganizationId("QH");
        dsd.setVersion(Version.createFromString("1.0.0"));
        dsd.setName(new InternationalString("DSD"));
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        List<DimensionComponent> dimensionComponents = new ArrayList<>();
        DimensionComponentImpl dimensionComponent = new DimensionImpl();
        dimensionComponent.setId("DIM");
        dimensionComponent.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT, "DIM"
        ));
        dimensionComponents.add(dimensionComponent);
        DimensionComponentImpl dimensionComponent2 = new DimensionImpl();
        dimensionComponent2.setId("DIM2");
        dimensionComponent2.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT, "DIM2"
        ));
        dimensionComponents.add(dimensionComponent2);
        dimensionDescriptor.setComponents(dimensionComponents);
        dsd.setDimensionDescriptor(dimensionDescriptor);
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId("AD");
        DataAttributeImpl dataAttribute = new DataAttributeImpl();
        dataAttribute.setId("ATTR");
        dataAttribute.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT_SCHEME, "ATTR"
        ));
        dataAttribute.setMaxOccurs(1);
        DataAttributeImpl dataAttribute2 = new DataAttributeImpl();
        dataAttribute2.setId("ATTR2");
        dataAttribute2.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT_SCHEME, "ATTR2"
        ));
        dataAttribute2.setMaxOccurs(1);
        DataAttributeImpl dataAttribute3 = new DataAttributeImpl();
        dataAttribute3.setId("MULT");
        dataAttribute3.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT_SCHEME, "MULT"
        ));
        dataAttribute3.setMaxOccurs(2);
        attributeDescriptor.setComponents(List.of(dataAttribute, dataAttribute2, dataAttribute3));
        MetadataAttributeRefImpl metaAttributeRef = new MetadataAttributeRefImpl();
        metaAttributeRef.setId("METAATTR_OBS");
        metaAttributeRef.setMetadataRelationship(new ObservationRelationshipImpl());
        MetadataAttributeRefImpl metaAttributeRef2 = new MetadataAttributeRefImpl();
        metaAttributeRef2.setId("METAATTR_SERIES");
        DimensionRelationshipImpl dimensionRelationship = new DimensionRelationshipImpl();
        dimensionRelationship.setDimensions(List.of("DIM1", "DIM2"));
        metaAttributeRef2.setMetadataRelationship(dimensionRelationship);
        MetadataAttributeRefImpl metaAttributeRef3 = new MetadataAttributeRefImpl();
        metaAttributeRef3.setId("METAATTR");
        metaAttributeRef3.setMetadataRelationship(null);
        attributeDescriptor.setMetadataAttributes(List.of(metaAttributeRef, metaAttributeRef2, metaAttributeRef3));
        dsd.setAttributeDescriptor(attributeDescriptor);
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId("MES");
        List<Measure> measures = new ArrayList<>();
        MeasureImpl measure = new MeasureImpl();
        measure.setId("MM");
        measure.setConceptIdentity(new IdentifiableArtefactReferenceImpl(
            "CS1", "IMF", "1.0.0", StructureClassImpl.CONCEPT_SCHEME, "MM"
        ));
        measures.add(measure);
        measureDescriptor.setComponents(measures);
        dsd.setMeasureDescriptor(measureDescriptor);
        return dsd;
    }

    private static ConceptSchemeImpl getConceptScheme() {
        ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setId("CS1");
        conceptScheme.setOrganizationId("IMF");

        ConceptImpl concept = TestUtils.getConceptWithEnumeratedRepresentation("DIM", "CL-1", "COD1");
        concept.setName(new InternationalString(prepareLocaleStrings("dim_name", "діменшин")));

        ConceptImpl concept2 = TestUtils.getConceptWithEnumeratedRepresentation("ATTR", "CL-1", "COD");
        concept2.setName(new InternationalString(prepareLocaleStrings("attr_name", "атрібут")));

        ConceptImpl concept3 = TestUtils.getConceptWithEnumeratedRepresentation("MM", "CL-1", "COD");
        concept3.setName(new InternationalString(prepareLocaleStrings("meas_name", "межур")));

        ConceptImpl concept4 = TestUtils.getConceptWithBaseRepresentation("MULT", FacetType.IS_MULTILINGUAL, FacetValueType.STRING);
        concept4.setId("MULT");
        concept4.setName(new InternationalString(prepareLocaleStrings("multi_name", "мульті")));

        conceptScheme.setItems(List.of(concept, concept2, concept3, concept4));
        conceptScheme.setVersion(Version.createFromString("1.0.0"));
        return conceptScheme;
    }

    private static Map<String, String> prepareLocaleStrings(String valueInEnglish, String valueInUkrainian) {
        Map<String, String> localeStrings = new HashMap<>();
        localeStrings.put("en", valueInEnglish);
        localeStrings.put("uk", valueInUkrainian);
        return localeStrings;
    }

    private static CodelistImpl getCodelist() {
        CodelistImpl codelist = TestUtils.getCodelist("CL-1");
        Map<String, String> langs = new HashMap<>();
        langs.put("en", "Some code");
        langs.put("fr", "Quelque code");
        codelist.setName(new InternationalString(langs));
        Map<String, String> langsDesc = new HashMap<>();
        langsDesc.put("en", "Some desc");
        langsDesc.put("fr", "Quelque desc");
        codelist.setDescription(new InternationalString(langsDesc));
        CodeImpl code = TestUtils.getCode("COD1");
        Map<String, String> langsCode = new HashMap<>();
        langsCode.put("en", "code");
        langsCode.put("fr", " codee");
        code.setName(new InternationalString(langsCode));
        CodeImpl code2 = TestUtils.getCode("DIM");
        codelist.setItems(List.of(code, code2));
        return codelist;
    }

    private static Artefacts getArtefacts() {
        CodelistImpl codelist = getCodelist();
        ConceptSchemeImpl conceptScheme = getConceptScheme();
        DataStructureDefinitionImpl dsd = getDataStructureDefinition();
        DataflowImpl dataflow = TestUtils.getDataflow("DSD1", "DFlow");
        Artefacts artefacts = new ArtefactsImpl();
        artefacts.getCodelists().add(codelist);
        artefacts.getConceptSchemes().add(conceptScheme);
        artefacts.getDataStructures().add(dsd);
        artefacts.getDataflows().add(dataflow);
        return artefacts;
    }

}