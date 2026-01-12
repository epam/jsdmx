package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils.buildAgencyScheme;
import static com.epam.jsdmx.xml21.structure.TestUtils.AGENCY_SCHEME_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class OrganisationSchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeAgencyScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        OrganisationSchemeWriter organisationSchemeWriter = createOrganisationSchemeWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(organisationSchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        AgencySchemeImpl agencyScheme = buildAgencyScheme();
        artefacts.getAgencySchemes().add(agencyScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(AGENCY_SCHEME_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());

        sdmxSourceCompatibilityTester.test(actual.toString(), SdmxBeans::getAgenciesSchemes);
    }
}
