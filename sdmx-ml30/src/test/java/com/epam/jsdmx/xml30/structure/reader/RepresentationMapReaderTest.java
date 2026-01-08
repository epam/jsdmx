package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.REPRESENTATION_MAP_XML;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RepresentationMapReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(REPRESENTATION_MAP_XML, minifyXml);

        RepresentationMapReader representationMapReader = createRepresentationMapReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(representationMapReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        RepresentationMap actual = artefacts.getRepresentationMaps().iterator().next();
        RepresentationMapImpl expected = MaintainableArtifactsTestUtils.buildRepresentationMap();
        assertEquals(actual.getRepresentationMappings(), expected.getRepresentationMappings());
        assertEquals(actual, expected);
    }
}
