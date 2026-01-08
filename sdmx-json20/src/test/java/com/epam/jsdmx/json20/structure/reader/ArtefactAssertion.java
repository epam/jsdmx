package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

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

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArtefactAssertion {

    private Artefacts artefacts;

    public static ArtefactAssertion assertThat(Artefacts artefact) {
        return new ArtefactAssertion(artefact);
    }

    ArtefactAssertion hasCategorisation(Set<Categorisation> categorisations) {
        assertEquals(artefacts.getCategorisations(), categorisations);
        return this;
    }

    ArtefactAssertion hasCategoryScheme(Set<CategoryScheme> categorySchemes) {
        assertEquals(artefacts.getCategorySchemes(), categorySchemes);
        return this;
    }

    ArtefactAssertion hasCodeLists(Set<Codelist> codelists) {
        assertEquals(artefacts.getCodelists(), codelists);
        return this;
    }

    ArtefactAssertion hasConceptSchemes(Set<ConceptScheme> conceptSchemes) {
        assertEquals(artefacts.getConceptSchemes(), conceptSchemes);
        return this;
    }

    ArtefactAssertion hasHierarchies(Set<Hierarchy> hierarchies) {
        assertEquals(artefacts.getHierarchies(), hierarchies);
        return this;
    }

    ArtefactAssertion hasDataFlows(Set<Dataflow> dataflows) {
        assertEquals(artefacts.getDataflows(), dataflows);
        return this;
    }

    ArtefactAssertion hasMetadataFlows(Set<Metadataflow> metadataflows) {
        assertEquals(artefacts.getMetadataflows(), metadataflows);
        return this;
    }

    ArtefactAssertion hasDSD(Set<DataStructureDefinition> dataStructureDefinitions) {
        assertEquals(artefacts.getDataStructures(), dataStructureDefinitions);
        return this;
    }

    ArtefactAssertion hasMDSD(Set<MetadataStructureDefinition> metadataStructureDefinitions) {
        assertEquals(artefacts.getMetadataStructureDefinitions(), metadataStructureDefinitions);
        return this;
    }

    ArtefactAssertion hasRepresentationMaps(Set<RepresentationMap> representationMaps) {
        assertEquals(artefacts.getRepresentationMaps(), representationMaps);
        return this;
    }

    ArtefactAssertion hasStructureMaps(Set<StructureMap> structureMaps) {
        assertEquals(artefacts.getStructureMaps(), structureMaps);
        return this;
    }

    ArtefactAssertion hasDataConstraint(Set<DataConstraint> dataConstraintMaps) {
        assertEquals(artefacts.getDataConstraints(), dataConstraintMaps);
        return this;
    }
}
