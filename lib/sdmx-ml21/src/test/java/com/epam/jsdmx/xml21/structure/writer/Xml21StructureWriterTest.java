package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.TestUtils.ALL_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureMapImpl;
import com.epam.jsdmx.xml21.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class Xml21StructureWriterTest extends BaseXmlWriterTest {

    @Test
    void writeAll() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);
        // root elements
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        Xml21StructureWriter xmlStructureWriter = new Xml21StructureWriter(
            actual,
            List.of(
                createCategorisationWriter(),
                createConceptSchemeWriter(),
                createCodelistWriter(),
                createCategorySchemeWriter(),
                createDataflowWriter(),
                createDataStructureDefinitionWriter(),
                createHierarchyWriter(),
                createOrganisationSchemeWriter()
            ),
            headerWriter,
            true
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();

        ConceptSchemeImpl conceptScheme = MaintainableArtifactsTestUtils.buildConceptScheme();
        artefacts.getConceptSchemes().add(conceptScheme);

        CategoryScheme categoryScheme = MaintainableArtifactsTestUtils.buildCategoryScheme();
        artefacts.getCategorySchemes().add(categoryScheme);

        CategorisationImpl categorisation = MaintainableArtifactsTestUtils.buildCategorisation();
        artefacts.getCategorisations().add(categorisation);

        CodelistImpl codelist = MaintainableArtifactsTestUtils.buildCodeList();
        artefacts.getCodelists().add(codelist);

        DataflowImpl dataflow = MaintainableArtifactsTestUtils.buildDataFlow();
        artefacts.getDataflows().add(dataflow);

        MetadataflowImpl metadataflow = MaintainableArtifactsTestUtils.buildMetadataFlow();
        artefacts.getMetadataflows().add(metadataflow);

        DataStructureDefinitionImpl dataStructureDefinition = MaintainableArtifactsTestUtils.buildDataStructureDefinition();
        artefacts.getDataStructures().add(dataStructureDefinition);

        MetadataStructureDefinitionImpl metadataStructureDefinition = MaintainableArtifactsTestUtils.buildMetadataStructureDefinition();
        artefacts.getMetadataStructureDefinitions().add(metadataStructureDefinition);

        HierarchyImpl hierarchy = MaintainableArtifactsTestUtils.buildHierarchy();
        artefacts.getHierarchies().add(hierarchy);

        RepresentationMapImpl representationMap = MaintainableArtifactsTestUtils.buildRepresentationMap();
        artefacts.getRepresentationMaps().add(representationMap);

        StructureMapImpl structureMap = MaintainableArtifactsTestUtils.buildStructureMap();
        artefacts.getStructureMaps().add(structureMap);

        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(ALL_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }
}
