package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Associates an Identifable Artefact with a Category.
 */
public interface Categorisation extends MaintainableArtefact {

    @Override
    Categorisation toStub();

    @Override
    Categorisation toCompleteStub();

    /**
     * @return {@link ArtefactReference} to categorized artefact.
     */
    ArtefactReference getCategorizedArtefact();

    /**
     * @return {@link ArtefactReference} to associated category.
     */
    ArtefactReference getCategorizedBy();
}
