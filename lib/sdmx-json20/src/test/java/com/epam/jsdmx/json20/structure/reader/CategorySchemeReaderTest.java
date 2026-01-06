package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.CATEGORY_SCHEMA_2_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.CATEGORY_SCHEMA_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CategorySchemeReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    private CategorySchemeReader categorySchemeReader;

    @Test
    void readCategorySchemeFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(CATEGORY_SCHEMA_JSON);
        CategoryScheme expected = MaintainableArtifactsTestUtils.buildCategoryScheme();

        //when
        CategoryScheme actual = (CategoryScheme) categorySchemeReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void testCategoryScheme2() throws IOException {
        //given
        JsonParser parser = createParser(CATEGORY_SCHEMA_2_JSON);
        CategoryScheme expected = getCategorySchemeExpected();

        //when
        CategorySchemeImpl actual = (CategorySchemeImpl) categorySchemeReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    private CategoryScheme getCategorySchemeExpected() {
        CategorySchemeImpl categoryScheme = new CategorySchemeImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(categoryScheme);
        categoryScheme.setPartial(true);
        return categoryScheme;
    }
}
