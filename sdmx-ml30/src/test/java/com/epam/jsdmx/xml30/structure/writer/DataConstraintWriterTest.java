package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS_EMPTY_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class DataConstraintWriterTest extends BaseXmlWriterTest {

    @Test
    void writeDataConstraint() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        DataConstraintWriter dataConstraintWriter = createDataConstraintWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(dataConstraintWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataConstraint dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();
        artefacts.getDataConstraints().add(dataConstraint);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(DATA_CONSTRAINTS_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }

    @Test
    void writeEmptyDataConstraint() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        DataConstraintWriter dataConstraintWriter = createDataConstraintWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(dataConstraintWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();
        dataConstraint.setDataContentKeys(List.of());
        dataConstraint.setCubeRegions(List.of());
        dataConstraint.setReleaseCalendar(new ReleaseCalendarImpl());
        artefacts.getDataConstraints().add(dataConstraint);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(DATA_CONSTRAINTS_EMPTY_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
