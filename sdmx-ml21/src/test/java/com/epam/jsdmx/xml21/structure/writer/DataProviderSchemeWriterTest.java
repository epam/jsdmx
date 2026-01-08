package com.epam.jsdmx.xml21.structure.writer;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataProviderSchemeImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml21.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class DataProviderSchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeDataProviderScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        OrganisationSchemeWriter dataProviderSchemeWriter = createOrganisationSchemeWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(dataProviderSchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataProviderSchemeImpl dataProviderScheme = MaintainableArtifactsTestUtils.buildDataProviderScheme();
        artefacts.getDataProviderSchemes().add(dataProviderScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.DATA_PROVIDER_SCHEME_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getDataProviderSchemes);
    }

}
