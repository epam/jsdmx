package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.REPRESENTATION_MAP_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class RepresentationMapReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    public RepresentationMapReader representationMapReader;

    @Test
    void readRepresentationMapFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(REPRESENTATION_MAP_JSON);
        RepresentationMapImpl expected = MaintainableArtifactsTestUtils.buildRepresentationMap();

        //when
        RepresentationMapImpl actual = (RepresentationMapImpl) representationMapReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }
}
