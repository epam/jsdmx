package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.CATEGORISATION_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class CategorisationReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    private CategorisationReader categorisationReader;

    @Test
    void readCategorisationFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(CATEGORISATION_JSON);
        CategorisationImpl expected = MaintainableArtifactsTestUtils.buildCategorisation();

        //when
        CategorisationImpl actual = (CategorisationImpl) categorisationReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

}
