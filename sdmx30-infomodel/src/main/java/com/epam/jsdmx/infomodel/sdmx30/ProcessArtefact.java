package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Identification of an object that is an input to or an output from a Process Step.
 */
public interface ProcessArtefact extends AnnotableArtefact {
    /**
     * Identification number of artefact.
     */
    String getLocalId();

    /**
     * Association to an Identifiable Artefact that is the input to or the output from the Process Step.
     */
    ArtefactReference getArtefact();
}
