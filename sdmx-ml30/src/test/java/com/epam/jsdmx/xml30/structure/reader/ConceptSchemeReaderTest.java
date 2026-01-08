package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.CONCEPT_SCHEME_XML;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ConceptSchemeReaderTest extends BaseXmlReaderTest {

    @Test
    void testEnumerationFormat() throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance("/xml/concept-scheme_with_enum_format.xml", true);

        ConceptSchemeReader conceptSchemeReader = createConceptSchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(conceptSchemeReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ConceptScheme actual = artefacts.getConceptSchemes().iterator().next();

        assertNotNull(actual);
    }


    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(CONCEPT_SCHEME_XML, minifyXml);

        ConceptSchemeReader conceptSchemeReader = createConceptSchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(conceptSchemeReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ConceptScheme actual = artefacts.getConceptSchemes().iterator().next();
        ConceptSchemeImpl expected = MaintainableArtifactsTestUtils.buildConceptScheme();
        assertEquals(expected, actual);
    }
}
