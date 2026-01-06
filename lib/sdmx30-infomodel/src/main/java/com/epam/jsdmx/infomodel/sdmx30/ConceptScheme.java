package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The descriptive information for an arrangement or division of concepts
 * into groups based on characteristics, which the objects have in common.
 */
public interface ConceptScheme extends ItemScheme<Concept> {
    @Override
    ConceptScheme toStub();

    @Override
    ConceptScheme toCompleteStub();
}
