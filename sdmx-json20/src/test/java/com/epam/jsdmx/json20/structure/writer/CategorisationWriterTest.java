package com.epam.jsdmx.json20.structure.writer;


import static com.epam.jsdmx.json20.structure.TestUtils.CATEGORISATION_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CategorisationWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public CategorisationWriter categorisationWriter;

    @Test
    void writeCategorisationToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Categorisation categorisation = MaintainableArtifactsTestUtils.buildCategorisation();

        //when
        categorisationWriter.writeAndClose(createJsonGenerator(stream), categorisation);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        MaintainableArtifactsTestUtils.assertMaintainableArtefactsInJson(actual);
        String expected = IOUtils.resourceToString(CATEGORISATION_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }
}
