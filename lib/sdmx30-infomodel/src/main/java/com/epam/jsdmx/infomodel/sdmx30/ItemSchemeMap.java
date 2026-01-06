package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Describes how the source value maps to the target value
 */
public interface ItemSchemeMap extends MaintainableArtefact {
    /**
     * Association to a source.
     */
    ArtefactReference getSource();

    /**
     * Links source and target.
     */
    ArtefactReference getTarget();

    /**
     * List items which describes how the source value maps to the target value
     */
    List<ItemMap> getItemMaps();
}
