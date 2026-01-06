package com.epam.jsdmx.json10.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

public class ContentConstraintWriterTest extends JsonWriterTestBase {

    @InjectMocks
    private ContentConstraintWriter contentConstraintWriter;

    @Test
    void writeDataConstraintToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();

        //when
        contentConstraintWriter.writeAndClose(createJsonGenerator(stream), dataConstraint);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(TestUtils.DATA_CONSTRAINT_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"contentconstraint\": [" + actual + "] } }",
            SdmxBeans::getContentConstraintBeans
        );
    }

    @Test
    void writeMetaDataConstraintToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MetadataConstraint metadataConstraint = MaintainableArtifactsTestUtils.buildMetadataConstraint();

        //when
        contentConstraintWriter.writeAndClose(createJsonGenerator(stream), metadataConstraint);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(TestUtils.META_DATA_CONSTRAINT_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"contentconstraint\": [" + actual + "] } }",
            SdmxBeans::getContentConstraintBeans
        );
    }
}
