package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSUMER_SCHEME_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerScheme;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataConsumerSchemeReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(DATA_CONSUMER_SCHEME_XML, minifyXml);

        DataConsumerSchemeReader dataConsumerSchemeReader = createDataConsumerSchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(dataConsumerSchemeReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataConsumerScheme actual = artefacts.getDataConsumerSchemes().iterator().next();
        DataConsumerScheme expected = MaintainableArtifactsTestUtils.buildDataConsumerScheme();

        Assertions.assertEquals(expected, actual);
    }
}
