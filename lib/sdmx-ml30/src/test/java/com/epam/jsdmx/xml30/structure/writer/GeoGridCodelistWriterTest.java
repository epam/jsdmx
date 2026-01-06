package com.epam.jsdmx.xml30.structure.writer;


import static com.epam.jsdmx.xml30.structure.TestUtils.GEOGRID_CODELIST_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.GeoGridCodelist;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class GeoGridCodelistWriterTest extends BaseXmlWriterTest {

    @Test
    void writeGeoGridCodelist() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        GeoGridCodelistWriter codeListWriter = createGeoGridCodelistWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(codeListWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        GeoGridCodelist codelist = MaintainableArtifactsTestUtils.buildGeoGridCodelist();
        artefacts.getGeoGridCodelists().add(codelist);
        xmlStructureWriter.write(artefacts);
        System.out.println(actual);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(GEOGRID_CODELIST_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
