package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.METADATA_STRUCTURE_DEF_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

class MetaDataStructureDefinitionWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public MetadataStructureDefinitionWriter metadataStructureDefinitionWriter;
    @Spy
    private ComponentWriter componentWriter = MaintainableArtifactsTestUtils.getComponentWriter();

    @Test
    void writeMetadataStructureDefinition() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MetadataStructureDefinition metadataStructureDefinitionMock = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();

        //when
        metadataStructureDefinitionWriter.writeAndClose(createJsonGenerator(stream), metadataStructureDefinitionMock);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(METADATA_STRUCTURE_DEF_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }
}
