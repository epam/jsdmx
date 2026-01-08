package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.DATA_STRUCTURE_DEFINITION_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

class DataStructureDefinitionWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public DataStructureDefinitionWriter dataStructureDefinitionWriter;
    @Spy
    private AttributeListWriter attributeListWriterSpy = MaintainableArtifactsTestUtils.getAttributeListWriter();
    @Spy
    private DimensionListWriter dimensionListWriterSpy = MaintainableArtifactsTestUtils.getDimensionListWriter();
    @Spy
    private MeasureListWriter measureListWriterSpy = MaintainableArtifactsTestUtils.getMeasureListWriter();

    @Test
    void writeDataStructureDefinitionToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataStructureDefinitionImpl dataStructureDefinition = MaintainableArtifactsTestUtils.buildDataStructureDefinition();

        //when
        dataStructureDefinitionWriter.writeAndClose(createJsonGenerator(stream), dataStructureDefinition);
        String actual = stream.toString(StandardCharsets.UTF_8);

        System.out.println(actual);
        //then
        String expected = IOUtils.resourceToString(DATA_STRUCTURE_DEFINITION_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

}
