package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json20.structure.TestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class MetadataFlowReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    public MetadataFlowReader metadataFlowReader;
    @InjectMocks
    public DataFlowReader dataFlowReader;

    @Test
    void testDataflow() throws IOException {
        //given
        JsonParser parser = createParser(TestUtils.DATA_FLOW_JSON);
        DataflowImpl expected = MaintainableArtifactsTestUtils.buildDataFlow();

        //when
        DataflowImpl actual = (DataflowImpl) dataFlowReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void testMetaDataflow() throws IOException {
        //given
        JsonParser parser = createParser(TestUtils.METADATA_FLOW_JSON);
        MetadataflowImpl expected = MaintainableArtifactsTestUtils.buildMetadataFlow();

        //when
        Metadataflow actual = (Metadataflow) metadataFlowReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }
}
