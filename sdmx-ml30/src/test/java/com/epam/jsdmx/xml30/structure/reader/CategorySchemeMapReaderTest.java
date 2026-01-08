package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORY_SCHEME_MAP_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategorySchemeMapReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(CATEGORY_SCHEME_MAP_XML, minifyXml);

        CategorySchemeMapReader categorySchemeMapReader = createCategorySchemeMapReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(categorySchemeMapReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        CategorySchemeMap actual = artefacts.getCategorySchemeMaps().iterator().next();
        CategorySchemeMap expected = MaintainableArtifactsTestUtils.buildCategorySchemeMap();

        Assertions.assertEquals(expected, actual);
    }
}
