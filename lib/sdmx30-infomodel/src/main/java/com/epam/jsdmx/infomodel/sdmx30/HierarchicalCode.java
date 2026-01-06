package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.List;

/**
 * A hierarchic structure of code references.
 */
public interface HierarchicalCode extends IdentifiableArtefact {

    /**
     * @return Association to the Code that is used at the specific point in the hierarchy.
     */
    ArtefactReference getCode();

    /**
     * @return Date from which the construct is valid.
     */
    Instant getValidFrom();

    /**
     * @return Date from which construct is superseded.
     */
    Instant getValidTo();

    /**
     * @return ID of an association to a Level where levels have been defined for the Hierarchy.
     */
    String getLevelId();

    /**
     * @return Collection of associations to the Code that is used at the specific point in the
     * hierarchy.
     */
    List<HierarchicalCode> getHierarchicalCodes();
}
