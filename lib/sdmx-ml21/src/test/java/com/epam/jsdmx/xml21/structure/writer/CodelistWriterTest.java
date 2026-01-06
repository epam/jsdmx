package com.epam.jsdmx.xml21.structure.writer;


import static com.epam.jsdmx.xml21.structure.TestUtils.CODELIST_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class CodelistWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCodelist() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        CodelistWriter codeListWriter = createCodelistWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(codeListWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CodelistImpl codelist = MaintainableArtifactsTestUtils.buildCodeList();
        artefacts.getCodelists().add(codelist);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(CODELIST_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);

        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getCodelists);
    }
}
