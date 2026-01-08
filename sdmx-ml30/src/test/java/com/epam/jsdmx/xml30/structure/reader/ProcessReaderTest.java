package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.PROCESS_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Process;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProcessReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(PROCESS_XML, minifyXml);

        ProcessReader processReader = createProcessReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(processReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        Process actual = artefacts.getProcesses().iterator().next();
        Process expected = MaintainableArtifactsTestUtils.buildProcess();

        Assertions.assertEquals(expected, actual);
    }
}
