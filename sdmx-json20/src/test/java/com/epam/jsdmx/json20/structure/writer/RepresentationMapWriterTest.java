package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.REPRESENTATION_MAP_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class RepresentationMapWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public RepresentationMapWriter representationMapWriter;

    @Test
    void writeRepresentationMapToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        RepresentationMap representationMap = MaintainableArtifactsTestUtils.buildRepresentationMap();

        //when
        representationMapWriter.writeAndClose(createJsonGenerator(stream), representationMap);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(REPRESENTATION_MAP_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

}
