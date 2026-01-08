package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORY_SCHEME_MAP_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMapImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class CategorySchemeMapWriterTest extends BaseXmlWriterTest {

    @Test
    void writeCategorySchemeMap() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        CategorySchemeMapWriter categorySchemeMapWriter = createCategorySchemeMapWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(categorySchemeMapWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        CategorySchemeMapImpl categorySchemeMap = MaintainableArtifactsTestUtils.buildCategorySchemeMap();
        artefacts.getCategorySchemeMaps().add(categorySchemeMap);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(CATEGORY_SCHEME_MAP_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
