package com.epam.jsdmx.json10.structure.writer;


import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class CategorisationWriterTest extends JsonWriterTestBase {

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
        String expected = IOUtils.resourceToString(TestUtils.CATEGORISATION_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"categorisations\": [" + actual + "] } }",
            SdmxBeans::getCategorisations
        );
    }
}
