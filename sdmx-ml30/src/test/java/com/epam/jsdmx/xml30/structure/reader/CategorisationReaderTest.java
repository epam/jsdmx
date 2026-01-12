package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategorisationReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.CATEGORISATION_XML, minifyXml);

        CategorisationReader categorisationReader = createCategorisationReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(categorisationReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        Categorisation actual = artefacts.getCategorisations().iterator().next();
        CategorisationImpl expected = MaintainableArtifactsTestUtils.buildCategorisation();

        Assertions.assertEquals(expected, actual);
    }
}
