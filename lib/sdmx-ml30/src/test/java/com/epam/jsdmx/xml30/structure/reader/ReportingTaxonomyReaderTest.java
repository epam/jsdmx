package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomy;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReportingTaxonomyReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(REPORTING_TAXONOMY_XML, minifyXml);

        ReportingTaxonomyReader reportingTaxonomyReader = createReportingTaxonomyReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(reportingTaxonomyReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ReportingTaxonomy actual = artefacts.getReportingTaxonomies().iterator().next();
        ReportingTaxonomy expected = MaintainableArtifactsTestUtils.buildReportingTaxonomy();

        Assertions.assertEquals(expected, actual);
    }
}
