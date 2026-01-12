package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ValueList;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ValueListReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.VALUE_LIST_XML, minifyXml);

        ValueListReader valueListReader = createValueListReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(valueListReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        ValueList actual = artefacts.getValueLists().iterator().next();
        ValueList expected = MaintainableArtifactsTestUtils.buildValueList();

        Assertions.assertEquals(expected, actual);
    }
}
