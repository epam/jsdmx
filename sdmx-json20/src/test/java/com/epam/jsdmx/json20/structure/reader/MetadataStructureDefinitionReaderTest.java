package com.epam.jsdmx.json20.structure.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json20.structure.TestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class MetadataStructureDefinitionReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    public MetaDataStructureDefinitionReader metaDataStructureDefinitionReader;

    @Test
    void readMetaDataStructureDefinitionFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(TestUtils.METADATA_STRUCTURE_DEF_JSON);
        MetadataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();

        //when
        MetadataStructureDefinitionImpl actual = (MetadataStructureDefinitionImpl) metaDataStructureDefinitionReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readMetaDataStructureDefinitionFromJsonTestMultipleAttributes() throws IOException {
        //given
        JsonParser parser = createParser("/serialization.expected/metadata-structure-definition-multi-attributes.json");

        MetadataStructureDefinitionImpl actual = (MetadataStructureDefinitionImpl) metaDataStructureDefinitionReader.readAndClose(parser);

        //then
        assertThat(actual).isNotNull();
    }
}
