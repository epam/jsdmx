package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_CONSTRAINTS_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MetadataConstraintReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(METADATA_CONSTRAINTS_XML, minifyXml);

        MetadataConstraintReader metadataConstraintReader = createMetadataConstraintReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(metadataConstraintReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        MetadataConstraint actual = artefacts.getMetadataConstraints().iterator().next();
        MetadataConstraint expected = MaintainableArtifactsTestUtils.buildMetadataConstraint(false);

        Assertions.assertEquals(expected, actual);
    }
}
