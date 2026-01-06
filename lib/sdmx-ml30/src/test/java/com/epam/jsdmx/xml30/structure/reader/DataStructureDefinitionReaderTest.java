package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.DSD_WITH_ENUM_FORMAT_XML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataStructureDefinitionReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(DSD_WITH_ENUM_FORMAT_XML, minifyXml);

        DataStructureDefinitionReader dataStructureDefinitionReader = createDataStructureDefinitionReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(dataStructureDefinitionReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataStructureDefinition actual = artefacts.getDataStructures().iterator().next();
        DataStructureDefinitionImpl expected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        expected.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        expected.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        expected.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());
        List<MetadataAttributeRef> attributeRefs = expected.getAttributeDescriptor().getMetadataAttributes();
        List<MetadataAttributeRef> refs = List.of(attributeRefs.get(0), attributeRefs.get(1));
        expected.getAttributeDescriptor().setMetadataAttributes(refs);
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void testDataflowAttributeRelationship() throws Exception {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance("/xml/dsd_with_df_attr_relationship.xml", true);

        DataStructureDefinitionReader dataStructureDefinitionReader = createDataStructureDefinitionReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(dataStructureDefinitionReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataStructureDefinition actual = artefacts.getDataStructures().iterator().next();

        AttributeDescriptor attributeDescriptor = actual.getAttributeDescriptor();
        DataAttribute dataAttribute = attributeDescriptor.getComponents().get(0);
        assertThat(dataAttribute.getAttributeRelationship()).isNull();

        List<MetadataAttributeRef> metaAttributes = actual.getMetaAttributeReferences();
        MetadataAttributeRef metaAttribute = metaAttributes.get(0);
        assertThat(metaAttribute.getMetadataRelationship()).isNull();
    }
}
