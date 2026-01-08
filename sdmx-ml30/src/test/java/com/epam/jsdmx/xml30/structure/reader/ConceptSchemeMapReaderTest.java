package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.CONCEPT_SCHEME_MAP_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ConceptSchemeMapReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(CONCEPT_SCHEME_MAP_XML, minifyXml);

        ConceptSchemeMapReader conceptSchemeMapReader = createConceptSchemeMapReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(conceptSchemeMapReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ConceptSchemeMap actual = artefacts.getConceptSchemeMaps().iterator().next();
        ConceptSchemeMap expected = MaintainableArtifactsTestUtils.buildConceptSchemeMap();

        Assertions.assertEquals(expected, actual);
    }
}
