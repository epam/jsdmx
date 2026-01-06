package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

/**
 * The IdentifiableArtefact is an abstract class that comprises the basic attributes needed
 * for identification. Concrete classes based on IdentifiableArtefact all inherit the ability to
 * be uniquely identified.
 */
public interface IdentifiableArtefact extends AnnotableArtefact {
    /**
     * The unique identifier of the object.
     */
    String getId();

    /**
     * Universal resource name – this is for use in registries: all registered objects have a urn.
     */
    String getUrn();

    /**
     * {@link StructureClass} enumeration which represents structure classes defined in SDMX-IM
     */
    StructureClass getStructureClass();

    /**
     * Universal resource identifier that  may or may not be resolvable.
     */
    URI getUri();

    /**
     * Reference to the parent of this object. null if this is a maintainable artefact.
     */
    ArtefactReference getContainer();
}
