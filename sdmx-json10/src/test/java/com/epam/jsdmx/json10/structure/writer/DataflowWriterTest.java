package com.epam.jsdmx.json10.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.AnnotationImpl;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;
import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class DataflowWriterTest extends JsonWriterTestBase {
    @InjectMocks
    public DataflowWriter dataFlowWriter;

    @Test
    void writeDataFlowToJson() throws IOException, URISyntaxException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Dataflow dataflow = MaintainableArtifactsTestUtils.buildDataFlow();
        AnnotationImpl annotation = (AnnotationImpl) dataflow.getAnnotations()
            .get(0);
        annotation.setUrl(new InternationalUri(new URI("http://some.com")));
        dataflow.getAnnotations()
            .clear();
        dataflow.getAnnotations()
            .add(annotation);

        //when
        dataFlowWriter.writeAndClose(createJsonGenerator(stream), dataflow);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(TestUtils.DATA_FLOW_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"dataflows\": [" + actual + "] } }",
            SdmxBeans::getDataflows
        );
    }
}
