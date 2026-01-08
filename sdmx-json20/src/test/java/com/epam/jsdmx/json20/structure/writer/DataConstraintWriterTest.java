package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.DATA_CONSTRAINT_EMPTY_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.DATA_CONSTRAINT_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

class DataConstraintWriterTest extends BaseJsonWriterTest {

    @InjectMocks
    public DataConstraintWriter dataConstraintWriter;
    @Spy
    private CubeRegionWriter cubeRegionWriterSpy = MaintainableArtifactsTestUtils.getCubeRegionWriter();
    @Spy
    private DataKeySetsWriter dataKeySetsWriter = MaintainableArtifactsTestUtils.getDataKeySetsWriter();

    @Test
    void writeDataConstraintToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();

        //when
        dataConstraintWriter.writeAndClose(createJsonGenerator(stream), dataConstraint);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(DATA_CONSTRAINT_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

    @Test
    void writeDataConstraintEmptyToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();
        dataConstraint.setDataContentKeys(List.of());
        dataConstraint.setCubeRegions(List.of());
        dataConstraint.setReleaseCalendar(null);

        //when
        dataConstraintWriter.writeAndClose(createJsonGenerator(stream), dataConstraint);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(DATA_CONSTRAINT_EMPTY_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

}
