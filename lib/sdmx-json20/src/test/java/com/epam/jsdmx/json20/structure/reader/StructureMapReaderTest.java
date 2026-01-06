package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.ComponentMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.DatePatternMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.FrequencyFormatMappingImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ResolvePeriod;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json20.structure.TestUtils;
import com.epam.jsdmx.serializer.sdmx30.common.InMemoryDataLocation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructureMapReaderTest {

    public JsonStructureReader jsonStructureReader;

    @BeforeEach
    void setup() {
        jsonStructureReader = (JsonStructureReader) MaintainableArtifactsTestUtils.getStructureReader();
    }

    @Test
    void testStructureMap() throws IOException {
        //given
        InputStream resourceAsStream = DataReaderTest.class.getResourceAsStream(TestUtils.STRUCTURE_MAP_JSON);
        assert resourceAsStream != null;
        byte[] structuresJsonBytes = resourceAsStream.readAllBytes();
        InMemoryDataLocation location = new InMemoryDataLocation(new String(structuresJsonBytes, StandardCharsets.UTF_8));
        //when
        ArtefactsImpl artefacts = jsonStructureReader.readAndClose(location);
        StructureMapImpl expectedStr = MaintainableArtifactsTestUtils.buildStructureMap();
        Set<StructureMap> structureMaps = Set.of(expectedStr);
        //then
        assertEquals(structureMaps, artefacts.getStructureMaps());
    }

    @Test
    void testStructureMapList() throws IOException {
        //given
        InputStream resourceAsStream = DataReaderTest.class.getResourceAsStream(TestUtils.STRUCTURE_MAP_LIST_JSON);
        assert resourceAsStream != null;
        byte[] structuresJsonBytes = resourceAsStream.readAllBytes();
        InMemoryDataLocation location = new InMemoryDataLocation(new String(structuresJsonBytes, StandardCharsets.UTF_8));
        //when
        ArtefactsImpl artefacts = jsonStructureReader.readAndClose(location);
        StructureMap expectedStructure1 = MaintainableArtifactsTestUtils.buildStructureMap();
        StructureMap expectedStructure2 = buildStructureMap2();
        Set<StructureMap> structureMaps = Set.of(expectedStructure1, expectedStructure2);
        //then
        assertEquals(structureMaps, artefacts.getStructureMaps());

    }

    private StructureMap buildStructureMap2() {
        StructureMapImpl structureMap = new StructureMapImpl();
        MaintainableArtifactsTestUtils.setMaintainableArtefact(structureMap);
        structureMap.setId("STRUC_ID2");
        structureMap.setOrganizationId("QUANTHUB");
        structureMap.setSource(new MaintainableArtefactReference("DataFSource", "EPM", "3.0", StructureClassImpl.DATAFLOW));
        structureMap.setTarget(new MaintainableArtefactReference("DataFTarget", "EPM", "3.0", StructureClassImpl.DATAFLOW));
        fillComponentMap(structureMap);
        fillDatePattern(structureMap);
        return structureMap;
    }

    void fillDatePattern(StructureMapImpl structureMap) {
        DatePatternMapImpl datePatternMap = new DatePatternMapImpl();
        datePatternMap.setLocale("EN");
        datePatternMap.setSourcePattern("SOURCE");
        datePatternMap.setId("DP1");
        datePatternMap.setResolvePeriod(ResolvePeriod.MID_PERIOD);
        FrequencyFormatMappingImpl frequencyFormatMapping1 = getFrequencyFormatMapping("CODE_1", "FR1");
        FrequencyFormatMappingImpl frequencyFormatMapping2 = getFrequencyFormatMapping("CODE_2", "FR2");
        datePatternMap.setMappedFrequencies(List.of(frequencyFormatMapping1, frequencyFormatMapping2));
        structureMap.setDatePatternMaps(List.of(datePatternMap));
    }

    private FrequencyFormatMappingImpl getFrequencyFormatMapping(String freqCode, String id) {
        FrequencyFormatMappingImpl frequencyFormatMapping = new FrequencyFormatMappingImpl();
        frequencyFormatMapping.setFrequencyCode(freqCode);
        frequencyFormatMapping.setDatePattern("2022-11");
        frequencyFormatMapping.setId(id);
        return frequencyFormatMapping;
    }

    void fillComponentMap(StructureMapImpl structureMap) {
        ComponentMapImpl componentMapDP = new ComponentMapImpl();
        componentMapDP.setSource(List.of("CompMapDPS"));
        componentMapDP.setTarget(List.of("CompMapDPT"));
        IdentifiableArtefactReferenceImpl reference2 = new IdentifiableArtefactReferenceImpl(
            "STRUC_ID2",
            "QUANTHUB",
            "1.2",
            StructureClassImpl.DATE_PATTERN_MAP,
            "DP1"
        );
        componentMapDP.setRepresentationMap(reference2);

        ComponentMapImpl componentMaRep = new ComponentMapImpl();
        componentMaRep.setSource(List.of("CompMapRepS"));
        componentMaRep.setTarget(List.of("CompMapRepT"));
        IdentifiableArtefactReferenceImpl reference3 = new IdentifiableArtefactReferenceImpl(
            "STRUC_ID2",
            "QUANTHUB",
            "1.2",
            StructureClassImpl.REPRESENTATION_MAP,
            "REPRES"
        );
        componentMaRep.setRepresentationMap(reference3);

        structureMap.setComponentMaps(List.of(componentMapDP, componentMaRep));
    }
}
