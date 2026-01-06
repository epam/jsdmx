package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.ORGANISATION_SCHEME_MAP_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class OrganisationSchemeMapWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCategorySchemeMap() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        OrganisationSchemeMapWriter organisationSchemeMapWriter = createOrganisationSchemeMapWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(organisationSchemeMapWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        OrganisationSchemeMap organisationSchemeMap = MaintainableArtifactsTestUtils.buildOrganisationSchemeMap();
        artefacts.getOrganisationSchemeMaps().add(organisationSchemeMap);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(ORGANISATION_SCHEME_MAP_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
