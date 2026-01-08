package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORISATION_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class CategorisationWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCategorisation() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        CategorisationWriter categorisationWriter = createCategorisationWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(categorisationWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CategorisationImpl categorisation = MaintainableArtifactsTestUtils.buildCategorisation();
        artefacts.getCategorisations().add(categorisation);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(CATEGORISATION_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
