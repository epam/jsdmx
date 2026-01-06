package com.epam.jsdmx.xml30.structure.writer;


import static com.epam.jsdmx.xml30.structure.TestUtils.GEOGRAPHICAL_CODELIST_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeographicCodelist;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class GeographicCodelistWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCodelist() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        GeographicCodelistWriter codeListWriter = createGeographicCodelistWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(codeListWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        GeographicCodelist codelist = MaintainableArtifactsTestUtils.buildGeographicCodelist();
        artefacts.getGeographicCodelists().add(codelist);
        xmlStructureWriter.write(artefacts);
        System.out.println(actual);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(GEOGRAPHICAL_CODELIST_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
