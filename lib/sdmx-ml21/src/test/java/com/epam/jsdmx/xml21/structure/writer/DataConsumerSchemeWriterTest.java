package com.epam.jsdmx.xml21.structure.writer;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConsumerSchemeImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml21.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class DataConsumerSchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeDataConsumerScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        OrganisationSchemeWriter dataConsumerSchemeWriter = createOrganisationSchemeWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(dataConsumerSchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataConsumerSchemeImpl consumerScheme = MaintainableArtifactsTestUtils.buildDataConsumerScheme();
        artefacts.getDataConsumerSchemes().add(consumerScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.DATA_CONSUMER_SCHEME_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getDataConsumerSchemes);
    }

}
