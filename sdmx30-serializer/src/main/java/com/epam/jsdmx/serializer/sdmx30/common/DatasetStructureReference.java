package com.epam.jsdmx.serializer.sdmx30.common;


import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;

/**
 * Contains the identification of a dataset object.
 * Reference to a data structure
 */
public interface DatasetStructureReference {

    /**
     * @return Reference to a structure
     */
    ArtefactReference getDataStructureReference();

    /**
     * @return Defines the action to be taken by the recipient system
     * (information, append, replace, delete)
     */
    ActionType getAction();

    /**
     * @return The value for the 'Dimension at the Observation Level'
     */
    String getDimensionAtObservation();
}
