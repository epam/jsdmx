package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelist;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeoGridCodelistReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.GEOGRID_CODELIST_XML, minifyXml);

        GeoGridCodelistReader codelistReader = createGeoGridCodelistReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(codelistReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        GeoGridCodelist actual = artefacts.getGeoGridCodelists().iterator().next();
        GeoGridCodelist expected = MaintainableArtifactsTestUtils.buildGeoGridCodelist();

        Assertions.assertTrue(expected.deepEquals(actual));
    }
}
