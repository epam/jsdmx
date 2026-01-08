package com.epam.jsdmx.xml30.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MetadataflowReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.METADATAFLOW_XML, minifyXml);

        MetadataflowReader metadataflowReader = createMetadataflowReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(metadataflowReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        Metadataflow actual = artefacts.getMetadataflows().iterator().next();
        MetadataflowImpl expected = MaintainableArtifactsTestUtils.buildMetadataFlow();
        assertEquals(expected, actual);
    }
}
