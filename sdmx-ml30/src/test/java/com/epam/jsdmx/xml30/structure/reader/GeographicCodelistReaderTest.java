package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelist;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeographicCodelistReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.GEOGRAPHICAL_CODELIST_XML, minifyXml);

        GeographicCodelistReader codelistReader = createGeographicCodelistReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(codelistReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        GeographicCodelist actual = artefacts.getGeographicCodelists().iterator().next();
        GeographicCodelist expected = MaintainableArtifactsTestUtils.buildGeographicCodelist();

        Assertions.assertTrue(expected.deepEquals(actual));
    }
}
