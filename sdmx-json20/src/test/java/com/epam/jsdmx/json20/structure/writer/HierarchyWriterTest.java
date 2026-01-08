package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.HIERARCHY_HI_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.HIERARCHY_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class HierarchyWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public HierarchyWriter hierarchyWriter;

    @Test
    void writeHierarchyToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Hierarchy hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();

        //when
        hierarchyWriter.writeAndClose(createJsonGenerator(stream), hierarchy);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(HIERARCHY_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
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
        String expected = IOUtils.resourceToString(HIERARCHY_HI_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }
}
