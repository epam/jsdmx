package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.TestUtils.DATAFLOW_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class DataflowWriterTest extends BaseXmlWriterTest {

    @Test
    void writeDataflow() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        DataflowWriter dataflowWriter = createDataflowWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(dataflowWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataflowImpl dataflow = MaintainableArtifactsTestUtils.buildDataFlow();
        artefacts.getDataflows().add(dataflow);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(DATAFLOW_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getDataflows);
    }
}
