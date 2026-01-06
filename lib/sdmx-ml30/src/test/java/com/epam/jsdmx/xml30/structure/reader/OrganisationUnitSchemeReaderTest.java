package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitScheme;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrganisationUnitSchemeReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.ORGANISATION_UNIT_SCHEME_XML, minifyXml);

        OrganisationUnitSchemeReader categorySchemeMapReader = createOrganisationUnitSchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(categorySchemeMapReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        OrganisationUnitScheme actual = artefacts.getOrganisationUnitSchemes().iterator().next();
        OrganisationUnitScheme expected = MaintainableArtifactsTestUtils.buildOrganisationUnitScheme();

        Assertions.assertEquals(expected, actual);
    }
}
