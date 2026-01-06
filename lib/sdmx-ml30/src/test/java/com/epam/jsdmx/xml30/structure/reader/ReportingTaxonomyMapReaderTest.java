package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_MAP_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReportingTaxonomyMapReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(REPORTING_TAXONOMY_MAP_XML, minifyXml);

        ReportingTaxonomyMapReader reportingTaxonomyMapReader = createReportingTaxonomyMapReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(reportingTaxonomyMapReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ReportingTaxonomyMap actual = artefacts.getReportingTaxonomyMaps().iterator().next();
        ReportingTaxonomyMap expected = MaintainableArtifactsTestUtils.buildReportingTaxonomyMap();

        Assertions.assertEquals(expected, actual);
    }
}
