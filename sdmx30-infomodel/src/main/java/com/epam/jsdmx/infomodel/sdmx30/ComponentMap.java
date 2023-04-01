package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Maps one or more Components from the source DataStructureDefinition to one or more
 * Components in the target DataStructureDefinition
 */
public interface ComponentMap extends AnnotableArtefact {

    /**
     * @return Collection of associations to zero or more source components.
     */
    List<String> getSource();

    /**
     * @return Collection of associations to zero or more target components.
     */
    List<String> getTarget();

    /**
     * @return The reference to the {@link RepresentationMap}
     */
    ArtefactReference getRepresentationMap();
}
