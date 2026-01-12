package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.serializer.sdmx30.common.InMemoryDataLocation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderTest {

    public static final String ARTEFACTS_JSON = "/deserialization.expected/json.json";
    public static final String ALL_ARTEFACTS_JSON = "/deserialization.expected/structures-all-artefacts.json";

    public JsonStructureReader jsonStructureReader;

    @BeforeEach
    void setup() {
        jsonStructureReader = (JsonStructureReader) MaintainableArtifactsTestUtils.getStructureReader();
    }

    @Test
    void readArtefacts() throws IOException {
        InputStream resourceAsStream = DataReaderTest.class.getResourceAsStream(ARTEFACTS_JSON);
        assert resourceAsStream != null;
        byte[] structuresJsonBytes = resourceAsStream.readAllBytes();
        InMemoryDataLocation location = new InMemoryDataLocation(new String(structuresJsonBytes, StandardCharsets.UTF_8));
        ArtefactsImpl artefacts = jsonStructureReader.readAndClose(location);
        CategorisationImpl expected = MaintainableArtifactsTestUtils.buildCategorisation();
        StructureMapImpl expectedStr = MaintainableArtifactsTestUtils.buildStructureMap();
        Set<CategorisationImpl> categorisations = Set.of(expected);
        Set<StructureMapImpl> structureMaps = Set.of(expectedStr);
        assertEquals(categorisations, artefacts.getCategorisations());
        assertEquals(structureMaps, artefacts.getStructureMaps());
    }

    @Test
    void readAllArtefactsFromJson() throws IOException {
        InputStream resourceAsStream = DataReaderTest.class.getResourceAsStream(ALL_ARTEFACTS_JSON);
        assert resourceAsStream != null;
        byte[] structuresJsonBytes = resourceAsStream.readAllBytes();
        InMemoryDataLocation location = new InMemoryDataLocation(new String(structuresJsonBytes, StandardCharsets.UTF_8));
        ArtefactsImpl artefacts = jsonStructureReader.readAndClose(location);
        CategorisationImpl categorisation = MaintainableArtifactsTestUtils.buildCategorisation();
        StructureMapImpl structureMap = MaintainableArtifactsTestUtils.buildStructureMap();
        DataStructureDefinitionImpl dataStructureDefinition = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        dataStructureDefinition.setAttributeDescriptor(MaintainableArtifactsTestUtils.fillAttributeDescriptor());
        dataStructureDefinition.setDimensionDescriptor(MaintainableArtifactsTestUtils.fillDimensionDescriptor());
        dataStructureDefinition.setMeasureDescriptor(MaintainableArtifactsTestUtils.fillMeasureDescriptor());
        MetadataStructureDefinitionImpl metadataStructureDefinition = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();
        DataflowImpl dataflow = MaintainableArtifactsTestUtils.buildDataFlow();
        MetadataflowImpl metadataflow = MaintainableArtifactsTestUtils.buildMetadataFlow();
        RepresentationMapImpl representationMap = MaintainableArtifactsTestUtils.buildRepresentationMap();
        HierarchyImpl hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();
        CodelistImpl codelist = MaintainableArtifactsTestUtils.buildCodeList();
        CategorySchemeImpl categoryScheme = MaintainableArtifactsTestUtils.buildCategoryScheme();
        ConceptSchemeImpl conceptScheme = MaintainableArtifactsTestUtils.buildConceptScheme();
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();

        assertArtefacts(artefacts, categorisation, structureMap, dataStructureDefinition, metadataStructureDefinition, dataflow, metadataflow,
            representationMap, hierarchy, codelist, categoryScheme, conceptScheme, dataConstraint
        );
    }

    @SneakyThrows
    private void assertArtefacts(ArtefactsImpl artefacts,
                                 CategorisationImpl categorisation,
                                 StructureMapImpl structureMap,
                                 DataStructureDefinitionImpl dataStructureDefinition,
                                 MetadataStructureDefinitionImpl metadataStructureDefinition,
                                 DataflowImpl dataflow,
                                 MetadataflowImpl metadataflow,
                                 RepresentationMapImpl representationMap,
                                 HierarchyImpl hierarchy,
                                 CodelistImpl codelist,
                                 CategorySchemeImpl categoryScheme,
                                 ConceptSchemeImpl conceptScheme,
                                 DataConstraintImpl dataConstraint) {

        Set<Categorisation> categorisations = Set.of(categorisation);
        Set<StructureMap> structureMaps = Set.of(structureMap);
        Set<Dataflow> dataflowSet = Set.of(dataflow);
        Set<Metadataflow> metadataflows = Set.of(metadataflow);
        Set<DataStructureDefinition> dataStructureDefinitions = Set.of(dataStructureDefinition);
        Set<MetadataStructureDefinition> metadataStructureDefinitions = Set.of(metadataStructureDefinition);
        Set<Hierarchy> hierarchies = Set.of(hierarchy);
        Set<Codelist> codelists = Set.of(codelist);
        Set<ConceptScheme> conceptSchemes = Set.of(conceptScheme);
        Set<CategoryScheme> categorySchemes = Set.of(categoryScheme);
        Set<RepresentationMap> representationMaps = Set.of(representationMap);
        Set<DataConstraint> dataConstraintMaps = Set.of(dataConstraint);
        ArtefactAssertion.assertThat(artefacts)
            .hasCategorisation(categorisations)
            .hasCategoryScheme(categorySchemes)
            .hasCodeLists(codelists)
            .hasDataFlows(dataflowSet)
            .hasDSD(dataStructureDefinitions)
            .hasMDSD(metadataStructureDefinitions)
            .hasHierarchies(hierarchies)
            .hasConceptSchemes(conceptSchemes)
            .hasRepresentationMaps(representationMaps)
            .hasStructureMaps(structureMaps)
            .hasMetadataFlows(metadataflows)
            .hasDataConstraint(dataConstraintMaps);
    }
}
