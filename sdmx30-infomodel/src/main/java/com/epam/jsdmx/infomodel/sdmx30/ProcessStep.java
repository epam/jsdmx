package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A specific operation, performed on data or metadata in order to validate or to derive new information according to a given set of rules.
 */
public interface ProcessStep extends IdentifiableArtefact, Copyable {
    /**
     * Association to the Process Artefact that identifies the objects which are input to the Process Step.
     */
    List<ProcessArtefact> getInputs();

    /**
     * Association to the Process Artefact that identifies the objects which are output from the Process Step.
     */
    List<ProcessArtefact> getOutputs();

    /**
     * Association to one or more Transitions.
     */
    List<Transition> getTransitions();

    /**
     * Association to child Processes that combine to form a part of this Process.
     */
    List<ProcessStep> getChildren();

    /**
     * Optional description of the computation performed by this process step.
     */
    Computation getComputation();
}
