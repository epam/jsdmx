package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.M_STRUCTURE_DEFINITION_XML;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MetadataStructureDefinitionReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(M_STRUCTURE_DEFINITION_XML, minifyXml);

        MetadataStructureDefinitionReader metadataStructureDefinitionReader = createMetadataStructureDefinitionReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(metadataStructureDefinitionReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        MetadataStructureDefinition actual = artefacts.getMetadataStructureDefinitions().iterator().next();
        MetadataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();
        assertEquals(expected, actual);
    }
}
