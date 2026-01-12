package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
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

class AttributeListWriterJsonTest {

    public static final String ATTRIBUTE_LIST_JSON = "/serialization.expected/attribute-list.json";
    public static final String ATTRIBUTE_LIST_EMPTY_JSON = "/serialization.expected/attribute-list_empty.json";

    @Spy
    private IdentifiableWriter annotableWriterSpy = MaintainableArtifactsTestUtils.getIdentifiableWriter();
    @Spy
    private ComponentWriter componentWriterSpy = MaintainableArtifactsTestUtils.getComponentWriter();
    @Spy
    private ConceptRoleWriter conceptRoleWriterSpy = MaintainableArtifactsTestUtils.getConceptRoleWriter();
    @InjectMocks
    public AttributeListWriter attributeListWriterJson;

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
    void writeAttributeListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        AttributeDescriptorImpl descriptor = MaintainableArtifactsTestUtils.fillAttributeDescriptor();

        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            attributeListWriterJson.write(jGenerator, descriptor);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(ATTRIBUTE_LIST_JSON, StandardCharsets.UTF_8);

        JsonAssert.assertJsonEquals(expected, actual, Configuration.empty().withOptions(Option.IGNORING_EXTRA_FIELDS));
    }

    @Test
    void writeEmptyAttributeListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            attributeListWriterJson.write(jGenerator, null);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);
        System.out.println(actual);

        //then
        String expected = IOUtils.resourceToString(ATTRIBUTE_LIST_EMPTY_JSON, StandardCharsets.UTF_8);
        JsonAssert.assertJsonEquals(expected, actual, Configuration.empty().withOptions(Option.IGNORING_EXTRA_FIELDS));
    }
}
