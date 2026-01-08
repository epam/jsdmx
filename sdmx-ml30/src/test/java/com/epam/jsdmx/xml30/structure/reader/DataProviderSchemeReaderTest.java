package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderScheme;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataProviderSchemeReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.DATA_PROVIDER_SCHEME_XML, minifyXml);

        DataProviderSchemeReader dataProviderSchemeReader = createDataProviderSchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(dataProviderSchemeReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataProviderScheme actual = artefacts.getDataProviderSchemes().iterator().next();
        DataProviderScheme expected = MaintainableArtifactsTestUtils.buildDataProviderScheme();

        Assertions.assertEquals(expected, actual);
    }
}
