package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils.buildReportingTaxonomyMap;
import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_MAP_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMap;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ReportingTaxonomyMapWriterTest extends BaseXmlWriterTest {

    @Test
    void writeReportingTaxonomyMap() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ReportingTaxonomyMapWriter reportingTaxonomyMapWriter = createReportingTaxonomyMapWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(reportingTaxonomyMapWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        ReportingTaxonomyMap reportingTaxonomyMap = buildReportingTaxonomyMap();
        artefacts.getReportingTaxonomyMaps().add(reportingTaxonomyMap);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(REPORTING_TAXONOMY_MAP_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
