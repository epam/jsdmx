package com.epam.jsdmx.infomodel.sdmx30;

/**
 * An expression in a textual or formalised way of the transformation of data between two specific operations (Processes) performed on the data.
 */
public interface Transition extends IdentifiableArtefact {
    /**
     * Local id of the transition.
     */
    String getLocalId();

    /**
     * Associates the Process Step that is the target of the Transition.
     */
    String getTargetProcessStep();

    /**
     * Associates a textual description of the Transition.
     */
    InternationalString getCondition();
}
