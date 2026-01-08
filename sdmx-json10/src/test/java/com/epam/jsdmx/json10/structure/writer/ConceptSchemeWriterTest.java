package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import net.javacrumbs.jsonunit.JsonAssert;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class ConceptSchemeWriterTest extends JsonWriterTestBase {

    @InjectMocks
    public ConceptSchemeWriter conceptSchemeWriter;

    @Test
    void writeConceptSchemeToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ConceptSchemeImpl conceptScheme = MaintainableArtifactsTestUtils.buildConceptScheme();

        //when
        conceptSchemeWriter.writeAndClose(createJsonGenerator(stream), conceptScheme);
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(TestUtils.CONCEPT_SCHEMA_JSON, StandardCharsets.UTF_8);

        JsonAssert.assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"conceptSchemes\": [" + actual + "] } }",
            SdmxBeans::getConceptSchemes
        );
    }

}
