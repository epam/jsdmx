package com.epam.jsdmx.xml30.structure.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMap;
import com.epam.jsdmx.infomodel.sdmx30.StructureMap;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.xml30.structure.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class XmlStructureReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void readAll(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(TestUtils.ALL_XML, minifyXml);

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader,
            List.of(
                createCategorisationReader(),
                createCategorySchemeReader(),
                createConceptSchemeReader(),
                createCodelistReader(),
                createDataFlowReader(),
                createDataStructureDefinitionReader(),
                createMetadataStructureDefinitionReader(),
                createHierarchyReader(),
                createRepresentationMapReader(),
                createStructureMapReader(),
                createMetadataflowReader(),
                createDataConstraintReader()
            )
        );

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        Categorisation categorisationActual = artefacts.getCategorisations().iterator().next();
        CategoryScheme categorySchemeActual = artefacts.getCategorySchemes().iterator().next();
        ConceptScheme conceptSchemeActual = artefacts.getConceptSchemes().iterator().next();
        Codelist codelistActual = artefacts.getCodelists().iterator().next();
        Dataflow dataflowActual = artefacts.getDataflows().iterator().next();
        DataStructureDefinition dataStructureDefinitionActual = artefacts.getDataStructures().iterator().next();
        Hierarchy hierarchyActual = artefacts.getHierarchies().iterator().next();
        Metadataflow metadataflowActual = artefacts.getMetadataflows().iterator().next();
        MetadataStructureDefinition metadataStructureDefinitionActual = artefacts.getMetadataStructureDefinitions().iterator().next();
        RepresentationMap representationMapActual = artefacts.getRepresentationMaps().iterator().next();
        StructureMap structureMapActual = artefacts.getStructureMaps().iterator().next();
        DataConstraint dataConstraintActual = artefacts.getDataConstraints().iterator().next();

        Categorisation categorisationExpected = MaintainableArtifactsTestUtils.buildCategorisation();
        CategoryScheme categorySchemeExpected = MaintainableArtifactsTestUtils.buildCategoryScheme();
        ConceptScheme conceptSchemeExpected = MaintainableArtifactsTestUtils.buildConceptScheme();
        Codelist codelistExpected = MaintainableArtifactsTestUtils.buildCodeList();
        Dataflow dataflowExpected = MaintainableArtifactsTestUtils.buildDataFlow();
        DataStructureDefinition dataStructureDefinitionExpected = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        Hierarchy hierarchyExpected = MaintainableArtifactsTestUtils.buildHierarchy();
        Metadataflow metadataflowExpected = MaintainableArtifactsTestUtils.buildMetadataFlow();
        MetadataStructureDefinition metadataStructureDefinitionExpected = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();
        RepresentationMap representationMapExpected = MaintainableArtifactsTestUtils.buildRepresentationMap();
        StructureMap structureMapExpected = MaintainableArtifactsTestUtils.buildStructureMap();
        DataConstraint dataConstraintExpected = MaintainableArtifactsTestUtils.buildDataConstraint();

        //then
        Assertions.assertEquals(conceptSchemeExpected, conceptSchemeActual);
        Assertions.assertEquals(dataflowExpected, dataflowActual);
        Assertions.assertEquals(codelistExpected, codelistActual);
        Assertions.assertEquals(categorisationExpected, categorisationActual);
        Assertions.assertEquals(dataStructureDefinitionExpected, dataStructureDefinitionActual);
        Assertions.assertEquals(metadataflowExpected, metadataflowActual);
        Assertions.assertEquals(metadataStructureDefinitionExpected, metadataStructureDefinitionActual);
        Assertions.assertEquals(representationMapExpected, representationMapActual);
        Assertions.assertEquals(structureMapExpected, structureMapActual);
        Assertions.assertEquals(categorySchemeExpected, categorySchemeActual);
        Assertions.assertEquals(hierarchyExpected, hierarchyActual);
        Assertions.assertEquals(dataConstraintExpected, dataConstraintActual);
    }
}
