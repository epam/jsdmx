package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.TestUtils.DATA_CONSTRAINTS_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.META_DATA_CONSTRAINTS_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraint;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

public class ContentConstraintWriterTest extends BaseXmlWriterTest {

    @Test
    void writeContentConstraint() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ContentConstraintWriter writer = createContentConstraintWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter =
            new Xml21StructureWriter(
                actual,
                List.of(writer),
                headerWriter,
                false
            );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();
        artefacts.getDataConstraints().add(dataConstraint);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(DATA_CONSTRAINTS_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(expected, SdmxBeans::getContentConstraintBeans);
    }

    @Test
    void writeContentConstraintWithMeta() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ContentConstraintWriter writer = createContentConstraintWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter =
            new Xml21StructureWriter(
                actual,
                List.of(writer),
                headerWriter,
                false
            );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        MetadataConstraint constraint = MaintainableArtifactsTestUtils.buildMetadataConstraint(false);
        artefacts.getMetadataConstraints().add(constraint);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(META_DATA_CONSTRAINTS_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actualString = actual.toString();
        assertXMLEqual(expected, actualString);

        sdmxSourceCompatibilityTester.test(actualString, SdmxBeans::getContentConstraintBeans);
    }

}
