package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Configuration;
import net.javacrumbs.jsonunit.core.Option;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class DimensionListWriterJsonTest {

    public static final String DIMENSION_LIST_JSON = "/serialization.expected/dimension-list.json";
    public static final String DIMENSION_LIST_EMPTY_JSON = "/serialization.expected/dimension-list_empty.json";

    @InjectMocks
    public DimensionListWriter dimensionListWriterJson;
    @Spy
    private IdentifiableWriter annotableWriterSpy = MaintainableArtifactsTestUtils.getIdentifiableWriter();
    @Spy
    private ComponentWriter componentWriterSpy = MaintainableArtifactsTestUtils.getComponentWriter();
    @Spy
    private ConceptRoleWriter conceptRoleWriterSpy = MaintainableArtifactsTestUtils.getConceptRoleWriter();

    AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }

    @Test
    void writeDimensionListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DimensionDescriptorImpl descriptor = MaintainableArtifactsTestUtils.fillDimensionDescriptor();
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            dimensionListWriterJson.write(jGenerator, descriptor);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(DIMENSION_LIST_JSON, StandardCharsets.UTF_8);
        System.out.println(expected);
        JsonAssert.assertJsonEquals(expected, actual, Configuration.empty().withOptions(Option.IGNORING_EXTRA_FIELDS));
    }

    @Test
    void writeEmptyDimensionListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            dimensionListWriterJson.write(jGenerator, null);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(DIMENSION_LIST_EMPTY_JSON, StandardCharsets.UTF_8);
        System.out.println(expected);
        JsonAssert.assertJsonEquals(expected, actual, Configuration.empty().withOptions(Option.IGNORING_EXTRA_FIELDS));
    }
}
