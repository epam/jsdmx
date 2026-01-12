package com.epam.jsdmx.json10.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class HierarchicalCodelistWriterTest extends JsonWriterTestBase {

    @InjectMocks
    public HierarchicalCodelistWriter hierarchyWriter;

    @Test
    void writeHierarchyToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Hierarchy hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();

        //when
        hierarchyWriter.writeAndClose(createJsonGenerator(stream), hierarchy);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(TestUtils.HIERARCHY_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        // sdmx source has incorrect expectation about hierarchical codes field name:
        // 'codes' VS 'hierarchicalCodes' as per specification

//        sdmxSourceCompatibilityTester.test(
//            "{ \"meta\": {}, \"data\": { \"hierarchicalCodelists\": [" + actual + "] } }",
//            SdmxBeans::getHierarchicalCodelists
//        );
    }

    @Test
    void writeHierarchyWithInnerHierarchyToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Hierarchy hierarchy = MaintainableArtifactsTestUtils.buildHierarchyWithInnerHierarchy();

        //when
        hierarchyWriter.writeAndClose(createJsonGenerator(stream), hierarchy);
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(TestUtils.HIERARCHY_WITH_CHILDREN_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

//        sdmxSourceCompatibilityTester.test(
//            "{ \"meta\": {}, \"data\": { \"hierarchicalCodelists\": [" + actual + "] } }",
//            SdmxBeans::getHierarchicalCodelists
//        );
    }
}
