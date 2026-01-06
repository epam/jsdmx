package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A scheme which defines or documents the operations performed on data or metadata in order
 * to validate data or metadata to derive new information according to a given set of rules.
 */
public interface Process extends MaintainableArtefact {
    /**
     * Associates the Process Steps.
     */
    List<ProcessStep> getSteps();
}
