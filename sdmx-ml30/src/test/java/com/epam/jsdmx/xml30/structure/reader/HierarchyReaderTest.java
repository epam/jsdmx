package com.epam.jsdmx.xml30.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HierarchyReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.HIERARCHIES_XML, minifyXml);
        HierarchyReader hierarchyReader = createHierarchyReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(hierarchyReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        Hierarchy actual = artefacts.getHierarchies().iterator().next();
        HierarchyImpl expected = MaintainableArtifactsTestUtils.buildHierarchy();
        assertEquals(expected, actual);
    }
}
