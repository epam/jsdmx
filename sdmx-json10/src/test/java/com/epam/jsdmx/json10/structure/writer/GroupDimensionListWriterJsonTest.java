package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptor;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Configuration;
import net.javacrumbs.jsonunit.core.Option;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class GroupDimensionListWriterJsonTest {
    public static final String GROUP_DIMENSION_LIST_JSON = "/serialization.expected/group-dimension-list.json";

    @InjectMocks
    public GroupDimensionListWriter groupDimensionListWriter;
    @Spy
    private IdentifiableWriter identifiableWriter = MaintainableArtifactsTestUtils.getIdentifiableWriter();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void writeDimensionListToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<GroupDimensionDescriptor> descriptors = MaintainableArtifactsTestUtils.fillListGroupDimensionDescriptor().stream()
            .map(a -> (GroupDimensionDescriptor) a)
            .collect(Collectors.toList());
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        try (jGenerator) {
            groupDimensionListWriter.write(jGenerator, descriptors);
        }
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(GROUP_DIMENSION_LIST_JSON, StandardCharsets.UTF_8);

        JsonAssert.assertJsonEquals(expected, actual, Configuration.empty().withOptions(Option.IGNORING_EXTRA_FIELDS));
    }
}