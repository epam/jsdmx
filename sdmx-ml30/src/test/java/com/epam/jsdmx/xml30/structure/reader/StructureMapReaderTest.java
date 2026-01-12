package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.STRUCTURE_MAP_XML;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StructureMapReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(STRUCTURE_MAP_XML, minifyXml);

        StructureMapReader structureMapReader = createStructureMapReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(structureMapReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        StructureMap actual = artefacts.getStructureMaps().iterator().next();
        StructureMap expected = MaintainableArtifactsTestUtils.buildStructureMap();
        assertEquals(expected, actual);
    }
}
