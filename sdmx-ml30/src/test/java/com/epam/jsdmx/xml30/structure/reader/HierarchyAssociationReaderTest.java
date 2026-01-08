package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.HIERARCHY_ASSOCIATION_XML;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociation;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HierarchyAssociationReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(HIERARCHY_ASSOCIATION_XML, minifyXml);
        HierarchyAssociationReader hierarchyAssociationReader = createHierarchyAssociationReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(hierarchyAssociationReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        HierarchyAssociation actual = artefacts.getHierarchyAssociations().iterator().next();
        HierarchyAssociation expected = MaintainableArtifactsTestUtils.buildHierarchyAssociation();
        assertEquals(expected, actual);
    }
}
