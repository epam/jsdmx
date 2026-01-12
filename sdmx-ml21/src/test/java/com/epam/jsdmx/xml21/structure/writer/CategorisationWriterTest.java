package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils.setMaintainableArtefact;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml21.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class CategorisationWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCategorisation() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        CategorisationWriter categorisationWriter = createCategorisationWriter();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            bytes,
            List.of(categorisationWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CategorisationImpl categorisation = MaintainableArtifactsTestUtils.buildCategorisation();
        artefacts.getCategorisations().add(categorisation);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.CATEGORISATION_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actual = bytes.toString();
        assertXMLEqual(expected, actual);

        sdmxSourceCompatibilityTester.test(actual, SdmxBeans::getCategorisations);
    }

    @Test
    void writeCategorisationWithHierarchyReference() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        CategorisationWriter categorisationWriter = createCategorisationWriter();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            bytes,
            List.of(categorisationWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CategorisationImpl categorisation = new CategorisationImpl();
        setMaintainableArtefact(categorisation);
        IdentifiableArtefactReferenceImpl cateogryReference = new IdentifiableArtefactReferenceImpl(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.CATEGORY,
            "CAT_TARGET"
        );

        categorisation.setCategorizedBy(cateogryReference);
        MaintainableArtefactReference artefactReference = new MaintainableArtefactReference(
            "ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.HIERARCHY
        );
        categorisation.setCategorizedArtefact(artefactReference);
        artefacts.getCategorisations().add(categorisation);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.CATEGORISATION_HIERARCHY_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        final String actual = bytes.toString();
        assertXMLEqual(expected, actual);

        sdmxSourceCompatibilityTester.test(actual, SdmxBeans::getCategorisations);
    }
}
