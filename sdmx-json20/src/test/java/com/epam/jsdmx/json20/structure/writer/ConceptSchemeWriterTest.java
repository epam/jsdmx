package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.CONCEPT_SCHEMA_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ConceptSchemeWriterTest extends BaseJsonWriterTest {

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
        String expected = IOUtils.resourceToString(CONCEPT_SCHEMA_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

}
