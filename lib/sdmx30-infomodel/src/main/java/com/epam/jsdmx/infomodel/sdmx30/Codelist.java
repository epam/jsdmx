package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A list from which some statistical concepts (coded
 * concepts) take their values.
 */
public interface Codelist extends ItemScheme<Code> {
    @Override
    Codelist toStub();

    @Override
    Codelist toCompleteStub();

    /**
     * @return List of {@link CodelistExtension} association that helps
     * {@link Codelist} extend other Codelists
     */
    List<? extends CodelistExtension> getExtensions();
}
