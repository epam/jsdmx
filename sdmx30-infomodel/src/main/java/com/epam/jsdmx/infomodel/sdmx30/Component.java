package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A Component is an abstract super class used to define qualitative and quantitative
 * data and metadata items that belong to a Component List and hence a Structure.
 * Each Component takes its semantic (and possibly also its representation) from a Concept in
 * a ConceptScheme. This is represented by the conceptIdentity association to Concept.
 * The Component may also have a localRepresentation.
 */
public interface Component extends IdentifiableArtefact, Copyable {

    /**
     * Association to a Concept in a Concept Scheme that identifies and defines the
     * semantic of the Component
     *
     * @return The reference of an artefact
     */
    ArtefactReference getConceptIdentity();

    /**
     * Association to the Representation of the Component if this is
     * different from the coreRepresentation of the Concept, which the
     * Component uses(ConceptUsage).
     *
     * @return The allowable value or format for Component or Concept
     */
    Representation getLocalRepresentation();
}
