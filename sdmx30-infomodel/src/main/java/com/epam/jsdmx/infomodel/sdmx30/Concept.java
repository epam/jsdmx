package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A unit of knowledge created by a unique combination of characteristics
 */
public interface Concept extends Item {

    /**
     * @return Collection of children. A Concept can have zero or more child Concepts,
     * thus supporting a hierarchy of Concepts.
     * Note that a child Concept can have only one parent Concept in this association.
     */
    List<? extends Concept> getHierarchy();

    /**
     * @return the association to an ISO concept reference.
     */
    IsoConceptReference getIsoConceptReference();

    /**
     * Specification of the format and value domain of the Concept when used on a structure like a DataStructureDefinition or
     * a MetadataStructureDefinition, unless the specification of the Representation is overridden in the relevant structure
     * definition. Presented by {@link Representation} interface.
     */
    Representation getCoreRepresentation();
}
