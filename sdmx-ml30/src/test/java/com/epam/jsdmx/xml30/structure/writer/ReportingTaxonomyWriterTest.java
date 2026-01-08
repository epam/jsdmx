package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils.buildReportingTaxonomy;
import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomy;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ReportingTaxonomyWriterTest extends BaseXmlWriterTest {


    @Test
    void writeReportingTaxonomy() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ReportingTaxonomyWriter reportingTaxonomyWriter = createReportingTaxonomyWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(reportingTaxonomyWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        ReportingTaxonomy reportingTaxonomy = buildReportingTaxonomy();
        artefacts.getReportingTaxonomies().add(reportingTaxonomy);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(REPORTING_TAXONOMY_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
