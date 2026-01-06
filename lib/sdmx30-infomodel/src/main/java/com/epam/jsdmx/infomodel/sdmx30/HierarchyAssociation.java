package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A HierarchyAssociation links an IdentifiableArtefact (+linkedObject), that
 * needs a Hierarchy, with the latter (+linkedHierarchy). The association is performed in a
 * certain context (+contextObject), e.g. a Dimension in the context of a Dataflow.
 */
public interface HierarchyAssociation extends MaintainableArtefact {
    /**
     * Associated hierarchy.
     *
     * @return reference to hierarchy
     */
    ArtefactReference getLinkedHierarchy();

    /**
     * Associates the Identifiable Artefact that needs the Hierarchy.
     *
     * @return reference to artefact in need.
     */
    ArtefactReference getLinkedObject();

    /**
     * The context within which the association is performed.
     *
     * @return reference to context object.
     */
    ArtefactReference getContextObject();

}
