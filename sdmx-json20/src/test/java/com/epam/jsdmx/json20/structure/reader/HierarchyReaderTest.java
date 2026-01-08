package com.epam.jsdmx.json20.structure.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json20.structure.TestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class HierarchyReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    public HierarchyReader hierarchyReader;

    @Test
    void readHierarchyFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(TestUtils.HIERARCHY_JSON);
        HierarchyImpl expected = MaintainableArtifactsTestUtils.buildHierarchy();

        //when
        HierarchyImpl actual = (HierarchyImpl) hierarchyReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readHierarchyFromJsonWithInsideHierarchyTest() throws IOException {
        //given
        JsonParser parser = createParser(TestUtils.HIERARCHY_HI_JSON);

        HierarchyImpl expected = MaintainableArtifactsTestUtils.buildHierarchyWithInnerHierarchy();

        //when
        HierarchyImpl actual = (HierarchyImpl) hierarchyReader.readAndClose(parser);
        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readHierarchyFromJsonWithInsideHierarchyAndLevelsTest() throws IOException {
        //given
        JsonParser parser = createParser("/deserialization.expected/hierarchy2.json");

        //when
        HierarchyImpl actual = (HierarchyImpl) hierarchyReader.readAndClose(parser);
        //then
        assertThat(actual).isNotNull();
    }

}
