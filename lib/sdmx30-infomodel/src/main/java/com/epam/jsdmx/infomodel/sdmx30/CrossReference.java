package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Reference abstraction that holds information both about the referencing artefact and the artefact being referenced
 */
public interface CrossReference extends ArtefactReference {
    /**
     * Maintainable artefact which holds the reference to another structure.
     */
    MaintainableArtefact getReferencedFrom();

    /**
     * The structure that is being referenced.
     */
    ArtefactReference getReferencedTo();
}
