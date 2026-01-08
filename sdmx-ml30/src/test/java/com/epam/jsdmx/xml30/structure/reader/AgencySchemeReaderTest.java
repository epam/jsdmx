package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.AGENCY_SCHEME_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.AgencyScheme;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AgencySchemeReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(AGENCY_SCHEME_XML, minifyXml);

        AgencySchemeReader agencySchemeReader = createAgencySchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(agencySchemeReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        AgencyScheme actual = artefacts.getAgencySchemes().iterator().next();
        AgencyScheme expected = MaintainableArtifactsTestUtils.buildAgencyScheme();

        Assertions.assertEquals(expected, actual);
    }
}
