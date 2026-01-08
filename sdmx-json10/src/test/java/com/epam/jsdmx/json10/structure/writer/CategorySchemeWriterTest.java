package com.epam.jsdmx.json10.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class CategorySchemeWriterTest extends JsonWriterTestBase {

    @InjectMocks
    public CategorySchemeWriter categorySchemeWriter;

    @Test
    void writeCategorySchemeToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CategorySchemeImpl categoryScheme = MaintainableArtifactsTestUtils.buildCategoryScheme();

        //when
        categorySchemeWriter.writeAndClose(createJsonGenerator(stream), categoryScheme);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        MaintainableArtifactsTestUtils.assertMaintainableArtefactsInJson(actual);
        String expected = IOUtils.resourceToString(TestUtils.CATEGORY_SCHEMA_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"categorySchemes\": [" + actual + "] } }",
            SdmxBeans::getCategorySchemes
        );
    }
}
