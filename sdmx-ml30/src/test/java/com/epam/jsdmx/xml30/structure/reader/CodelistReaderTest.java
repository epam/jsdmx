package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CodelistReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.CODELIST_XML, minifyXml);

        CodelistReader codelistReader = createCodelistReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(codelistReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        Codelist actual = artefacts.getCodelists().iterator().next();
        CodelistImpl expected = MaintainableArtifactsTestUtils.buildCodeList();

        Assertions.assertTrue(expected.deepEquals(actual));
    }
}
