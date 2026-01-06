package com.epam.jsdmx.xml21.structure.writer;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml21.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class DataStructureDefinitionWriterTest extends BaseXmlWriterTest {

    @Test
    void writeDsd() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        DataStructureDefinitionWriter dataStructureDefinitionWriter = createDataStructureDefinitionWriter();
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(dataStructureDefinitionWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataStructureDefinitionImpl dataStructureDefinition = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        dataStructureDefinition.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        dataStructureDefinition.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        dataStructureDefinition.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());
        dataStructureDefinition.setGroupDimensionDescriptor(MaintainableArtifactsTestUtils.fillListGroupDimensionDescriptor());

        List<MetadataAttributeRef> attributeRefs = dataStructureDefinition.getAttributeDescriptor().getMetadataAttributes();
        List<MetadataAttributeRef> refs = List.of(attributeRefs.get(0), attributeRefs.get(1));
        dataStructureDefinition.getAttributeDescriptor().setMetadataAttributes(refs);
        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.DSD_XML);
        artefacts.getDataStructures().add(dataStructureDefinition);
        xmlStructureWriter.write(artefacts);

        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getDataStructures);
    }
}
