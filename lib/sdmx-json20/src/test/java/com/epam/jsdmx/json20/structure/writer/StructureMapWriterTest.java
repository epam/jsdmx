package com.epam.jsdmx.json20.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ComponentMap;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class StructureMapWriterTest extends BaseJsonWriterTest {

    public static final String STRUCTURE_MAP_JSON = "/serialization.expected/structure-map.json";
    public static final String STRUCTURE_MAP_WITH_NULL_TARGET_SOURCE_JSON = "/serialization.expected/structure-map-with-null-target-source.json";
    public static final String STRUCTURE_MAP_WITH_NULL_MAP_JSON = "/serialization.expected/structure-map-with-null-map.json";
    @InjectMocks
    public StructureMapWriter structureMapWriter;

    @Test
    void writeStructureMapToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StructureMap structureMap = MaintainableArtifactsTestUtils.buildStructureMap();

        //when
        structureMapWriter.writeAndClose(createJsonGenerator(stream), structureMap);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then

        String expected = IOUtils.resourceToString(STRUCTURE_MAP_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

    @Test
    void writeStructureMapToJsonWithNullTargetSource() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StructureMapImpl structureMap = MaintainableArtifactsTestUtils.buildStructureMap();
        ComponentMapImpl componentMaRep = new ComponentMapImpl();
        MaintainableArtefactReference reference3 = new MaintainableArtefactReference(
            "urn:sdmx:org.sdmx.infomodel.structuremapping.RepresentationMap=IMF:ARTEFACT(1.2)");
        componentMaRep.setRepresentationMap(reference3);
        List<ComponentMap> componentMaps = structureMap.getComponentMaps();
        List<ComponentMap> componentMapsAll = new ArrayList<>(componentMaps);
        componentMapsAll.add(componentMaRep);
        structureMap.setComponentMaps(componentMapsAll);
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        structureMapWriter.writeAndClose(jGenerator, structureMap);
        String actual = stream.toString(StandardCharsets.UTF_8);
        //then
        String expected = IOUtils.resourceToString(STRUCTURE_MAP_WITH_NULL_TARGET_SOURCE_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }

    @Test
    void writeStructureMapToJsonWithNullMap() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StructureMapImpl structureMap = MaintainableArtifactsTestUtils.buildStructureMap();
        structureMap.setComponentMaps(null);
        //when
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        structureMapWriter.writeAndClose(jGenerator, structureMap);
        String actual = stream.toString(StandardCharsets.UTF_8);
        //then
        String expected = IOUtils.resourceToString(STRUCTURE_MAP_WITH_NULL_MAP_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }
}
