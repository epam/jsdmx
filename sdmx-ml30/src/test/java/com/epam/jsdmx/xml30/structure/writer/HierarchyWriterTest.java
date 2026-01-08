package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.HIERARCHIES_NAMES_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.HIERARCHIES_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class HierarchyWriterTest extends BaseXmlWriterTest {

    @Test
    void writeHierarchy() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        HierarchyWriter hierarchyWriter = createHierarchyWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter =
            new XmlStructureWriter(
                actual,
                List.of(hierarchyWriter),
                headerWriter,
                false
            );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        HierarchyImpl hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();
        artefacts.getHierarchies().add(hierarchy);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(HIERARCHIES_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }

    @Test
    void writeHierarchyWithNull() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        HierarchyWriter hierarchyWriter = createHierarchyWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter =
            new XmlStructureWriter(actual, List.of(hierarchyWriter), new HeaderWriter(defaultHeaderProvider), false);

        //when
        Artefacts artefacts = new ArtefactsImpl();
        HierarchyImpl hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();
        InternationalString name = hierarchy.getName();
        name.add("it", null);
        artefacts.getHierarchies().add(hierarchy);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(HIERARCHIES_NAMES_XML);
        System.out.println(actual);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
