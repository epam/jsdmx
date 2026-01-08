package com.epam.jsdmx.xml21.structure.writer;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml21.structure.TestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.xml.sax.SAXException;

class CategorySchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCategoryScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        CategorySchemeWriter categorySchemeWriter = createCategorySchemeWriter();
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(categorySchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CategorySchemeImpl categoryScheme = MaintainableArtifactsTestUtils.buildCategoryScheme();
        artefacts.getCategorySchemes().add(categoryScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(TestUtils.CATEGORY_SCHEME_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());

        sdmxSourceCompatibilityTester.test(actual.toString(), SdmxBeans::getCategorySchemes);
    }
}
