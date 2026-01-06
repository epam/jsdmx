package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines which data is present or which content is allowed for the constraint attachment
 */
public interface Constraint extends MaintainableArtefact {
    /**
     * Describes the collection of constrainable artefacts that the constraint is attached to.
     */
    List<ArtefactReference> getConstrainedArtefacts();

    /**
     * Defines a list of roles for a content constraint.
     * A constraint can state which data is present or which content is allowed for the constraint attachment.
     */
    ConstraintRoleType getConstraintRoleType();
}
