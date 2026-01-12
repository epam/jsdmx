package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils.buildValueList;
import static com.epam.jsdmx.xml30.structure.TestUtils.VALUE_LIST_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueList;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ValueListWriterTest extends BaseXmlWriterTest {

    @Test
    void writeValueList() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ValueListWriter valueListWriter = createValueListWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(valueListWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        ValueList valueList = buildValueList();
        artefacts.getValueLists().add(valueList);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(VALUE_LIST_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
