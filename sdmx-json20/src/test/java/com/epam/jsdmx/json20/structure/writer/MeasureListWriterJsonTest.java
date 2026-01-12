package com.epam.jsdmx.json20.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class MeasureListWriterJsonTest {

    public static final String MEASURE_LIST_JSON = "/serialization.expected/measure-list.json";
    public static final String MEASURE_LIST_EMPTY_JSON = "/serialization.expected/measure-list_empty.json";
    @InjectMocks
    public MeasureListWriter measureListWriterJson;
    @Spy
    private AnnotableWriter annotableWriterSpy = MaintainableArtifactsTestUtils.getAnnotableWriter();
    @Spy
    private ComponentWriter componentWriterSpy = MaintainableArtifactsTestUtils.getComponentWriter();
    @Spy
    private ConceptRoleWriter conceptRoleWriterSpy = MaintainableArtifactsTestUtils.getConceptRoleWriter();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void writeMeasureListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MeasureDescriptorImpl descriptor = MaintainableArtifactsTestUtils.fillMeasureDescriptor();
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            measureListWriterJson.write(jGenerator, descriptor);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(MEASURE_LIST_JSON, StandardCharsets.UTF_8);
        assertJsonEquals(expected, actual);
    }

    @Test
    void writeEmptyMeasureListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            measureListWriterJson.write(jGenerator, null);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(MEASURE_LIST_EMPTY_JSON, StandardCharsets.UTF_8);
        assertJsonEquals(expected, actual);
    }
}
