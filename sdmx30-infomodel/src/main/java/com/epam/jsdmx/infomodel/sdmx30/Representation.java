package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

/**
 * The allowable value or format for Component or Concept.
 */
public interface Representation extends Copyable {
    /**
     * @return true if the representation is Enumerated, and false otherwise
     */
    boolean isEnumerated();

    /**
     * Association to an enumerated list that contains the allowable content for the Component when reported in a data or metadata set.
     * The type of enumerated list that is allowed for any concrete Component is shown in the constraints on the association.
     */
    ArtefactReference enumerated();

    /**
     * Association to a set of {@link Facet} that define the allowable format for the content of the Component when reported in a data or metadata set.
     */
    Set<Facet> nonEnumerated();
}
