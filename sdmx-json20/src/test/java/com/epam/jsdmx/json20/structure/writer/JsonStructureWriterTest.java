package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.writer.StructureUtils.SCHEMA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.infomodel.sdmx30.RepresentationMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonStructureWriterTest {

    public static final String STRUCTURE_JSON = "/serialization.expected/structure.json";
    public static final String EMPTY_STRUCTURE_JSON = "/serialization.expected/empty-structure.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final DefaultHeaderProvider defaultHeaderProvider = mock(DefaultHeaderProvider.class);
    private JsonStructureWriter jsonStructureWriter;

    @BeforeEach
    @SneakyThrows
    void setup() {
        jsonStructureWriter = MaintainableArtifactsTestUtils.createStructureWriter(outputStream, objectMapper, defaultHeaderProvider);
        when(defaultHeaderProvider.provide()).thenReturn(buildHeader());
    }

    @SneakyThrows
    private Header buildHeader() {
        Header header = new Header();
        header.setSchema(new URI(SCHEMA));
        header.setId("IDREF7553");
        header.setContentLanguages(List.of(Locale.ENGLISH));
        header.setTest(false);
        header.setPrepared(Instant.parse("2022-04-03T15:35:01Z"));
        header.setSender(buildParty());
        return header;
    }

    private Party buildParty() {
        Party party = new Party();
        party.setId("unknown");
        return party;
    }

    @Test
    void writeArtefactsToJson() throws Exception {
        ArtefactsImpl artefacts = new ArtefactsImpl();
        fillCategorisation(artefacts);
        fillCategoryScheme(artefacts);
        fillConceptScheme(artefacts);
        fillDataFlow(artefacts);
        fillCodeList(artefacts);
        fillDSD(artefacts);
        fillHierarchy(artefacts);
        fillMetadataFlow(artefacts);
        fillMDSD(artefacts);
        fillRepresentationMap(artefacts);
        jsonStructureWriter.write(artefacts);
        String actual = outputStream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(STRUCTURE_JSON, StandardCharsets.UTF_8);
        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("meta").withOptions(Option.IGNORING_ARRAY_ORDER));
    }

    @Test
    void writeEmptyArtefactsToJson() throws Exception {

        ArtefactsImpl artefacts = new ArtefactsImpl();
        jsonStructureWriter.write(artefacts);
        String actual = outputStream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(EMPTY_STRUCTURE_JSON, StandardCharsets.UTF_8);
        System.out.println(actual);
        System.out.println(expected);
        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("meta"));
    }

    private void fillCategoryScheme(ArtefactsImpl artefacts) {
        CategorySchemeImpl categoryScheme = new CategorySchemeImpl();
        categoryScheme.setId("CS1");
        categoryScheme.setVersion(Version.createFromString("1.0"));
        artefacts.setCategorySchemes(new HashSet<>(Set.of(categoryScheme)));
    }

    private void fillMDSD(ArtefactsImpl artefacts) {
        MetadataStructureDefinitionImpl metadataStructureDefinition = new MetadataStructureDefinitionImpl();
        metadataStructureDefinition.setId("MDSD1");
        metadataStructureDefinition.setVersion(Version.createFromString("1.0"));
        artefacts.setMetadataStructureDefinitions(new HashSet<>(Set.of(metadataStructureDefinition)));
    }

    private void fillDataFlow(ArtefactsImpl artefacts) {
        DataflowImpl dataflow = new DataflowImpl();
        dataflow.setVersion(Version.createFromString("1.0"));
        dataflow.setId("DF1");
        artefacts.setDataflows(new HashSet<>(Set.of(dataflow)));
    }

    private void fillConceptScheme(ArtefactsImpl artefacts) {
        ConceptSchemeImpl conceptScheme = new ConceptSchemeImpl();
        conceptScheme.setId("CS1");
        conceptScheme.setVersion(Version.createFromString("1.0"));
        ConceptSchemeImpl conceptSchemeSec = new ConceptSchemeImpl();
        conceptSchemeSec.setId("CS2");
        conceptSchemeSec.setVersion(Version.createFromString("1.0"));
        Set<ConceptScheme> sortedSet = new TreeSet<>(Comparator.comparing(IdentifiableArtefact::getId));
        sortedSet.add(conceptScheme);
        sortedSet.add(conceptSchemeSec);
        artefacts.setConceptSchemes(sortedSet);
    }

    private void fillCategorisation(ArtefactsImpl artefacts) {
        CategorisationImpl categorisation = new CategorisationImpl();
        IdentifiableArtefactReferenceImpl structureItemReference = new IdentifiableArtefactReferenceImpl("ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.CATEGORY,
            "CAT_TARGET"
        );

        categorisation.setCategorizedBy(structureItemReference);
        IdentifiableArtefactReferenceImpl structureReference = new IdentifiableArtefactReferenceImpl("ARTEFACT",
            "IMF",
            "1.2",
            StructureClassImpl.CATEGORISATION,
            "CAT_SOURCE"
        );
        categorisation.setCategorizedArtefact(structureReference);
        categorisation.setId("catid");
        categorisation.setVersion(Version.createFromString("1.0"));
        categorisation.setDescription(new InternationalString("categ"));
        categorisation.setOrganizationId("EPM");
        artefacts.setCategorisations(new HashSet<>(Set.of(categorisation)));
    }

    private void fillCodeList(ArtefactsImpl artefacts) {
        CodelistImpl codeList = new CodelistImpl();
        codeList.setId("CL1");
        codeList.setVersion(Version.createFromString("1.0"));
        artefacts.setCodelists(new HashSet<>(Set.of(codeList)));
    }

    private void fillDSD(ArtefactsImpl artefacts) {
        DataStructureDefinitionImpl dataStructureDefinition = new DataStructureDefinitionImpl();
        dataStructureDefinition.setId("DSD1");
        dataStructureDefinition.setVersion(Version.createFromString("1.0"));
        artefacts.setDataStructures(new HashSet<>(Set.of(dataStructureDefinition)));
    }

    private void fillHierarchy(ArtefactsImpl artefacts) {
        HierarchyImpl hierarchy = new HierarchyImpl();
        hierarchy.setId("Hi1");
        hierarchy.setVersion(Version.createFromString("1.0"));
        artefacts.setHierarchies(new HashSet<>(Set.of(hierarchy)));
    }

    private void fillMetadataFlow(ArtefactsImpl artefacts) {
        MetadataflowImpl metadataflow = new MetadataflowImpl();
        metadataflow.setId("MF1");
        metadataflow.setVersion(Version.createFromString("1.0"));
        artefacts.setMetadataflows(new HashSet<>(Set.of(metadataflow)));
    }

    private void fillRepresentationMap(ArtefactsImpl artefacts) {
        RepresentationMapImpl representationMap = new RepresentationMapImpl();
        representationMap.setId("RM1");
        representationMap.setVersion(Version.createFromString("1.0"));
        artefacts.setRepresentationMaps(new HashSet<>(Set.of(representationMap)));
    }
}
