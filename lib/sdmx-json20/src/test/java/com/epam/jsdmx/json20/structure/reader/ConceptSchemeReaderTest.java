package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.CONCEPT_SCHEMA_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ConceptSchemeReaderTest extends BaseJsonReaderTest {

    @InjectMocks
    public ConceptSchemeReader conceptSchemeReader;

    @Test
    void readConceptSchemeFromJsonTest() throws IOException {
        //given
        JsonParser parser = createParser(CONCEPT_SCHEMA_JSON);
        ConceptSchemeImpl expected = MaintainableArtifactsTestUtils.buildConceptScheme();

        //when
        ConceptSchemeImpl actual = (ConceptSchemeImpl) conceptSchemeReader.readAndClose(parser);

        //then
        assertTrue(expected.deepEquals(actual));
    }

    @Test
    void readConceptWithEnumeration() throws Exception {
        //given
        JsonParser parser = createParser("/serialization.expected/concept-scheme-enumerated-concept.json");

        //when
        ConceptScheme actual = (ConceptScheme) conceptSchemeReader.readAndClose(parser);

        //then
        assertThat(actual.getItems()).hasSize(5);
        final var multilingualFacet = new BaseFacetImpl();
        multilingualFacet.setType(FacetType.IS_MULTILINGUAL);
        multilingualFacet.setValue("true");

        assertThat(actual.getItems().get(3).getCoreRepresentation().nonEnumerated())
            .contains(multilingualFacet);
    }
}
