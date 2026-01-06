package com.epam.jsdmx.infomodel.sdmx30;

/**
 * An artefact whose components are described by a Structure.
 * In concrete terms (sub-classes) an example would be a Dataflow which is linked
 * to a given structure – in this case the Data Structure Definition.
 */
public interface StructureUsage extends MaintainableArtefact {
    /**
     * An association to a {@link Structure} specifying the structure of the artefact.
     */
    ArtefactReference getStructure();
}
