package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import net.javacrumbs.jsonunit.JsonAssert;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class CodelistWriterTest extends JsonWriterTestBase {

    @InjectMocks
    public CodelistWriter codeListWriter;

    @Test
    void writeCodeListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Codelist codelist = MaintainableArtifactsTestUtils.buildCodeList();

        //when
        codeListWriter.writeAndClose(createJsonGenerator(stream), codelist);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        MaintainableArtifactsTestUtils.assertMaintainableArtefactsInJson(actual);
        String expected = IOUtils.resourceToString(TestUtils.CODE_LIST_JSON, StandardCharsets.UTF_8);

        JsonAssert.assertJsonEquals(expected, actual);

        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"codelists\": [" + actual + "] } }",
            SdmxBeans::getCodelists
        );
    }
}
