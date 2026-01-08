package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORY_SCHEME_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Category;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategorySchemeReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(CATEGORY_SCHEME_XML, minifyXml);

        CategorySchemeReader categorySchemeReader = createCategorySchemeReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(categorySchemeReader)
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);
        CategoryScheme actualCatScheme = artefacts.getCategorySchemes().iterator().next();
        CategorySchemeImpl expectedCatScheme = MaintainableArtifactsTestUtils.buildCategoryScheme();

        //then
        List<? extends Category> actualCategorySchemeItems = actualCatScheme.getItems();
        List<Category> expectedCategorySchemeItems = expectedCatScheme.getItems();

        Assertions.assertEquals(expectedCatScheme, actualCatScheme);
        Assertions.assertEquals(expectedCategorySchemeItems, actualCategorySchemeItems);
    }
}
