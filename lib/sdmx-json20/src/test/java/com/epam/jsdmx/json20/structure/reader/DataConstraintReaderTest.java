package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.DATA_CONSTRAINT_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class DataConstraintReaderTest {

    @InjectMocks
    private DataConstraintReader dataConstraintReader;
    @Spy
    private VersionableReader versionableReader = MaintainableArtifactsTestUtils.getVersionableReader();
    @Spy
    private ReleaseCalendarReader releaseCalendarReader = MaintainableArtifactsTestUtils.getReleaseCalendarReader();
    @Spy
    private DataKeySetReader dataKeySetReader = MaintainableArtifactsTestUtils.getDataKeySetReader();
    @Spy
    private CubeRegionReader cubeRegionReader = MaintainableArtifactsTestUtils.getCubeRegionReader();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readDataConstraintFromJsonTest() throws IOException {
        //given
        InputStream resourceAsStream = DataConstraintReaderTest.class.getResourceAsStream(DATA_CONSTRAINT_JSON);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(resourceAsStream);
        DataConstraintImpl expected = MaintainableArtifactsTestUtils.buildDataConstraint();

        //when
        DataConstraintImpl actual = (DataConstraintImpl) dataConstraintReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

}
