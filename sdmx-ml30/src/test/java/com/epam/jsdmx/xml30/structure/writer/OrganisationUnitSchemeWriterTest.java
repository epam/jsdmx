package com.epam.jsdmx.xml30.structure.writer;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitScheme;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class OrganisationUnitSchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeOrganisationUnitScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        OrganisationUnitSchemeWriter organisationUnitSchemeWriter = createOrganisationUnitSchemeWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(organisationUnitSchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        OrganisationUnitScheme organisationUnitScheme = MaintainableArtifactsTestUtils.buildOrganisationUnitScheme();
        artefacts.getOrganisationUnitSchemes().add(organisationUnitScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.ORGANISATION_UNIT_SCHEME_XML);
        assert resourceAsStream != null;

        System.out.println(actual);
        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
