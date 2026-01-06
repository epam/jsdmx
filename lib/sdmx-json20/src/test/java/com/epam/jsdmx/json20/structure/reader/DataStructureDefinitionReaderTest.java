package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class DataStructureDefinitionReaderTest extends BaseJsonReaderTest {

    public static final String DSD_JSON = "/serialization.expected/data-structure-definition.json";
    public static final String DSD_JSON_MES_FIRST = "/serialization.expected/data-structure-definition-order.json";
    public static final String DSD_JSON_NULL = "/deserialization.expected/data-structure-definition-components_null.json";
    public static final String DSD_JSON_COMPONENTS_EMPTY = "/deserialization.expected/data-structure-definition-components_empty.json";

    public static final String DSD_ATTR_DESC_JSON = "/deserialization.expected/data-structure-definition-attr.json";
    public static final String DSD_ATTR_DESC_NULL = "/deserialization.expected/data-structure-definition-attr_null.json";
    public static final String DSD_ATTR_DESC_AND_NULL = "/deserialization.expected/data-structure-definition-attr_and_null.json";

    public static final String DSD_DIM_DESC_JSON = "/deserialization.expected/data-structure-definition-dim.json";
    public static final String DSD_DIM_DESC_NULL = "/deserialization.expected/data-structure-definition-dim_null.json";
    public static final String DSD_DIM_DESC_AND_NULL = "/deserialization.expected/data-structure-definition-dim_and_null.json";

    public static final String DSD_MEAS_DESC_JSON = "/deserialization.expected/data-structure-definition-meas.json";
    public static final String DSD_MEAS_DESC_NULL = "/deserialization.expected/data-structure-definition-meas_null.json";
    public static final String DSD_MEAS_DESC_AND_NULL = "/deserialization.expected/data-structure-definition-meas_and_null.json";
    public static final String DSD_FULL_JSON = "/deserialization.expected/data-structure-definition-full.json";
    public static final String ATTRIBUTE_DESCRIPTOR = "AttributeDescriptor";
    public static final String DIMENSION_DESCRIPTOR = "DimensionDescriptor";
    private static final String MEASURE_DESCRIPTOR = "MeasureDescriptor";

    @InjectMocks
    public DataStructureDefinitionReader dataStructureDefinitionReader;

    @Test
    void readDataStructureDefinitionFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_JSON);

        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDataStructureDefinitionWithAllDescriptorsFromJsonOrderTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_JSON_MES_FIRST);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDataStructureDefinitionWithAttributeDescriptorFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_ATTR_DESC_JSON);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        dimensionDescriptor.setId(DIMENSION_DESCRIPTOR);
        expected.setDimensionDescriptor(dimensionDescriptor);
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId(MEASURE_DESCRIPTOR);
        expected.setMeasureDescriptor(measureDescriptor);
        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDataStructureDefinitionWithDimensionDescriptorFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_DIM_DESC_JSON);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId(MEASURE_DESCRIPTOR);
        expected.setMeasureDescriptor(measureDescriptor);
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId(ATTRIBUTE_DESCRIPTOR);
        expected.setAttributeDescriptor(attributeDescriptor);
        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDataStructureDefinitionWithMeasureDescriptorFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_MEAS_DESC_JSON);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId(ATTRIBUTE_DESCRIPTOR);
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        dimensionDescriptor.setId(DIMENSION_DESCRIPTOR);
        expected.setAttributeDescriptor(attributeDescriptor);
        expected.setDimensionDescriptor(dimensionDescriptor);
        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDataStructureDefinitionWithAllDescriptorsFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(DSD_FULL_JSON);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readAttributeDescriptorsAndNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_ATTR_DESC_AND_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDimDescriptorsAndNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_DIM_DESC_AND_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readMeasDescriptorsAndNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_MEAS_DESC_AND_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readAttributeDescriptorNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_ATTR_DESC_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readDimDescriptorNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_DIM_DESC_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readMeasDescriptorNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_MEAS_DESC_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());

        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);

        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readAllDescriptorNull() throws IOException {
        //given
        JsonParser parser = createParser(DSD_JSON_NULL);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);
        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readAllDescriptorEmpty() throws IOException {
        //given
        JsonParser parser = createParser(DSD_JSON_COMPONENTS_EMPTY);
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        attributeDescriptor.setId(ATTRIBUTE_DESCRIPTOR);
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        dimensionDescriptor.setId(DIMENSION_DESCRIPTOR);
        expected.setAttributeDescriptor(attributeDescriptor);
        expected.setDimensionDescriptor(dimensionDescriptor);
        MeasureDescriptorImpl measureDescriptor = new MeasureDescriptorImpl();
        measureDescriptor.setId(MEASURE_DESCRIPTOR);
        expected.setMeasureDescriptor(measureDescriptor);
        //when
        DataStructureDefinitionImpl actual = (DataStructureDefinitionImpl) dataStructureDefinitionReader.readAndClose(parser);
        //then
        assertEquals(expected.getAnnotations(), actual.getAnnotations());
        assertTrue(expected.deepEquals(actual));
    }
}
